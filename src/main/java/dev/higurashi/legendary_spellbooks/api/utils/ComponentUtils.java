package dev.higurashi.legendary_spellbooks.api.utils;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ComponentUtils {
    public static MutableComponent getUIComponent(String modId, String componentName, Object... args) {
        return Component.translatable("ui." + modId + "." + componentName, args);
    }

    public static MutableComponent getUIComponent(String componentName, Object... args) {
        return getUIComponent(LegendarySpellbooks.MOD_ID, componentName, args);
    }
}
