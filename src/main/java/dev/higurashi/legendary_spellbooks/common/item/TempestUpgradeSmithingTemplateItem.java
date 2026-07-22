package dev.higurashi.legendary_spellbooks.common.item;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class TempestUpgradeSmithingTemplateItem extends SmithingTemplateItem {
    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;

    private static final Component APPLIES_TO = Component.translatable(
            Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "smithing_template.stormmancer_upgrade.applies_to"))
    ).withStyle(DESCRIPTION_FORMAT);

    private static final Component INGREDIENTS = Component.translatable(
            Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "smithing_template.stormmancer_upgrade.ingredients"))
    ).withStyle(DESCRIPTION_FORMAT);

    private static final Component UPGRADE_TITLE = Component.translatable(
            Util.makeDescriptionId("upgrade", ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "stormmancer_upgrade"))
    ).withStyle(TITLE_FORMAT);

    private static final List<ResourceLocation> BASE_ICONS = List.of(
            ResourceLocation.tryParse("item/empty_armor_slot_helmet"),
            ResourceLocation.tryParse("item/empty_armor_slot_chestplate"),
            ResourceLocation.tryParse("item/empty_armor_slot_leggings"),
            ResourceLocation.tryParse("item/empty_armor_slot_boots")
    );

    private static final List<ResourceLocation> MATERIAL_ICONS = List.of(
            ResourceLocation.tryParse("item/empty_slot_ingot")
    );

    public TempestUpgradeSmithingTemplateItem() {
        super(APPLIES_TO, INGREDIENTS, UPGRADE_TITLE, Component.literal(""), Component.literal(""), BASE_ICONS, MATERIAL_ICONS);
    }
}
