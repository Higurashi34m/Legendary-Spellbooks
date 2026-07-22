package dev.higurashi.legendary_spellbooks.registries;

import com.mojang.serialization.Codec;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.loot.modifier.ItemLootModifier;
import dev.higurashi.legendary_spellbooks.common.loot.modifier.SpellScrollLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LSLootModifierRegistry {
    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, LegendarySpellbooks.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> SPELL_SCROLL =
            LOOT_MODIFIERS.register("spell_scroll", () -> SpellScrollLootModifier.CODEC);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ITEM_DROP =
            LOOT_MODIFIERS.register("item_drop", () -> ItemLootModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIERS.register(eventBus);
    }
}

