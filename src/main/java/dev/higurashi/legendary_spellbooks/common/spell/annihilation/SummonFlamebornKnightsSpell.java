package dev.higurashi.legendary_spellbooks.common.spell.annihilation;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSummonSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.common.entity.spell.summoned.SummonedFlamebornGuardEntity;
import dev.higurashi.legendary_spellbooks.common.entity.spell.summoned.SummonedFlamebornWarriorEntity;
import dev.higurashi.legendary_spellbooks.registry.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registry.LSSchoolRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.miauczel.legendary_monsters.Particle.custom.MovingTrailParticle;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SummonFlamebornKnightsSpell extends BaseSummonSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "summon_flameborn_knights");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(LSSchoolRegistry.ANNIHILATION_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setCooldownSeconds(240)
            .setMaxLevel(5).build();

    public SummonFlamebornKnightsSpell() {
        super(spellResource, spellConfig);
        this.castTime = 60;
        this.baseManaCost = 250;
        this.baseSpellPower = 8;
        this.manaCostPerLevel = 50;
        this.spellPowerPerLevel = 3;

        this.allowLooting = false;
    }

    // === SPELL SETTINGS ===
    @Override public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                ComponentUtils.getUIComponent(LegendarySpellbooks.MOD_ID, "hp", getHealth(spellLevel, false), LSEntityRegistry.SUMMONED_FLAMEBORN_GUARD_ENTITY.get().getDescription()),
                ComponentUtils.getUIComponent(LegendarySpellbooks.MOD_ID, "hp", getHealth(spellLevel, true), LSEntityRegistry.SUMMONED_FLAMEBORN_WARRIOR_ENTITY.get().getDescription())
        );
    }

    @Override
    protected Entity[] getEntitiesToSummon(Level level, LivingEntity caster, int spellLevel) {
        return new Entity[]{
                new SummonedFlamebornGuardEntity(level, caster),
                new SummonedFlamebornWarriorEntity(level, caster)
        };
    }

    @Override
    public void onClientCastTick(Level level, int spellLevel, LivingEntity caster) {
        var recasts = ClientMagicData.getRecasts();
        if (recasts.hasRecastForSpell(this)) return;

        float yaw = caster.getYRot();
        float[] angles = { yaw, yaw + 180.0f };

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                double angle = Math.toRadians(angles[i]);
                Vec3 spawn = caster.position().add(Math.cos(angle) * 1.5, 0, Math.sin(angle) * 1.5);

                float d1 = (float)Math.sqrt(j);
                float ran = 0.4f;
                float r = 0.0f;
                float g = 0.75f + level.random.nextFloat() * ran;
                float b = 0.0f;

                level.addParticle(new MovingTrailParticle.TrailData(r, g, b, 0.2f, 0.1f), spawn.x, spawn.y, spawn.z, Math.sin(j), 0, d1 * 0.01f);
            }
        }
    }

    public static int getHealth(int spellLevel, boolean isWarrior) { return isWarrior ? 50 + spellLevel * 10 : 48 + spellLevel * 8; }
    public static double getDamage(float spellPower) { return spellPower; }
}
