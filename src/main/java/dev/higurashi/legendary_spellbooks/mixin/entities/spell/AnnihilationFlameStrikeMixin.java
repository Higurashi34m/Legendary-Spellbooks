package dev.higurashi.legendary_spellbooks.mixin.entities.spell;

import dev.higurashi.legendary_spellbooks.common.mixin.helper.ISpellSourceFlag;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationFlameStrike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = AnnihilationFlameStrike.class, remap = false)
public abstract class AnnihilationFlameStrikeMixin implements ISpellSourceFlag {
    @Unique private boolean legendarySpellbooks$isFromSpell = false;
    @Unique private float legendarySpellbooks$healthDamageMultiplier = 0;

    @ModifyConstant(method = "damage", constant = @Constant(floatValue = 0.03F))
    private float modifyDamage(float constant) {
        if (legendarySpellbooks$isFromSpell()) return legendarySpellbooks$getDamage();
        else return constant;
    }

    @Override public void legendarySpellbooks$markSpell() { this.legendarySpellbooks$isFromSpell = true; }
    @Override public boolean legendarySpellbooks$isFromSpell() { return legendarySpellbooks$isFromSpell; }

    @Override public void legendarySpellbooks$setDamage(float value) { this.legendarySpellbooks$healthDamageMultiplier = value; }
    @Override public float legendarySpellbooks$getDamage() { return this.legendarySpellbooks$healthDamageMultiplier; }
}
