package dev.higurashi.legendary_spellbooks.common.spells.annihilation;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import dev.higurashi.legendary_spellbooks.common.mixin.helper.ISpellSourceFlag;
import dev.higurashi.legendary_spellbooks.registries.LSSchoolRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.Particle.custom.LightningParticle;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationFlameStrike;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AnnihilationShockwaveSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation_shockwave");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(LSSchoolRegistry.ANNIHILATION_RESOURCE)
            .setMinRarity(SpellRarity.RARE)
            .setCooldownSeconds(16)
            .setMaxLevel(6).build();

    public AnnihilationShockwaveSpell() {
        super(spellResource, spellConfig, false);
        this.castTime = 10;
        this.baseManaCost = 120;
        this.baseSpellPower = 6;
        this.manaCostPerLevel = 10;
        this.spellPowerPerLevel = 2;

        this.allowLooting = false;

        this.castStartAnimation = SpellAnimations.STOMP;
        this.castFinishAnimation = AnimationHolder.pass();

        this.castFinishSound = ModSounds.HUGE_ENERGY_EXPLOSION;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(LegendarySpellbooks.MOD_ID, "health_damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster)), ComponentUtils.format1f(getHealthDamageMultiplier(spellLevel) * 100)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "distance", getBlockDistance(getWaveCount(spellLevel)))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        if (level instanceof ServerLevel serverLevel) {
            createLightning(serverLevel, caster);
            serverLevel.sendParticles(new Circle.RingData(0.0f, (float) (Math.PI / 2.0f), 30, 0.0f, 1.0f, 0.0f, 1.0f, caster.getBbWidth() * 60.0f, false, Circle.EnumRingBehavior.GROW), caster.getX(), caster.getY(), caster.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
        }

        float damage = getSpellPower(spellLevel, caster);
        int waveCount = getWaveCount(spellLevel);
        float healthDamageMultiplier = getHealthDamageMultiplier(spellLevel);

        for (int i = 0; i < waveCount; i++) {
            double distance = getBlockDistance(i);
            int flameCount = 1 + i;

            List<Vec3> wavePoints = GeometryUtils.getFanPoints(caster.position(), caster.getYRot(), 50.0f, distance, flameCount);

            for (Vec3 spawnPoint : wavePoints) {
                Vec3 spawnPos = RaycastUtils.findGround(level, spawnPoint, 4, 2);
                if (spawnPos == null) continue;

                float yaw = GeometryUtils.getYawBetween(caster.position(), spawnPos);
                AnnihilationFlameStrike flame = new AnnihilationFlameStrike(level, spawnPos.x, spawnPos.y, spawnPos.z, yaw, i * 2, caster, 20, damage);
                ((ISpellSourceFlag) flame).legendarySpellbooks$markSpell();
                ((ISpellSourceFlag) flame).legendarySpellbooks$setDamage(healthDamageMultiplier);

                level.addFreshEntity(flame);
            }
        }
        CameraShakeEntity.cameraShake(level, caster.position(), 20.0f, 0.03f, 0, 20);

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private void createLightning(ServerLevel level, LivingEntity caster) {
        double centerX = caster.getX();
        double centerZ = caster.getZ();
        double centerY = caster.getY();

        for(int i = 0; i < 360; i += 10) {
            double angle = Math.toRadians(i);
            double vx = Math.cos(angle) * 2.0f;
            double vz = Math.sin(angle) * 2.0f;

            double vy = (caster.getRandom().nextDouble() - 0.5) * 0.5;

            level.sendParticles(new LightningParticle.OrbData(0, 255, 0), centerX, centerY, centerZ, 1, vx, vy, vz, 1.0);
        }
    }

    private int getWaveCount(int spellLevel) {
        return Math.min(2 + spellLevel, 10);
    }

    private double getBlockDistance(int waveCount) {
        return (waveCount + 1) * 1.5;
    }

    private float getHealthDamageMultiplier(int spellLevel) {
        return 0.01f * spellLevel / 2;
    }
}
