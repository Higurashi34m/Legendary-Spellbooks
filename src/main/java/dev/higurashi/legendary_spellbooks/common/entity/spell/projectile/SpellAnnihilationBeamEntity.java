package dev.higurashi.legendary_spellbooks.common.entity.spell.projectile;

import dev.higurashi.legendary_spellbooks.api.entities.helper.IWarmupEntity;
import dev.higurashi.legendary_spellbooks.registry.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registry.LSSpellRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.LightningParticle;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpellAnnihilationBeamEntity extends Entity implements IWarmupEntity, AntiMagicSusceptible {
    private static final EntityDataAccessor<Integer> MAX_RANGE = SynchedEntityData.defineId(SpellAnnihilationBeamEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> WARMUP    = SynchedEntityData.defineId(SpellAnnihilationBeamEntity.class, EntityDataSerializers.INT);
    private final List<SpellAnnihilationExplosionEntity> spawnedExplosions = new ArrayList<>();
    private final List<SpellSmallAnnihilationBombEntity> spawnedBullets = new ArrayList<>();

    public double collidePosX;
    public double collidePosY;
    public double collidePosZ;
    public Direction blockSide = null;

    private LivingEntity caster;
    private float damage;
    private float hpDamage;

    private int lifeTick;

    public SpellAnnihilationBeamEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noCulling = true;
        this.noPhysics = true;
    }

    public SpellAnnihilationBeamEntity(Level level, LivingEntity caster, Vec3 spawnPos, float damage, float hpDamage, int range, int lifeTick, int warmupTick) {
        this(LSEntityRegistry.SPELL_ANNIHILATION_BEAM.get(), level);

        this.caster = caster;
        this.damage = damage;
        this.hpDamage = hpDamage;
        this.lifeTick = lifeTick;

        this.setPos(spawnPos);
        this.setXRot(caster.xRotO);
        this.setYRot(caster.yRotO);

        if (!this.level().isClientSide()) {
            this.setWarmup(warmupTick);
            this.setMaxRange(range);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.warmupTick();
        if (this.getWarmup() > 0) return;

        Vec3 start = this.position();
        Vec3 end = new Vec3(this.collidePosX, this.collidePosY, this.collidePosZ);

        float length = (float) start.distanceTo(end);
        int divisions = (int) (length / 5);

        if (this.level().isClientSide()) {
            this.spawnExplosionParticle();
        } else {
            this.hurtEntities(start, end);
            this.cameraShake(start, end, divisions);

            this.lifeTick--;
            if (lifeTick <= 0) this.discard();
        }
    }

    @Override public void onWarmupTick() {}

    @Override
    public void onWarmupFinished() {
        this.calculateBeamPos();

        Vec3 start = this.position();
        Vec3 end = new Vec3(this.collidePosX, this.collidePosY, this.collidePosZ);

        float length = (float) start.distanceTo(end);
        int divisions = (int) (length / 5);

        this.spawnExplosions(start, end, divisions);
    }

    public void calculateBeamPos() {
        Vec3 direction = Vec3.directionFromRotation(this.getXRot(), this.getYRot());

        Vec3 start = this.position();
        Vec3 end = start.add(direction.scale(this.getMaxRange()));

        BlockHitResult hitResult = this.level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

        if (hitResult.getType() == BlockHitResult.Type.BLOCK) {
            Vec3 hitPos = hitResult.getLocation();
            this.collidePosX = hitPos.x;
            this.collidePosY = hitPos.y;
            this.collidePosZ = hitPos.z;
            this.blockSide = hitResult.getDirection();
        } else {
            this.collidePosX = end.x;
            this.collidePosY = end.y;
            this.collidePosZ = end.z;
            this.blockSide = null;
        }
    }

    private void hurtEntities(Vec3 start, Vec3 end) {
        if (this.caster == null) return;

        AABB boundingBox = new AABB(start, end).inflate(1.0);

        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, boundingBox, target -> target != this.caster && target.isAlive() && !target.isAlliedTo(this.caster) && !this.caster.isAlliedTo(target) && !target.isInvulnerable());
        targets.forEach(target -> {
            AABB targetBox = target.getBoundingBox().inflate(0.5);
            Optional<Vec3> hitResult = targetBox.clip(start, end);

            if (hitResult.isPresent() || targetBox.contains(start)) {
                float damage = this.damage + target.getMaxHealth() * this.hpDamage;

                AbstractSpell spell = LSSpellRegistry.ANNIHILATION_BEAM_SPELL.get();
                if (DamageSources.applyDamage(target, damage, spell.getDamageSource(this, this.caster))) {
                    target.addEffect(new MobEffectInstance(ModEffects.ANNIHILATION.get(), 60));
                    this.spawnHitParticle(target);
                    target.setSecondsOnFire(2);
                }
            }
        });
    }

    private void cameraShake(Vec3 start, Vec3 end, int divisions) {
        if (this.tickCount % 5 != 0) return;

        Vec3 beamVector = end.subtract(start);

        for (int i = 0; i < divisions; i++) {
            Vec3 spawnPoint = start.add(beamVector.scale((double) i / divisions));
            CameraShakeEntity.cameraShake(this.level(), spawnPoint, 5, 0.06f, 5, 5);
        }

        if (divisions == 0 || !start.add(beamVector.scale((double) (divisions - 1) / divisions)).closerThan(end, 1.0)) {
            CameraShakeEntity.cameraShake(this.level(), end, 5, 0.06f, 5, 5);
        }
    }

    private void spawnExplosions(Vec3 start, Vec3 end, int divisions) {
        Vec3 beamVector = end.subtract(start);
        Vec3 direction = beamVector.normalize();

        if (Math.abs(direction.y) < 0.25) {
            if (divisions <= 0) {
                this.spawnExplosion(start.add(beamVector.scale(0.5)));
                return;
            }
            for (int i = 0; i <= divisions; i++) {
                double pct = (double) i / divisions;
                Vec3 checkPoint = start.add(beamVector.scale(pct));

                this.spawnExplosion(checkPoint);
            }
        }
    }

    private void spawnExplosion(Vec3 spawnPoint) {
        Vec3 endRay = spawnPoint.add(0.0, -3.0, 0.0);

        BlockHitResult result = this.level().clip(new ClipContext(spawnPoint, endRay, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (result.getType() == BlockHitResult.Type.BLOCK) {
            Vec3 hitPos = result.getLocation();

            SpellAnnihilationExplosionEntity explosion = new SpellAnnihilationExplosionEntity(this.level(), this.caster, this.damage, this.hpDamage, 2, (int) (this.lifeTick * 0.5));
            explosion.setPos(hitPos);
            explosion.setYRot(this.getYRot());
            explosion.setParentBeam(this);
            this.level().addFreshEntity(explosion);
            this.spawnedExplosions.add(explosion);
        }
    }

    private void spawnExplosionParticle() {
        if (this.blockSide == null) return;

        float yaw = (float) ((this.random.nextFloat() * 2.0f) * Math.PI);
        float motionX = 0.5f * Mth.cos(yaw);
        float motionY = this.random.nextFloat() * 0.4f;
        float motionZ = 0.5f * Mth.sin(yaw);

        this.level().addParticle(ModParticles.ANNIHILATION_EXPLOSION.get(), this.collidePosX, this.collidePosY, this.collidePosZ, motionX, motionY, motionZ);
    }

    private void spawnHitParticle(LivingEntity target) {
        ServerLevel level = (ServerLevel) this.level();
        RandomSource random = target.getRandom();
        LightningParticle.OrbData lightning = new LightningParticle.OrbData(25, 255, 0);
        level.sendParticles(lightning, target.getRandomX(0.5), target.getY() + target.getBbHeight() / 2, target.getRandomZ(0.5), 15 + random.nextInt(20), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0.5);
    }

    @Override
    public void onAntiMagic(MagicData magicData) {
        for (SpellAnnihilationExplosionEntity explosion : this.spawnedExplosions) {
            if (explosion != null && explosion.isAlive()) {
                explosion.onAntiMagic(magicData);
            }
        }
        this.spawnedExplosions.clear();

        for (SpellSmallAnnihilationBombEntity bullet : this.spawnedBullets) {
            if (bullet != null && bullet.isAlive()) {
                bullet.discard();
            }
        }
        this.spawnedBullets.clear();

        this.discard();
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(MAX_RANGE, 30);
        this.entityData.define(WARMUP, 0);
    }

    @Override protected void readAdditionalSaveData(@NotNull CompoundTag tag) {}
    @Override protected void addAdditionalSaveData(@NotNull CompoundTag tag) {}

    // Getter / Setter
    public int getMaxRange()           { return this.entityData.get(MAX_RANGE); }
    public void setMaxRange(int range) { this.entityData.set(MAX_RANGE, range); }

    @Override public int getWarmup()           { return entityData.get(WARMUP); }
    @Override public void setWarmup(int ticks) { this.entityData.set(WARMUP, ticks); }

    public void trackBullet(SpellSmallAnnihilationBombEntity bullet) {
        if (!this.level().isClientSide()) {
            this.spawnedBullets.add(bullet);
        }
    }
}
