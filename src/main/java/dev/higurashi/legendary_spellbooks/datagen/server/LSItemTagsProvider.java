package dev.higurashi.legendary_spellbooks.datagen.server;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.tags.LSTags;
import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import io.redspace.ironsspellbooks.util.ModTags;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class LSItemTagsProvider extends ItemTagsProvider {
    public LSItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper helper) {
        super(output, provider, blockTags, LegendarySpellbooks.MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        this.tag(ModTags.SPELLBOOK_CURIO).add(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get());
        this.tag(ModTags.SPELLBOOK_CURIO).add(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get());

        this.tag(LSTags.ANNIHILATION_FOCUS).add(ModItems.BOTTLE_OF_ANNIHILATION.get());
        this.tag(ModTags.SCHOOL_FOCUS).addTag(LSTags.ANNIHILATION_FOCUS);
    }
}
