package dev.higurashi.legendary_spellbooks.common.item.handler;

import dev.higurashi.daybreaklib.api.client.animation.AnimationController;
import dev.higurashi.daybreaklib.api.util.GeometryUtils;
import dev.higurashi.daybreaklib.api.util.position.PositionSearch;
import dev.higurashi.legendary_spellbooks.common.item.AnnihilatorsProtocolSpellbookItem;
import dev.higurashi.legendary_spellbooks.common.tag.LSTags;
import dev.higurashi.legendary_spellbooks.registry.LSAnimationRegistry;
import dev.higurashi.legendary_spellbooks.registry.LSItemRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.item.UniqueSpellBook;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.AnnihilationBombTrail;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnnihilationProtocolSpellbookHandler {
    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide) return;
        if (!((UniqueSpellBook) LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get()).isEquippedBy(entity)) return;

        DamageSource source = event.getSource();
        if (source.is(LSTags.BYPASS_ANNIHILATORS_PROTOCOL)) return;
        if (entity.getRandom().nextFloat() > AnnihilatorsProtocolSpellbookItem.getDodgePercent()) return;

        MagicManager.spawnParticles(entity.level(), ModParticles.TELEPORT_EFFECT.get(), entity.getX(), entity.getEyeY(), entity.getZ(), 1, 0, 0, 0, 0, false);
        MagicManager.spawnParticles(entity.level(), new AnnihilationBombTrail.OrbData(0.0f, 1.0f, 0.0f, 0.0f, entity.getBbHeight() / 2, entity.getId()), entity.getX(), entity.getEyeY(), entity.getZ(), 2, 0, 0, 0, 0, false);
        entity.level().playSound(null, entity.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);
        entity.level().playSound(null, entity.blockPosition(), SoundEvents.SHULKER_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);

        Vec3 teleportPos = PositionSearch.builder(entity.level())
                .positions(GeometryUtils.pointsOnCircle(entity.position(), 3, 40))
                .requireSpace(entity.getDimensions(entity.getPose()))
                .requireLineOfSight(List.of(entity.position(), entity.position().add(0, entity.getBbHeight(), 0)))
                .ground(3, 3)
                .build().find(-1);
        if (teleportPos != null) entity.teleportTo(teleportPos.x, teleportPos.y, teleportPos.z);

        AnimationController.syncPlay(entity, LSAnimationRegistry.ANNIHILATOR_DODGE);
        event.setCanceled(true);
    }
}
