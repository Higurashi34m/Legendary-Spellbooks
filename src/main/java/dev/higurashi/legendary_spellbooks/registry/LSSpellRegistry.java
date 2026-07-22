package dev.higurashi.legendary_spellbooks.registry;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.spell.annihilation.*;
import dev.higurashi.legendary_spellbooks.common.spell.blood.*;
import dev.higurashi.legendary_spellbooks.common.spell.evocation.CollapsedKingdomsLegionSpell;
import dev.higurashi.legendary_spellbooks.common.spell.fire.FlameEaterSpell;
import dev.higurashi.legendary_spellbooks.common.spell.fire.FlameSectorSpell;
import dev.higurashi.legendary_spellbooks.common.spell.fire.SentinelSaturationSpell;
import dev.higurashi.legendary_spellbooks.common.spell.ice.GlacierEruptionSpell;
import dev.higurashi.legendary_spellbooks.common.spell.ice.GlacierRingburstSpell;
import dev.higurashi.legendary_spellbooks.common.spell.lightning.*;
import dev.higurashi.legendary_spellbooks.common.spell.nature.AmbushThornsSpell;
import dev.higurashi.legendary_spellbooks.common.spell.nature.FossilizedFurySpell;
import dev.higurashi.legendary_spellbooks.common.spell.nature.OvergrownShockwaveSpell;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class LSSpellRegistry {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, LegendarySpellbooks.MOD_ID);
    public static void register(IEventBus bus) { SPELLS.register(bus); }
    private static RegistryObject<AbstractSpell> register(AbstractSpell spell) { return SPELLS.register(spell.getSpellName(), () -> spell); }

    // Annihilation
    public static final RegistryObject<AbstractSpell> ANNIHILATION_ARROW_SPELL = register(new AnnihilationArrowSpell());
    public static final RegistryObject<AbstractSpell> ANNIHILATION_BEAM_SPELL = register(new AnnihilationBeamSpell());
    public static final RegistryObject<AbstractSpell> ANNIHILATION_BOMB_SPELL = register(new AnnihilationBombSpell());
    public static final RegistryObject<AbstractSpell> ANNIHILATION_SHOCKWAVE_SPELL = register(new AnnihilationShockwaveSpell());
    public static final RegistryObject<AbstractSpell> ANNIHILATION_RESONANCE_SPELL = register(new AnnihilationResonanceSpell());
    public static final RegistryObject<AbstractSpell> ANNIHILATION_GEYSER_SPELL = register(new AnnihilationGeyserSpell());
    public static final RegistryObject<AbstractSpell> SUMMON_FLAMEBORN_KNIGHTS_SPELL = register(new SummonFlamebornKnightsSpell());
    public static final RegistryObject<AbstractSpell> RELEASE_RIFTWALKER_PREDATOR_SPELL = register(new ReleaseRiftwalkerPredatorSpell());
    public static final RegistryObject<AbstractSpell> FLAMEBORN_DRIFT_SPELL = register(new FlamebornDriftSpell());

    // Blood
    public static final RegistryObject<AbstractSpell> POSSESSED_SOUL_BLADE_SPELL = register(new PossessedSoulBladeSpell());
//    public static final RegistryObject<AbstractSpell> POSSESSED_FALLING_SOUL_BLADE_SPELL = register(new PossessedFallingSoulBladeSpell());
    public static final RegistryObject<AbstractSpell> HEMATITE_TRISHULA_SPELL = register(new HematiteTrishulaSpell());
    public static final RegistryObject<AbstractSpell> POSSESSED_WING_SPELL = register(new PossessedWingSpell());

    // Evocation
    public static final RegistryObject<AbstractSpell> COLLAPSED_KINGDOMS_LEGION_SPELL = register(new CollapsedKingdomsLegionSpell());

    // Fire
    public static final RegistryObject<AbstractSpell> FLAME_EATER_SPELL = register(new FlameEaterSpell());
    public static final RegistryObject<AbstractSpell> FLAME_SECTOR_SPELL = register(new FlameSectorSpell());
    public static final RegistryObject<AbstractSpell> SENTINEL_SATURATION_SPELL = register(new SentinelSaturationSpell());

    // Ice
    public static final RegistryObject<AbstractSpell> GLACIER_ERUPTION_SPELL = register(new GlacierEruptionSpell());
    public static final RegistryObject<AbstractSpell> GLACIER_RINGBURST_SPELL = register(new GlacierRingburstSpell());

    // Lightning
    public static final RegistryObject<AbstractSpell> CLOUD_RAIL_SPELL = register(new CloudRailSpell());
    public static final RegistryObject<AbstractSpell> CLOUD_RING_SPELL = register(new CloudRingSpell());
    public static final RegistryObject<AbstractSpell> NIMBUS_ARRAY_SPELL = register(new NimbusArraySpell());
    public static final RegistryObject<AbstractSpell> TRIPLE_NIMBUS_ARRAY_SPELL = register(new TripleNimbusArraySpell());
    public static final RegistryObject<AbstractSpell> THUNDER_FANBURST_SPELL = register(new ThunderFanburstSpell());
    public static final RegistryObject<AbstractSpell> TORNADO_SPELL = register(new TornadoSpell());
    public static final RegistryObject<AbstractSpell> QUAD_TORNADO_SPELL = register(new QuadTornadoSpell());
    public static final RegistryObject<AbstractSpell> ENERGY_BEAM_SPELL = register(new EnergyBeamSpell());
    public static final RegistryObject<AbstractSpell> CUMULO_CHARGE_SPELL = register(new CumuloChargeSpell());

    // Nature
    public static final RegistryObject<AbstractSpell> AMBUSH_THORNS_SPELL = register(new AmbushThornsSpell());
    public static final RegistryObject<AbstractSpell> OVERGROWN_SHOCKWAVE_SPELL = register(new OvergrownShockwaveSpell());
    public static final RegistryObject<AbstractSpell> FOSSILIZED_FURY_SPELL = register(new FossilizedFurySpell());
}