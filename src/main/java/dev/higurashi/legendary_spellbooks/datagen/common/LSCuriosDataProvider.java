package dev.higurashi.legendary_spellbooks.datagen.common;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class LSCuriosDataProvider extends CuriosDataProvider {
    public LSCuriosDataProvider(PackOutput output, ExistingFileHelper helper, CompletableFuture<HolderLookup.Provider> provider) {
        super(LegendarySpellbooks.MOD_ID, output, helper, provider);
    }

    @Override
    public void generate(HolderLookup.Provider provider, ExistingFileHelper helper) {
        this.createSlot("head").size(1).addCosmetic(true);

        this.createEntities("player").addPlayer().addSlots("head");
    }
}