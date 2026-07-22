package dev.higurashi.legendary_spellbooks.registry;

import dev.higurashi.daybreaklib.api.annotation.AutoRegister;
import dev.higurashi.daybreaklib.api.registry.CreativeTabRegistryManager;
import dev.higurashi.daybreaklib.api.registry.reference.CreativeTabReference;
import dev.higurashi.daybreaklib.api.util.TextUtils;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

@AutoRegister
public class LSCreativeTabRegistry {
    public static final CreativeTabRegistryManager CREATIVE_TABS = new CreativeTabRegistryManager(LegendarySpellbooks.MOD_ID);

    public static final CreativeTabReference LS_ITEMS = CREATIVE_TABS.create("ls_items")
            .setIconItem(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM)
            .setBeforeTab(IronsSpellbooks.id("spellbook_scrolls"))
            .addItems(LSItemRegistry.getEntries())
            .build();

    public static final RegistryObject<CreativeModeTab> LS_SCROLLS = CREATIVE_TABS.register("ls_scrolls", () -> CreativeModeTab.builder()
            .title(TextUtils.creativeTabKey(LegendarySpellbooks.id("scrolls")).translate())
            .icon(() -> new ItemStack(ItemRegistry.SCROLL.get()))
            .displayItems((features, entries) -> LSSpellRegistry.SPELLS.getEntries().forEach(registry -> {
                AbstractSpell spell = registry.get();
                for (int level = spell.getMinLevel(); level <= spell.getMaxLevel(); level++) {
                    ItemStack stack = new ItemStack(ItemRegistry.SCROLL.get());
                    ISpellContainer.createScrollContainer(spell, level, stack);
                    entries.accept(stack);
                }
            }))
            .withTabsBefore(LS_ITEMS.getId())
            .build());
}
