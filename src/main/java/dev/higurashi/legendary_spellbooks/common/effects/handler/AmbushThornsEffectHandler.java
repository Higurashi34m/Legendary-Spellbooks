package dev.higurashi.legendary_spellbooks.common.effects.handler;

import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import javax.annotation.Nullable;

@EventBusSubscriber()
public class AmbushThornsEffectHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent.Pre event) {
        if (event == null) return;

        LivingEntity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();

        MobEffectInstance effect = target.getEffect(LSEffectRegistry.AMBUSH_THORNS_EFFECT);
        if (attacker == null || effect == null) return;

        int amplifier = effect.getAmplifier();
        double damage = event.getOriginalDamage() * 0.2 + getDamage(amplifier, target);

        if (attacker instanceof LivingEntity attackerEntity && Math.random() < 0.5f) {
            attackerEntity.hurt(target.damageSources().thorns(target), (float) damage);
        }
    }

    public static float getDamage(int amplifier, @Nullable LivingEntity caster) {
        float multiplier = (caster == null) ? 1.0f : LSSpellRegistry.AMBUSH_THORNS_SPELL.get().getEntityPowerMultiplier(caster);

        float base = 0.5f;
        float levelScale = 0.5f + (amplifier * 0.5f);

        return base * levelScale * multiplier;
    }
}
