package dev.higurashi.legendary_spellbooks.common.entity.spell.summoned;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import dev.higurashi.legendary_spellbooks.common.spell.evocation.CollapsedKingdomsLegionSpell;
import dev.higurashi.legendary_spellbooks.registry.LSEntityRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.HauntedKnightEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.KnightParryFrameGoal;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.KnightParryGoal;
import net.miauczel.legendary_monsters.entity.ai.goal.IAttackGoal;
import net.miauczel.legendary_monsters.entity.ai.goal.IMoveGoal;
import net.miauczel.legendary_monsters.entity.ai.goal.IStateGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SummonedHauntedKnightEntity extends HauntedKnightEntity implements ISummonedMob {
    public SummonedHauntedKnightEntity(EntityType<? extends HauntedKnightEntity> entity, Level level) {
        super(entity, level);
        xpReward = 0;
    }

    public SummonedHauntedKnightEntity(Level level, LivingEntity owner) {
        this(LSEntityRegistry.SUMMONED_HAUNTED_KNIGHT_ENTITY.getAs(), level);
        SummonManager.setOwner(this, owner);
    }

    @Override
    public void setupAttributes(int spellLevel, float spellPower) {
        int hp = CollapsedKingdomsLegionSpell.getHealth(spellLevel);

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(hp);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(CollapsedKingdomsLegionSpell.getDamage(spellPower));
        this.setHealth(hp);
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
        this.goalSelector.addGoal(1, createAttackGoal(2, 39, 20));
        this.goalSelector.addGoal(1, createAttackGoal(3, 65, 40));

        this.goalSelector.addGoal(1, new KnightParryGoal(this, 0, 4, 0, 8, 8, 3.0f) {
            public boolean canUse() {
                return super.canUse() && SummonedHauntedKnightEntity.this.getRandom().nextFloat() < 0.33f;
            }
        });
        this.goalSelector.addGoal(1, new KnightParryFrameGoal(this, 5, 5, 0, 25, 25) {});

        this.goalSelector.addGoal(0, new IStateGoal(this, 7, 7, 0, 11, 11) {});
        this.goalSelector.addGoal(0, new IStateGoal(this, 8, 8, 0, 27, 27) {});

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

    @Override @Nullable
    public ItemEntity LGspawnatlocation(ItemStack stack) {
        return null;
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

    private IAttackGoal createAttackGoal(int attackState, int attackMaxTick, int attackSeeTick) {
        return new IAttackGoal(this, 0, attackState, 0, attackMaxTick, attackSeeTick, 3.0f) {
            @Override
            public boolean canUse() {
                return super.canUse() && SummonedHauntedKnightEntity.this.getRandom().nextFloat() < 0.28f;
            }
        };
    }
}