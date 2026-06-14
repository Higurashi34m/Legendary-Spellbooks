package dev.higurashi.legendary_spellbooks.common.effects.handler;

import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.util.EntityUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

@EventBusSubscriber
public class PossessedWingHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent.Pre event) {
        LivingEntity target = event.getEntity();
        Entity caster = event.getSource().getDirectEntity();
        if (caster == null) return;
        if (!(caster instanceof LivingEntity livingCaster) || !(livingCaster.hasEffect(LSEffectRegistry.POSSESSED_WING_EFFECT))) return;
        if (!(livingCaster.equals(event.getSource().getEntity()))) return;

        EntityUtil.applyStackingEffect(target, ModEffects.SOUL_FRACTURE, 1, 1, 60);
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity target = event.getEntity();
        MobEffectInstance effect = target.getEffect(LSEffectRegistry.POSSESSED_WING_EFFECT);
        if (effect == null) return ;

        target.setDeltaMovement(target.getDeltaMovement().add(0, 0.25 * effect.getAmplifier(), 0));
    }
}
