package dev.higurashi.legendary_spellbooks.common.spells.lightning;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellCloudEntity;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CloudRingSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "cloud_ring");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMinRarity(SpellRarity.EPIC)
            .setCooldownSeconds(15)
            .setMaxLevel(5).build();

    public CloudRingSpell() {
        super(spellResource, spellConfig, CastType.LONG);
        this.castTime = 40;
        this.baseManaCost = 100;
        this.baseSpellPower = 2;
        this.manaCostPerLevel = 10;
        this.spellPowerPerLevel = 3;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "aoe_damage", (int) getSpellPower(spellLevel, caster)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "ring_count", getRingCount(spellLevel))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        int damage = (int) getSpellPower(spellLevel, caster);
        int ringCount = getRingCount(spellLevel);

        for (int i = 0; i < ringCount; i++) {
            double radius = 1.5 + i * 1.75f;
            int cloudCount = (int) (radius * 3);

            List<Vec3> spawnPoints = GeometryUtils.getCirclePoints(caster.position().add(0, 8 + i / 2.0, 0), radius, cloudCount, caster.getYRot());
            for (Vec3 spawnPos : spawnPoints) {
                SpellCloudEntity cloud = new SpellCloudEntity(level, caster);
                cloud.setDamage(damage);
                cloud.setPos(spawnPos);
                cloud.setWarmup((i + 1) * 4);
                cloud.setInvisible(true);

                level.addFreshEntity(cloud);
            }
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getRingCount(int spellLevel) {
        return Math.min(spellLevel, 10);
    }
}
