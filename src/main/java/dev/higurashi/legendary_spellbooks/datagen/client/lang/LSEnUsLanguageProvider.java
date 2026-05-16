package dev.higurashi.legendary_spellbooks.datagen.client.lang;

import dev.higurashi.legendary_spellbooks.registries.*;
import net.minecraft.data.PackOutput;

import java.util.Locale;

public class LSEnUsLanguageProvider extends BaseLanguageProvider {
    public LSEnUsLanguageProvider(PackOutput output) {
        super(output, Locale.US.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        // --------------------
        // ATTRIBUTE
        // --------------------
        addAttribute(LSAttributeRegistry.ANNIHILATION_SPELL_POWER, "Annihilation Spell Power");
        addAttribute(LSAttributeRegistry.ANNIHILATION_MAGIC_RESIST, "Annihilation Magic Resistance");

        // --------------------
        // CREATIVE TAB
        // --------------------
        addCreativeTab("equipments", "Legendary Spellbooks Equipments");
        addCreativeTab("scrolls", "Legendary Spellbooks Scrolls");

        // --------------------
        // ENTITY
        // --------------------
        addEntityType(LSEntityRegistry.SUMMONED_ANNIHILATION_PURSUER_ENTITY, "Summoned Annihilation Pursuer");
        addEntityType(LSEntityRegistry.SUMMONED_FLAMEBORN_GUARD_ENTITY, "Summoned FlameBorn Guard");
        addEntityType(LSEntityRegistry.SUMMONED_FLAMEBORN_WARRIOR_ENTITY, "Summoned FlameBorn Warrior");
        addEntityType(LSEntityRegistry.SUMMONED_HAUNTED_KNIGHT_ENTITY, "Summoned Haunted Knight");
        addEntityType(LSEntityRegistry.SUMMONED_HAUNTED_GUARD_ENTITY, "Summoned Haunted Guard");
        addEntityType(LSEntityRegistry.SUMMONED_SKELORAPTOR_ENTITY, "Summoned Skeloraptor");

        // --------------------
        // EFFECT
        // --------------------
        addEffect(LSEffectRegistry.ANNIHILATION_RESONANCE_EFFECT, "Annihilation Resonance");
        addEffect(LSEffectRegistry.BEAM_EFFECT, "Beam");
        addEffect(LSEffectRegistry.AMBUSH_THORNS_EFFECT, "Ambush Thorns");

        // --------------------
        // ITEM
        // --------------------
        addItem(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM, "Annihilator's Protocol");
        addItem(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM, "Stormbound Grimoire");
        addItem(LSItemRegistry.OBLIVIONMANCER_HAT, "Oblivionmancer Hat");
        addItem(LSItemRegistry.OBLIVIONMANCER_ROBE, "Oblivionmancer Robe");
        addItem(LSItemRegistry.OBLIVIONMANCER_LEGGINGS, "Oblivionmancer Leggings");
        addItem(LSItemRegistry.OBLIVIONMANCER_BOOTS, "Oblivionmancer Boots");
        addItem(LSItemRegistry.ANNIHILATION_RUNE, "Annihilation Rune");
        addItem(LSItemRegistry.ANNIHILATION_UPGRADE_ORB, "Annihilation Upgrade Orb");

        // --------------------
        // SPELL
        // --------------------

        // Ender
        addSpell(LSSpellRegistry.ANNIHILATION_BEAM_SPELL, "Annihilation Beam", "Channel a devastating beam of energy. While firing, you are locked in place. If aimed near the ground, the beam scatters small energy orbs on impact.");
        addSpell(LSSpellRegistry.ANNIHILATION_BOMB_SPELL, "Annihilation Bomb", "Launch a concentrated bomb of destructive energy that explodes on impact, dealing damage and scattering small energy orbs around the area.");
        addSpell(LSSpellRegistry.ANNIHILATION_SHOCKWAVE_SPELL, "Annihilation Shockwave", "Stomp the ground to release a fan-shaped shockwave of green fire. The waves deal damage based on the target's maximum health.");
        addSpell(LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL, "Annihilation Resonance", "Imbue yourself with an unstable frequency, reducing your defense and attack by 25%. For the duration, landing a critical hit triggers a powerful explosion at the target's location.");
        addSpell(LSSpellRegistry.ANNIHILATION_GEYSER_SPELL, "Annihilation Geyser", "Create a gravitational well that pulls in nearby creatures. Upon completion, a colossal geyser of energy erupts, dealing massive damage.");
        addSpell(LSSpellRegistry.SUMMON_FLAMEBORN_KNIGHTS_SPELL, "Summon Flameborn Knights", "Summon knights made of eternal fire to fight for you.");
        addSpell(LSSpellRegistry.RELEASE_RIFTWALKER_PREDATOR_SPELL, "Release Riftwalker Protocol", "Manifest an Annihilation Pursuer, a relentless hunter that tracks its target through dimensional rifts until they are destroyed.");

        // Evocation
        addSpell(LSSpellRegistry.SUMMON_HAUNTED_KNIGHTS_SPELL, "Summon Haunted Knights", "Cast to summon spectral knights that will follow you and strike at your foes.");

        // Fire
        addSpell(LSSpellRegistry.FLAME_EATER_SPELL, "Flame Eater", "Target multiple creatures to erupt bursts of fire at their feet. This spell fails if targets are too high above the ground.");
        addSpell(LSSpellRegistry.FLAME_SECTOR_SPELL, "Flame Sector", "Emit a radial burst of fire in equally spaced directions. The flames travel along the ground.");

        // Ice
        addSpell(LSSpellRegistry.GLACIER_ERUPTION_SPELL, "Glacier Eruption", "Conjure a line of ice wedges in front of you, freezing any creatures they strike.");
        addSpell(LSSpellRegistry.GLACIER_RINGBURST_SPELL, "Glacier Ringburst", "Conjure multiple expanding rings of ice centered on your position, freezing nearby creatures.");

        // Lightning
        addSpell(LSSpellRegistry.CLOUD_RAIL_SPELL, "Cloud Rail", "Conjure a line of magical clouds in the direction you are looking. These clouds fall and deal area damage upon impact.");
        addSpell(LSSpellRegistry.CLOUD_RING_SPELL, "Cloud Ring", "Conjure multiple rings of magical clouds centered on your position. These clouds fall and deal area damage upon impact.");
        addSpell(LSSpellRegistry.NIMBUS_ARRAY_SPELL, "Nimbus Array", "Summon a row of thunderclouds in front of you that continuously strike lightning beneath them.");
        addSpell(LSSpellRegistry.TRIPLE_NIMBUS_ARRAY_SPELL, "Triple Nimbus Array", "Summon three rows of thunderclouds in a fan shape that continuously strike lightning beneath them.");
        addSpell(LSSpellRegistry.THUNDER_FANBURST_SPELL, "Thunder Fanburst", "Unleash a burst of lightning bolts that travel outwards in a fan shape, piercing through creatures in their path.");
        addSpell(LSSpellRegistry.TORNADO_SPELL, "Tornado", "Summon a violent vortex that pulls in nearby creatures and renders them immobile.");
        addSpell(LSSpellRegistry.QUAD_TORNADO_SPELL, "Quad Tornado", "Unleash multiple tornadoes in a radial burst, pulling in and immobilizing creatures in their path.");
        addSpell(LSSpellRegistry.ENERGY_BEAM_SPELL, "Energy Beam", "Ascend into the air to unleash a high-output laser. The energy consumption leaves the caster stunned for a short duration after the beam ends.");
        addSpell(LSSpellRegistry.CUMULO_CHARGE_SPELL, "Cumulo Charge", "Summon a phantom of the 'Cumulonimbus' to charge forward. If locked on, the phantom will pursue its target.");

        // Nature
        addSpell(LSSpellRegistry.AMBUSH_THORNS_SPELL, "Ambush Thorns", "Imbue yourself with thorns, acting as the Thorns enchantment to damage attackers.");
        addSpell(LSSpellRegistry.OVERGROWN_SHOCKWAVE_SPELL, "Overgrown Shockwave", "Slam the ground to unleash a burst of toxic spores, poisoning nearby creatures in a wide area.");
        addSpell(LSSpellRegistry.FOSSILIZED_FURY_SPELL, "Fossilized Fury", "Conjure a pack of skeletal raptors to hunt and shred your foes. Higher spell power increases the number of raptors summoned.");

        // Death Attack
        addSpellDamageSource(LSSpellRegistry.CLOUD_RAIL_SPELL, "%1$s was engulfed and crushed by %2$s's clouds");
        addSpellDamageSource(LSSpellRegistry.FLAME_EATER_SPELL, "%1$s was consumed by the subterranean flames summoned by %2$s");
        addSpellDamageSource(LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL, "%1$s was obliterated by a resonant annihilation blast from %2$s");

        // UI
        addUi("nimbus_count", "%d Nimbus");
        addUi("health_damage", "%s damage + %s%% of target's max HP");
        addUi("thorn_damage", "%d thorn damage + 20%% of damage received");
        addUi("hp", "%d %s's HP");

        addUi("cast_error_exclusive_book", "%s can only be cast with %s!");

        // --------------------
        // Tooltip
        // --------------------
        addTooltip("on_sunny", "In sunny weather:");
        addTooltip("on_thunder", "During thunderstorms:");

        addTooltip(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get(), "Grants a 10% chance to teleport away upon taking damage, effectively neutralizing the threat.");
        addTooltip(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get(), "This spellbook calls forth thunder, causing rain to swell into storms.");

        // --------------------
        // Advancement
        // --------------------
        addAdvancement("stormbound_grimoire", "Binary Skies", "Acquire the Stormbound Grimoire");
        addAdvancement("annihilator_protocol", "If You Gaze Into the Abyss...", "Acquire the Annihilator's Protocol");

        add("advancement.legendary_spellbooks.title", "Legendary Spellbooks");
        add("advancement.legendary_spellbooks.description", "Legendary Spellbooks");

        addSchool(LSSchoolRegistry.ANNIHILATION, "Annihilation");
    }
}
