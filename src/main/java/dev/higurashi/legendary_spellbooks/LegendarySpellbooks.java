package dev.higurashi.legendary_spellbooks;

import dev.higurashi.daybreaklib.DaybreakLib;
import dev.higurashi.legendary_spellbooks.registry.LSEffectRegistry;
import dev.higurashi.legendary_spellbooks.registry.LSLootModifierRegistry;
import dev.higurashi.legendary_spellbooks.registry.LSSpellRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LegendarySpellbooks.MOD_ID)
public class LegendarySpellbooks {
    public static final String MOD_ID = "legendary_spellbooks";

    public LegendarySpellbooks(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        DaybreakLib.init(MOD_ID, modEventBus);

        LSEffectRegistry.register(modEventBus);
        LSSpellRegistry.register(modEventBus);
        LSLootModifierRegistry.register(modEventBus);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
