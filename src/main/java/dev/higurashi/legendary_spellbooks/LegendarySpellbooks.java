package dev.higurashi.legendary_spellbooks;

import com.mojang.logging.LogUtils;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(LegendarySpellbooks.MOD_ID)
public class LegendarySpellbooks {
    public static final String MOD_ID = "legendary_spellbooks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LegendarySpellbooks() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LSEntityRegistry.register(modEventBus);
        LSSpellRegistry.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
