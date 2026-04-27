package dev.higurashi.legendary_spellbooks.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AnnihilationResonanceEffect extends MobEffect {
    private static final String ATTACK_DAMAGE_UUID = "c6c542d4-ad08-42c2-9371-4ddb8477d556";
    private static final String ARMOR_UUID = "e9f8f3ae-5164-47b5-8b02-e44dde0dc85e";

    public AnnihilationResonanceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x3F9877);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID, -0.25D, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR, ARMOR_UUID, -0.25D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount();
    }
}
