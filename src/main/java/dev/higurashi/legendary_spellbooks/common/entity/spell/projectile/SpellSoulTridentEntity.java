package dev.higurashi.legendary_spellbooks.common.entity.spell.projectile;

import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SoulPillarExplosionEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SoulTridentEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class SpellSoulTridentEntity extends SoulTridentEntity {
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(SpellSoulTridentEntity.class, EntityDataSerializers.FLOAT);

    public SpellSoulTridentEntity(EntityType<? extends SoulTridentEntity> type, Level level) {
        super(type, level);
    }

    public SpellSoulTridentEntity(Level level, LivingEntity caster, float damage) {
        super(LSEntityRegistry.SPELL_SOUL_TRIDENT_ENTITY.get(), level);
        this.setOwner(caster);
        this.setDamage(damage);
    }

    @Override
    public boolean isAlliedTo(Entity target) {
        if (getOwner() == null) return false;
        return target.isAlliedTo(getOwner()) || getOwner().isAlliedTo(target);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount >= 400 && !this.level().isClientSide) {
            this.spawnSoulPillarExplosions(this.getX(), this.getY(), this.getZ(), 0, this.level());
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        Entity caster = this.getOwner();
        float damage = this.getDamage();

        if (!(target instanceof LivingEntity livingTarget)) return;
        if (this.isAlliedTo(livingTarget)) return;

        this.playSound(SoundEvents.TRIDENT_HIT, 1.0f, 1.0f);
        DamageSources.applyDamage(livingTarget, damage, LSSpellRegistry.HEMATITE_TRISHULA_SPELL.get().getDamageSource(caster));
        this.discard();
    }

    @Override
    public void spawnSpiralStrike(double maxRadius, double angleStep, double radiusGrowthFactor, int baseDelayTicks, double delayMultiplier) {
        if (level().isClientSide) return;
        Vec3 centerPos = this.position();

        for (double i = 0.0; i < maxRadius; i += angleStep) {
            int warmupDelay = baseDelayTicks + (int) (i * delayMultiplier);
            double currentRadius = i * radiusGrowthFactor;

            float angleInDegrees = (float) Math.toDegrees(i);
            Vec3 spawnPos = GeometryUtils.getPointAtAngle(centerPos, angleInDegrees, currentRadius);

            this.spawnSoulPillarExplosions(spawnPos.x, spawnPos.y, spawnPos.z, warmupDelay, this.level());
        }
    }

    private void spawnSoulPillarExplosions(double x, double y, double z, int warmup, Level level) {
        BlockPos blockpos = BlockPos.containing(x, y, z);

        Vec3 groundPos = RaycastUtils.findGround(level, blockpos.getCenter(), 5, 2);
        if (groundPos == null) return;

        level.addFreshEntity(new SoulPillarExplosionEntity(level, groundPos.x, groundPos.y, groundPos.z, 0.0f, warmup, (LivingEntity) this.getOwner(), 20, getDamage() / 2.0f, true));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", getDamage());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setDamage(tag.getFloat("Damage"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DAMAGE, 0.0f);
    }

    @Override public void playerTouch(Player player) {}
    @Override protected boolean tryPickup(Player player) { return false; }

    public void setDamage(float damage) { this.entityData.set(DAMAGE, damage); }
    public float getDamage() { return this.entityData.get(DAMAGE); }
}
