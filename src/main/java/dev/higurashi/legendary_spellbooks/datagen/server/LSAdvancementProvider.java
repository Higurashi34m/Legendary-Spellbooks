package dev.higurashi.legendary_spellbooks.datagen.server;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class LSAdvancementProvider extends ForgeAdvancementProvider {
    public LSAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, helper, List.of(new Generator()));
    }

    public static class Generator implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.@NotNull Provider provider, @NotNull Consumer<Advancement> saver, @NotNull ExistingFileHelper helper) {
            Advancement root = Advancement.Builder.advancement()
                    .display(
                            LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get(),
                            Component.translatable("advancement.legendary_spellbooks.title"),
                            Component.translatable("advancement.legendary_spellbooks.description"),
                            ResourceLocation.withDefaultNamespace("textures/block/obsidian.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion("root", PlayerTrigger.TriggerInstance.tick())
                    .save(saver, ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "main/root"), helper);

            Advancement stormboundGrimoireAdvancement = Advancement.Builder.advancement()
                    .parent(root)
                    .display(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get(), Component.translatable("advancement.legendary_spellbooks.stormbound_grimoire.title"), Component.translatable("advancement.legendary_spellbooks.stormbound_grimoire.description"), null, FrameType.TASK, true, true, false)
                    .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "main/get_stormbound_grimoire"), helper);

            Advancement annihilatorsProtocolAdvancement = Advancement.Builder.advancement()
                    .parent(root)
                    .display(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get(), Component.translatable("advancement.legendary_spellbooks.annihilator_protocol.title"), Component.translatable("advancement.legendary_spellbooks.annihilator_protocol.description"), null, FrameType.TASK, true, true, false)
                    .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get()))
                    .save(saver, ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "main/get_annihilators_protocol"), helper);
        }
    }
}