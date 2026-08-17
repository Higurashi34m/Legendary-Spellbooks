package dev.higurashi.legendary_spellbooks;

import com.mojang.logging.LogUtils;
import dev.higurashi.legendary_spellbooks.config.CommonConfig;
import dev.higurashi.legendary_spellbooks.registries.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(LegendarySpellbooks.MOD_ID)
public class LegendarySpellbooks {
    public static final String MOD_ID = "legendary_spellbooks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LegendarySpellbooks() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LSAttributeRegistry.register(modEventBus);
        LSCreativeTabRegistry.register(modEventBus);
        LSEffectRegistry.register(modEventBus);
        LSEntityRegistry.register(modEventBus);
        LSItemRegistry.register(modEventBus);
        LSSchoolRegistry.register(modEventBus);
        LSSpellRegistry.register(modEventBus);
        LSLootModifierRegistry.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, String.format("%s.toml", LegendarySpellbooks.MOD_ID));
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, path);
    }
}
