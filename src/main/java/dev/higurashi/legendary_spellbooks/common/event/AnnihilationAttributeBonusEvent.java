package dev.higurashi.legendary_spellbooks.common.event;

import dev.higurashi.legendary_spellbooks.config.CommonConfig;
import dev.higurashi.legendary_spellbooks.registries.LSAttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnnihilationAttributeBonusEvent {
    private static final UUID BONUS_UUID = UUID.fromString("d1c92506-bc83-41ed-ae4b-47b0b798a33e");

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.tickCount % 2 != 0 || entity.level().isClientSide()) return;

        AttributeInstance fire = entity.getAttribute(AttributeRegistry.FIRE_SPELL_POWER.get());
        AttributeInstance ender = entity.getAttribute(AttributeRegistry.ENDER_SPELL_POWER.get());
        AttributeInstance annihilation = entity.getAttribute(LSAttributeRegistry.ANNIHILATION_SPELL_POWER.get());

        if (annihilation == null) return;

        double fireBonus = fire == null ? 0.0 : fire.getValue() - 1.0;
        double enderBonus = ender == null ? 0.0 : ender.getValue() - 1.0;

        double finalValue = fireBonus * CommonConfig.ANNIHILATION_FIRE_BONUS.get() + enderBonus * CommonConfig.ANNIHILATION_ENDER_BONUS.get();

        AttributeModifier beforeModifier = annihilation.getModifier(BONUS_UUID);
        if (beforeModifier == null || beforeModifier.getAmount() != finalValue) {
            annihilation.removeModifier(BONUS_UUID);
            annihilation.addTransientModifier(new AttributeModifier(BONUS_UUID, "Annihilation Bonus", finalValue, AttributeModifier.Operation.ADDITION));
        }
    }
}
