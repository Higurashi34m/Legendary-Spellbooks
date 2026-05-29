package dev.higurashi.legendary_spellbooks.setups;

import dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile.CumuloChargeRenderer;
import dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile.DuneSentinelPhantomRenderer;
import dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile.SpellBombRenderer;
import dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile.SpellCloudRender;
import dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.summoned.SummonedSkeloraptorRenderer;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.AnnihilationBombChargeLayer;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.HematiteTrishulaChargeLayer;
import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.PaladinWingsLayer;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.PowerBallBombRenderer;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.SoulTridentRenderer;
import net.miauczel.legendary_monsters.entity.client.Render.*;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
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
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Summoned
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_ANNIHILATION_PURSUER_ENTITY.get(), AnnihilationPursuerRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_FLAMEBORN_GUARD_ENTITY.get(), FlamebornGuardRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_FLAMEBORN_WARRIOR_ENTITY.get(), FlamebornWarriorRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_HAUNTED_KNIGHT_ENTITY.get(), LivingArmorRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_HAUNTED_GUARD_ENTITY.get(), HauntedGuardRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_SKELORAPTOR_ENTITY.get(), SummonedSkeloraptorRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SUMMONED_FRACTURED_APOSTLE_ENTITY.get(), FracturedApostleRenderer::new);

        // Projectile
        event.registerEntityRenderer(LSEntityRegistry.SPELL_CLOUD_ENTITY.get(), SpellCloudRender::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_FIRE_COLUMN_ENTITY.get(), NoRendererEntityRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_POISONOUS_SHOCKWAVE_ENTITY.get(), NoRendererEntityRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.CUMULO_CHARGE_ENTITY.get(), CumuloChargeRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_ICE_SPIKE_ENTITY.get(), IceSpikeRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_ANNIHILATION_BOMB_ENTITY.get(), PowerBallBombRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_SOUL_TRIDENT_ENTITY.get(), SoulTridentRenderer::new);

        event.registerEntityRenderer(LSEntityRegistry.DUNE_SENTINEL_PHANTOM_ENTITY.get(), DuneSentinelPhantomRenderer::new);
        event.registerEntityRenderer(LSEntityRegistry.SPELL_BOMB_ENTITY.get(), SpellBombRenderer::new);
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        for (String skinName : event.getSkins()) {
            PlayerRenderer renderer = event.getPlayerSkin(skinName);
            if (renderer != null) {
                renderer.addLayer(new AnnihilationBombChargeLayer<>(renderer));
                renderer.addLayer(new HematiteTrishulaChargeLayer<>(renderer));
                renderer.addLayer(new PaladinWingsLayer<>(renderer));
            }
        }
    }
}
