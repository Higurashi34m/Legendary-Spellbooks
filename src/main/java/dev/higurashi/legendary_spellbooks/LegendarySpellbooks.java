package dev.higurashi.legendary_spellbooks;

import com.mojang.logging.LogUtils;
import dev.higurashi.legendary_spellbooks.registries.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(LegendarySpellbooks.MOD_ID)
public class LegendarySpellbooks {
    public static final String MOD_ID = "legendary_spellbooks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LegendarySpellbooks(IEventBus eventBus, ModContainer container) {
        LSArmorMaterialRegistry.register(eventBus);
        LSAttributeRegistry.register(eventBus);
        LSCreativeTabRegistry.register(eventBus);
        LSEffectRegistry.register(eventBus);
        LSEntityRegistry.register(eventBus);
        LSItemRegistry.register(eventBus);
        LSLootModifierRegistry.register(eventBus);
        LSSpellRegistry.register(eventBus);
        LSSchoolRegistry.register(eventBus);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
