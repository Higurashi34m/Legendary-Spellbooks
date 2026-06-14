package dev.higurashi.legendary_spellbooks.common.effects.handler;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@EventBusSubscriber
public class AnnihilationEffectHandler {
    @SubscribeEvent
    public static void onAttributeModification(EntityAttributeModificationEvent event) {
        MobEffect annihilationEffect = BuiltInRegistries.MOB_EFFECT.get(ModEffects.ANNIHILATION.getKey());
        if (annihilationEffect == null) return;

        annihilationEffect.addAttributeModifier(AttributeRegistry.SPELL_RESIST, LegendarySpellbooks.id("effect_annihilation"), -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
