package dev.higurashi.legendary_spellbooks.common.effect.handler;

import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BeamEffectClientHandler {
    @SubscribeEvent
    public static void onInput(MovementInputUpdateEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player == null || !player.level().isClientSide) return;

        if (player.hasEffect(LSEffectRegistry.BEAM_EFFECT.get())) {
            Input input = event.getInput();

            input.leftImpulse = 0;
            input.forwardImpulse = 0;

            input.jumping = false;
            input.shiftKeyDown = false;
        }
    }
}
