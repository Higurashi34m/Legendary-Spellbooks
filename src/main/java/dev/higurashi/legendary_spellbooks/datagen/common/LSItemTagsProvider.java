package dev.higurashi.legendary_spellbooks.datagen.common;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import io.redspace.ironsspellbooks.compat.Curios;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.CompletableFuture;

public class LSItemTagsProvider extends ItemTagsProvider {
    public LSItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper helper) {
        super(output, provider, blockTags, LegendarySpellbooks.MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        TagKey<Item> CURIOS = ItemTags.create(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, Curios.SPELLBOOK_SLOT));

        this.tag(CURIOS).add(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get());
    }
}
