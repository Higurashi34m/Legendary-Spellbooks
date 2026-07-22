package dev.higurashi.legendary_spellbooks.registry;

import dev.higurashi.daybreaklib.api.annotation.AutoRegister;
import dev.higurashi.daybreaklib.api.client.animation.transform.Transform;
import dev.higurashi.daybreaklib.api.registry.AnimationRegistryManager;
import dev.higurashi.daybreaklib.api.registry.reference.AnimationReference;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.client.animation.LSEntityAnimations;
import dev.higurashi.legendary_spellbooks.client.animation.LSPlayerAnimations;

@AutoRegister
public class LSAnimationRegistry {
    private static final AnimationRegistryManager ANIMATIONS = new AnimationRegistryManager(LegendarySpellbooks.MOD_ID);

    public static final AnimationReference<Transform> ANNIHILATOR_DODGE = ANIMATIONS.register("annihilator_dodge", LSPlayerAnimations.DODGE);
    public static final AnimationReference<Transform> ICE_SPIKE = ANIMATIONS.register("ice_spike", LSEntityAnimations.ICE_SPIKE);

}
