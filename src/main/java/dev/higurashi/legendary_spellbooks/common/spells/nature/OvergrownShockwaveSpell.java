package dev.higurashi.legendary_spellbooks.common.spells.nature;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellPoisonousShockwaveEntity;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OvergrownShockwaveSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "overglown_shockwave");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.NATURE_RESOURCE)
            .setMinRarity(SpellRarity.EPIC)
            .setCooldownSeconds(30)
            .setMaxLevel(4).build();

    public OvergrownShockwaveSpell() {
        super(spellResource, spellConfig, true);
        this.castTime = 18;
        this.baseManaCost = 150;
        this.baseSpellPower = 4;
        this.manaCostPerLevel = 15;
        this.spellPowerPerLevel = 4;

        this.castStartAnimation = SpellAnimations.OVERHEAD_MELEE_SWING_ANIMATION;
        this.castFinishAnimation = AnimationHolder.pass();

        this.interrupted = false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "radius", 1.25 + getShockwaveCount(spellLevel) * 1.5),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "recast_count", getRecastCount(spellLevel, caster))
        );
    }

    @Override
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        if (!magicData.getPlayerCooldowns().isOnCooldown(this) && !magicData.getPlayerRecasts().hasRecastForSpell(getSpellId())) {
            magicData.getPlayerRecasts().addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, caster), 80, source, null), magicData);
        }

        int damage = (int) getSpellPower(spellLevel, caster);
        int shockwaveCount = getShockwaveCount(spellLevel);

        for (int i = 0; i < shockwaveCount; i++) {
            double radius = 1.25 + i * 1.5;
            int poisonCount = shockwaveCount * 4;

            List<Vec3> spawnPoints = GeometryUtils.getCirclePoints(caster.position(), radius, poisonCount, caster.getYRot());
            for (Vec3 spawnPoint : spawnPoints) {
                Vec3 spawnPos = RaycastUtils.findGround(level, spawnPoint, 4, 6);
                if (spawnPos == null) return;

                SpellPoisonousShockwaveEntity shockwave = new SpellPoisonousShockwaveEntity(level, spawnPos.x, spawnPos.y, spawnPos.z, caster.getYRot(), i * 2, caster, 15, 1, damage);

                level.addFreshEntity(shockwave);
            }
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getShockwaveCount(int spellLevel) {
        return Math.min(spellLevel + 2, 10);
    }
}
