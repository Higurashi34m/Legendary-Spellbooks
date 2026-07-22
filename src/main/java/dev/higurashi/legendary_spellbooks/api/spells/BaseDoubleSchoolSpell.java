package dev.higurashi.legendary_spellbooks.api.spells;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.config.IronConfigParameters;
import io.redspace.ironsspellbooks.api.config.SpellConfigManager;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Supplier;

public abstract class BaseDoubleSchoolSpell extends BaseSpell {
    protected final Supplier<SchoolType> anotherSchool;
    protected final int level;

    public BaseDoubleSchoolSpell(ResourceLocation spellResource, DefaultConfig spellConfig, CastType castType, int level, Supplier<SchoolType> anotherSchool) {
        super(spellResource, spellConfig, castType);
        this.level = level;
        this.anotherSchool = anotherSchool;
    }

    @Override
    public SchoolType getSchoolType() {
        if (isAnother(this.level)) return this.anotherSchool.get();
        else return super.getSchoolType();
    }

    @Override
    public float getSpellPower(int spellLevel, Entity caster) {
        float basePower = this.baseSpellPower + this.spellPowerPerLevel * (spellLevel - 1);

        double spellPowerModifier = 1.0;
        double schoolPowerModifier = 1.0;
        float configModifier = SpellConfigManager.getSpellConfigValue(this, IronConfigParameters.POWER_MULTIPLIER).floatValue();

        if (caster instanceof LivingEntity livingCaster) {
            spellPowerModifier = livingCaster.getAttributeValue(AttributeRegistry.SPELL_POWER.get());

            SchoolType normalSchool = SpellConfigManager.getSpellConfigValue(this, IronConfigParameters.SCHOOL);
            if (isAnother(spellLevel)) schoolPowerModifier = this.anotherSchool.get().getPowerFor(livingCaster);
            else schoolPowerModifier = normalSchool.getPowerFor(livingCaster);
        }

        return (float) (basePower * spellPowerModifier * schoolPowerModifier * configModifier);
    }

    public boolean isAnother(int spellLevel) {
        return spellLevel >= this.getChangeSchoolLevel();
    }

    public abstract int getChangeSchoolLevel();
    public abstract BaseDoubleSchoolSpell newDoubleSchoolSpell(int newLevel);
}
