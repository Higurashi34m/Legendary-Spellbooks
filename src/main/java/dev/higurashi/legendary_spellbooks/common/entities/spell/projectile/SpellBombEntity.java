package dev.higurashi.legendary_spellbooks.common.entities.spell.projectile;

import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class SpellBombEntity extends AbstractMagicProjectile {
    private float damage = 0.0f;

    public SpellBombEntity(EntityType<SpellBombEntity> type, Level level) {
        super(type, level);
    }

    public SpellBombEntity(Level level, LivingEntity thrower, float damage) {
        super(LSEntityRegistry.SPELL_BOMB_ENTITY.get(), level);
        this.setOwner(thrower);
        this.damage = damage;
        this.explosionRadius = 4;
    }

    @Override
    public void onHit(@NotNull HitResult result) {
        super.onHit(result);

        if (this.level().isClientSide) return;

        List<Entity> targets = level().getEntities(this, this.getBoundingBox().inflate(explosionRadius));
        targets.stream().filter(this::canHitEntity).forEach(target -> {
            if (!(target instanceof LivingEntity livingTarget)) return;
            double distance = target.distanceToSqr(result.getLocation());

            if (distance < explosionRadius * explosionRadius) {
                if (DamageSources.applyDamage(target, damage, LSSpellRegistry.SENTINEL_SATURATION_SPELL.get().getDamageSource(this, this.getOwner()))) {
                    livingTarget.addEffect(new MobEffectInstance(ModEffects.PHARAONS_CURSE.get(), 20, 2));
                }
            }
        });

        CameraShakeEntity.cameraShake(this.level(), this.position(), 10.0f, 0.1f, 0, 20);
        this.discardHelper(result);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putFloat("Damage", this.damage);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.damage = nbt.getFloat("Damage");
    }

    @Override
    public void trailParticles() {
        Vec3 delta = this.getDeltaMovement();
        Vec3 spawnPos = new Vec3(this.getX() - delta.x, this.getY() - delta.y, this.getZ() - delta.z);

        this.level().addParticle(ParticleTypes.FLAME, spawnPos.x, spawnPos.y, spawnPos.z, 0.0f, 0.0f, 0.0f);
        BlockState sandState = Blocks.SAND.defaultBlockState();
        this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, sandState), spawnPos.x, spawnPos.y, spawnPos.z, 0.0f, 0.0f, 0.0f);
        this.level().addParticle(ParticleTypes.SMOKE, spawnPos.x, spawnPos.y, spawnPos.z, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 1, 1.0, 0.0, 0.0, 1);

        BlockState sandState = Blocks.SAND.defaultBlockState();
        for (int i = 0; i < 8; i++) {
            double mx = (this.random.nextDouble() - 0.5) * 0.5;
            double my = this.random.nextDouble() * 0.5;
            double mz = (this.random.nextDouble() - 0.5) * 0.5;
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, sandState), x, y, z, 1, mx, my, mz, 1);
        }
    }

    @Override
    public float getSpeed() {
        return 2;
    }

    @Override
    public Optional<Supplier<SoundEvent>> getImpactSound() {
        return Optional.of(() -> SoundEvents.GENERIC_EXPLODE);
    }
}
