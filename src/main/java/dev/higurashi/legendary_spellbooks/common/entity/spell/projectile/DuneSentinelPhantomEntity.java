package dev.higurashi.legendary_spellbooks.common.entity.spell.projectile;

import dev.higurashi.legendary_spellbooks.registry.LSEntityRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DuneSentinelPhantomEntity extends Entity implements AntiMagicSusceptible {
    private static final EntityDataAccessor<Boolean> SHOULD_ATTACK = SynchedEntityData.defineId(DuneSentinelPhantomEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHOULD_DEATH = SynchedEntityData.defineId(DuneSentinelPhantomEntity.class, EntityDataSerializers.BOOLEAN);

    private Object dummySentinel = null;

    private LivingEntity caster;
    private float damage;
    private float tick = 0;

    public DuneSentinelPhantomEntity(EntityType<? extends DuneSentinelPhantomEntity> type, Level level) {
        super(type, level);
    }

    public DuneSentinelPhantomEntity(Level level, LivingEntity caster, float damage) {
        this(LSEntityRegistry.DUNE_SENTINEL_PHANTOM_ENTITY.getAs(), level);
        this.caster = caster;
        this.damage = damage;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SHOULD_ATTACK, false);
        this.entityData.define(SHOULD_DEATH, false);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount < 15) return;
        if (this.tickCount == 15) {
            this.entityData.set(SHOULD_ATTACK, true);
            if (Math.random() < 0.5) this.playSound(ModSounds.CANNON_SHOOT_2.get(), 3.0f, 1.0f / (this.random.nextFloat() * 0.4f + 0.8f));
            else this.playSound(ModSounds.CANNON_SHOOT_1.get(), 3.0f, 1.0f / (this.random.nextFloat() * 0.4f + 0.8f));
        }

        if (this.tick > 0) this.tick++;
        if (this.tick >= 20) {
            MagicManager.spawnParticles(this.level(), ParticleTypes.POOF, getX(), getY(), getZ(), 25, 0.4, 0.8, 0.4, 0.03, false);
            this.discard();
        }

        if (!this.level().isClientSide) {
            if (this.tickCount >= 15 && this.tickCount <= 20 && !isShouldDeath()) this.shootProjectile();
            if (this.tickCount == 40 && !isShouldDeath()) {
                this.entityData.set(SHOULD_ATTACK, false);
                this.entityData.set(SHOULD_DEATH, true);
                this.setSecondsOnFire(2);
            }
            if (this.tickCount >= 60) {
                MagicManager.spawnParticles(this.level(), ParticleTypes.POOF, getX(), getY(), getZ(), 25, 0.4, 0.8, 0.4, 0.03, false);
                this.discard();
            }
        }
    }

    private void death() {
        MagicManager.spawnParticles(this.level(), ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 25, 0.4, 0.8, 0.4, 0.03, false);
        this.discard();
    }

    private void shootProjectile() {
        SpellBombEntity bomb = new SpellBombEntity(this.level(), this.caster, this.damage);
        bomb.setPos(this.getEyePosition());
        bomb.shootFromRotation(this, -20.0f, this.getYRot(), 0.0f, 1.0f + this.random.nextFloat(), 10.0f);
        this.level().addFreshEntity(bomb);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag nbt) {

    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag nbt) {

    }

    @Override
    public boolean alwaysAccepts() {
        return super.alwaysAccepts();
    }

    public boolean isShouldAttack() {
        return this.entityData.get(SHOULD_ATTACK);
    }

    public boolean isShouldDeath() {
        return this.entityData.get(SHOULD_DEATH);
    }

    @Override
    public void onAntiMagic(MagicData playerMagicData) {
        if (this.tick == 0) {
            this.entityData.set(SHOULD_ATTACK, false);
            this.entityData.set(SHOULD_DEATH, true);
            this.setSecondsOnFire(2);
            this.tick++;
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends LivingEntity> T getOrCreateDummy(EntityType<T> type) {
        if (this.dummySentinel == null && this.level().isClientSide) {
            this.dummySentinel = type.create(this.level());
        }
        return (T) this.dummySentinel;
    }
}
