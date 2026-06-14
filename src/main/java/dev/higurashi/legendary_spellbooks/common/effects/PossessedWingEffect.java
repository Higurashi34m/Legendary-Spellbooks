package dev.higurashi.legendary_spellbooks.common.effects;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class PossessedWingEffect extends MagicMobEffect {
    public PossessedWingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xEC5353);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, LegendarySpellbooks.id("effect_possessed_wing"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.STEP_HEIGHT, LegendarySpellbooks.id("effect_possessed_wing"), 0.25, AttributeModifier.Operation.ADD_VALUE);
    }
}
