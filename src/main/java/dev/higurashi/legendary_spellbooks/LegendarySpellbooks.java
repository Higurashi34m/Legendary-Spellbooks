package dev.higurashi.legendary_spellbooks;

import com.mojang.logging.LogUtils;
import dev.higurashi.daybreaklib.DaybreakLib;
import dev.higurashi.legendary_spellbooks.registry.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(LegendarySpellbooks.MOD_ID)
public class LegendarySpellbooks {
    public static final String MOD_ID = "legendary_spellbooks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LegendarySpellbooks(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        DaybreakLib.init(MOD_ID, modEventBus);

        LSEffectRegistry.register(modEventBus);
        LSSpellRegistry.register(modEventBus);
        LSLootModifierRegistry.register(modEventBus);
    }
}
