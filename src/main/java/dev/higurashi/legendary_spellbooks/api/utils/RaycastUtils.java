package dev.higurashi.legendary_spellbooks.api.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class RaycastUtils {
    public static Vec3 findGround(Level level, Vec3 basePos, int maxDown, int maxUp) {
        BlockPos blockPos = BlockPos.containing(basePos);

        for (int i = 0; i < maxDown; i++) {
            BlockPos check = blockPos.below(i);
            BlockState state = level.getBlockState(check);

            if (isSolidGround(level, check, state)) {
                double top = state.getCollisionShape(level, check).max(Direction.Axis.Y);
                return new Vec3(basePos.x, check.getY() + top, basePos.z);
            }
        }

        for (int i = 0; i < maxUp; i++) {
            BlockPos check = blockPos.above(i);
            BlockState state = level.getBlockState(check);

            if (isSolidGround(level, check, state)) {
                double top = state.getCollisionShape(level, check).max(Direction.Axis.Y);
                return new Vec3(basePos.x, check.getY() + top, basePos.z);
            }
        }

        return null;
    }

    private static boolean isSolidGround(Level level, BlockPos pos, BlockState state) {
        return !state.isAir() && !state.getCollisionShape(level, pos).isEmpty() && state.isSolid();
    }
}
