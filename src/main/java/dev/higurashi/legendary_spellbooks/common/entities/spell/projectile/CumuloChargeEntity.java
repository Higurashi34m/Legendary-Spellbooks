package dev.higurashi.legendary_spellbooks.common.entities.spell.projectile;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CumuloChargeEntity extends Mob implements AntiMagicSusceptible {
    private static final EntityDataAccessor<Boolean> DATA_HAS_HIT = SynchedEntityData.defineId(CumuloChargeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(CumuloChargeEntity.class, EntityDataSerializers.INT);

    private static final double HORIZONTAL_SPEED = 0.8;
    private static final double FALL_SPEED = -0.1;
    private static final double HOMING_STRENGTH = 0.1;
    private static final double COLLISION_INFLATE = 0.8;
    private static final double DAMAGE_RADIUS = 4.0;
    private static final int POST_HIT_DURATION = 15;
    private static final int END_ANIM_DURATION = 20;

    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState endChargeState = new AnimationState();
    public final AnimationState aendChargeState = new AnimationState();

    private LivingEntity summoner;
    private LivingEntity target;
    private float damage = 20.0f;

    public CumuloChargeEntity(EntityType<CumuloChargeEntity> type, Level level) {
        super(type, level);
        this.noPhysics = false;
        this.setInvulnerable(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_HAS_HIT, false);
        builder.define(DATA_DURATION, 40);
    }

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        level().playSound(null, getX(), getY(), getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, getSoundSource(), 2.0f, 0.7f);
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide) {
            this.handleClientSideTick();
            return;
        }

        this.handleServerSideTick();
    }

    private void handleServerSideTick() {
        int timeLeft = this.entityData.get(DATA_DURATION) - 1;
        this.entityData.set(DATA_DURATION, timeLeft);

        if (timeLeft <= 0) {
            this.discard();
            return;
        }

        if (this.entityData.get(DATA_HAS_HIT)) {
            this.setDeltaMovement(Vec3.ZERO);
            return;
        }

        updateRushMovement();
        checkCollisions();
    }

    private void updateRushMovement() {
        float yawRad = (float) Math.toRadians(getYRot());
        Vec3 direction = new Vec3(-Math.sin(yawRad), 0, Math.cos(yawRad));

        if (target != null && target.isAlive()) {
            Vec3 toTarget = new Vec3(target.getX() - getX(), 0, target.getZ() - getZ()).normalize();
            direction = direction.lerp(toTarget, HOMING_STRENGTH).normalize();
            setYRot((float) (Math.atan2(direction.z, direction.x) * (180 / Math.PI)) - 90);
        }

        double verticalVel = this.onGround() ? 0 : FALL_SPEED;
        Vec3 velocity = new Vec3(direction.x * HORIZONTAL_SPEED, verticalVel, direction.z * HORIZONTAL_SPEED);

        double horizontalDist = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
        setXRot((float) (Math.atan2(-verticalVel, horizontalDist) * (180 / Math.PI)));

        setDeltaMovement(velocity);
        move(MoverType.SELF, getDeltaMovement());
    }

    private void checkCollisions() {
        List<LivingEntity> targets = level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(COLLISION_INFLATE));
        for (LivingEntity target : targets) {
            if (isValidTarget(target)) {
                performHit(target);
                break;
            }
        }
    }

    private boolean isValidTarget(LivingEntity target) {
        return target != this && !this.isAlliedTo(target) && target.isAlive() && !target.isInvulnerable();
    }

    private void performHit(LivingEntity target) {
        this.entityData.set(DATA_HAS_HIT, true);
        this.entityData.set(DATA_DURATION, POST_HIT_DURATION);
        this.setDeltaMovement(Vec3.ZERO);

        List<LivingEntity> nearby = level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(DAMAGE_RADIUS));
        for (LivingEntity nearbyTarget : nearby) {
            if (isValidTarget(nearbyTarget) && nearbyTarget.distanceTo(this) <= DAMAGE_RADIUS) {
                nearbyTarget.hurt(damageSources().mobAttack(summoner != null ? summoner : this), damage);
                nearbyTarget.addEffect(new MobEffectInstance(ModEffects.STUN, 20));

                Vec3 knockback = this.getDeltaMovement().multiply(1.5, 1.0, 1.5).add(0, 0.4, 0);
                nearbyTarget.setDeltaMovement(nearbyTarget.getDeltaMovement().add(knockback));
            }
        }

        level().playSound(null, getX(), getY(), getZ(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 2.0f, 1.0f);
        this.spawnRingParticlesOnHit();
    }

    private void handleClientSideTick() {
        this.handleAnimations();
        this.spawnCloudParticles();
        if (!this.entityData.get(DATA_HAS_HIT) && this.entityData.get(DATA_DURATION) % 2 == 0) {
            this.spawnRingParticlesMoving();
        }
    }

    private void handleAnimations() {
        boolean hasHit = this.entityData.get(DATA_HAS_HIT);
        int duration = this.entityData.get(DATA_DURATION);

        if (hasHit) {
            startAnimationIfNotPlaying(aendChargeState);
        } else if (duration <= END_ANIM_DURATION) {
            startAnimationIfNotPlaying(endChargeState);
        } else {
            startAnimationIfNotPlaying(chargeAnimationState);
        }
    }

    private void startAnimationIfNotPlaying(AnimationState state) {
        if (!state.isStarted()) {
            stopAllAnimations();
            state.start(tickCount);
        }
    }

    private void stopAllAnimations() {
        chargeAnimationState.stop();
        endChargeState.stop();
        aendChargeState.stop();
    }

    private void spawnRingParticlesMoving() {
        float ringAngle = (float) Math.toRadians(-getYRot() + 180F);
        Vec3 pos = position().add(getForward().scale(1.5));
        level().addParticle(new Circle.RingData(ringAngle, 0.0F, 30, 1.0F, 1.0F, 1.0F, 0.5F, 40.0F, false, Circle.EnumRingBehavior.GROW_THEN_SHRINK),
                pos.x, getY() + 1.0, pos.z, 0, 0, 0);
    }

    private void spawnCloudParticles() {
        for (int i = 0; i < 3; i++) {
            level().addParticle(ParticleTypes.CLOUD, getRandomX(1.0), getY() + 0.5, getRandomZ(1.0), 0, 0.05, 0);
        }
    }

    private void spawnRingParticlesOnHit() {
        if (!level().isClientSide) return;
        for (int i = 0; i < 20; i++) {
            double angle = i * Math.PI * 2 / 20;
            level().addParticle(ParticleTypes.ELECTRIC_SPARK, getX() + Math.cos(angle) * 2.0, getY() + 1, getZ() + Math.sin(angle) * 2.0, 0, 0.1, 0);
        }
    }

    public AnimationState getAnimationState(String key) {
        return switch (key) {
            case "charge" -> this.chargeAnimationState;
            case "endcharge" -> this.endChargeState;
            case "aendcharge" -> this.aendChargeState;
            default -> this.chargeAnimationState;
        };
    }

    public void setSummoner(LivingEntity summoner) { this.summoner = summoner; }
    public void setTarget(LivingEntity target) { this.target = target; }
    public void setDamage(float damage) { this.damage = damage; }
    public void setDuration(int duration) { this.entityData.set(DATA_DURATION, duration); }

    @Override public boolean isAlliedTo(@NotNull Entity target) {
        if (target == this) return true;
        if (summoner != null && (target == summoner || summoner.isAlliedTo(target))) return true;
        return false;
    }

    @Override public boolean hurt(@NotNull DamageSource source, float amount) { return false; }
    @Override public boolean isPushable() { return false; }
    @Override protected void doPush(@NotNull Entity entity) {}
    @Override protected void pushEntities() {}
    @Override public boolean isPickable() { return false; }
    @Override public boolean isAttackable() { return false; }
    @Override public boolean canBeSeenAsEnemy() { return false; }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE).add(Attributes.MAX_HEALTH, 1.0F);
    }

    @Override
    public void onAntiMagic(MagicData magicData) {
        this.discard();
    }
}