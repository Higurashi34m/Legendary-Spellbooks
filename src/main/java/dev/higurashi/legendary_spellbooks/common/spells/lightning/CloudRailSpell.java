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
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CloudRailSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "cloud_rail");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMinRarity(SpellRarity.COMMON)
            .setCooldownSeconds(6)
            .setMaxLevel(10).build();

    public CloudRailSpell() {
        super(spellResource, spellConfig);
        this.baseManaCost = 20;
        this.baseSpellPower = 2;
        this.manaCostPerLevel = 5;
        this.spellPowerPerLevel = 1;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "aoe_damage", (int) getSpellPower(spellLevel, caster)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "projectile_count", getCloudCount(spellLevel))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        int damage = (int) getSpellPower(spellLevel, caster);
        int cloudCount = getCloudCount(spellLevel);

        List<Vec3> spawnPoints = GeometryUtils.getLinePoints(caster.position().add(0, 6, 0), caster.getYRot(), cloudCount, 1.5, 1.75);
        for (int i = 0; i < spawnPoints.size(); i++) {
            SpellCloudEntity cloud = new SpellCloudEntity(level, caster);
            cloud.setDamage(damage);
            cloud.setPos(spawnPoints.get(i));
            cloud.setWarmup(i + 1);
            cloud.setInvisible(true);

            level.addFreshEntity(cloud);
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getCloudCount(int spellLevel) {
        return Math.min(2 + spellLevel, 15);
    }
}
