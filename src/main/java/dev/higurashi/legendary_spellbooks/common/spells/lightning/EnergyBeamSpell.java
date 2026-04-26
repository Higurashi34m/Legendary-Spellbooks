package dev.higurashi.legendary_spellbooks.common.spells.lightning;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastResult;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.EnergyBeamEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EnergyBeamSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "energy_beam");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setCooldownSeconds(45)
            .setAllowCrafting(false)
            .setMaxLevel(3).build();

    public EnergyBeamSpell() {
        super(spellResource, spellConfig, false);
        this.castTime = 45;
        this.baseManaCost = 400;
        this.baseSpellPower = 25;
        this.manaCostPerLevel = 50;
        this.spellPowerPerLevel = 5;

        this.allowLooting = false;
        this.stopSound = true;

        this.castStartSound = ModSounds.BEAM_CHARGE;
        this.castFinishSound = ModSounds.BEAM_GO;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "duration", ComponentUtils.ticksToSecondsString(getDuration(spellLevel)))
        );
    }

    @Override
    public CastResult canBeCastedBy(int spellLevel, CastSource castSource, MagicData magicData, Player player) {
        if (castSource != CastSource.SPELLBOOK) return new CastResult(CastResult.Type.FAILURE, Component.translatable("ui.irons_spellbooks.cast_error_scroll", getDisplayName(player)).withStyle(ChatFormatting.RED));
        return super.canBeCastedBy(spellLevel, castSource, magicData, player);
    }

    @Override
    public void onServerCastTick(Level level, int spellLevel, LivingEntity caster, MagicData magicData) {
        if (caster.isShiftKeyDown()) caster.setDeltaMovement(caster.getDeltaMovement().x, 0.20f, caster.getDeltaMovement().z);
        else caster.setDeltaMovement(caster.getDeltaMovement().x, 0.25f, caster.getDeltaMovement().z);
        caster.hurtMarked = true;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        Vec3 spawnPos = GeometryUtils.getRelativePos(caster, 1.0, -0.15, 0.0);

        float yaw = GeometryUtils.getMathYawRad(caster);
        float pitch = GeometryUtils.getMathPitchRad(caster);

        float damage = getSpellPower(spellLevel, caster);
        int duration = getDuration(spellLevel);

        caster.addEffect(new MobEffectInstance(LSEffectRegistry.BEAM_EFFECT.get(), duration, Math.min(spellLevel, 5), false, false));
        EnergyBeamEntity beam = new EnergyBeamEntity(ModEntities.ENERGY_BEAM.get(), level, caster, spawnPos.x, spawnPos.y, spawnPos.z, yaw, pitch, duration, damage, 0.0f);

        level.addFreshEntity(beam);
        caster.resetFallDistance();

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getDuration(int spellLevel) {
        return Math.min(40 + spellLevel * 10, 80);
    }
}
