package dev.higurashi.legendary_spellbooks.client.renderer.spell.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.higurashi.legendary_spellbooks.registry.LSEffectRegistry;
import net.miauczel.legendary_monsters.entity.client.ModModelLayers;
import net.miauczel.legendary_monsters.entity.client.Model.NewPossessedPaladinModel;
import net.miauczel.legendary_monsters.entity.client.Render.LMRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class PaladinWingsLayer <T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M>  {
    private static final ResourceLocation POSSESSED_PALADIN_WINGS_RED = ResourceLocation.fromNamespaceAndPath("legendary_monsters", "textures/entity/posessed_paladin/layer/possessed_paladin_wings_red_layer.png");

    private NewPossessedPaladinModel<?> paladinModel = null;

    public PaladinWingsLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, @NotNull T caster, float limbSwing, float swingAmount, float partialTick, float ageInTicks, float headYaw, float headPitch) {
        if (!caster.hasEffect(LSEffectRegistry.POSSESSED_WING_EFFECT.get())) return;

        if (this.paladinModel == null) {
            this.paladinModel = new NewPossessedPaladinModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.NEW_POSSESSED_PALADIN_LAYER));
        }

        this.paladinModel.root().getAllParts().forEach(ModelPart::resetPose);

        float angle = ageInTicks * (caster.isFallFlying() ? 0.35f : 0.15f);

        float baseFlap = Mth.cos(angle) * 0.15f;
        float edgeFold = (Mth.sin(angle) + 0.5f) * 0.6f;

        ModelPart lowerbody = this.paladinModel.root().getChild("lowerbody");
        ModelPart body = lowerbody.getChild("body");

        ModelPart rightWing = body.getChild("RightWing");
        ModelPart rightWingEdge = rightWing.getChild("RightWingEdge");

        ModelPart leftWing = body.getChild("LeftWing");
        ModelPart leftWingEdge = leftWing.getChild("LeftWingEdge");

        rightWing.yRot += baseFlap;
        leftWing.yRot -= baseFlap;

        rightWingEdge.yRot += edgeFold;
        leftWingEdge.yRot -= edgeFold;

        poseStack.pushPose();

        this.getParentModel().body.translateAndRotate(poseStack);

        poseStack.translate(0.0, 0.0, -0.15);

        VertexConsumer consumer = buffer.getBuffer(LMRenderTypes.entityTranslucentEmissive(POSSESSED_PALADIN_WINGS_RED));

        int maxLight = 15728640;
        this.paladinModel.renderToBuffer(poseStack, consumer, maxLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 0.8f);

        poseStack.popPose();
    }
}