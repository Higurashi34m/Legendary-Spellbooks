package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class LSDamageTypeRegistry {
    public static final ResourceKey<DamageType> ANNIHILATION_MAGIC = ResourceKey.create(Registries.DAMAGE_TYPE, LegendarySpellbooks.id("annihilation_magic"));
}
