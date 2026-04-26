package dev.higurashi.legendary_spellbooks.datagen.common;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class LSBlockTagsProvider extends BlockTagsProvider {
    public LSBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, LegendarySpellbooks.MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {

    }
}
