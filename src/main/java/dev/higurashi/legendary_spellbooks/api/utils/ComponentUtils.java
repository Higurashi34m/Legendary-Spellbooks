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

    // Time
    public static float ticksToSeconds(int ticks) {
        return (float) ticks / 20;
    }

    public static int secondsToTicks(float seconds) {
        return (int) (seconds * 20);
    }

    public static String ticksToSecondsString(int ticks) {
        String seconds = Float.toString(ticksToSeconds(ticks));

        seconds = seconds.replaceAll("0+$", "");
        seconds = seconds.replaceAll("\\.$", "");

        return seconds + "s";
    }

    // Format
    public static String format1f(float value) {
        return String.format("%.1f", value);
    }

    public static String format2f(float value) {
        return String.format("%.2f", value);
    }
}
