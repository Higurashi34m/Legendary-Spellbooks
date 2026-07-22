package dev.higurashi.legendary_spellbooks.common.effect.handler;

import dev.higurashi.legendary_spellbooks.registry.LSEffectRegistry;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BeamEffectServerHandler {
    @SubscribeEvent
    public static void onSpellPreCast(SpellPreCastEvent event) {
        if (event.getEntity().hasEffect(LSEffectRegistry.BEAM_EFFECT.get())) {
            event.setCanceled(true);
        }
    }
}
