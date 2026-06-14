package dev.higurashi.legendary_spellbooks.datagen.server;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LSAdvancementProvider extends AdvancementProvider {
    public LSAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, helper, List.of((provider1, consumer, helper1) -> {
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(
                            LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get(),
                            Component.translatable("advancement.legendary_spellbooks.title"),
                            Component.translatable("advancement.legendary_spellbooks.description"),
                            ResourceLocation.withDefaultNamespace("textures/block/obsidian.png"),
                            AdvancementType.TASK, false, false, false)
                    .addCriterion("root", PlayerTrigger.TriggerInstance.tick())
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "main/root"), helper1);

            AdvancementHolder stormboundGrimoireAdvancement = Advancement.Builder.advancement()
                    .parent(root)
                    .display(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get(), Component.translatable("advancement.legendary_spellbooks.stormbound_grimoire.title"), Component.translatable("advancement.legendary_spellbooks.stormbound_grimoire.description"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "main/get_stormbound_grimoire"), helper1);

            AdvancementHolder annihilatorsProtocolAdvancement = Advancement.Builder.advancement()
                    .parent(root)
                    .display(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get(), Component.translatable("advancement.legendary_spellbooks.annihilator_protocol.title"), Component.translatable("advancement.legendary_spellbooks.annihilator_protocol.description"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "main/get_annihilators_protocol"), helper1);

            AdvancementHolder oblivionmancerArmorAdvancement = Advancement.Builder.advancement()
                    .parent(root)
                    .display(LSItemRegistry.STORMMANCER_HOOD.get(), Component.translatable("advancement.legendary_spellbooks.stormmancer_armor.title"), Component.translatable("advancement.legendary_spellbooks.stormmancer_armor_description"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(LSItemRegistry.STORMMANCER_HOOD.get(), LSItemRegistry.STORMMANCER_ROBE.get(), LSItemRegistry.STORMMANCER_LEGGINGS.get(), LSItemRegistry.STORMMANCER_BOOTS.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "main/get_stormmancer_armor"), helper1);
        }));
    }
}