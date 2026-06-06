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

        // Damage Source
        addSpellDamageSource(LSSpellRegistry.ANNIHILATION_ARROW_SPELL, "%1$s was pierced and obliterated by %2$s's annihilation arrow");
        addSpellDamageSource(LSSpellRegistry.ANNIHILATION_BEAM_SPELL, "%1$s was turned to ash by %2$s's devastating beam");
        addSpellDamageSource(LSSpellRegistry.ANNIHILATION_BOMB_SPELL, "%1$s was caught in the torrent of annihilation unleashed by %2$s and utterly perished");

        // --------------------
        // ENTITY
        // --------------------
        addEntityType(LSEntityRegistry.SUMMONED_ANNIHILATION_PURSUER_ENTITY, "Summoned Annihilation Pursuer");
        addEntityType(LSEntityRegistry.SUMMONED_FLAMEBORN_GUARD_ENTITY, "Summoned FlameBorn Guard");
        addEntityType(LSEntityRegistry.SUMMONED_FLAMEBORN_WARRIOR_ENTITY, "Summoned FlameBorn Warrior");
        addEntityType(LSEntityRegistry.SUMMONED_HAUNTED_KNIGHT_ENTITY, "Summoned Haunted Knight");
        addEntityType(LSEntityRegistry.SUMMONED_HAUNTED_GUARD_ENTITY, "Summoned Haunted Guard");
        addEntityType(LSEntityRegistry.SUMMONED_SKELORAPTOR_ENTITY, "Summoned Skeloraptor");
        addEntityType(LSEntityRegistry.SUMMONED_FRACTURED_APOSTLE_ENTITY, "Summoned Fractured Apostle");

        // --------------------
        // EFFECT
        // --------------------
        addEffect(LSEffectRegistry.ANNIHILATION_RESONANCE_EFFECT, "Annihilation Resonance");
        addEffect(LSEffectRegistry.BEAM_EFFECT, "Beam");
        addEffect(LSEffectRegistry.AMBUSH_THORNS_EFFECT, "Ambush Thorns");
        addEffect(LSEffectRegistry.FLAMEBORN_DASH_EFFECT, "Flameborn Drift");

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
        addItem(LSItemRegistry.STORMMANCER_HOOD, "Stormmancer Hood");
        addItem(LSItemRegistry.STORMMANCER_ROBE, "Stormmancer Robe");
        addItem(LSItemRegistry.STORMMANCER_LEGGINGS, "Stormmancer Leggings");
        addItem(LSItemRegistry.STORMMANCER_BOOTS, "Stormmancer Boots");

        // --------------------
        // SPELL
        // --------------------

        // Annihilation
        addSpell(LSSpellRegistry.ANNIHILATION_ARROW_SPELL, "Annihilation Arrow", "Fire an arrow imbued with potent energy, triggering an explosion upon impact.");
        addSpell(LSSpellRegistry.ANNIHILATION_BEAM_SPELL, "Annihilation Beam", "Channel a devastating beam of energy. While firing, you are locked in place. When fired nearly parallel to the ground, it releases energy orbs spaced at equal distances along its path.");
        addSpell(LSSpellRegistry.ANNIHILATION_BOMB_SPELL, "Annihilation Bomb", "Launch a concentrated orb of destructive energy. Upon impact, it detonates violently to deal area damage and scatters multiple energy orbs into the surroundings.");
        addSpell(LSSpellRegistry.ANNIHILATION_SHOCKWAVE_SPELL, "Annihilation Shockwave", "Stomp the ground to release a fan-shaped shockwave of green fire. The waves deal damage based on the target's maximum health.");
        addSpell(LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL, "Annihilation Resonance", "Imbue yourself with an unstable frequency, reducing your defense and attack by 25%. For the duration, landing a critical hit triggers a powerful explosion at the target's location.");
        addSpell(LSSpellRegistry.ANNIHILATION_GEYSER_SPELL, "Annihilation Geyser", "Create a gravitational well that pulls in nearby creatures. Upon completion, a colossal geyser of energy erupts, dealing massive damage.");
        addSpell(LSSpellRegistry.SUMMON_FLAMEBORN_KNIGHTS_SPELL, "Summon Flameborn Knights", "Summon knights made of eternal fire to fight for you.");
        addSpell(LSSpellRegistry.RELEASE_RIFTWALKER_PREDATOR_SPELL, "Release Riftwalker Predator", "Manifest an Annihilation Pursuer, a relentless hunter that tracks its target through dimensional rifts until they are destroyed.");
        addSpell(LSSpellRegistry.FLAMEBORN_DRIFT_SPELL, "Flameborn Drift", "Dash forward wrapped in green fire. Upon colliding with a creature, you stop and deal area damage.");

        // Blood
        addSpell(LSSpellRegistry.POSSESSED_SOUL_BLADE_SPELL, "Possessed Soul Blade", "Conjure multiple expanding rings of spectral blades from the ground centered on your position. The color of the blades changes based on the spell level.");
//        addSpell(LSSpellRegistry.POSSESSED_FALLING_SOUL_BLADE_SPELL, "Possessed Falling Soul Blade", "Rain down multiple expanding rings of spectral blades centered on your position. The color of the blades changes based on the spell level.");
        addSpell(LSSpellRegistry.HEMATITE_TRISHULA_SPELL, "Hematite Trishula", "Hurls a large red trident that explodes and scatters multiple explosions around the impact area upon hitting the ground.");
        addSpell(LSSpellRegistry.POSSESSED_WING_SPELL, "Possessed Wings", "Manifest red wings for a short duration, allowing Elytra flight and increasing your movement speed, jump height, and step height. While active, your attacks inflict the Soul Fracture debuff on targets.");

        // Evocation
        addSpell(LSSpellRegistry.COLLAPSED_KINGDOMS_LEGION_SPELL, "Collapsed Kingdom's Legion", "Summons the spectral knights of the Collapsed Kingdom, bound by ancient duty to arise from the ashes and strike down your foes.");

        // Fire
        addSpell(LSSpellRegistry.FLAME_EATER_SPELL, "Flame Eater", "Target multiple creatures to erupt bursts of fire at their feet. This spell fails if targets are too high above the ground.");
        addSpell(LSSpellRegistry.FLAME_SECTOR_SPELL, "Flame Sector", "Emit a radial burst of fire in equally spaced directions. The flames travel along the ground.");
        addSpell(LSSpellRegistry.SENTINEL_SATURATION_SPELL, "Sentinel Saturation", "Summon multiple spectral images of the Dune Sentinel behind you to unleash a massive barrage of bombs, carpet-bombing the target area.");

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
        addUi("hp_damage", "%s%% of target's max HP damage");
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

        add("upgrade.legendary_spellbooks.stormmancer_upgrade", "Tempest Upgrade");
        add("item.legendary_spellbooks.smithing_template.stormmancer_upgrade.applies_to", "Netherite Mage Armor");
        add("item.legendary_spellbooks.smithing_template.stormmancer_upgrade.ingredients", "Air Rune");
    }
}
