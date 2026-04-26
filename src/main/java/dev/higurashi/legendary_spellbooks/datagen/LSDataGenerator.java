package dev.higurashi.legendary_spellbooks.datagen;

import dev.higurashi.legendary_spellbooks.datagen.client.LSItemModelProvider;
import dev.higurashi.legendary_spellbooks.datagen.client.lang.LSEnUsLanguageProvider;
import dev.higurashi.legendary_spellbooks.datagen.client.lang.LSJaJpLanguageProvider;
import dev.higurashi.legendary_spellbooks.datagen.common.LSAdvancementProvider;
import dev.higurashi.legendary_spellbooks.datagen.common.LSBlockTagsProvider;
import dev.higurashi.legendary_spellbooks.datagen.common.LSItemTagsProvider;
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
        generator.addProvider(event.includeClient(), new LSItemModelProvider(output, helper));

        // Server
        LSBlockTagsProvider blockTagsProvider = new LSBlockTagsProvider(output, provider, helper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new LSItemTagsProvider(output, provider, blockTagsProvider.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new LSAdvancementProvider(output, provider, helper));
    }
}
