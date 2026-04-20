package dev.higurashi.legendary_spellbooks.common.spells.ender;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import dev.higurashi.legendary_spellbooks.common.mixin.helper.ISpellSourceFlag;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastResult;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.Particle.custom.MovingTrailParticle;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationPortalEntity;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class AnnihilationGeyserSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation_geyser");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.ENDER_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setCooldownSeconds(60)
            .setMaxLevel(1).build();

    public AnnihilationGeyserSpell() {
        super(spellResource, spellConfig, false);
        this.castTime = 120;
        this.baseManaCost = 500;
        this.baseSpellPower = 30;

        this.castFinishSound = ModSounds.FLAME_BURST;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "aoe_damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "duration", ComponentUtils.ticksToSecondsString(50)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "radius", 8)
        );
    }

    @Override
    public CastResult canBeCastedBy(int spellLevel, CastSource castSource, MagicData magicData, Player player) {
        if (castSource != CastSource.SPELLBOOK) return new CastResult(CastResult.Type.FAILURE, Component.translatable("ui.irons_spellbooks.cast_error_scroll", getDisplayName(player)).withStyle(ChatFormatting.RED));
        return super.canBeCastedBy(spellLevel, castSource, magicData, player);
    }

    @Override
    public void onClientCastTick(Level level, int spellLevel, LivingEntity caster) {
        for (int i = 0; i < 4; i++) {
            ParticleOptions trailData = new MovingTrailParticle.TrailData(0.0f, 0.76f, 0.0f, 0.2f, 0.1f);
            double zOffset = Math.sqrt(i);

            level.addParticle(trailData, caster.getX(), caster.getY(), caster.getZ(), Math.sin(i), 0.0f, zOffset * 0.01f);
        }

        if (caster.tickCount % 15 == 0) {
            ParticleOptions ringData = new Circle.RingData(0.0f, (float) Math.PI / 2.0f, 20, 0.0f, 1.0f, 0.0f, 1.0f, 150.0f, false, Circle.EnumRingBehavior.SHRINK);

            level.addParticle(ringData, caster.getX(), caster.getY(), caster.getZ(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void onServerCastTick(Level level, int spellLevel, LivingEntity caster, @Nullable MagicData magicData) {
        if (magicData != null && magicData.getCastDurationRemaining() == 30) {
            level.playSound(null, caster.getX(), caster.getY(), caster.getZ(), ModSounds.ULTIMATE_FLAME_IMPACT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        CameraShakeEntity.cameraShake(level, caster.position(), 5.0f, 0.025f, 1, 1);

        double[] radius = {0.0, 4.0, 7.5};
        for (double multiplier : radius) {
            pullEntities(level, caster, multiplier);
        }
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        int lifeTick = 50;
        float damage = getSpellPower(spellLevel, caster);

        doPortalEffect(caster, 7.5, lifeTick, damage);
        doPortalEffect(caster, 4.0, lifeTick, damage);

        AnnihilationPortalEntity centerPortal = new AnnihilationPortalEntity(level, caster.getX(), caster.getY(), caster.getZ(), 0.0f, -15, caster, lifeTick, damage, true, 4.0f);
        ((ISpellSourceFlag) centerPortal).legendarySpellbooks$markSpell();
        level.addFreshEntity(centerPortal);

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private void pullEntities(Level level, LivingEntity caster, double multiplier) {
        List<Vec3> positions = GeometryUtils.getCirclePoints(caster.position(), multiplier, 5, 90.0f);

        for (Vec3 position : positions) {
            BlockPos centerPos = BlockPos.containing(position);
            AABB area = new AABB(centerPos).inflate(1.5);

            for (Entity target : level.getEntities(caster, area)) {
                if (target instanceof LivingEntity && !target.isAlliedTo(caster)) {
                    Vec3 pullVec = GeometryUtils.getDirection(target.position(), position).scale(0.1);
                    target.setDeltaMovement(target.getDeltaMovement().subtract(pullVec));
                    target.hurtMarked = true;
                }
            }

            if (multiplier == 0.0) break;
        }
    }

    private void doPortalEffect(LivingEntity caster, double radius, int life, float damage) {
        List<Vec3> positions = GeometryUtils.getCirclePoints(caster.position(), radius, 5, 0.0f);

        for (Vec3 position : positions) {
            Vec3 spawnPos = RaycastUtils.findGround(caster.level(), position, 5, 5);
            if (spawnPos == null) return;

            AnnihilationPortalEntity portal = new AnnihilationPortalEntity(caster.level(), spawnPos.x, spawnPos.y, spawnPos.z, 0.0f, -15, caster, life, damage, true, 2.0f);
            ((ISpellSourceFlag) portal).legendarySpellbooks$markSpell();
            caster.level().addFreshEntity(portal);
        }
    }
}
