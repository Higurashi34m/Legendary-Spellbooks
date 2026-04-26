package dev.higurashi.legendary_spellbooks.setups;

import dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile.SpellCloudRender;
import dev.higurashi.legendary_spellbooks.client.renderer.items.PPHaloRenderer;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import net.miauczel.legendary_monsters.entity.client.Render.AnnihilationPursuerRenderer;
import net.miauczel.legendary_monsters.entity.client.Render.NoRendererEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get(), SpellBookCurioRenderer::new);
        CuriosRendererRegistry.register(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get(), SpellBookCurioRenderer::new);

        CuriosRendererRegistry.register(LSItemRegistry.PP_FALLEN_HALO.get(), PPHaloRenderer::new);
        CuriosRendererRegistry.register(LSItemRegistry.PP_LUMIERE_HALO.get(), PPHaloRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Summoned
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_ANNIHILATION_PURSUER_ENTITY.get(), AnnihilationPursuerRenderer::new);

        // Projectile
        event.registerEntityRenderer(LSEntityRegistry.SPELL_CLOUD_ENTITY.get(), SpellCloudRender::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_FIRE_COLUMN_ENTITY.get(), NoRendererEntityRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_POISONOUS_SHOCKWAVE_ENTITY.get(), NoRendererEntityRenderer::new);
    }
}
