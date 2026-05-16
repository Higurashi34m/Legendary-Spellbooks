package dev.higurashi.legendary_spellbooks.common.spells.annihilation;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSummonSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.SummonedAnnihilationPursuerEntity;
import dev.higurashi.legendary_spellbooks.registries.LSSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ReleaseRiftwalkerPredatorSpell extends BaseSummonSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "release_riftwalker_predator");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(LSSchoolRegistry.ANNIHILATION_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setCooldownSeconds(500)
            .setAllowCrafting(false)
            .setMaxLevel(3).build();

    public ReleaseRiftwalkerPredatorSpell() {
        super(spellResource, spellConfig);

        this.castTime = 100;
        this.baseManaCost = 700;
        this.baseSpellPower = 15;
        this.manaCostPerLevel = 50;
        this.spellPowerPerLevel = 3;

        this.allowLooting = false;
    }

    @Override public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                Component.translatable("ui.irons_spellbooks.hp", getHealth(spellLevel))
        );
    }

    @Override
    protected Entity[] getEntitiesToSummon(Level level, LivingEntity caster, int spellLevel) {
        return new Entity[] {
                new SummonedAnnihilationPursuerEntity(level, caster)
        };
    }

    @Override
    protected Vec3 getSpawnOffset(int index, int totalCount, float yaw, LivingEntity caster) {
        return caster.getLookAngle().scale(-1);
    }

    public static int getHealth(int spellLevel) { return 140 + spellLevel * 20; }
    public static double getDamage(float spellPower) { return spellPower; }
}
