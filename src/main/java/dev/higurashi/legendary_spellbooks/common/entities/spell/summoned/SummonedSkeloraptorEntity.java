package dev.higurashi.legendary_spellbooks.common.entities.spell.summoned;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import dev.higurashi.legendary_spellbooks.common.entities.ai.goal.SkeloraptorMoveGoal;
import dev.higurashi.legendary_spellbooks.common.spells.nature.FossilizedFurySpell;
import dev.higurashi.legendary_spellbooks.mixin.entities.spell.summoned.SkeloraptorEntityAccessor;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Pets.SkeloraptorEntity;
import net.miauczel.legendary_monsters.entity.ai.goal.ITamableMonster.ITamableMobAttackGoal;
import net.minecraft.core.particles.ParticleTypes;
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
import org.jetbrains.annotations.NotNull;

public class SummonedSkeloraptorEntity extends SkeloraptorEntity implements ISummonedMob {
    private double summonedDamage = 10.0;

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

    @Override public void updateAttributes() {}

    @Override
    public double damageMult() {
        return switch (this.getAttackState()) {
            case 4 -> this.summonedDamage / 7.0;
            default -> this.summonedDamage / 10.0;
        };
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
        this.summonedDamage = getAttributeValue(Attributes.ATTACK_DAMAGE);
        ((SkeloraptorEntityAccessor) this).setRegenerationCooldown(100);
    }

    @Override
    public void onRemovedFromWorld() {
        this.onRemovedHelper(this);
        super.onRemovedFromWorld();
    }
}
