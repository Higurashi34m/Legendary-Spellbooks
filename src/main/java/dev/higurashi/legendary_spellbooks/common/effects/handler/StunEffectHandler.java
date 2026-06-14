package dev.higurashi.legendary_spellbooks.common.effects.handler;

import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class StunEffectHandler {
    @SubscribeEvent
    public static void onSpellPreCast(SpellPreCastEvent event) {
        if (event.getEntity().hasEffect(ModEffects.STUN)) {
            event.setCanceled(true);
        }
    }
}
