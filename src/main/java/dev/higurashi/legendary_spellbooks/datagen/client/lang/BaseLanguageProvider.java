package dev.higurashi.legendary_spellbooks.datagen.client.lang;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.function.Supplier;

public abstract class BaseLanguageProvider extends LanguageProvider {
    private static String modId = LegendarySpellbooks.MOD_ID;

    public BaseLanguageProvider(PackOutput output, String locale) {
        super(output, modId, locale);
    }

    @Override protected abstract void addTranslations();

    protected void addSpell(Supplier<? extends AbstractSpell> spellKey, String name, String descriptionName) {
        add(spellKey.get().getComponentId(), name);
        add(spellKey.get().getComponentId() + ".guide", descriptionName);
    }

    protected void addUi(String key, String name) {
        add("ui." + modId + "." + key, name);
    }

    protected void addTooltip(Item key, String name) {
        add(ComponentUtils.itemTooltip(key), name);
    }
    protected void addTooltip(String key, String name) {
        add("tooltip.legendary_spellbooks." + key, name);
    }

    protected void addDamageSource(String key, String name) {
        add("death.attack." + key, name);
    }
    protected void addSpellDamageSource(Supplier<? extends AbstractSpell> spellKey, String name) {
        addDamageSource(modId + "." + spellKey.get().getSpellName(), name);
    }

    public void addCreativeTab(String key, String name) {
        add("tab." + LegendarySpellbooks.MOD_ID + "." + key, name);
    }
}
