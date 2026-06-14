package dev.higurashi.legendary_spellbooks.registries;

import com.mojang.serialization.MapCodec;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.loots.modifier.ItemLootModifier;
import dev.higurashi.legendary_spellbooks.common.loots.modifier.SpellScrollLootModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LSLootModifierRegistry {
    private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, LegendarySpellbooks.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<SpellScrollLootModifier>> SPELL_SCROLL =
            LOOT_MODIFIERS.register("spell_scroll", () -> SpellScrollLootModifier.CODEC);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ItemLootModifier>> ITEM_DROP =
            LOOT_MODIFIERS.register("item_drop", () -> ItemLootModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIERS.register(eventBus);
    }
}

