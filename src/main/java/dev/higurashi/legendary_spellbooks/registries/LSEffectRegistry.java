package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.effects.AnnihilationResonanceEffect;
import dev.higurashi.legendary_spellbooks.common.effects.BeamEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LSEffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, LegendarySpellbooks.MOD_ID);
    public static void register(IEventBus bus) { EFFECTS.register(bus); }

    public static final RegistryObject<MobEffect> BEAM_EFFECT = EFFECTS.register("beam", BeamEffect::new);
    public static final RegistryObject<MobEffect> ANNIHILATION_RESONANCE_EFFECT = EFFECTS.register("annihilation_resonance", AnnihilationResonanceEffect::new);
}
