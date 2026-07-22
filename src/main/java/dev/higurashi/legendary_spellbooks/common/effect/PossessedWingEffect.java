package dev.higurashi.legendary_spellbooks.common.effect;

import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class PossessedWingEffect extends MagicMobEffect {
    public PossessedWingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xEC5353);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "28d237e1-27eb-4f4f-b338-f73582ff777c", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(ForgeMod.STEP_HEIGHT_ADDITION.get(), "4767f61f-076c-41ca-99b5-706996e5b824", 0.25, AttributeModifier.Operation.ADDITION);
    }
}
