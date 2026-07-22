package dev.higurashi.legendary_spellbooks.datagen;

import dev.higurashi.legendary_spellbooks.datagen.client.lang.LSEnUsLanguageProvider;
import dev.higurashi.legendary_spellbooks.datagen.client.lang.LSJaJpLanguageProvider;
import dev.higurashi.legendary_spellbooks.datagen.server.LSAdvancementProvider;
import dev.higurashi.legendary_spellbooks.datagen.server.tag.LSItemTagsProvider;
import dev.higurashi.legendary_spellbooks.datagen.server.LSRecipeProvider;
import dev.higurashi.legendary_spellbooks.datagen.server.loot.LSSpellScrollLootProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LSDataGenerator {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

        // Client
        generator.addProvider(event.includeClient(), new LSEnUsLanguageProvider(output));
        generator.addProvider(event.includeClient(), new LSJaJpLanguageProvider(output));

        // Server
        generator.addProvider(event.includeServer(), new LSItemTagsProvider(output, provider, helper));
        generator.addProvider(event.includeServer(), new LSAdvancementProvider(output, provider, helper));
        generator.addProvider(event.includeServer(), new LSSpellScrollLootProvider(output));
        generator.addProvider(event.includeServer(), new LSRecipeProvider(output));
    }
}
