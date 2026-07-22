package dev.higurashi.legendary_spellbooks.common.spell.annihilation;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.AnnihilationArrowEntity;
import dev.higurashi.legendary_spellbooks.registries.LSSchoolRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AnnihilationArrowSpell extends BaseSpell {
    private static final ResourceLocation SPELL_ID = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation_arrow");
    private static final DefaultConfig CONFIG = new DefaultConfig()
            .setSchoolResource(LSSchoolRegistry.ANNIHILATION_RESOURCE)
            .setMinRarity(SpellRarity.RARE)
            .setCooldownSeconds(10)
            .setMaxLevel(8).build();

    public AnnihilationArrowSpell() {
        super(SPELL_ID, CONFIG, true);
        this.castTime = 30;
        this.baseManaCost = 80;
        this.baseSpellPower = 8;
        this.manaCostPerLevel = 10;
        this.spellPowerPerLevel = 2;

        this.castStartAnimation = SpellAnimations.BOW_CHARGE_ANIMATION;
        this.castFinishAnimation = AnimationHolder.none();

        this.castStartSound = SoundRegistry.MAGIC_ARROW_CHARGE;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(LegendarySpellbooks.MOD_ID, "hp_damage", ComponentUtils.format2f(this.getHpDamage(spellLevel) * 100)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID,      "damage",    ComponentUtils.format1f(this.getDamage(spellLevel, caster)))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        float damage = this.getDamage(spellLevel, caster);
        float hpDamage = this.getHpDamage(spellLevel);

        Vec3 spawnPos = caster.getEyePosition().add(caster.getForward());
        AnnihilationArrowEntity arrow = new AnnihilationArrowEntity(level, caster, spawnPos, damage, hpDamage);
        arrow.shoot(caster.getLookAngle());
        level.addFreshEntity(arrow);

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private float getDamage(int spellLevel, LivingEntity caster) { return this.getSpellPower(spellLevel, caster); }
    private float getHpDamage(int spellLevel) { return 0.0025f * spellLevel; }
}
