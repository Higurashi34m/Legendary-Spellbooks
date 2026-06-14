package dev.higurashi.legendary_spellbooks.common.entities.handler;

import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.Tornado;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber
public class TornadoEntityHandler {
    private static final String IN_TORNADO = "in_tornado";

    @SubscribeEvent
    public static void onLivingUpdate(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
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