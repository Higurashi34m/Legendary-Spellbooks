package dev.higurashi.legendary_spellbooks.common.spell.annihilation;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.SpellAnnihilationBeamEntity;
import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSchoolRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.CastResult;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AnnihilationBeamSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation_beam");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(LSSchoolRegistry.ANNIHILATION_RESOURCE)
            .setMinRarity(SpellRarity.EPIC)
            .setCooldownSeconds(45)
            .setAllowCrafting(false)
            .setMaxLevel(3).build();

    public AnnihilationBeamSpell() {
        super(spellResource, spellConfig, false);
        this.castTime = 40;
        this.baseManaCost = 200;
        this.baseSpellPower = 8;
        this.manaCostPerLevel = 50;
        this.spellPowerPerLevel = 6;

        this.stopSound = true;

        this.castStartSound = ModSounds.ANNIHILATION_LASER_CHARGE;
        this.castFinishSound = ModSounds.QUAD_ANNIHILATION_LASER_SHOOT;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(LegendarySpellbooks.MOD_ID, "hp_damage", ComponentUtils.format1f(getHpDamage(spellLevel) * 100)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "duration", ComponentUtils.ticksToSecondsString(getDuration(spellLevel))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "distance", getLength(spellLevel))
        );
    }

    @Override
    public CastResult canBeCastedBy(int spellLevel, CastSource source, MagicData magicData, Player player) {
        if (source != CastSource.SPELLBOOK && source != CastSource.COMMAND) {
            return new CastResult(CastResult.Type.FAILURE, ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "cast_error_scroll", getDisplayName(player)).withStyle(ChatFormatting.RED));
        } else {
            return super.canBeCastedBy(spellLevel, source, magicData, player);
        }
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        float damage = this.getDamage(spellLevel, caster);
        float hpDamage = this.getHpDamage(spellLevel);
        int duration = this.getDuration(spellLevel);
        int length = this.getLength(spellLevel);

        caster.addEffect(new MobEffectInstance(LSEffectRegistry.BEAM_EFFECT.get(), duration, 0, true, false));

        Vec3 spawnPos = caster.getEyePosition().add(caster.getForward().scale(1.5));
        SpellAnnihilationBeamEntity beam = new SpellAnnihilationBeamEntity(level, caster, spawnPos, damage, hpDamage, length, duration, 1);
        level.addFreshEntity(beam);

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private float getDamage(int spellLevel, @Nullable LivingEntity caster) { return this.getSpellPower(spellLevel, caster); }
    private float getHpDamage(int spellLevel) { return 0.01f * spellLevel; }
    private int getDuration(int spellLevel) { return Math.max(100 - spellLevel * 20, 40); }
    private int getLength(int spellLevel) { return 20 + 10 * spellLevel; }
}
