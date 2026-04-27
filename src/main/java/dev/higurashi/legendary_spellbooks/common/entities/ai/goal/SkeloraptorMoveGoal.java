package dev.higurashi.legendary_spellbooks.common.entities.ai.goal;

import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.SummonedSkeloraptorEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class SkeloraptorMoveGoal extends Goal {
    private final SummonedSkeloraptorEntity monster;
    private final double moveSpeed;
    private int delayCounter;

    public SkeloraptorMoveGoal(SummonedSkeloraptorEntity monster, double moveSpeed) {
        this.monster = monster;
        this.moveSpeed = moveSpeed;
        this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.monster.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.monster.getTarget();
        if (target == null || !target.isAlive()) return false;
        return !(target instanceof Player p && (p.isSpectator() || p.isCreative()));
    }

    @Override
    public void start() {
        this.monster.setAggressive(true);
    }

    @Override
    public void stop() {
        this.monster.getNavigation().stop();
        this.monster.setAggressive(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.monster.getTarget();
        if (target != null) {
            this.monster.getLookControl().setLookAt(target, 30.0F, 30.0F);
            double distSq = this.monster.distanceToSqr(
                    target.getX(), target.getBoundingBox().minY, target.getZ()
            );
            if (--this.delayCounter <= 0) {
                this.delayCounter = 4 + this.monster.getRandom().nextInt(7);
                if (distSq > Math.pow(this.monster.getAttribute(Attributes.FOLLOW_RANGE).getValue(), 2.0)) {
                    this.monster.getNavigation().moveTo(target, 1.0);
                } else {
                    this.monster.getNavigation().moveTo(target, this.moveSpeed);
                }
            }
        }
    }
}
