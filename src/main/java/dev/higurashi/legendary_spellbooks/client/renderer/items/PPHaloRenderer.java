package dev.higurashi.legendary_spellbooks.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class PPHaloRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack itemStack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource multiBufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LivingEntity entity = slotContext.entity();

        poseStack.pushPose();

        if (renderLayerParent.getModel() instanceof HumanoidModel<?> humanoidModel) {
            ModelPart head = humanoidModel.head;
            head.translateAndRotate(poseStack);
            poseStack.scale(0.5f, 0.5f, 0.5f);

            poseStack.translate(0.42, -1, -0.5);
            poseStack.mulPose(Axis.XP.rotationDegrees(-55));
            poseStack.mulPose(Axis.YP.rotationDegrees(-30));
            poseStack.mulPose(Axis.ZP.rotationDegrees(10));


            Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.NONE, 0xF000F0, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, entity.level(), 0);
        }

        poseStack.popPose();
    }
}