package dev.higurashi.legendary_spellbooks.client.renderer.spell.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.util.DefaultBipedBoneIdents;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationBombEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.PowerBallBombRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class AnnihilationBombChargeGeoLayer extends GeoRenderLayer<AbstractSpellCastingMob> {
    private AnnihilationBombEntity dummyBomb = null;

    public AnnihilationBombChargeGeoLayer(GeoRenderer<AbstractSpellCastingMob> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void renderForBone(PoseStack poseStack, AbstractSpellCastingMob caster, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (!bone.getName().equals(DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT)) return;

        SyncedSpellData spellData = ClientMagicData.getSyncedSpellData(caster);
        if (!spellData.isCasting() || !LSSpellRegistry.ANNIHILATION_BOMB_SPELL.get().getSpellId().equals(spellData.getCastingSpellId())) return;

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<?> renderer = dispatcher.renderers.get(ModEntities.ANNIHILATION_BOMB_ENTITY.get());

        if (renderer instanceof PowerBallBombRenderer bombRenderer) {
            poseStack.pushPose();

            poseStack.translate(0.45, -0.4, 0);
            poseStack.scale(0.75f, 0.75f, 0.75f);

            if (dummyBomb == null) {
                dummyBomb = new AnnihilationBombEntity(ModEntities.ANNIHILATION_BOMB_ENTITY.get(), caster.level());
            }
            dummyBomb.tickCount = caster.tickCount;

            bombRenderer.render(dummyBomb, 0.0f, partialTick, poseStack, bufferSource, packedLight);

            bufferSource.getBuffer(renderType);

            poseStack.popPose();
        }
    }
}
