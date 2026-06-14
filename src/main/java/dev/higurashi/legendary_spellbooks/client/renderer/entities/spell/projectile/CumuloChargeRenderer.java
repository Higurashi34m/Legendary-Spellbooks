package dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.higurashi.legendary_spellbooks.client.model.entities.spell.projectile.CumuloChargeModel;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.CumuloChargeEntity;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.entity.client.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class CumuloChargeRenderer extends MobRenderer<CumuloChargeEntity, CumuloChargeModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/cloud_golem/cloud_golem.png");

    public CumuloChargeRenderer(EntityRendererProvider.Context context) {
        super(context, new CumuloChargeModel(context.bakeLayer(ModModelLayers.CLOUD_GOLEM_LAYER)), 1.5f);
    }

    @Override
    public void render(@NotNull CumuloChargeEntity entity, float yaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        this.model.setupAnim(entity, 0, 0, entity.tickCount + partialTicks, 0, 0);

        poseStack.pushPose();
        poseStack.scale(-1.0f, -1.0f, 1.0f);
        poseStack.translate(0.0f, -1.5f, 0.0f);

        float rotationYaw = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(rotationYaw + 180.0f));

        RenderType type = RenderType.entityTranslucent(getTextureLocation(entity));
        VertexConsumer vertexConsumer = buffer.getBuffer(type);

        int color = 0x4DFFFFFF;
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0f), color);

        poseStack.popPose();
    }

    @Override @NotNull public ResourceLocation getTextureLocation(@NotNull CumuloChargeEntity entity) { return TEXTURE; }
    @Override protected RenderType getRenderType(@NotNull CumuloChargeEntity entity, boolean bodyVisible, boolean translucent, boolean outline) { return RenderType.entityTranslucent(getTextureLocation(entity)); }
}
