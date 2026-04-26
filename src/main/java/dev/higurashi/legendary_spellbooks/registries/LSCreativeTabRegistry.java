package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class LSCreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LegendarySpellbooks.MOD_ID);
    public static void register(IEventBus modEventBus) { TABS.register(modEventBus); }

    public static final RegistryObject<CreativeModeTab> LS_EQUIPMENTS_TAB =
            TABS.register("ls_equipments", () -> CreativeModeTab.builder()
                    .title(Component.translatable("tab." + LegendarySpellbooks.MOD_ID + ".equipments"))
                    .icon(() -> new ItemStack(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get()))
                    .displayItems((enabledFeatures, entries) -> LSItemRegistry.ITEMS.getEntries().forEach(item -> entries.accept(item.get())))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "spellbook_scrolls"))
                    .build());

    public static final RegistryObject<CreativeModeTab> LS_SCROLLS_TAB =
            TABS.register("ls_scrolls", () -> CreativeModeTab.builder()
                    .title(Component.translatable("tab." + LegendarySpellbooks.MOD_ID + ".scrolls"))
                    .icon(() -> new ItemStack(ItemRegistry.SCROLL.get()))
                    .displayItems((features, entries) -> LSSpellRegistry.SPELLS.getEntries().forEach(registry -> {
                        AbstractSpell spell = registry.get();
                        for (int level = spell.getMinLevel(); level <= spell.getMaxLevel(); level++) {
                            ItemStack stack = new ItemStack(ItemRegistry.SCROLL.get());
                            ISpellContainer.createScrollContainer(spell, level, stack);
                            entries.accept(stack);
                        }
                    }))
                    .withTabsBefore(LS_EQUIPMENTS_TAB.getKey())
                    .build());
}
