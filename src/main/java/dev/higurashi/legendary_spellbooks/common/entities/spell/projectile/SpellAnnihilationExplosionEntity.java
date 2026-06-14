package dev.higurashi.legendary_spellbooks.common.entities.spell.projectile;

import dev.higurashi.legendary_spellbooks.api.entities.helper.IWarmupEntity;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.damagetype.ModDamageTypes;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpellAnnihilationExplosionEntity extends Entity implements AntiMagicSusceptible, IWarmupEntity {
    private static final EntityDataAccessor<Integer> WARMUP    = SynchedEntityData.defineId(SpellAnnihilationExplosionEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LIFE_TICK = SynchedEntityData.defineId(SpellAnnihilationExplosionEntity.class, EntityDataSerializers.INT);
    private final List<SpellSmallAnnihilationBombEntity> spawnedBullets = new ArrayList<>();
    private SpellAnnihilationBeamEntity parentBeam;

    private LivingEntity caster;
    private float damage;
    private float hpDamage;
    private int bulletAmount;

    public SpellAnnihilationExplosionEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public SpellAnnihilationExplosionEntity(Level level, LivingEntity caster, float damage, float hpDamage, int bulletAmount, int warmupTick) {
        this(LSEntityRegistry.SPELL_ANNIHILATION_EXPLOSION.get(), level);
        this.caster = caster;
        this.damage = damage;
        this.hpDamage = hpDamage;
        this.bulletAmount = bulletAmount;

        if (!this.level().isClientSide) {
            this.setLifeTick(20);
            this.setWarmup(warmupTick);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.warmupTick();
        if (getWarmup() > 0) return;

        int lifeTick = this.getLifeTick();
        if (this.level().isClientSide) {
            if (lifeTick == 20) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.BLAZE_SHOOT, this.getSoundSource(), 0.45f, 1.25f, true);
            } else if (lifeTick == 15) {
                this.level().addParticle(ModParticles.ANNIHILATION_EXPLOSION.get(), this.getX(), this.getY() + 1.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        } else {
            if (lifeTick == 15) {
                this.applyDamage();
            } else if (lifeTick == 12) {
                this.shootBullet(this.damage / 2);
            } else if (lifeTick <= 0) {
                this.discard();
            }
            this.setLifeTick(this.getLifeTick() - 1);
        }
    }

    @Override public void onWarmupTick() {}

    @Override
    public void onWarmupFinished() {
        if (this.level().isClientSide) {
            Circle.RingData ringData = new Circle.RingData(0.0f, (float) Math.toRadians(90.0), 25, 0.0f, 1.0f, 0.0f, 1.0f, 14.0f, false, Circle.EnumRingBehavior.SHRINK);
            this.level().addParticle(ringData, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    private void applyDamage() {
        if (this.caster == null) return;

        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox(), target -> target != this.caster && !target.isAlliedTo(this.caster) && !this.caster.isAlliedTo(target) && !target.isInvulnerable() && target.isAlive());
        targets.forEach(target -> {
            float damage = this.damage + target.getMaxHealth() * this.hpDamage;

            if (DamageSources.applyDamage(target, damage, ModDamageTypes.causeAnnihilationDamage(this, this.caster))) {
                target.setRemainingFireTicks(40);
                target.addEffect(new MobEffectInstance(ModEffects.ANNIHILATION, 40));
            }
        });
    }

    private void shootBullet(float damage) {
        if (this.caster == null) return;

        RandomSource random = this.caster.getRandom();
        float angleStep = (float) (Math.PI * 2.0 / this.bulletAmount);
        float baseYaw = (float) Math.toRadians(this.getYRot());

        for (int i = 0; i < this.bulletAmount; i++) {
            float angle = baseYaw + (i * angleStep);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);

            Vec3 spawnPos = new Vec3(this.getX() + cos, this.getY() + 1.5, this.getZ() + sin);
            SpellSmallAnnihilationBombEntity bullet = new SpellSmallAnnihilationBombEntity(this.level(), spawnPos, this.caster, damage, this.hpDamage);
            bullet.shoot(cos, 2.0 + (random.nextFloat() * 0.3), sin, 0.5f, 1.0f);
            this.level().addFreshEntity(bullet);
            this.spawnedBullets.add(bullet);
            if (this.parentBeam != null) {
                this.parentBeam.trackBullet(bullet);
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(WARMUP, 0);
        builder.define(LIFE_TICK, 20);
    }

    @Override protected void readAdditionalSaveData(@NotNull CompoundTag tag) {}
    @Override protected void addAdditionalSaveData(@NotNull CompoundTag tag) {}

    @Override
    public void onAntiMagic(MagicData magicData) {
        for (SpellSmallAnnihilationBombEntity bullet : this.spawnedBullets) {
            if (bullet != null && bullet.isAlive()) {
                bullet.discard();
            }
        }
        this.spawnedBullets.clear();

        this.discard();
    }
    @Override public boolean alwaysAccepts() { return super.alwaysAccepts(); }

    @Override public int getWarmup()           { return this.entityData.get(WARMUP); }
    @Override public void setWarmup(int ticks) { this.entityData.set(WARMUP, ticks); }

    public int getLifeTick()           { return this.entityData.get(LIFE_TICK); }
    public void setLifeTick(int ticks) { this.entityData.set(LIFE_TICK, ticks); }

    public void setParentBeam(SpellAnnihilationBeamEntity parentBeam) {
        this.parentBeam = parentBeam;
    }
}
