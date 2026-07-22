package dev.higurashi.legendary_spellbooks.common.entity.spell.projectile;

import dev.higurashi.daybreaklib.api.client.animation.AnimationController;
import dev.higurashi.daybreaklib.api.common.entity.IWarmupEntity;
import dev.higurashi.daybreaklib.api.common.entity.WarmupManager;
import dev.higurashi.daybreaklib.api.util.attack.AttackUtils;
import dev.higurashi.daybreaklib_iss.api.util.ISSTextUtils;
import dev.higurashi.legendary_spellbooks.registry.LSAnimationRegistry;
import dev.higurashi.legendary_spellbooks.registry.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registry.LSSpellRegistry;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SpellIceSpikeEntity extends Entity implements IWarmupEntity {
    private static final EntityDataAccessor<Integer> WARMUP = SynchedEntityData.defineId(SpellIceSpikeEntity.class, EntityDataSerializers.INT);

    private LivingEntity caster;
    private float damage;
    private int lifeTick;

    public SpellIceSpikeEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public SpellIceSpikeEntity(Level level, Vec3 spawnPos, float yaw, LivingEntity caster, float damage, int warmup) {
        this(LSEntityRegistry.SPELL_ICE_SPIKE_ENTITY.get(), level);
        this.setPos(spawnPos);
        this.setYRot(yaw);

        this.caster = caster;
        this.damage = damage;
        this.setWarmup(warmup);
        this.lifeTick = 0;

        this.setInvisible(true);
    }

    @Override
    public void setWarmup(int warmup) {
        this.entityData.set(WARMUP, warmup);
    }

    @Override
    public int getWarmup() {
        return this.entityData.get(WARMUP);
    }

    @Override
    public void tick() {
        super.tick();
        WarmupManager.tick(this);
        if (this.getWarmup() >= 0) return;

        this.lifeTick++;
        Level level = this.level();

        if (level.isClientSide) {
            if (this.lifeTick == 2) {
                level.playLocalSound(this.getX(), this.getY(), this.getZ(), ModSounds.ICE_SPIKE_EMERGE.get(), this.getSoundSource(), 0.5f, 0.85f + this.random.nextFloat() * 0.2f, true);
            } else if (this.lifeTick >= 4) {
                BlockState blockState = level.getBlockState(this.blockPosition().below());

                double x = this.getX() + this.random.nextDouble() * this.getBbWidth() * 0.5;
                double z = this.getZ() + this.random.nextDouble() * this.getBbWidth() * 0.5;

                double xSpeed = this.random.nextDouble() * 0.07;
                double ySpeed = this.random.nextDouble() * 0.07;
                double zSpeed = this.random.nextDouble() * 0.07;

                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState), x, this.getY(), z, xSpeed, ySpeed, zSpeed);
            }
        } else {
            if (this.lifeTick >= 10 && this.lifeTick <= 20 && this.lifeTick % 5 == 0) {
                level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox()).forEach(target -> {
                    if (!AttackUtils.canDamage(this.caster, target)) return;

                    if (DamageSources.applyDamage(target, this.damage, LSSpellRegistry.GLACIER_ERUPTION_SPELL.get().getDamageSource(this, this.caster))) {
                        target.addEffect(new MobEffectInstance(ModEffects.FREEZE.get(), 60));
                    }
                });
            } else if (this.lifeTick == 32) {
                this.discard();
            }
        }
    }

    @Override @NotNull
    protected Component getTypeName() {
        return ISSTextUtils.getSpellEntityComponent(ModEntities.ICE_SPIKE_ENTITY::get);
    }

    @Override
    public void onWarmupFinish() {
        if (!this.level().isClientSide()) return;

        AnimationController.play(this, LSAnimationRegistry.ICE_SPIKE);
        this.setInvisible(false);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(WARMUP, 0);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
    }
}
