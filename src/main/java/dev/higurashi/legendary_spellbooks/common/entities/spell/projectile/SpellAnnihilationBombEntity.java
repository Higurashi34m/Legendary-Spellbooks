package dev.higurashi.legendary_spellbooks.common.entities.spell.projectile;

import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationBombEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SmallAnnihilationBombEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class SpellAnnihilationBombEntity extends AnnihilationBombEntity {
    private static final EntityDataAccessor<Float> HP_RATIO = SynchedEntityData.defineId(SpellAnnihilationBombEntity.class, EntityDataSerializers.FLOAT);

    public SpellAnnihilationBombEntity(EntityType<AnnihilationBombEntity> type, Level level) {
        super(type, level);
    }

    public SpellAnnihilationBombEntity(Level level, LivingEntity caster, float damage, float hpRatio, int bulletsAmount) {
        super(LSEntityRegistry.SPELL_ANNIHILATION_BOMB_ENTITY.get(), level);
        this.setOwner(caster);
        this.setDamage(damage);
        this.setHpRatio(hpRatio);
        this.setBulletsAmount(bulletsAmount);
    }

    @Override
    public void onHit(@NotNull HitResult result) {
        HitResult.Type type = result.getType();

        if (type == HitResult.Type.ENTITY) {
            this.onHitEntity((EntityHitResult) result);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), GameEvent.Context.of(this, null));
        } else if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult)result;
            this.onHitBlock(blockHit);
            BlockPos blockpos = blockHit.getBlockPos();
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
        }

        this.playSound(ModSounds.DIMENSIONAL_BOMB_EXPLODE.get(), 1.0f, 1.0f);

        if (!(getOwner() instanceof LivingEntity caster)) return;

        spawnSmallBomb(caster, getBulletAmount() * 0.5f, 0.5f, 2.0f, 0.5f);
        spawnSmallBomb(caster, getBulletAmount(), 0.8f, 0.2f, 0.7f);

        if (!level().isClientSide) {
            if (level() instanceof ServerLevel server) {
                double posX = this.getX();
                double posY = this.getY() + 2.0f;
                double posZ = this.getZ();

                server.sendParticles(ModParticles.ANNIHILATION_FLAME_STRIKE.get(), posX, posY, posZ, 1, 0.0, 0.0, 0.0, 0.0);
                this.discard();
            }
        }
    }

    private void spawnSmallBomb(LivingEntity caster, float bulletAmount, float damageMultiplier, float vyMultiplier, float speed) {
        for (int i = 0; i < bulletAmount; i++) {
            float throwAngle = (float) (i * Math.PI / (bulletAmount / 2.0f));

            double spawnX = this.getX() + Math.cos(throwAngle);
            double spawnY = this.getY() + getBbHeight() * 0.2;
            double spawnZ = this.getZ() + Math.sin(throwAngle);

            double dirX = Math.cos(throwAngle);
            double dirY = 0.0 + this.random.nextDouble() * 0.3;
            double dirZ = Math.sin(throwAngle);

            double horizontalLength = Math.sqrt(dirX * dirX + dirZ * dirZ);

            SmallAnnihilationBombEntity projectile = new SmallAnnihilationBombEntity(ModEntities.SMALL_ANNIHILATION_BOMB_ENTITY.get(), level(), caster, getDamage() * damageMultiplier);

            projectile.moveTo(spawnX, spawnY, spawnZ, i * 11.25f, getXRot());
            projectile.shoot(dirX, dirY + horizontalLength * vyMultiplier, dirZ, speed, 1.0f);
            level().addFreshEntity(projectile);
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if (this.level().isClientSide) return;

        Entity target = result.getEntity();
        Entity caster = this.getOwner();
        if (caster == null || target == caster || caster.isAlliedTo(target) || target.isAlliedTo(caster)) return;

        for (LivingEntity hitEntity : level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.5))) {
            float damage = getDamage() + hitEntity.getMaxHealth() * getHpRatio();
            DamageSources.applyDamage(hitEntity, damage, LSSpellRegistry.ANNIHILATION_BOMB_SPELL.get().getDamageSource(this, caster));
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(HP_RATIO, 0.0f);
    }

    public float getHpRatio() { return this.entityData.get(HP_RATIO); }
    public void setHpRatio(float hpRatio) { this.entityData.set(HP_RATIO, hpRatio); }
}
