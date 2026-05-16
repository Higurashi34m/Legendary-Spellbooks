package dev.higurashi.legendary_spellbooks.client.renderer.spell.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationBombEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.PowerBallBombRenderer;
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

public class AnnihilationBombChargeLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private AnnihilationBombEntity dummyBomb = null;

    public AnnihilationBombChargeLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, @NotNull T caster, float limbSwing, float swingAmount, float partialTick, float ageInTicks, float headYaw, float headPitch) {
        SyncedSpellData spellData = ClientMagicData.getSyncedSpellData(caster);
        String spellId = spellData.getCastingSpellId();
        if (!spellData.isCasting() || !LSSpellRegistry.ANNIHILATION_BOMB_SPELL.get().getSpellId().equals(spellId)) return;

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<?> renderer = dispatcher.renderers.get(ModEntities.ANNIHILATION_BOMB_ENTITY.get());

        if (renderer instanceof PowerBallBombRenderer bombRenderer) {
            poseStack.pushPose();

            this.getParentModel().translateToHand(HumanoidArm.RIGHT, poseStack);

            poseStack.translate(1.0 / 32.0 - 0.125, 0.5, 0.0);
            poseStack.scale(0.75f, 0.75f, 0.75f);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));

            if (dummyBomb == null) {
                dummyBomb = new AnnihilationBombEntity(ModEntities.ANNIHILATION_BOMB_ENTITY.get(), caster.level());
            }
            dummyBomb.tickCount = caster.tickCount;

            bombRenderer.render(dummyBomb, 0, partialTick, poseStack, buffer, packedLight);

            poseStack.popPose();
        }
    }
}
