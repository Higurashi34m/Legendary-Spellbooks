package dev.higurashi.legendary_spellbooks.api.entities.helper;

import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.goals.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;

public interface ISummonedMob extends IMagicSummon {
    void setupAttributes(int spellLevel, float spellPower);

    default void defaultTargetSelector(PathfinderMob entity) {
        if (entity instanceof IMagicSummon mob) {
            entity.targetSelector.addGoal(1, new GenericOwnerHurtByTargetGoal(entity, mob::getSummoner));
            entity.targetSelector.addGoal(2, new GenericOwnerHurtTargetGoal(entity, mob::getSummoner));
            entity.targetSelector.addGoal(3, new GenericProtectOwnerTargetGoal(entity, mob::getSummoner));
            entity.targetSelector.addGoal(4, new GenericCopyOwnerTargetGoal(entity, mob::getSummoner));
            entity.targetSelector.addGoal(5, new GenericHurtByTargetGoal(entity, livingEntity -> livingEntity == getSummoner()));
        }
    }

    default void defaultGoalSelector(PathfinderMob entity, Float followSpeed, float teleportDistance, boolean canFly) {
        if (entity instanceof IMagicSummon mob) {
            entity.goalSelector.addGoal(6, new GenericFollowOwnerGoal(entity, mob::getSummoner, followSpeed, 8, 4, canFly, teleportDistance));
            entity.goalSelector.addGoal(7, new LookAtPlayerGoal(entity, Player.class, 4.0f, 1.0f));
            entity.goalSelector.addGoal(8, new LookAtPlayerGoal(entity, Mob.class, 8.0f));
            entity.goalSelector.addGoal(9, new RandomLookAroundGoal(entity));
        }
    }

    default void defaultGoalSelector(PathfinderMob entity, Float followSpeed, float teleportDistance) {
        defaultGoalSelector(entity, followSpeed, teleportDistance, false);
    }

    default void defaultGoalSelector(PathfinderMob entity, Float followSpeed) {
        defaultGoalSelector(entity, followSpeed, 50);
    }

    default void defaultGoalSelector(PathfinderMob entity) {
        defaultGoalSelector(entity, 3.0f);
    }
}
