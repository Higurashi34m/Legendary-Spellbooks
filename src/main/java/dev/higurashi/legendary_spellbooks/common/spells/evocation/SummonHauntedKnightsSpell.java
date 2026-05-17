package dev.higurashi.legendary_spellbooks.common.spells.evocation;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSummonSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.SummonedFracturedApostleEntity;
import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.SummonedHauntedGuardEntity;
import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.SummonedHauntedKnightEntity;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.HauntedGuardEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.HauntedKnightEntity;
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
        this.castTime = 45;
        this.baseManaCost = 180;
        this.baseSpellPower = 6;
        this.manaCostPerLevel = 40;
        this.spellPowerPerLevel = 3;

        this.allowLooting = false;
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
                new SummonedFracturedApostleEntity(level, caster)
        };
    }

    @Override
    protected void settingSpawnMob(Entity spawnMob, int spellLevel) {
        if (spawnMob instanceof HauntedGuardEntity guard) {
            switch (spellLevel) {
                case 1 -> guard.setTextureVariant(3);
                case 2 -> guard.setTextureVariant(1);
                default -> guard.setTextureVariant(2);
            }
        }
        if (spawnMob instanceof HauntedKnightEntity knight) {
            switch (spellLevel) {
                case 1 -> knight.setTextureVariant(3);
                case 2 -> knight.setTextureVariant(1);
                default -> knight.setTextureVariant(2);
            }
        }
    }

    public static int getHealth(int spellLevel) { return 30 + spellLevel * 10; }
    public static double getDamage(float spellPower) { return spellPower; }
}
