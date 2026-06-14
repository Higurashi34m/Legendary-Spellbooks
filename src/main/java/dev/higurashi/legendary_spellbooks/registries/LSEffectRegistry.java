package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.effects.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LSEffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, LegendarySpellbooks.MOD_ID);
    public static void register(IEventBus bus) { EFFECTS.register(bus); }

    public static final DeferredHolder<MobEffect, MobEffect> BEAM_EFFECT = EFFECTS.register("beam", BeamEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> ANNIHILATION_RESONANCE_EFFECT = EFFECTS.register("annihilation_resonance", AnnihilationResonanceEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> AMBUSH_THORNS_EFFECT = EFFECTS.register("ambush_thorns", AmbushThornsEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> FLAMEBORN_DASH_EFFECT = EFFECTS.register("flameborn_dash", FlamebornDriftEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> POSSESSED_WING_EFFECT = EFFECTS.register("possessed_wing", PossessedWingEffect::new);
}
