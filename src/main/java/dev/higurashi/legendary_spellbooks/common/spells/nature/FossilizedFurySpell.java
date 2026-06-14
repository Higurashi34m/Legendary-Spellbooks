package dev.higurashi.legendary_spellbooks.common.spells.nature;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSummonSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.SummonedSkeloraptorEntity;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FossilizedFurySpell extends BaseSummonSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "fossilized_fury");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.NATURE_RESOURCE)
            .setMinRarity(SpellRarity.EPIC)
            .setCooldownSeconds(180)
            .setAllowCrafting(false)
            .setMaxLevel(6).build();

    public FossilizedFurySpell() {
        super(spellResource, spellConfig);
        this.castTime = 80;
        this.baseManaCost = 100;
        this.baseSpellPower = 4;
        this.manaCostPerLevel = 35;
        this.spellPowerPerLevel = 1;

        this.allowLooting = false;
    }

    @Override public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "hp", getHealth(spellLevel)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "summon_count", spellLevel)
        );
    }

    @Override
    protected Vec3 getSpawnOffset(int index, int totalCount, float yaw, LivingEntity caster) {
        return GeometryUtils.getPointInCircle(Vec3.ZERO, 2.0, totalCount, index, caster.getYRot());
    }

    @Override
    protected Entity[] getEntitiesToSummon(Level level, LivingEntity caster, int spellLevel) {
        Entity[] entities = new Entity[spellLevel];
        for (int i = 0; i < spellLevel; i++) {
            entities[i] = new SummonedSkeloraptorEntity(level, caster);
        }

        return entities;
    }

    public static int getHealth(int spellLevel) { return 20 + spellLevel * 5; }
    public static double getDamage(float spellPower) { return spellPower; }
}
