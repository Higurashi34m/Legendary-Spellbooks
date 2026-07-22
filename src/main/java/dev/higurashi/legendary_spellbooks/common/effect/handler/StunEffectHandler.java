package dev.higurashi.legendary_spellbooks.common.effect.handler;

import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StunEffectHandler {
    @SubscribeEvent
    public static void onSpellPreCast(SpellPreCastEvent event) {
        if (event.getEntity().hasEffect(ModEffects.STUN.get())) {
            event.setCanceled(true);
        }
    }
}
