package dev.higurashi.legendary_spellbooks.datagen.client.lang;

import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import net.minecraft.data.PackOutput;

import java.util.Locale;

public class LSEnUsLanguageProvider extends BaseLanguageProvider {
    public LSEnUsLanguageProvider(PackOutput output) {
        super(output, Locale.US.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        // --------------------
        // SPELL
        // --------------------

        // Lightning
        addSpell(LSSpellRegistry.CLOUD_RAIL_SPELL, "Cloud Rail", "Conjure a cascading line of magical clouds in front of you. These clouds fall freely from the sky, exploding and dealing area damage upon impact with the ground.");

        // Death Attack
        addSpellDamageSource(LSSpellRegistry.CLOUD_RAIL_SPELL, "%1$s was engulfed and crushed by %2$s's clouds");
    }
}
