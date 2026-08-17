package dev.higurashi.legendary_spellbooks.common.items.handler;

import dev.higurashi.legendary_spellbooks.config.CommonConfig;
import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import io.redspace.ironsspellbooks.effect.EvasionEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnnihilationProtocolSpellbookHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingAttack(LivingAttackEvent event) {
        LivingEntity target = event.getEntity();
        if (target.level().isClientSide) return;

        CuriosApi.getCuriosInventory(target).ifPresent(handler -> handler.findFirstCurio(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get()).ifPresent(result -> {
            if (target.getRandom().nextDouble() <= CommonConfig.ANNIHILATORS_PROTOCOL_DODGE_CHANCE.get()) {
                EvasionEffect.doEffect(target, event.getSource());
            }
        }));
    }
}
