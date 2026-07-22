package dev.higurashi.legendary_spellbooks.registry;

import dev.higurashi.daybreaklib.api.annotation.AutoRegister;
import dev.higurashi.daybreaklib.api.registry.AttributeRegistryManager;
import dev.higurashi.daybreaklib.api.registry.reference.AttributeReference;
import dev.higurashi.daybreaklib_iss.api.registry.ISSRegistryHelper;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;

@AutoRegister
public class LSAttributeRegistry {
    public static final AttributeRegistryManager ATTRIBUTES = new AttributeRegistryManager(LegendarySpellbooks.MOD_ID);

    public static final AttributeReference ANNIHILATION_SPELL_POWER = ISSRegistryHelper.spellPower(ATTRIBUTES, "annihilation");
    public static final AttributeReference ANNIHILATION_MAGIC_RESIST = ISSRegistryHelper.magicResist(ATTRIBUTES, "annihilation");
}
