package dev.higurashi.legendary_spellbooks.common.spells.evocation;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSummonSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.SummonedHauntedGuardEntity;
import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.SummonedHauntedKnightEntity;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class SummonHauntedKnightsSpell extends BaseSummonSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "summon_haunted_knights");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.EVOCATION_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setAllowCrafting(false)
            .setCooldownSeconds(120)
            .setMaxLevel(3).build();

    public SummonHauntedKnightsSpell() {
        super(spellResource, spellConfig);
        this.castTime = 30;
        this.baseManaCost = 180;
        this.baseSpellPower = 6;
        this.manaCostPerLevel = 40;
        this.spellPowerPerLevel = 3;
    }

    @Override public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "hp", getHealth(spellLevel))
        );
    }

    @Override
    protected Entity[] getEntitiesToSummon(Level level, LivingEntity caster, int spellLevel) {
        return new Entity[] {
                new SummonedHauntedGuardEntity(level, caster),
                new SummonedHauntedKnightEntity(level, caster),
        };
    }

    public static int getHealth(int spellLevel) { return 30 + spellLevel * 10; }
    public static double getDamage(float spellPower) { return spellPower; }
}
