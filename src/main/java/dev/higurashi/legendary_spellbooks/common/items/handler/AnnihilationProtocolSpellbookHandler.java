package dev.higurashi.legendary_spellbooks.common.items.handler;

import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber
public class AnnihilationProtocolSpellbookHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingAttack(LivingDamageEvent.Pre event) {
        LivingEntity target = event.getEntity();
        if (target.level().isClientSide) return;

        CuriosApi.getCuriosInventory(target).flatMap(handler -> handler.findFirstCurio(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get())).ifPresent(result -> {
            if (target.getRandom().nextFloat() < 0.1f) target.addEffect(new MobEffectInstance(MobEffectRegistry.EVASION, 10, 0, false, false));
        });
    }
}
