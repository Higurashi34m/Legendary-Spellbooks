package dev.higurashi.legendary_spellbooks.common.mixin.helper;

public interface ISpellSourceFlag {
    void legendarySpellbooks$markSpell();
    boolean legendarySpellbooks$isFromSpell();

    void legendarySpellbooks$setDamage(float value);
    float legendarySpellbooks$getDamage();
}
