/*
 * This Code Copy and modification by net.miauczel.legendary_monsters.entity.client.Render.IceSpikeRenderer
 */

package dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.higurashi.daybreaklib.api.client.animation.transform.Pivot;
import dev.higurashi.daybreaklib.api.client.animation.transform.TransformApplier;
import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.SpellIceSpikeEntity;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.entity.client.ModModelLayers;
import net.miauczel.legendary_monsters.entity.client.Model.IceSpikeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SpellIceSpikeEntityRenderer extends EntityRenderer<SpellIceSpikeEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/ice_spike.png");

    private final IceSpikeModel<SpellIceSpikeEntity> model;

    public SpellIceSpikeEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new IceSpikeModel<>(context.bakeLayer(ModModelLayers.ICE_SPIKE));
    }

    @Override @NotNull
    public ResourceLocation getTextureLocation(@NotNull SpellIceSpikeEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(@NotNull SpellIceSpikeEntity entity, float yaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        if (entity.isInvisible()) return;

        poseStack.pushPose();

        poseStack.mulPose(Axis.YP.rotationDegrees(90.0f - entity.getYRot()));
        poseStack.scale(-0.7f, -0.7f, 0.7f);

        TransformApplier.apply(poseStack, entity, partialTicks, Pivot.ORIGIN);

        this.model.setupAnim(entity, 0.0f, 0.0f, 0.0f, entity.getYRot(), entity.getXRot());
        this.model.root().y = 0.0f;
        this.model.renderToBuffer(poseStack, bufferSource.getBuffer(this.model.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

        poseStack.popPose();

        super.render(entity, yaw, partialTicks, poseStack, bufferSource, packedLight);
    }
}
