package dev.higurashi.legendary_spellbooks.common.effects;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AnnihilationResonanceEffect extends MobEffect {
    public AnnihilationResonanceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x3F9877);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, IronsSpellbooks.id("effect_annihliation_resonance"), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, (lvl) -> -0.25D);
        this.addAttributeModifier(Attributes.ARMOR, IronsSpellbooks.id("effect_annihilation_resonance"), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, (lvl) -> -0.25D);
    }
}
