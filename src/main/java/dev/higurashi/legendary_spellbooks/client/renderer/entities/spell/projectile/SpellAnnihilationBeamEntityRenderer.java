package dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.SpellAnnihilationBeamEntity;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.entity.client.Render.LMRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class SpellAnnihilationBeamEntityRenderer extends EntityRenderer<SpellAnnihilationBeamEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/the_warped_one/annihilation_beam.png");

    public SpellAnnihilationBeamEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override @NotNull
    public ResourceLocation getTextureLocation(@NotNull SpellAnnihilationBeamEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(@NotNull SpellAnnihilationBeamEntity entity, float yaw, float delta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        if (entity.getWarmup() > 0) return;

        double collideX = entity.collidePosX;
        double collideY = entity.collidePosY;
        double collideZ = entity.collidePosZ;

        double posX = entity.getX();
        double posY = entity.getY();
        double posZ = entity.getZ();

        float length = (float) Math.sqrt(Math.pow(collideX - posX, 2) + Math.pow(collideY - posY, 2) + Math.pow(collideZ - posZ, 2));

        int frame = 5;

        VertexConsumer consumer = buffer.getBuffer(LMRenderTypes.getGlowingEffect(this.getTextureLocation(entity)));

        Quaternionf beamRotation = new Quaternionf()
                .rotationX((float) Math.toRadians(90.0))
                .rotateZ((float) Math.toRadians(entity.getYRot()))
                .rotateX((float) Math.toRadians(entity.getXRot()));

        poseStack.pushPose();
        poseStack.mulPose(beamRotation);

        this.renderStart(frame, poseStack, consumer, packedLight);
        this.renderBeam(length, frame, poseStack, consumer, packedLight);

        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(collideX - posX, collideY - posY, collideZ - posZ);

        poseStack.pushPose();
        poseStack.mulPose(beamRotation);
        this.renderStart(frame, poseStack, consumer, packedLight);
        poseStack.popPose();

        this.renderEnd(frame, entity.blockSide, poseStack, consumer, packedLight);
        poseStack.popPose();
    }

    private void renderFlatQuad(int frame, PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        float minU = 0.0f + 0.0625f * frame;
        float minV = 0.0f;
        float maxU = minU + 0.0625f;
        float maxV = minV + 0.5f;

        PoseStack.Pose lastEntry = poseStack.last();
        Matrix4f matrix4f = lastEntry.pose();
        Matrix3f matrix3f = lastEntry.normal();

        this.drawVertex(matrix4f, matrix3f, consumer, -1.3f, -1.3f, 0.0f, minU, minV, packedLight);
        this.drawVertex(matrix4f, matrix3f, consumer, -1.3f, 1.3f, 0.0f, minU, maxV, packedLight);
        this.drawVertex(matrix4f, matrix3f, consumer, 1.3f, 1.3f, 0.0f, maxU, maxV, packedLight);
        this.drawVertex(matrix4f, matrix3f, consumer, 1.3f, -1.3f, 0.0f, maxU, minV, packedLight);
    }

    private void renderStart(int frame, PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        poseStack.pushPose();

        poseStack.mulPose(new Quaternionf().rotationX((float) Math.toRadians(-90.0)));

        this.renderFlatQuad(frame, poseStack, consumer, packedLight);
        poseStack.popPose();
    }

    private void renderEnd(int frame, Direction side, PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        if (side == null) return;

        poseStack.pushPose();
        poseStack.mulPose(side.getRotation());
        poseStack.mulPose(new Quaternionf().rotationX((float) Math.toRadians(90.0)));
        poseStack.translate(0.0f, 0.0f, -0.01f);
        this.renderFlatQuad(frame, poseStack, consumer, packedLight);
        poseStack.popPose();
    }

    private void renderBeam(float length, int frame, PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        for (int i = 0; i < 2; i++) {
            poseStack.pushPose();
            poseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(45.0 + i * 90.0)));
            this.drawBeam(length, frame, poseStack, consumer, packedLight);
            poseStack.popPose();
        }
    }

    private void drawBeam(float length, int frame, PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        float minU = 0.0f;
        float minV = 0.5f + 0.03125f * frame;
        float maxU = minU + 0.078125f;
        float maxV = minV + 0.03125f;

        PoseStack.Pose lastEntry = poseStack.last();
        Matrix4f matrix4f = lastEntry.pose();
        Matrix3f matrix3f = lastEntry.normal();

        this.drawVertex(matrix4f, matrix3f, consumer, -1.0f, 0.0f, 0.0f, minU, minV, packedLight);
        this.drawVertex(matrix4f, matrix3f, consumer, -1.0f, length, 0.0f, minU, maxV, packedLight);
        this.drawVertex(matrix4f, matrix3f, consumer, 1.0f, length, 0.0f, maxU, maxV, packedLight);
        this.drawVertex(matrix4f, matrix3f, consumer, 1.0f, 0.0f, 0.0f, maxU, minV, packedLight);
    }

    private void drawVertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer consumer, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, int packedLight) {
        consumer.vertex(matrix4f, offsetX, offsetY, offsetZ)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .uv(textureX, textureY)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(matrix3f, 0.0f, 1.0f, 0.0f)
                .endVertex();
    }
}
