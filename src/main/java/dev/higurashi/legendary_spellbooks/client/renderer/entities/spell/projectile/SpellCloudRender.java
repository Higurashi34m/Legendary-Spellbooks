package dev.higurashi.legendary_spellbooks.client.renderer.entities.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.CloudEntity;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.CloudRender;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SpellCloudRender extends CloudRender {
    public SpellCloudRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(CloudEntity entity, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int packedLight) {
        if (entity.isInvisible()) return;
        super.render(entity, yaw, partialTicks, stack, buffer, packedLight);
    }
}