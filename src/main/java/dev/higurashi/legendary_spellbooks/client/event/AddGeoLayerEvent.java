package dev.higurashi.legendary_spellbooks.client.event;

import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.AnnihilationArrowLayer;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.AnnihilationBombLayer;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import software.bernie.geckolib.event.GeoRenderEvent;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@EventBusSubscriber
public class AddGeoLayerEvent {
    @SubscribeEvent
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void onGeoEntityRender(GeoRenderEvent.Entity.Pre event) {
        if (!(event.getEntity() instanceof AbstractSpellCastingMob)) return;

        GeoEntityRenderer renderer = event.getRenderer();

        if (notLayer(renderer, AnnihilationArrowLayer.Geo.class)) {
            renderer.addRenderLayer(new AnnihilationArrowLayer.Geo(renderer));
        }

        if (notLayer(renderer, AnnihilationBombLayer.Geo.class)) {
            renderer.addRenderLayer(new AnnihilationBombLayer.Geo(renderer));
        }
    }

    private static boolean notLayer(GeoEntityRenderer<?> renderer, Class<? extends GeoRenderLayer<?>> layerClass) {
        for (GeoRenderLayer<?> layer : renderer.getRenderLayers()) {
            if (layer.getClass() == layerClass) return false;
        }
        return true;
    }
}
