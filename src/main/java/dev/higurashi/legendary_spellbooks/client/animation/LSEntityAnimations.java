package dev.higurashi.legendary_spellbooks.client.animation;

import dev.higurashi.daybreaklib.api.client.animation.easing.Easings;
import dev.higurashi.daybreaklib.api.client.animation.interpolation.KeyframeAnimation;
import dev.higurashi.daybreaklib.api.client.animation.transform.Transform;
import dev.higurashi.daybreaklib.api.client.animation.transform.TransformInterpolator;
import net.minecraft.world.phys.Vec3;

public final class LSEntityAnimations {
    public static final KeyframeAnimation<Transform> ICE_SPIKE = KeyframeAnimation.<Transform>builder()
            .add(0.0f, new Transform(new Vec3(0.0, 1.5, 0.0), new Vec3(2.0, 2.0, 2.0), Vec3.ZERO), Easings.SINE_OUT)
            .add(26.0f, new Transform(new Vec3(0.0, -0.2, 0.0), new Vec3(2.0, 2.0, 2.0), Vec3.ZERO), Easings.SINE_IN_OUT)
            .add(32.0f, Transform.ZERO)
            .interpolator(new TransformInterpolator())
            .build();
}
