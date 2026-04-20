package dev.higurashi.legendary_spellbooks.common.entities.spell.projectile;

import dev.higurashi.legendary_spellbooks.api.entities.helper.IWarmupEntity;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.FireColumnEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SpellFireColumnEntity extends FireColumnEntity implements IWarmupEntity {
    private static final EntityDataAccessor<Integer> WARMUP = SynchedEntityData.defineId(SpellFireColumnEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LIFE_TICKS = SynchedEntityData.defineId(SpellFireColumnEntity.class, EntityDataSerializers.INT);

    private float damage;

    public SpellFireColumnEntity(EntityType<? extends FireColumnEntity> type, Level level) {
        super(type, level);
    }

    public SpellFireColumnEntity(Level level, double x, double y, double z, float yaw, float damage, int warmupTick, int lifeTicks, LivingEntity caster) {
        this(LSEntityRegistry.SPELL_FIRE_COLUMN_ENTITY.get(), level);
        this.setLifeTicks(lifeTicks);
        this.setWarmup(warmupTick);
        this.setCaster(caster);
        this.setYRot(yaw * (180.0f / (float) Math.PI));
        this.setPos(x, y, z);
        this.damage = damage;
    }

    @Override
    public void tick() {
        super.baseTick();

        this.warmupTick();
        if (getWarmup() >= 0) return;

        if (this.getAttackState() == 0) this.setAttackState(1);

        if (this.level().isClientSide()) spawnParticles();
        else for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.0, 2.0, 1.0))) applyDamage(target, damage);

        this.setLifeTicks(getLifeTicks() - 1);
        if (getLifeTicks() < 0) this.discard();
    }

    private void applyDamage(LivingEntity target, float damage) {
        LivingEntity caster = this.getCaster();
        if (caster == null) return;

        if (target.isAlive() && !target.isInvulnerable() && target != caster && !caster.isAlliedTo(target) && this.tickCount % 5 == 0) {
            AbstractSpell spell = LSSpellRegistry.FLAME_EATER_SPELL.get();

            DamageSources.applyDamage(target, damage, spell.getDamageSource(this, caster));
            target.setRemainingFireTicks(80);
        }
    }

    private void spawnParticles() {
        if (this.tickCount % 5 == 0 && this.random.nextFloat() < 0.5f) {
            double x = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * this.getBbWidth();
            double y = this.getY() + 0.5 + this.random.nextDouble() * this.getBbHeight();
            double z = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * this.getBbWidth();
            this.level().addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
        }

        this.level().addParticle(ModParticles.FLAME.get(), this.getX(), this.getY(), this.getZ(), 0.0, 1.0, 0.0);
    }

    // Warmup
    @Override
    public void onWarmupTick() {
        this.setActivate(false);
    }

    @Override
    public void onWarmupFinished() {
        this.setActivate(true);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.saveWarmupData(tag);
        tag.putFloat("Damage", damage);
        tag.putInt("LifeTicks", getLifeTicks());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.loadWarmupData(tag);
        setDamage(tag.getFloat("Damage"));
        setLifeTicks(tag.getInt("LifeTicks"));
    }

    // Client Sync
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WARMUP, 0);
        this.entityData.define(LIFE_TICKS, 100);
    }

    @Override public void setWarmup(int ticks) { this.entityData.set(WARMUP, ticks); }
    @Override public int getWarmup() { return this.entityData.get(WARMUP); }

    private void setLifeTicks(int ticks) { this.entityData.set(LIFE_TICKS, ticks); }
    private int getLifeTicks() { return this.entityData.get(LIFE_TICKS); }

    public void setDamage(float damage) { this.damage = damage; }
}
