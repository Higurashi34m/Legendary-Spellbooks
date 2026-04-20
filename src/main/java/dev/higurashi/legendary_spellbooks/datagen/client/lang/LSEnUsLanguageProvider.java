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
        addSpell(LSSpellRegistry.CLOUD_RING_SPELL, "Cloud Ring", "Conjure multiple expanding rings of magical clouds centered on your position. These clouds descend from the sky in a rhythmic cascade, dealing heavy area damage upon impacting the ground.");
        addSpell(LSSpellRegistry.NIMBUS_ARRAY_SPELL, "Nimbus Array", "Summon a row of thunderclouds in front of you that continuously strike lightning upon anything beneath them for the duration of the spell.");
        addSpell(LSSpellRegistry.TRIPLE_NIMBUS_ARRAY_SPELL, "Triple Nimbus Array", "Manifest three rows of thunderclouds in a fanned formation. These clouds continuously strike lightning upon anything beneath them for the duration of the spell.");
        addSpell(LSSpellRegistry.THUNDER_FANBURST_SPELL, "Thunder Fanburst", "Unleash a fanned burst of lightning bolts that travel outwards in multiple directions. These bolts pierce through all creatures in their path.");

        // Death Attack
        addSpellDamageSource(LSSpellRegistry.CLOUD_RAIL_SPELL, "%1$s was engulfed and crushed by %2$s's clouds");

        // UI
        addUi("nimbus_count", "%d Nimbus");
    }
}
