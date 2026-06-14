package dev.higurashi.legendary_spellbooks.common.entities.spell.projectile;

import dev.higurashi.legendary_spellbooks.api.entities.helper.IWarmupEntity;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.CloudEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SpellCloudEntity extends CloudEntity implements IWarmupEntity {
    private static final EntityDataAccessor<Integer> WARMUP = SynchedEntityData.defineId(SpellCloudEntity.class, EntityDataSerializers.INT);

    public SpellCloudEntity(EntityType<CloudEntity> type, Level level) {
        super(type, level);
    }

    public SpellCloudEntity(Level level, LivingEntity caster) {
        this(LSEntityRegistry.SPELL_CLOUD_ENTITY.get(), level);
        this.setOwner(caster);
    }

    @Override
    public void tick() {
        this.warmupTick();
        if (this.getWarmup() > 0) return;

        super.tick();
    }

    @Override
    public void onHit(HitResult result) {
        this.applyCloudDamage();
        this.processHitEffects(result);
        this.level().broadcastEntityEvent(this, (byte) 3);

        this.playSound(SoundEvents.SHULKER_BULLET_HURT, 3.0F, 1.0F);
        this.discard();
    }

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case 3 -> this.Particle();
            default -> super.handleEntityEvent(id);
        }
    }

    private void applyCloudDamage() {
        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.0, 1.5, 1.0));

        Entity caster = this.getOwner();
        if (caster == null) return;

        for (LivingEntity target : targets) {
            if (target == caster || caster.isAlliedTo(target)) continue;

            float distance = this.distanceTo(target) / 2.0f;
            float effectiveDistance = Math.max(distance, 1.0f);

            float damage = (float) this.damage / effectiveDistance;

            AbstractSpell spell = LSSpellRegistry.CLOUD_RAIL_SPELL.get();
            DamageSources.applyDamage(target, damage, spell.getDamageSource(this, getOwner()));
        }
    }

    private void processHitEffects(HitResult result) {
        HitResult.Type hitresult$type = result.getType();
        if (hitresult$type == HitResult.Type.ENTITY) {
            EntityHitResult entityhitresult = (EntityHitResult)result;
            Entity entity = entityhitresult.getEntity();
            if (entity.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE) && entity instanceof Projectile projectile) {
                projectile.deflect(ProjectileDeflection.AIM_DEFLECT, this.getOwner(), this.getOwner(), true);
            }

            this.onHitEntity(entityhitresult);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), GameEvent.Context.of(this, (BlockState)null));
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)result;
            this.onHitBlock(blockhitresult);
            BlockPos blockpos = blockhitresult.getBlockPos();
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
        }
    }

    // Warmup
    @Override
    public void onWarmupTick() {
        this.setInvisible(true);
        this.setDeltaMovement(Vec3.ZERO);
    }

    @Override
    public void onWarmupFinished() {
        this.setInvisible(false);
    }

    // Save to Nbt
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.saveWarmupData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.loadWarmupData(tag);
    }

    // Client Sync
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(WARMUP, 0);
    }

    @Override public void setWarmup(int ticks) { this.entityData.set(WARMUP, ticks); }
    @Override public int getWarmup() { return this.entityData.get(WARMUP); }
}