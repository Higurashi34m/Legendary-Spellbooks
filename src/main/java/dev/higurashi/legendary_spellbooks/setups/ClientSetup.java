package dev.higurashi.legendary_spellbooks.setups;

import dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile.SpellCloudRender;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import net.miauczel.legendary_monsters.entity.client.Render.NoRendererEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Projectile
        event.registerEntityRenderer(LSEntityRegistry.SPELL_CLOUD_ENTITY.get(), SpellCloudRender::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_FIRE_COLUMN_ENTITY.get(), NoRendererEntityRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_POISONOUS_SHOCKWAVE_ENTITY.get(), NoRendererEntityRenderer::new);
    }
}
