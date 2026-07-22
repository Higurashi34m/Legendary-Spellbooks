package dev.higurashi.legendary_spellbooks.registry;

import dev.higurashi.daybreaklib.api.annotation.AutoRegister;
import dev.higurashi.daybreaklib_iss.api.common.upgrade_orb.UpgradeOrbTypeBuilder;
import dev.higurashi.daybreaklib_iss.api.registry.UpgradeOrbTypeRegistryManager;
import dev.higurashi.daybreaklib_iss.api.registry.reference.UpgradeOrbTypeReference;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

@AutoRegister
public class LSUpgradeOrbTypeRegistry {
    private static final UpgradeOrbTypeRegistryManager UPGRADE_ORB_TYPES = new UpgradeOrbTypeRegistryManager(LegendarySpellbooks.MOD_ID);

    public static final UpgradeOrbTypeReference ANNIHILATION_POWER = UPGRADE_ORB_TYPES.create("annihilation_power",
            id -> new UpgradeOrbTypeBuilder(id, 0.05, AttributeModifier.Operation.MULTIPLY_BASE)
                    .setAttribute(LSAttributeRegistry.ANNIHILATION_SPELL_POWER)
                    .setItem(LSItemRegistry.ANNIHILATION_UPGRADE_ORB).build());
}
