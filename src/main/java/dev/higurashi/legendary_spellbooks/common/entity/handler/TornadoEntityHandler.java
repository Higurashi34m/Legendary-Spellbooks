package dev.higurashi.legendary_spellbooks.common.entity.handler;

import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.Tornado;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TornadoEntityHandler {
    private static final String IN_TORNADO = "in_tornado";

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) return;

        AABB area = entity.getBoundingBox().inflate(2.0);
        boolean isActive = entity.getVehicle() instanceof Tornado || !entity.level().getEntitiesOfClass(Tornado.class, area).isEmpty();

        if (isActive) {
            entity.fallDistance = -2;
            if (!entity.getTags().contains(IN_TORNADO)) entity.addTag(IN_TORNADO);
        } else {
            if (entity.getTags().contains(IN_TORNADO)) entity.removeTag(IN_TORNADO);
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity().getTags().contains(IN_TORNADO)) {
            event.setDistance(0);
            event.setCanceled(true);
        }
    }
}