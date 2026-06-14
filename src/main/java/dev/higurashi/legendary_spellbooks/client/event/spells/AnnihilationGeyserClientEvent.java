package dev.higurashi.legendary_spellbooks.client.event.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.client.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class AnnihilationGeyserClientEvent {
    private static final ResourceLocation[] PORTAL_TEXTURES = new ResourceLocation[]{
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_0.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_1.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_2.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_3.png"),
            ResourceLocation.fromNamespaceAndPath(LegendaryMonsters.MOD_ID, "textures/entity/annihilation_portal/type_a/annihilation_portal_4.png")
    };

    @SubscribeEvent
    public static void renderAnnihilationPortal(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null || minecraft.level == null) return;

        if (ClientMagicData.isCasting() && ClientMagicData.getCastingSpellId().equals(LSSpellRegistry.ANNIHILATION_GEYSER_SPELL.get().getSpellId())) {
            PoseStack poseStack = event.getPoseStack();
            float partialTick = event.getPartialTick().getGameTimeDeltaTicks();
            Vec3 cameraPos = event.getCamera().getPosition();

            int frame = (int) ((player.tickCount + partialTick) * 12 / 20 % PORTAL_TEXTURES.length);
            ResourceLocation texture = PORTAL_TEXTURES[Math.floorMod(frame, PORTAL_TEXTURES.length)];

            float castPercent = ClientMagicData.getCastCompletionPercent();

            poseStack.pushPose();
            poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

            double positionX = Mth.lerp(partialTick, player.xOld, player.getX());
            double positionY = Mth.lerp(partialTick, player.yOld, player.getY()) + 0.1;
            double positionZ = Mth.lerp(partialTick, player.zOld, player.getZ());
            poseStack.translate(positionX, positionY, positionZ);

            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(texture));

            float easedPercent = (float) Math.cbrt(castPercent);
            float bigSize = Mth.clamp(easedPercent * 4.0f, 0.0f, 4.0f);
            float smallSize = Mth.clamp(easedPercent * 2.0f, 0.0f, 2.0f);

            render(bigSize, 0, 0, consumer, poseStack);

            for (Vec3 pos : GeometryUtils.getCirclePoints(Vec3.ZERO, 7.5, 5, 0)) render(smallSize, pos.x, pos.z, consumer, poseStack);
            for (Vec3 pos : GeometryUtils.getCirclePoints(Vec3.ZERO, 4.0, 5, 0)) render(smallSize, pos.x, pos.z, consumer, poseStack);

            poseStack.popPose();
            bufferSource.endBatch();
        }
    }

    private static void render(float size, double x, double z, VertexConsumer consumer, PoseStack poseStack) {
        RenderUtils.renderQuad(size, x, 0, z, 90, 0, 0, consumer, poseStack, OverlayTexture.NO_OVERLAY, LightTexture.pack(15, 15));
    }
}
