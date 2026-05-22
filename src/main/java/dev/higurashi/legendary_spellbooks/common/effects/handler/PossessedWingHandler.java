package dev.higurashi.legendary_spellbooks.common.effects.handler;

import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.util.EntityUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PossessedWingHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        Entity caster = event.getSource().getDirectEntity();
        MobEffect effect = LSEffectRegistry.POSSESSED_WING_EFFECT.get();
        if (target == null || caster == null) return;
        if (!(caster instanceof LivingEntity livingCaster) || !(livingCaster.hasEffect(effect))) return;

        EntityUtil.applyStackingEffect(target, ModEffects.SOUL_FRACTURE.get(), 1, livingCaster.getEffect(effect).getAmplifier(), 60);
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity target = event.getEntity();
        MobEffect effect = LSEffectRegistry.POSSESSED_WING_EFFECT.get();
        if (!(target.hasEffect(effect))) return ;

        target.setDeltaMovement(target.getDeltaMovement().add(0, 0.25 * target.getEffect(effect).getAmplifier(), 0));
    }
}
