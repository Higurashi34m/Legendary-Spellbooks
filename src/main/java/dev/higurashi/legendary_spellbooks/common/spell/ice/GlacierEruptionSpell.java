package dev.higurashi.legendary_spellbooks.common.spell.ice;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.SpellIceSpikeEntity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GlacierEruptionSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "glacier_eruption");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMinRarity(SpellRarity.RARE)
            .setCooldownSeconds(6)
            .setMaxLevel(7).build();

    public GlacierEruptionSpell() {
        super(spellResource, spellConfig);
        this.baseManaCost = 30;
        this.baseSpellPower = 3;
        this.manaCostPerLevel = 5;
        this.spellPowerPerLevel = 1;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                Component.translatable("ui.irons_spellbooks.spike_count", getSpikesCount(spellLevel))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        int damage = (int) getSpellPower(spellLevel, caster);
        int spikeCount = getSpikesCount(spellLevel);

        Vec3 center = caster.position();
        List<Vec3> spawnPoints = GeometryUtils.getLinePoints(center, caster.getYRot(), spikeCount, 1.5, 1.5);

        for (int i = 0; i < spawnPoints.size(); i++) {
            Vec3 spawnPos = RaycastUtils.findGround(level, spawnPoints.get(i), 6, 3);
            if (spawnPos == null) continue;

            SpellIceSpikeEntity spike = new SpellIceSpikeEntity(level, spawnPos, (float) Math.toRadians(caster.getYRot()), i + 1, caster, damage);
            level.addFreshEntity(spike);
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getSpikesCount(int spellLevel) { return Math.min(3 + spellLevel, 15); }
}
