package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.spells.annihilation.*;
import dev.higurashi.legendary_spellbooks.common.spells.blood.HematiteTrishulaSpell;
import dev.higurashi.legendary_spellbooks.common.spells.blood.PossessedSoulBladeSpell;
import dev.higurashi.legendary_spellbooks.common.spells.blood.PossessedWingSpell;
import dev.higurashi.legendary_spellbooks.common.spells.evocation.CollapsedKingdomsLegionSpell;
import dev.higurashi.legendary_spellbooks.common.spells.fire.FlameEaterSpell;
import dev.higurashi.legendary_spellbooks.common.spells.fire.FlameSectorSpell;
import dev.higurashi.legendary_spellbooks.common.spells.fire.SentinelSaturationSpell;
import dev.higurashi.legendary_spellbooks.common.spells.ice.GlacierEruptionSpell;
import dev.higurashi.legendary_spellbooks.common.spells.ice.GlacierRingburstSpell;
import dev.higurashi.legendary_spellbooks.common.spells.lightning.*;
import dev.higurashi.legendary_spellbooks.common.spells.nature.AmbushThornsSpell;
import dev.higurashi.legendary_spellbooks.common.spells.nature.FossilizedFurySpell;
import dev.higurashi.legendary_spellbooks.common.spells.nature.OvergrownShockwaveSpell;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LSSpellRegistry {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, LegendarySpellbooks.MOD_ID);
    public static void register(IEventBus bus) { SPELLS.register(bus); }
    private static Supplier<AbstractSpell> register(AbstractSpell spell) { return SPELLS.register(spell.getSpellName(), () -> spell); }

    // Annihilation
    public static final Supplier<AbstractSpell> ANNIHILATION_ARROW_SPELL = register(new AnnihilationArrowSpell());
    public static final Supplier<AbstractSpell> ANNIHILATION_BEAM_SPELL = register(new AnnihilationBeamSpell());
    public static final Supplier<AbstractSpell> ANNIHILATION_BOMB_SPELL = register(new AnnihilationBombSpell());
    public static final Supplier<AbstractSpell> ANNIHILATION_SHOCKWAVE_SPELL = register(new AnnihilationShockwaveSpell());
    public static final Supplier<AbstractSpell> ANNIHILATION_RESONANCE_SPELL = register(new AnnihilationResonanceSpell());
    public static final Supplier<AbstractSpell> ANNIHILATION_GEYSER_SPELL = register(new AnnihilationGeyserSpell());
    public static final Supplier<AbstractSpell> SUMMON_FLAMEBORN_KNIGHTS_SPELL = register(new SummonFlamebornKnightsSpell());
    public static final Supplier<AbstractSpell> RELEASE_RIFTWALKER_PREDATOR_SPELL = register(new ReleaseRiftwalkerPredatorSpell());
    public static final Supplier<AbstractSpell> FLAMEBORN_DRIFT_SPELL = register(new FlamebornDriftSpell());

    // Blood
    public static final Supplier<AbstractSpell> POSSESSED_SOUL_BLADE_SPELL = register(new PossessedSoulBladeSpell());
//    public static final Supplier<AbstractSpell> POSSESSED_FALLING_SOUL_BLADE_SPELL = register(new PossessedFallingSoulBladeSpell());
    public static final Supplier<AbstractSpell> HEMATITE_TRISHULA_SPELL = register(new HematiteTrishulaSpell());
    public static final Supplier<AbstractSpell> POSSESSED_WING_SPELL = register(new PossessedWingSpell());

    // Evocation
    public static final Supplier<AbstractSpell> COLLAPSED_KINGDOMS_LEGION_SPELL = register(new CollapsedKingdomsLegionSpell());

    // Fire
    public static final Supplier<AbstractSpell> FLAME_EATER_SPELL = register(new FlameEaterSpell());
    public static final Supplier<AbstractSpell> FLAME_SECTOR_SPELL = register(new FlameSectorSpell());
    public static final Supplier<AbstractSpell> SENTINEL_SATURATION_SPELL = register(new SentinelSaturationSpell());

    // Ice
    public static final Supplier<AbstractSpell> GLACIER_ERUPTION_SPELL = register(new GlacierEruptionSpell());
    public static final Supplier<AbstractSpell> GLACIER_RINGBURST_SPELL = register(new GlacierRingburstSpell());

    // Lightning
    public static final Supplier<AbstractSpell> CLOUD_RAIL_SPELL = register(new CloudRailSpell());
    public static final Supplier<AbstractSpell> CLOUD_RING_SPELL = register(new CloudRingSpell());
    public static final Supplier<AbstractSpell> NIMBUS_ARRAY_SPELL = register(new NimbusArraySpell());
    public static final Supplier<AbstractSpell> TRIPLE_NIMBUS_ARRAY_SPELL = register(new TripleNimbusArraySpell());
    public static final Supplier<AbstractSpell> THUNDER_FANBURST_SPELL = register(new ThunderFanburstSpell());
    public static final Supplier<AbstractSpell> TORNADO_SPELL = register(new TornadoSpell());
    public static final Supplier<AbstractSpell> QUAD_TORNADO_SPELL = register(new QuadTornadoSpell());
    public static final Supplier<AbstractSpell> ENERGY_BEAM_SPELL = register(new EnergyBeamSpell());
    public static final Supplier<AbstractSpell> CUMULO_CHARGE_SPELL = register(new CumuloChargeSpell());

    // Nature
    public static final Supplier<AbstractSpell> AMBUSH_THORNS_SPELL = register(new AmbushThornsSpell());
    public static final Supplier<AbstractSpell> OVERGROWN_SHOCKWAVE_SPELL = register(new OvergrownShockwaveSpell());
    public static final Supplier<AbstractSpell> FOSSILIZED_FURY_SPELL = register(new FossilizedFurySpell());
}