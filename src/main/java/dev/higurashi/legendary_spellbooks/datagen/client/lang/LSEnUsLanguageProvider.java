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

        // Ender
        addSpell(LSSpellRegistry.ANNIHILATION_BEAM_SPELL, "Annihilation Beam", "Channel a devastating beam of pure annihilation. While firing, you are locked in place. When aimed horizontally near the ground, the beam's overwhelming power causes it to scatter unstable energy orbs across the terrain.");
        addSpell(LSSpellRegistry.ANNIHILATION_BOMB_SPELL, "Annihilation Bomb", "Launch a concentrated orb of annihilation that detonates upon impact with a creature or the ground. The resulting explosion deals heavy damage and scatters a multitude of unstable energy orbs across the surrounding area.");
        addSpell(LSSpellRegistry.ANNIHILATION_SHOCKWAVE_SPELL, "Annihilation Shockwave", "Stomp the ground to release a fan-shaped shockwave of green annihilation fire. The flames erupt in successive waves, consuming the life force of all creatures caught in the tremor. Damage increases based on the target's maximum health.");
        addSpell(LSSpellRegistry.ANNIHILATION_GEYSER_SPELL, "Annihilation Geyser", "Create a massive gravitational well that pulls in nearby creatures. Upon completing the cast, a colossal geyser of destructive energy erupts, finishing with a devastating explosion.");

        // Fire
        addSpell(LSSpellRegistry.FLAME_EATER_SPELL, "Flame Eater", "Target multiple creatures to erupt a burst of fire at their feet. This spell fails to reach targets positioned too high above the ground.");
        addSpell(LSSpellRegistry.FLAME_SECTOR_SPELL, "Flame Sector", "Emit a radial burst of fire in equally spaced directions around you. These flames travel along the ground, erupting at the feet of creatures in their path. This spell fails to manifest if the caster are too high above the ground.");

        // Lightning
        addSpell(LSSpellRegistry.CLOUD_RAIL_SPELL, "Cloud Rail", "Conjure a cascading line of magical clouds in front of you. These clouds fall freely from the sky, exploding and dealing area damage upon impact with the ground.");
        addSpell(LSSpellRegistry.CLOUD_RING_SPELL, "Cloud Ring", "Conjure multiple expanding rings of magical clouds centered on your position. These clouds descend from the sky in a rhythmic cascade, dealing heavy area damage upon impacting the ground.");
        addSpell(LSSpellRegistry.NIMBUS_ARRAY_SPELL, "Nimbus Array", "Summon a row of thunderclouds in front of you that continuously strike lightning upon anything beneath them for the duration of the spell.");
        addSpell(LSSpellRegistry.TRIPLE_NIMBUS_ARRAY_SPELL, "Triple Nimbus Array", "Manifest three rows of thunderclouds in a fanned formation. These clouds continuously strike lightning upon anything beneath them for the duration of the spell.");
        addSpell(LSSpellRegistry.THUNDER_FANBURST_SPELL, "Thunder Fanburst", "Unleash a fanned burst of lightning bolts that travel outwards in multiple directions. These bolts pierce through all creatures in their path.");

        // Nature
        addSpell(LSSpellRegistry.OVERGROWN_SHOCKWAVE_SPELL, "Overgrown Shockwave", "Slam the ground to unleash a burst of toxic nature magic in a large radius around you, poisoning all creatures caught in the tremor.");

        // Death Attack
        addSpellDamageSource(LSSpellRegistry.CLOUD_RAIL_SPELL, "%1$s was engulfed and crushed by %2$s's clouds");
        addSpellDamageSource(LSSpellRegistry.FLAME_EATER_SPELL, "%1$s was consumed by the subterranean flames summoned by %2$s");

        // UI
        addUi("nimbus_count", "%d Nimbus");
        addUi("health_damage", "%s damage + %s%% of target's max HP");
    }
}
