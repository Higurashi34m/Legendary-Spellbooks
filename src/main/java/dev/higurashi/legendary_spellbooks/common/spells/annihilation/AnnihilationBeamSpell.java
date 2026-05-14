package dev.higurashi.legendary_spellbooks.common.spells.annihilation;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.common.mixin.helper.ISpellSourceFlag;
import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSchoolRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationBeamEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AnnihilationBeamSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation_beam");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(LSSchoolRegistry.ANNIHILATION_RESOURCE)
            .setMinRarity(SpellRarity.EPIC)
            .setCooldownSeconds(50)
            .setMaxLevel(3).build();

    public AnnihilationBeamSpell() {
        super(spellResource, spellConfig, false);
        this.castTime = 45;
        this.baseManaCost = 350;
        this.baseSpellPower = 10;
        this.manaCostPerLevel = 50;
        this.spellPowerPerLevel = 5;

        this.stopSound = true;
        this.allowLooting = false;

        this.castStartSound = ModSounds.ANNIHILATION_LASER_CHARGE;
        this.castFinishSound = ModSounds.ANNIHILATION_LASER_SINGLE_SHOOT;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "duration", ComponentUtils.ticksToSecondsString(getDuration(spellLevel))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "radius", getBeamLength(spellLevel))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        Vec3 spawnPos = GeometryUtils.getRelativePos(caster, 1.0, -0.15, 0.0);
        float yaw = GeometryUtils.getMathYawRad(caster);
        float pitch = GeometryUtils.getMathPitchRad(caster);

        float damage = getSpellPower(spellLevel, caster);
        float smallDamage = damage * 0.5f;
        int duration = getDuration(spellLevel);
        int length = getBeamLength(spellLevel) * 2;

        CameraShakeEntity.cameraShake(level, spawnPos, 6.0f, 0.05f, duration, duration / 10);
        caster.addEffect(new MobEffectInstance(LSEffectRegistry.BEAM_EFFECT.get(), duration, 0, false, false));

        AnnihilationBeamEntity beam = new AnnihilationBeamEntity(ModEntities.ANNIHILATION_BEAM.get(), level, caster, spawnPos.x, spawnPos.y, spawnPos.z, yaw, pitch, duration, damage, 0.0f, 0, false, 0, 0, 0, false, length);
        ((ISpellSourceFlag) beam).legendarySpellbooks$markSpell();
        ((ISpellSourceFlag) beam).legendarySpellbooks$setDamage(smallDamage);

        level.addFreshEntity(beam);

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getDuration(int spellLevel) {
        return Math.min(20 + spellLevel * 10, 80);
    }

    private int getBeamLength(int spellLevel) {
        return Math.min(20 + spellLevel * 5, 40);
    }
}
