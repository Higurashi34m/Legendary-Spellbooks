package dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.AnnihilationArrowEntity;
import io.redspace.ironsspellbooks.render.RenderHelper;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class AnnihilationArrowRenderer extends EntityRenderer<AnnihilationArrowEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "textures/entity/projectile/annihilation_arrow.png");

    public AnnihilationArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override @NotNull
    public ResourceLocation getTextureLocation(@NotNull AnnihilationArrowEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(@NotNull AnnihilationArrowEntity entity, float yaw, float delta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        Vec3 movement = entity.getDeltaMovement();
        poseStack.mulPose(Axis.YP.rotationDegrees(-((float) (Math.toDegrees(Math.atan2(movement.z, movement.x))) + 90.0f)));
        poseStack.mulPose(Axis.XP.rotationDegrees(-((float) (Math.toDegrees(Math.atan2(movement.horizontalDistance(), movement.y))) - 90.0f)));

        renderModel(poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    public static void renderModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(0.25f, 0.25f, 0.25f);
        poseStack.translate(0.0, 0.0f, 5.0f);

        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();

        VertexConsumer consumer = buffer.getBuffer(RenderHelper.CustomerRenderType.magic(TEXTURE));

        poseStack.mulPose(Axis.YP.rotationDegrees(90.0f));
        for (int i = 0; i < 4; i++) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
            vertex(matrix4f, pose, consumer, -8.0f, -2.0f, 0.0f, 0.0f, packedLight);
            vertex(matrix4f, pose, consumer,  8.0f, -2.0f, 0.5f, 0.0f, packedLight);
            vertex(matrix4f, pose, consumer,  8.0f,  2.0f, 0.5f, 0.15625f, packedLight);
            vertex(matrix4f, pose, consumer, -8.0f,  2.0f, 0.0f, 0.15625f, packedLight);
        }
    }

    private static void vertex(Matrix4f matrix, PoseStack.Pose pose, VertexConsumer consumer, float x, float y, float u, float v, int packedLight) {
        consumer.addVertex(matrix, x, y, 0.0f)
                .setUv(u, v)
                .setUv2(LightTexture.block(packedLight), LightTexture.sky(packedLight))
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setNormal(pose, 0.0f, 1.0f, 0.0f)
                .setColor(0xC8C8C8C8);
    }
}