package dev.higurashi.legendary_spellbooks.common.effects.handler;

import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AmbushThornsEffectHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event == null || event.getEntity() == null) return;

        LivingEntity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();

        if (target == null || attacker == null || !target.hasEffect(LSEffectRegistry.AMBUSH_THORNS_EFFECT.get())) return;

        int amplifier = target.getEffect(LSEffectRegistry.AMBUSH_THORNS_EFFECT.get()).getAmplifier();
        double damage = event.getAmount() * 0.2 + getDamage(amplifier, target);

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
