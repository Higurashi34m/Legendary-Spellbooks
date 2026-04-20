package dev.higurashi.legendary_spellbooks.common.entities.spell.projectile;

import dev.higurashi.legendary_spellbooks.api.entities.helper.IWarmupEntity;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.PoisonousShockwave;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SpellPoisonousShockwaveEntity extends PoisonousShockwave implements IWarmupEntity {
    private static final EntityDataAccessor<Integer> WARMUP = SynchedEntityData.defineId(SpellPoisonousShockwaveEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LIFE_TICKS = SynchedEntityData.defineId(SpellPoisonousShockwaveEntity.class, EntityDataSerializers.INT);

    private float damage;

    public SpellPoisonousShockwaveEntity(EntityType<? extends PoisonousShockwave> type, Level level) {
        super(type, level);
    }

    public SpellPoisonousShockwaveEntity(Level level, double x, double y, double z, float yaw, int warmup, LivingEntity caster, int lifeTicks, float radius, float damage) {
        this(LSEntityRegistry.SPELL_POISONOUS_SHOCKWAVE_ENTITY.get(), level);
        this.setWarmup(warmup);
        this.setCaster(caster);
        this.setYRot(yaw * (180 / (float) Math.PI));
        this.setPos(x, y, z);
        this.setDamage(damage);
        this.setLifeTicks(lifeTicks);
        this.setRadius(radius);
    }

    public void tick() {
        super.baseTick();

        this.warmupTick();
        if (getWarmup() > 0) return;

        if (this.level().isClientSide) {
            for(int i = 0; i < 20; ++i) {
                double x = this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0 - this.getBbWidth();
                double y = this.getY() + 0.5;
                double z = this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0 - this.getBbWidth();
                int k = this.getColor();
                double d5 = (k >> 16 & 255) / 255.0;
                double d6 = (k >> 8 & 255) / 255.0;
                double d7 = (k & 255) / 255.0;
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, x, y, z, d5, d6, d7);
            }
        }

        for(LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2))) {
            this.applyDamage(target, damage);
        }

        if (this.getLifeTicks() == 32 && !this.isSilent()) {
            this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), ModSounds.ICE_SPIKE_EMERGE.get(), this.getSoundSource(), 0.5f, this.random.nextFloat() * 0.2f + 0.85f, false);
        }

        if (this.getAttackState() == 0) this.setAttackState(1);


        this.setLifeTicks(getLifeTicks() - 1);
        if (getLifeTicks() < 0) this.discard();
    }

    private void applyDamage(LivingEntity target, float damage) {
        LivingEntity caster = this.getCaster();
        if (caster == null) return;

        if (target.isAlive() && !target.isInvulnerable() && target != caster && !caster.isAlliedTo(target) && this.tickCount % 5 == 0) {
            AbstractSpell spell = LSSpellRegistry.OVERGROWN_SHOCKWAVE_SPELL.get();

            DamageSources.applyDamage(target, damage, spell.getDamageSource(this, caster));
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 2));
        }
    }

    @Override public void onWarmupTick() {}
    @Override public void onWarmupFinished() { this.playSound(SoundEvents.FIRE_EXTINGUISH, 1.0f, 1.0f); }

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

    @Override public void setLifeTicks(int ticks) { this.entityData.set(LIFE_TICKS, ticks); }
    @Override public int getLifeTicks() { return this.entityData.get(LIFE_TICKS); }

    public void setDamage(float damage) { this.damage = damage; }
}
