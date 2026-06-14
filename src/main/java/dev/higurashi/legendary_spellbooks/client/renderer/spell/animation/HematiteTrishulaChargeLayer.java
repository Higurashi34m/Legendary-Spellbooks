package dev.higurashi.legendary_spellbooks.client.renderer.spell.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SoulTridentEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.SoulTridentRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class HematiteTrishulaChargeLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private SoulTridentEntity dummyTrident = null;

    public HematiteTrishulaChargeLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, @NotNull T caster, float limbSwing, float swingAmount, float partialTick, float ageInTicks, float headYaw, float headPitch) {
        SyncedSpellData spellData = ClientMagicData.getSyncedSpellData(caster);
        String spellId = spellData.getCastingSpellId();
        if (!spellData.isCasting() || !LSSpellRegistry.HEMATITE_TRISHULA_SPELL.get().getSpellId().equals(spellId)) return;

        if (this.dummyTrident == null) this.dummyTrident = new SoulTridentEntity(ModEntities.SOUL_TRIDENT.get(), caster.level());
        this.dummyTrident.tickCount = caster.tickCount;

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();

        EntityRenderer<? super SoulTridentEntity> renderer = dispatcher.getRenderer(this.dummyTrident);

        if (renderer instanceof SoulTridentRenderer tridentRenderer) {
            poseStack.pushPose();

            this.getParentModel().translateToHand(HumanoidArm.RIGHT, poseStack);

            poseStack.translate(1.0 / 32.0, -1.0, 0.0);
            poseStack.scale(1.0f, 1.0f, 1.0f);

            tridentRenderer.render(this.dummyTrident, 0.0f, partialTick, poseStack, buffer, packedLight);

            poseStack.popPose();
        }
    }
}
