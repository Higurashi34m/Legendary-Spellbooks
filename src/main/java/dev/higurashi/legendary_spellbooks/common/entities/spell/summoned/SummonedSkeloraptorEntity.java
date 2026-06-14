package dev.higurashi.legendary_spellbooks.common.entities.spell.summoned;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import dev.higurashi.legendary_spellbooks.common.entities.ai.goal.SkeloraptorMoveGoal;
import dev.higurashi.legendary_spellbooks.common.spells.nature.FossilizedFurySpell;
import dev.higurashi.legendary_spellbooks.mixin.entities.spell.summoned.SkeloraptorEntityAccessor;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Pets.SkeloraptorEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ShockwaveEntity;
import net.miauczel.legendary_monsters.entity.ai.goal.ITamableMonster.ITamableMobAttackGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SummonedSkeloraptorEntity extends SkeloraptorEntity implements ISummonedMob {
    public SummonedSkeloraptorEntity(EntityType<? extends SkeloraptorEntity> type, Level level) {
        super(type, level);
        this.xpReward = 0;
    }

    public SummonedSkeloraptorEntity(Level level, LivingEntity owner) {
        super(LSEntityRegistry.SUMMONED_SKELORAPTOR_ENTITY.get(), level);
        SummonManager.setOwner(this, owner);
    }

    @Override
    public void setupAttributes(int spellLevel, float spellPower) {
        int hp = FossilizedFurySpell.getHealth(spellLevel);

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(hp);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FossilizedFurySpell.getDamage(spellPower));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1);
        this.setHealth(hp);
    }

    @Override
    public float getScale() {
        return 0.5f;
    }

    @Override
    public Entity getSummoner() {
        return ISummonedMob.super.getSummoner();
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public boolean isAlliedTo(@NotNull Entity target) {
        Entity owner = getSummoner();
        return owner == target || this.isAlliedHelper(target);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new ITamableMobAttackGoal(this, 0, 4, 0, 55, 55, 4.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && SummonedSkeloraptorEntity.this.getRandom().nextFloat() * 35.0f < 16.0f;
            }
        });

        this.goalSelector.addGoal(1, new ITamableMobAttackGoal(this, 0, 3, 0, 50, 55, 4.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && SummonedSkeloraptorEntity.this.getRandom().nextFloat() * 35.0f < 16.0f;
            }
        });

        this.goalSelector.addGoal(1, new ITamableMobAttackGoal(this, 0, 1, 0, 53, 55, 4.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && SummonedSkeloraptorEntity.this.getRandom().nextFloat() * 35.0F < 16.0F && SummonedSkeloraptorEntity.this.roarCooldown <= 0;
            }
            @Override
            public void stop() {
                SummonedSkeloraptorEntity.this.roarCooldown = 300;
                super.stop();
            }
        });

        this.goalSelector.addGoal(2, new SkeloraptorMoveGoal(this, 3.0));

        this.defaultGoalSelector(this);
        this.defaultTargetSelector(this);
    }

    @NotNull @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.stopAttackingAllies();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (shouldIgnoreDamage(source)) return false;
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        this.onDeathHelper();
        super.die(source);
    }

    @Override
    public void onUnSummon() {
        if (!this.level().isClientSide) {
            MagicManager.spawnParticles(this.level(), ParticleTypes.POOF, getX(), getY(), getZ(), 25, 0.4, 0.8, 0.4, 0.03, false);
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        ((SkeloraptorEntityAccessor) this).setRegenerationCooldown(100);
    }

    @Override
    public void onRemovedFromLevel() {
        this.onRemovedHelper(this);
        super.onRemovedFromLevel();
    }

    @Override
    public void UpdateWithAttack() {
        if (this.getAttackState() == 4) {
            Vec3 position = this.position();

            if (this.attackTicks == 12) {
                this.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 2.0f, 1.0f);
                ((SkeloraptorEntityAccessor) this).areaAttack(2.0f, 3.0f, 360.0f, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE), 40, false, 0.0f, false, 0.75f, false);
                this.SpawnCircleParticle(0.25f, -0.5f, 10.0f, true, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f);
                CameraShakeEntity.cameraShake(this.level(), position, 20.0f, 0.05f, 0, 20);

                double standingOnY = Math.floor(this.getY());
                for (int k = 0; k < 6; ++k) {
                    float f2 = (float) (k * Math.PI * 2.0f / 6.0f + 1.2566371f);
                    this.spawnShockwave(this.getX() + Math.cos(f2) * 2.5, this.getZ() + Math.sin(f2) * 2.5, standingOnY, this.getY() + 1.0, f2, 0);
                }
            } else if (this.attackTicks == 37) {
                this.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 2.0f, 1.0f);
                ((SkeloraptorEntityAccessor) this).areaAttack(2.0f, 3.0f, 360.0f, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE), 40, false, 0.0f, false, 0.75f, false);
                this.SpawnCircleParticle(0.25f, -0.5f, 10.0f, true, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f);
                CameraShakeEntity.cameraShake(this.level(), position, 20.0f, 0.05f, 0, 20);

                double standingOnY = Math.floor(this.getY());
                for (int k = 0; k < 6; ++k) {
                    float f2 = (float) (k * Math.PI * 2.0f / 6.0f + 1.2566371f);
                    this.spawnShockwave(this.getX() + Math.cos(f2) * 2.5, this.getZ() + Math.sin(f2) * 2.5, standingOnY, this.getY() + 1.0, f2, 0);
                }
            } else {
                super.UpdateWithAttack();
            }
        } else {
            super.UpdateWithAttack();
        }
    }

    private void spawnShockwave(double x, double z, double minY, double maxY, float rotation, int delay) {
        BlockPos blockpos = new BlockPos((int) x, (int) maxY, (int) z).below();
        boolean flag = false;
        double yOffset = 0.0;

        do {
            BlockState state = this.level().getBlockState(blockpos);
            if (state.isFaceSturdy(this.level(), blockpos, Direction.UP)) {
                if (!this.level().isEmptyBlock(blockpos)) {
                    BlockState state1 = this.level().getBlockState(blockpos);
                    VoxelShape voxelshape = state1.getCollisionShape(this.level(), blockpos);
                    if (!voxelshape.isEmpty()) yOffset = voxelshape.max(Direction.Axis.Y);
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while (blockpos.getY() >= Mth.floor(minY) - 1);

        if (flag) this.level().addFreshEntity(new ShockwaveEntity(this.level(), x, blockpos.getY() + yOffset, z, rotation, delay, this, false, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2.0f));
    }
}
