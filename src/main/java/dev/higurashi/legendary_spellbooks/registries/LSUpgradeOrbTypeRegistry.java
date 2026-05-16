package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import io.redspace.ironsspellbooks.item.armor.UpgradeOrbType;
import io.redspace.ironsspellbooks.registries.UpgradeOrbTypeRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class LSUpgradeOrbTypeRegistry {
    public static ResourceKey<UpgradeOrbType> ANNIHILATION_SPELL_POWER = ResourceKey.create(UpgradeOrbTypeRegistry.UPGRADE_ORB_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation_power"));
}
