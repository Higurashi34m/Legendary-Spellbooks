package dev.higurashi.legendary_spellbooks.client.animation;

import dev.higurashi.daybreaklib.api.client.animation.interpolation.KeyframeAnimation;
import dev.higurashi.daybreaklib.api.client.animation.transform.Transform;
import dev.higurashi.daybreaklib.api.client.animation.transform.TransformInterpolator;
import net.minecraft.world.phys.Vec3;

public final class LSPlayerAnimations {
    public static final KeyframeAnimation<Transform> DODGE = KeyframeAnimation.<Transform>builder()
            .add(0.0f,  Transform.IDENTITY)
            .add(0.75f, Transform.size(new Vec3(0.45, 0.8, 0.45)))
            .add(1.5f,  Transform.ZERO)
            .add(6.0f,  Transform.IDENTITY)
            .interpolator(new TransformInterpolator())
            .build();
}
