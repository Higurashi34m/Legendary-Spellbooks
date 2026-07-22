package dev.higurashi.legendary_spellbooks.common.tag;

import dev.higurashi.daybreaklib.api.common.tag.TagManager;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.Curios;

public class LSTags {
    private static final TagManager TAGS = new TagManager(LegendarySpellbooks.MOD_ID);

    // Item
    public static final TagKey<Item> ANNIHILATION_FOCUS = TAGS.createItemTag("annihilation_focus");
    public static final TagKey<Item> SPELLBOOK_SLOT = TAGS.createItemTag(ResourceLocation.fromNamespaceAndPath(Curios.MODID, "spellbook"));

    // DamageType
    public static final TagKey<DamageType> ANNIHILATION_MAGIC = TAGS.createDamageTypeTag("annihilation_magic");
    public static final TagKey<DamageType> BYPASS_ANNIHILATORS_PROTOCOL = TAGS.createDamageTypeTag("bypass_annihilators_protocol");
}
