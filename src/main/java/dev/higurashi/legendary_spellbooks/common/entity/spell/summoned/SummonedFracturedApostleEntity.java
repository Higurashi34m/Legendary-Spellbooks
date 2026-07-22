package dev.higurashi.legendary_spellbooks.common.entity.spell.summoned;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import dev.higurashi.legendary_spellbooks.common.spell.evocation.CollapsedKingdomsLegionSpell;
import dev.higurashi.legendary_spellbooks.registry.LSEntityRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.SoulSigil;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.Fractured.FracturedApostleEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ThrownPhantomDaggerEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.entity.ai.goal.IAttackGoal;
import net.miauczel.legendary_monsters.entity.ai.goal.IMoveGoal;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.miauczel.legendary_monsters.util.MathUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class SummonedFracturedApostleEntity extends FracturedApostleEntity implements ISummonedMob {
    public SummonedFracturedApostleEntity(EntityType<? extends FracturedApostleEntity> entity, Level level) {
        super(entity, level);
        xpReward = 0;
    }

    public SummonedFracturedApostleEntity(Level level, LivingEntity owner) {
        this(LSEntityRegistry.SUMMONED_FRACTURED_APOSTLE_ENTITY.getAs(), level);
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
        this.goalSelector.addGoal(1, createAttackGoal(2, 40, 10.0f));
        this.goalSelector.addGoal(1, createAttackGoal(4, 38, 3.5f));

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

    @Override
    public void UpdateWithAttack() {
        if (this.getAttackState() == 2) {
            if (this.attackTicks == MathUtils.toTicks(0.92f)) {
                this.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

                double spawnY = this.getY() + 4.0;

                if (this.level().isClientSide) {
                    float yawRad = (float) Math.toRadians(-this.getYRot());
                    float pitchRad = (float) Math.toRadians(this.getXRot());

                    this.level().addParticle(ModParticles.SOUL_SHOOT.get(), this.getX(), spawnY, this.getZ(), 0.0, 0.0, 0.0);
                    this.level().addParticle(new SoulSigil.RingData(yawRad, pitchRad, 20, 1.0f, 1.0f, 1.0f, 0.5f, 15.0f, false, SoulSigil.EnumRingBehavior.CONSTANT), this.getX(), spawnY, this.getZ(), 0.0, 0.0, 0.0);
                }

                if (this.targetIsNotNull()) {
                    ThrownPhantomDaggerEntity dagger = new ThrownPhantomDaggerEntity(ModEntities.THROWN_PHANTOM_DAGGER.get(), this.level());
                    dagger.setPosRaw(this.getX(), spawnY, this.getZ());
                    dagger.setReturnEntity(this.target());
                    dagger.setReturnTick(0);
                    dagger.setDamage((float) (this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 4.0f));
                    dagger.setOwner(this);
                    dagger.setInteria(0.9f);
                    dagger.setLessLifeTicks(50);

                    this.level().addFreshEntity(dagger);
                }
            } else super.UpdateWithAttack();
        }

        if (this.getAttackState() == 4) {
            if (this.attackTicks != MathUtils.toTicks(0.67f)) {
                super.UpdateWithAttack();
                return;
            }

            this.SideAreaAttack(1.75f, 3.0f, 180.0f, 0.0f, 0.0f, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE), 30, ModSounds.ZOMBIE_ATTACK_IRON_DOOR, 1.0f, false, 0.0f);
        }
    }

    private IAttackGoal createAttackGoal(int attackState, int attackMaxTick,  float attackRange) {
        return new IAttackGoal(this, 0, attackState, 0, attackMaxTick, attackMaxTick, attackRange) {
            @Override
            public boolean canUse() {
                return super.canUse() && SummonedFracturedApostleEntity.this.getRandom().nextFloat() * 40.0f < 16.0f;
            }
        };
    }
}
