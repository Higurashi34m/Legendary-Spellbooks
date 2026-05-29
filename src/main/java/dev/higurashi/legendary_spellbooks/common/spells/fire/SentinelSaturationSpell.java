package dev.higurashi.legendary_spellbooks.common.spells.fire;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.DuneSentinelPhantomEntity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SentinelSaturationSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "sentinel_saturation");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setCooldownSeconds(60)
            .setMaxLevel(2).build();

    public SentinelSaturationSpell() {
        super(spellResource, spellConfig, true);
        this.castTime = 60;
        this.baseManaCost = 400;
        this.baseSpellPower = 6;
        this.manaCostPerLevel = 100;
        this.spellPowerPerLevel = 6;

        this.castFinishSound = ModSounds.CANNON_SHOOT_3;
        this.castFinishAnimation = SpellAnimations.ONE_HANDED_VERTICAL_UPSWING_ANIMATION;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.aoe_damage", this.getSpellPower(spellLevel, caster))
        );
    }

    @Override
    public void onClientCastTick(Level level, int spellLevel, LivingEntity caster) {
        if (caster.getRandom().nextInt() <= 0.24) {
            level.playLocalSound(caster.getX(), caster.getY(), caster.getZ(), SoundEvents.BLAZE_BURN, caster.getSoundSource(), 1.0f + caster.getRandom().nextFloat(), caster.getRandom().nextFloat() * 0.7f + 0.3f, true);
        }

        if (caster.tickCount % 3 == 0) {
            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, caster.getRandomX(1.0), caster.getRandomY(), caster.getRandomZ(1.0), 0.0f, 0.025, 0.0);
            level.addParticle(ParticleTypes.FLAME, caster.getRandomX(1.0), caster.getRandomY(), caster.getRandomZ(1.0), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        float damage = this.getSpellPower(spellLevel, caster);
        int count = getSpawnCount(spellLevel);

        double interval = 5.0;
        float sideYaw = caster.getYRot() + 90.0f;

        double startOffset = -((count - 1) * interval) / 2.0;
        Vec3 centerPos = GeometryUtils.getRelativePos(caster, -5.0, 0.0, 0.0);

        List<Vec3> spawnPositions = GeometryUtils.getLinePoints(centerPos, sideYaw, count, interval, startOffset);

        for (Vec3 spawnPos : spawnPositions) {
            Vec3 finalSpawn = RaycastUtils.findGround(level, spawnPos, 10, 10);
            if (finalSpawn == null) continue;

            DuneSentinelPhantomEntity phantom = new DuneSentinelPhantomEntity(level, caster, damage);

            phantom.setPos(finalSpawn);
            phantom.setYRot(caster.getYRot());
            level.addFreshEntity(phantom);
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getSpawnCount(int spellLevel) {
        return 1 + spellLevel * 2;
    }
}
