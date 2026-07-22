package dev.higurashi.legendary_spellbooks.client.renderer.spell.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile.SpellAnnihilationBombRenderer;
import dev.higurashi.legendary_spellbooks.registry.LSSpellRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class AnnihilationBombLayer<E extends LivingEntity, M extends HumanoidModel<E>> extends RenderLayer<E, M> {
    public AnnihilationBombLayer(RenderLayerParent<E, M> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, @NotNull E entity, float limbSwing, float swingAmount, float delta, float ageInTicks, float headYaw, float headPitch) {
        SyncedSpellData spellData = ClientMagicData.getSyncedSpellData(entity);
        if (!spellData.isCasting() || !spellData.getCastingSpellId().equals(LSSpellRegistry.ANNIHILATION_BOMB_SPELL.get().getSpellId())) return;

        poseStack.pushPose();
        this.getParentModel().translateToHand(HumanoidArm.RIGHT, poseStack);
        poseStack.translate(-0.2, 0.2, 0.0);
        poseStack.scale(0.75f, 0.75f, 0.75f);
        SpellAnnihilationBombRenderer.renderModel(poseStack, buffer, entity.level(), ageInTicks, packedLight);
        poseStack.popPose();
    }

    public static class Geo extends GeoRenderLayer<AbstractSpellCastingMob> {
        public Geo(GeoRenderer<AbstractSpellCastingMob> renderer) {
            super(renderer);
        }

        @Override
        public void renderForBone(PoseStack poseStack, AbstractSpellCastingMob entity, GeoBone bone, RenderType renderType, MultiBufferSource buffer, VertexConsumer consumer, float partialTick, int packedLight, int packedOverlay) {
            SyncedSpellData spellData = ClientMagicData.getSyncedSpellData(entity);
            if (!spellData.isCasting() || !spellData.getCastingSpellId().equals(LSSpellRegistry.ANNIHILATION_BOMB_SPELL.get().getSpellId())) return;

            if (bone.getName().equals("right_arm")) {
                poseStack.pushPose();
                poseStack.translate(0.5, -0.3, 0.0);
                poseStack.scale(0.75f, 0.75f, 0.75f);
                SpellAnnihilationBombRenderer.renderModel(poseStack, buffer, entity.level(), entity.tickCount + partialTick, packedLight);
                poseStack.popPose();
            }
        }
    }
}