package dev.higurashi.legendary_spellbooks.common.effect.handler;

import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.player.SpinAttackType;
import net.miauczel.legendary_monsters.Particle.custom.AnnihilationBombTrail;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.Particle.custom.LightningParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class FlamebornDriftSpellHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!(event.phase == TickEvent.Phase.END) || !(event.side.isClient())) return;

        Player player = event.player;
        if (player == null) return;

        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) return;

        List<Entity> casters = level.getEntities((Entity) null, player.getBoundingBox().inflate(64), entity -> entity instanceof LivingEntity);
        for (Entity caster : casters) {
            LivingEntity entity = (LivingEntity) caster;

            if (entity.isAutoSpinAttack() && ClientMagicData.getSyncedSpellData(entity).getSpinAttackType().equals(SpinAttackType.FIRE)) {
                float yRot = (float) Math.toRadians(entity.yBodyRot);
                float cos = Mth.cos(yRot);
                float sin = Mth.sin(yRot);

                double centerX = entity.getX();
                double centerY = entity.getY();
                double centerZ = entity.getZ();

                double leftX = centerX + (cos * 2.0);
                double leftZ = centerZ + (sin * 2.0);
                double rightX = centerX - (cos * 2.0);
                double rightZ = centerZ - (sin * 2.0);

                float greenShade = 0.76f + entity.getRandom().nextFloat() * 0.4f;

                level.addParticle(new AnnihilationBombTrail.OrbData(0.0f, greenShade, 0.0f, 0.5f, 1.0f, entity.getId()), leftX, centerY + 1.5, leftZ, 0, 0, 0);
                level.addParticle(new AnnihilationBombTrail.OrbData(0.0f, greenShade, 0.0f, 0.5f, 1.0f, entity.getId()), rightX, centerY + 1.5, rightZ, 0, 0, 0);

                Vec3 delta = entity.getDeltaMovement();

                if (entity.tickCount % 2 == 0) {
                    float yaw = (float) Math.atan2(delta.x, delta.z);

                    double horizontalLen = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
                    float pitch = (float) -Math.atan2(delta.y, horizontalLen);

                    level.addParticle(new Circle.RingData(yaw, pitch, 30, 0.0f, 1.0f, 0.0f, 1.0f, 40.0f, false, Circle.EnumRingBehavior.GROW_THEN_SHRINK), centerX, centerY + 0.5, centerZ, 0, 0, 0);
                }

                double rx = (entity.getRandom().nextDouble() - 0.5) + delta.x;
                double ry = (entity.getRandom().nextDouble() - 0.5) + delta.y;
                double rz = (entity.getRandom().nextDouble() - 0.5) + delta.z;
                level.addParticle(new LightningParticle.OrbData(25, 255, 0), centerX + rx, centerY + 0.5, centerZ + rz, rx * 2.0, ry * 2.0, rz * 2.0);
                level.addParticle(new LightningParticle.OrbData(255, 255, 255), centerX + rx, centerY + 0.5, centerZ + rz, rx * 2.0, ry * 2.0, rz * 2.0);
            }
        }
    }
}

