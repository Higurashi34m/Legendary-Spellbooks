package dev.higurashi.legendary_spellbooks.common.entities.spell.summoned;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import dev.higurashi.legendary_spellbooks.common.spells.evocation.CollapsedKingdomsLegionSpell;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.HauntedGuardEntity;
import net.miauczel.legendary_monsters.entity.ai.goal.IAttackGoal;
import net.miauczel.legendary_monsters.entity.ai.goal.IMoveGoal;
import net.miauczel.legendary_monsters.util.MathUtils;
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

public class SummonedHauntedGuardEntity extends HauntedGuardEntity implements ISummonedMob {
    public SummonedHauntedGuardEntity(EntityType<? extends HauntedGuardEntity> entity, Level level) {
        super(entity, level);
        xpReward = 0;
    }

    public SummonedHauntedGuardEntity(Level level, LivingEntity owner) {
        this(LSEntityRegistry.SUMMONED_HAUNTED_GUARD_ENTITY.get(), level);
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
        this.goalSelector.addGoal(1, createAttackGoal(2, MathUtils.toTicks(2.67f), 15));
        this.goalSelector.addGoal(1, createAttackGoal(3, 90, 55));
        this.goalSelector.addGoal(0, new IAttackGoal(this, 4, 5, 0, 15, 15, 10.0f));

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

    private IAttackGoal createAttackGoal(int attackState, int attackMaxTick, int attackSeeTicks) {
        return new IAttackGoal(this, 0, attackState, 0, attackMaxTick, attackSeeTicks, 4.0f) {
            @Override
            public boolean canUse() {
                return super.canUse() && SummonedHauntedGuardEntity.this.getRandom().nextFloat() * 35.0f < 16.0f;
            }
        };
    }
}