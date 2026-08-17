package dev.higurashi.legendary_spellbooks.common.effects.handler;

import dev.higurashi.legendary_spellbooks.config.CommonConfig;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StunEffectHandler {
    @SubscribeEvent
    public static void onSpellPreCast(SpellPreCastEvent event) {
        if (event.getEntity().hasEffect(ModEffects.STUN.get()) && !CommonConfig.STUN_CASTING.get()) {
            event.setCanceled(true);
        }
    }
}
