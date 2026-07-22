package dev.higurashi.legendary_spellbooks.common.entity.spell.summoned;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import dev.higurashi.legendary_spellbooks.common.spell.annihilation.SummonFlamebornKnightsSpell;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.SpaceStation.Flameborn.FlamebornWarriorEntity;
import net.miauczel.legendary_monsters.entity.ai.goal.IAttackGoal;
import net.miauczel.legendary_monsters.entity.ai.goal.IMoveGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SummonedFlamebornWarriorEntity extends FlamebornWarriorEntity implements ISummonedMob {
    public SummonedFlamebornWarriorEntity(EntityType<? extends FlamebornWarriorEntity> entity, Level level) {
        super(entity, level);
        xpReward = 0;
    }

    public SummonedFlamebornWarriorEntity(Level level, LivingEntity owner) {
        this(LSEntityRegistry.SUMMONED_FLAMEBORN_WARRIOR_ENTITY.get(), level);
        SummonManager.setOwner(this, owner);
    }

    @Override
    public void setupAttributes(int spellLevel, float spellPower) {
        int hp = SummonFlamebornKnightsSpell.getHealth(spellLevel, true);

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(hp);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(SummonFlamebornKnightsSpell.getDamage(spellPower));
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
        this.goalSelector.addGoal(1, createHitAttackGoal(2, 1));
        this.goalSelector.addGoal(1, createHitAttackGoal(3, 2));
        this.goalSelector.addGoal(1, createDoubleHitAttackGoal(4, 1));
        this.goalSelector.addGoal(1, createDoubleHitAttackGoal(5, 2));

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
    public void SideAreaAttack(float range, float height, float arc, float boxOffset, float forwardOffset, float damage, int brokenShieldTicks,SoundEvent soundEvent, float pitch) {
        damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);

        double yawRad = Math.toRadians(this.yBodyRot);
        double fx = Math.cos(yawRad + Math.PI / 2) * forwardOffset;
        double fz = Math.sin(yawRad + Math.PI / 2) * forwardOffset;

        double cx = this.getX() + fx;
        double cz = this.getZ() + fz;

        float attackerAngle = (this.yBodyRot - boxOffset + 360) % 360;

        for (LivingEntity hit : this.getEntityLivingBaseNearby(range, height, range, range)) {
            if (hit == this || this.isAlliedTo(hit)) continue;

            double dx = hit.getX() - cx;
            double dz = hit.getZ() - cz;

            float dist = (float) Math.sqrt(dx * dx + dz * dz);
            if (dist > range) continue;

            float hitAngle = (float) ((Math.toDegrees(Math.atan2(dz, dx)) - 90 + 360) % 360);
            float rel = Math.abs(hitAngle - attackerAngle);
            if (rel > 180) rel = 360 - rel;

            if (rel <= arc / 2) {
                boolean damaged = hit.hurt(this.damageSources().mobAttack(this), damage);
                if (damaged) this.playSound(soundEvent, 1.0f, pitch);

                if (hit instanceof Player player && player.isBlocking() && brokenShieldTicks > 0) {
                    disableShield(player, brokenShieldTicks);
                }
            }
        }
    }

    private IAttackGoal createHitAttackGoal(int attackState, int nextHitType) {
        return new IAttackGoal(this, 0, attackState, 0, 49, 49, 3.5f) {
            @Override
            public boolean canUse() {
                boolean chance = SummonedFlamebornWarriorEntity.this.getRandom().nextFloat() * 100 < 20.0f;
                boolean hasTarget = SummonedFlamebornWarriorEntity.this.getTarget() != null;
                boolean canHit = SummonedFlamebornWarriorEntity.this.getNextHitType() == nextHitType;

                return super.canUse() && chance && hasTarget && canHit;
            }

            @Override
            public void stop() {
                SummonedFlamebornWarriorEntity.this.randomizeAttacks();
                super.stop();
            }
        };
    }

    private IAttackGoal createDoubleHitAttackGoal(int attackState, int nextHitType) {
        return new IAttackGoal(this, 0, attackState, 0, 62, 62, 3.5f) {
            @Override
            public boolean canUse() {
                boolean chance = SummonedFlamebornWarriorEntity.this.getRandom().nextFloat() * 100 < 20.0f;
                boolean hasTarget = SummonedFlamebornWarriorEntity.this.getTarget() != null;
                boolean canHit = SummonedFlamebornWarriorEntity.this.getNextDoubleHitType() == nextHitType;

                return super.canUse() && chance && hasTarget && canHit;
            }

            @Override
            public void stop() {
                SummonedFlamebornWarriorEntity.this.randomizeAttacks();
                super.stop();
            }
        };
    }
}
