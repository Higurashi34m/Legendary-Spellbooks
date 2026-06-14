package dev.higurashi.legendary_spellbooks.common.event;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.registries.LSAttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber
public class AnnihilationAttributeBonusEvent {
    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity) || entity.tickCount % 2 != 0 || entity.level().isClientSide()) return;

        AttributeInstance fire = entity.getAttribute(AttributeRegistry.FIRE_SPELL_POWER);
        AttributeInstance ender = entity.getAttribute(AttributeRegistry.ENDER_SPELL_POWER);
        AttributeInstance annihilation = entity.getAttribute(LSAttributeRegistry.ANNIHILATION_SPELL_POWER);

        if (annihilation == null) return;

        double fireBonus = fire == null ? 0.0 : Math.max(0.0, fire.getValue() - 1.0);
        double enderBonus = ender == null ? 0.0 : Math.max(0.0, ender.getValue() - 1.0);

        double finalValue = (fireBonus + enderBonus) / 2.0;

        AttributeModifier beforeModifier = annihilation.getModifier(LegendarySpellbooks.id("annihilation_bonus"));
        if (beforeModifier == null || beforeModifier.amount() != finalValue) {
            annihilation.removeModifier(LegendarySpellbooks.id("annihilation_bonus"));
            annihilation.addTransientModifier(new AttributeModifier(LegendarySpellbooks.id("annihilation_bonus"), finalValue, AttributeModifier.Operation.ADD_VALUE));
        }
    }
}
