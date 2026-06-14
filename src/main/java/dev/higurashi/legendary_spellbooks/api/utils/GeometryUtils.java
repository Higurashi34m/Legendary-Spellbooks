package dev.higurashi.legendary_spellbooks.api.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class GeometryUtils {
    public static List<Vec3> getLinePoints(Vec3 center, float yaw, int count, double interval, double startOffset) {
        List<Vec3> points = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double distance = startOffset + i * interval;
            points.add(getPointAtAngle(center, yaw, distance));
        }
        return points;
    }

    public static Vec3 getPointAtAngle(Vec3 center, float yaw, double radius) {
        float radians = (float) Math.toRadians(yaw);
        double x = -Math.sin(radians) * radius;
        double z = Math.cos(radians) * radius;
        return center.add(x, 0.0, z);
    }

    public static Vec3 getPointInCircle(Vec3 center, double radius, int count, int index, float rotationOffset) {
        double angle = (2.0 * Math.PI / count) * index + rotationOffset;
        double x = Math.cos(angle) * radius;
        double z = Math.sin(angle) * radius;
        return center.add(x, 0.0, z);
    }

    public static List<Vec3> getCirclePoints(Vec3 center, double radius, int count, float rotationOffset) {
        List<Vec3> points = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            points.add(getPointInCircle(center, radius, count, i, rotationOffset));
        }
        return points;
    }

    public static List<Vec3> getFanPoints(Vec3 center, float centerYaw, float spreadDegrees, double radius, int count) {
        List<Vec3> points = new ArrayList<>();
        if (count <= 0) return points;

        if (count == 1) {
            points.add(getPointAtAngle(center, centerYaw, radius));
            return points;
        }

        float startAngle = centerYaw - (spreadDegrees / 2.0f);
        float step = spreadDegrees / (count - 1.0f);

        for (int i = 0; i < count; i++) {
            points.add(getPointAtAngle(center, startAngle + (step * i), radius));
        }

        return points;
    }

    public static Vec3 getDirection(Vec3 from, Vec3 to) {
        if (from.equals(to)) return Vec3.ZERO;
        return to.subtract(from).normalize();
    }

    public static Vec3 getRelativePos(LivingEntity entity, double forward, double up, double side) {
        Vec3 look = entity.getLookAngle();
        Vec3 right = look.cross(new Vec3(0, 1, 0)).normalize();
        Vec3 upVec = right.cross(look).normalize();

        return entity.getEyePosition().add(look.scale(forward)).add(upVec.scale(up)).add(right.scale(side));
    }

    public static float getYawBetween(Vec3 from, Vec3 to) {
        double dX = to.x - from.x;
        double dZ = to.z - from.z;
        return (float) (Math.atan2(dZ, dX) * (180 / Math.PI)) - 90.0F;
    }

    public static float getMathYawRad(LivingEntity entity) {
        return (float) Math.toRadians(entity.getYHeadRot() + 90.0f);
    }

    public static float getMathPitchRad(LivingEntity entity) {
        return (float) Math.toRadians(-entity.getXRot());
    }
}