package dev.higurashi.legendary_spellbooks.setups;

import dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile.*;
import dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.summoned.SummonedSkeloraptorRenderer;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.AnnihilationArrowLayer;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.AnnihilationBombLayer;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.HematiteTrishulaChargeLayer;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.PaladinWingsLayer;
import dev.higurashi.legendary_spellbooks.registry.LSEntityRegistry;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.SmallPowerBallBombRenderer;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.SoulTridentRenderer;
import net.miauczel.legendary_monsters.entity.client.Render.*;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Summoned
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_ANNIHILATION_PURSUER_ENTITY.getAs(), AnnihilationPursuerRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_FLAMEBORN_GUARD_ENTITY.getAs(), FlamebornGuardRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_FLAMEBORN_WARRIOR_ENTITY.getAs(), FlamebornWarriorRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_HAUNTED_KNIGHT_ENTITY.getAs(), LivingArmorRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_HAUNTED_GUARD_ENTITY.getAs(), HauntedGuardRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_SKELORAPTOR_ENTITY.getAs(), SummonedSkeloraptorRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_FRACTURED_APOSTLE_ENTITY.getAs(), FracturedApostleRenderer::new);

        // Projectile
        event.registerEntityRenderer(LSEntityRegistry.SPELL_CLOUD_ENTITY.getAs(), SpellCloudRender::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_FIRE_COLUMN_ENTITY.getAs(), NoRendererEntityRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_POISONOUS_SHOCKWAVE_ENTITY.getAs(), NoRendererEntityRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.CUMULO_CHARGE_ENTITY.getAs(), CumuloChargeRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_ICE_SPIKE_ENTITY.getAs(), SpellIceSpikeEntityRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_ANNIHILATION_BOMB_ENTITY.getAs(), SpellAnnihilationBombRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_SOUL_TRIDENT_ENTITY.getAs(), SoulTridentRenderer::new);

        event.registerEntityRenderer(LSEntityRegistry.DUNE_SENTINEL_PHANTOM_ENTITY.getAs(), DuneSentinelPhantomRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_BOMB_ENTITY.getAs(), SpellBombRenderer::new);

        event.registerEntityRenderer(LSEntityRegistry.SPELL_ANNIHILATION_BEAM.getAs(), SpellAnnihilationBeamEntityRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_SMALL_ANNIHILATION_BOMB.getAs(), SmallPowerBallBombRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_ANNIHILATION_EXPLOSION.getAs(), NoopRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.ANNIHILATION_ARROW.getAs(), AnnihilationArrowRenderer::new);
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        for (String skinName : event.getSkins()) {
            PlayerRenderer renderer = event.getPlayerSkin(skinName);
            if (renderer != null) {
                renderer.addLayer(new AnnihilationArrowLayer<>(renderer));
                renderer.addLayer(new AnnihilationBombLayer<>(renderer));
                renderer.addLayer(new HematiteTrishulaChargeLayer<>(renderer));
                renderer.addLayer(new PaladinWingsLayer<>(renderer));
            }
        }
    }
}
