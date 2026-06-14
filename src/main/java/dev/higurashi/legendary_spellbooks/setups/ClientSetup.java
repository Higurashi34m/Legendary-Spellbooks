package dev.higurashi.legendary_spellbooks.setups;

import dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile.*;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.AnnihilationArrowLayer;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.AnnihilationBombLayer;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.HematiteTrishulaChargeLayer;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.PaladinWingsLayer;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.SmallPowerBallBombRenderer;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.SoulTridentRenderer;
import net.miauczel.legendary_monsters.entity.client.Render.*;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get(), SpellBookCurioRenderer::new);
        CuriosRendererRegistry.register(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get(), SpellBookCurioRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Summoned
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_ANNIHILATION_PURSUER_ENTITY.get(), AnnihilationPursuerRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_FLAMEBORN_GUARD_ENTITY.get(), FlamebornGuardRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_FLAMEBORN_WARRIOR_ENTITY.get(), FlamebornWarriorRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_HAUNTED_KNIGHT_ENTITY.get(), LivingArmorRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_HAUNTED_GUARD_ENTITY.get(), HauntedGuardRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_SKELORAPTOR_ENTITY.get(), SkeloraptorRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_FRACTURED_APOSTLE_ENTITY.get(), FracturedApostleRenderer::new);

        // Projectile
        event.registerEntityRenderer(LSEntityRegistry.SPELL_CLOUD_ENTITY.get(), SpellCloudRender::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_FIRE_COLUMN_ENTITY.get(), NoRendererEntityRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_POISONOUS_SHOCKWAVE_ENTITY.get(), NoRendererEntityRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.CUMULO_CHARGE_ENTITY.get(), CumuloChargeRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_ICE_SPIKE_ENTITY.get(), IceSpikeRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_ANNIHILATION_BOMB_ENTITY.get(), SpellAnnihilationBombRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_SOUL_TRIDENT_ENTITY.get(), SoulTridentRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.DUNE_SENTINEL_PHANTOM_ENTITY.get(), DuneSentinelPhantomRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_BOMB_ENTITY.get(), SpellBombRenderer::new);

        event.registerEntityRenderer(LSEntityRegistry.SPELL_ANNIHILATION_BEAM.get(), SpellAnnihilationBeamEntityRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_SMALL_ANNIHILATION_BOMB.get(), SmallPowerBallBombRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_ANNIHILATION_EXPLOSION.get(), NoopRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.ANNIHILATION_ARROW.get(), AnnihilationArrowRenderer::new);
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new AnnihilationArrowLayer<>(renderer));
                renderer.addLayer(new AnnihilationBombLayer<>(renderer));
                renderer.addLayer(new HematiteTrishulaChargeLayer<>(renderer));
                renderer.addLayer(new PaladinWingsLayer<>(renderer));
            }
        }
    }
}
