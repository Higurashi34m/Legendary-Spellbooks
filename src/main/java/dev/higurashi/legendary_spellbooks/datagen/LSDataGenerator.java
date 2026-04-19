package dev.higurashi.legendary_spellbooks.datagen;

import dev.higurashi.legendary_spellbooks.datagen.client.lang.LSEnUsLanguageProvider;
import dev.higurashi.legendary_spellbooks.datagen.client.lang.LSJaJpLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LSDataGenerator {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(event.includeClient(), new LSEnUsLanguageProvider(output));
        generator.addProvider(event.includeClient(), new LSJaJpLanguageProvider(output));
    }
}
