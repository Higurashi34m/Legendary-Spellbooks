package dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellBombEntity;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.BombModel;
import net.miauczel.legendary_monsters.entity.client.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SpellBombRenderer extends EntityRenderer<SpellBombEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("legendary_monsters", "textures/entity/bomb_dune.png");
    private final BombModel model;

    public SpellBombRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BombModel(context.bakeLayer(ModModelLayers.BOMB_LAYER));
    }

    @Override @NotNull
    public ResourceLocation getTextureLocation(@NotNull SpellBombEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(SpellBombEntity entity, float entityYaw, float partialTick, PoseStack stack, MultiBufferSource source, int packedLight) {
        stack.pushPose();

        stack.translate(0.0, 0.5, 0.0);
        stack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot())));
        stack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));

        VertexConsumer vertexconsumer = source.getBuffer(this.model.renderType(this.getTextureLocation(entity)));

        this.model.renderToBuffer(stack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

        stack.popPose();

        super.render(entity, entityYaw, partialTick, stack, source, packedLight);
    }
}
