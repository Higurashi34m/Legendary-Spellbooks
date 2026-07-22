package dev.higurashi.legendary_spellbooks.registry;

import dev.higurashi.daybreaklib.api.annotation.AutoRegister;
import dev.higurashi.daybreaklib.api.registry.DamageTypeRegistryManager;
import dev.higurashi.daybreaklib.api.registry.reference.DamageTypeReference;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import net.minecraft.world.damagesource.DamageScaling;

@AutoRegister
public class LSDamageTypeRegistry {
    private static final DamageTypeRegistryManager DAMAGE_TYPES = new DamageTypeRegistryManager(LegendarySpellbooks.MOD_ID);

    public static final DamageTypeReference ANNIHILATION_MAGIC = DAMAGE_TYPES.create("annihilation_magic", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.0f).build();
}
