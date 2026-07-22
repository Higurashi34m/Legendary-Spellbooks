package dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.SpellAnnihilationBombEntity;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationBombEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.PowerBallBombModel;
import net.miauczel.legendary_monsters.entity.client.ModModelLayers;
import net.miauczel.legendary_monsters.entity.client.Render.LMRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpellAnnihilationBombRenderer extends EntityRenderer<SpellAnnihilationBombEntity> {
    private static final ResourceLocation INNER_TEXTURE = ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/the_warped_one/ball/power_ball_inner.png");
    private static final ResourceLocation OUTER_TEXTURE = ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/the_warped_one/ball/power_ball_outer.png");

    private static PowerBallBombModel model;
    private static AnnihilationBombEntity dummy;

    public SpellAnnihilationBombRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new PowerBallBombModel(context.bakeLayer(ModModelLayers.POWER_BALL_LAYER));
    }

    @Override @NotNull
    public ResourceLocation getTextureLocation(@NotNull SpellAnnihilationBombEntity entity) {
        return INNER_TEXTURE;
    }

    @Override
    public void render(@NotNull SpellAnnihilationBombEntity entity, float yaw, float delta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        float time = (float) entity.tickCount + delta;
        renderModel(poseStack, buffer, entity.level(), time, packedLight);
    }

    public static void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, Level level, float time, int packedLight) {
        if (model == null) return;

        if (dummy == null) dummy = new AnnihilationBombEntity(ModEntities.ANNIHILATION_BOMB_ENTITY.get(), level);
        dummy.tickCount = (int) time;

        poseStack.pushPose();

        VertexConsumer innerConsumer = bufferSource.getBuffer(LMRenderTypes.getGlowEyes(INNER_TEXTURE));
        model.setupAnim(dummy, 0.0f, 0.0f, time, 0.0f, 0.0f);
        model.renderToBuffer(poseStack, innerConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

        VertexConsumer outerConsumer = bufferSource.getBuffer(LMRenderTypes.getGlowEyes(OUTER_TEXTURE));
        model.renderToBuffer(poseStack, outerConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 0.4f);

        poseStack.popPose();
    }
}
