package dev.higurashi.legendary_spellbooks.common.effects.handler;

import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class BeamEffectClientHandler {
    @SubscribeEvent
    public static void onInput(MovementInputUpdateEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player == null || !player.level().isClientSide) return;

        if (player.hasEffect(LSEffectRegistry.BEAM_EFFECT)) {
            Input input = event.getInput();

            input.leftImpulse = 0;
            input.forwardImpulse = 0;

            input.jumping = false;
            input.shiftKeyDown = false;
        }
    }
}
