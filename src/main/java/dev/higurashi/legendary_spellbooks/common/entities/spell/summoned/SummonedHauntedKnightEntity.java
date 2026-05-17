package dev.higurashi.legendary_spellbooks.common.entities.spell.summoned;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import dev.higurashi.legendary_spellbooks.common.spells.evocation.SummonHauntedKnightsSpell;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.HauntedKnightEntity;
import net.miauczel.legendary_monsters.entity.ai.goal.IAttackGoal;
import net.miauczel.legendary_monsters.entity.ai.goal.IMoveGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class SummonedHauntedKnightEntity extends HauntedKnightEntity implements ISummonedMob {
    public SummonedHauntedKnightEntity(EntityType<? extends HauntedKnightEntity> entity, Level level) {
        super(entity, level);
        xpReward = 0;
    }

    public SummonedHauntedKnightEntity(Level level, LivingEntity owner) {
        this(LSEntityRegistry.SUMMONED_HAUNTED_KNIGHT_ENTITY.get(), level);
        SummonManager.setOwner(this, owner);
    }

    @Override
    public void setupAttributes(int spellLevel, float spellPower) {
        int hp = SummonHauntedKnightsSpell.getHealth(spellLevel);

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(hp);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(SummonHauntedKnightsSpell.getDamage(spellPower));
        this.setHealth(hp);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1);
    }

    @Override
    public Entity getSummoner() {
        return ISummonedMob.super.getSummoner();
    }

    @Override
    public boolean isAlliedTo(Entity target) {
        Entity owner = getSummoner();
        return owner == target || this.isAlliedHelper(target);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(1, new IAttackGoal(this, 0, 2, 0, 28, 28, 3.0f) {
            @Override
            public boolean canUse() {
                return super.canUse() && SummonedHauntedKnightEntity.this.getRandom().nextFloat() * 35 < 16 && SummonedHauntedKnightEntity.this.getTarget() != null && SummonedHauntedKnightEntity.this.parryCooldown <= 0;
            }

            @Override
            public void stop() {
                SummonedHauntedKnightEntity.this.parryCooldown = 20;
                super.stop();
            }
        });
        this.goalSelector.addGoal(1, createAttackGoal(4, 48));
        this.goalSelector.addGoal(1, createAttackGoal(5, 44));

        this.goalSelector.addGoal(2, new IMoveGoal(this, false, 3.0f));

        this.defaultGoalSelector(this);
        this.defaultTargetSelector(this);
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
    public void onRemovedFromWorld() {
        this.onRemovedHelper(this);
        super.onRemovedFromWorld();
    }

    private IAttackGoal createAttackGoal(int attackState, int attackMaxTick) {
        return new IAttackGoal(this, 0, attackState, 0, attackMaxTick, 48, 3.5f) {
            @Override
            public boolean canUse() {
                boolean chance = SummonedHauntedKnightEntity.this.getRandom().nextFloat() * 35 < 16;
                boolean hasTarget = SummonedHauntedKnightEntity.this.getTarget() != null;

                return super.canUse() && chance && hasTarget;
            }
        };
    }
}
