package dev.higurashi.legendary_spellbooks.common.effects.handler;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnnihilationEffectHandler {
    private static final UUID SPELL_RESIST_UUID = UUID.fromString("a6993b67-26da-4558-9ec0-8291fe766d79");

    @SubscribeEvent
    public static void onAttributeModification(EntityAttributeModificationEvent event) {
        MobEffect annihilationEffect = ForgeRegistries.MOB_EFFECTS.getValue(ModEffects.ANNIHILATION.getId());
        if (annihilationEffect == null) return;

        annihilationEffect.addAttributeModifier(AttributeRegistry.SPELL_RESIST.get(), SPELL_RESIST_UUID.toString(), -0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
