package dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.DuneSentinelPhantomEntity;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.RuinedPyramid.DuneSentinelEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.entity.client.ModModelLayers;
import net.miauczel.legendary_monsters.entity.client.Model.DuneSentinelModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DuneSentinelPhantomRenderer extends EntityRenderer<DuneSentinelPhantomEntity> {
    protected final DuneSentinelModel<DuneSentinelEntity> model;

    public DuneSentinelPhantomRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new DuneSentinelModel<>(context.bakeLayer(ModModelLayers.BIG_CANNON_LAYER));
    }

    @Override @NotNull
    public ResourceLocation getTextureLocation(@NotNull DuneSentinelPhantomEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/dune_sentinel/dune_sentinel.png");
    }

    @Override
    public void render(@NotNull DuneSentinelPhantomEntity entity, float entityYaw, float ticks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLightIn) {
        stack.pushPose();

        stack.mulPose(Axis.YP.rotationDegrees(180.0f - entityYaw));
        stack.mulPose(Axis.XP.rotationDegrees(-entity.getXRot()));
        stack.scale(-1.0f, -1.0f, 1.0f);
        stack.translate(0.0, -1.5, 0.0);

        DuneSentinelEntity currentDummy = entity.getOrCreateDummy(ModEntities.BlastCannon.get());

        if (currentDummy != null) {
            currentDummy.tickCount = entity.tickCount;

            if (entity.isShouldAttack()) {
                if (currentDummy.shootAnimationState != null) {
                    currentDummy.shootAnimationState.startIfStopped(entity.tickCount);
                }
            } else if (entity.isShouldDeath()) {
                if (currentDummy.DeathAnimationState != null) {
                    currentDummy.DeathAnimationState.startIfStopped(entity.tickCount);
                }
            } else {
                if (currentDummy.idleAnimationState != null) {
                    currentDummy.idleAnimationState.startIfStopped(entity.tickCount);
                }
            }

            float ageInTicks = (float) entity.tickCount + ticks;

            this.model.setupAnim(currentDummy, 0.0f, 0.0f, ageInTicks, 0.0f, entity.getXRot());

            float extraUpAngleDegrees = 20.0f;
            this.model.root().getChild("legs").getChild("main").getChild("head").xRot -= extraUpAngleDegrees * ((float)Math.PI / 180.0f);
        } else {
            this.model.root().getAllParts().forEach(ModelPart::resetPose);
        }

        ResourceLocation texture = this.getTextureLocation(entity);
        RenderType translucentRenderType = RenderType.entityTranslucentCull(texture);
        VertexConsumer vertexConsumer = source.getBuffer(translucentRenderType);
        this.model.renderToBuffer(stack, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 0.5f);

        stack.popPose();

        super.render(entity, entityYaw, ticks, stack, source, packedLightIn);
    }
}