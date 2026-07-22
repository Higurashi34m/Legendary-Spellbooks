package dev.higurashi.legendary_spellbooks.common.tag;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class LSTags {
    public static final TagKey<Item> ANNIHILATION_FOCUS = ItemTags.create(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation_focus"));
}
