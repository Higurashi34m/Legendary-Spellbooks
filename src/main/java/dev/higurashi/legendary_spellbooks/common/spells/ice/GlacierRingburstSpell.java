package dev.higurashi.legendary_spellbooks.common.spells.ice;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellIceSpikeEntity;
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

public class GlacierRingburstSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "glacier_ringburst");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMinRarity(SpellRarity.EPIC)
            .setAllowCrafting(false)
            .setCooldownSeconds(12)
            .setMaxLevel(4).build();

    public GlacierRingburstSpell() {
        super(spellResource, spellConfig, true);
        this.castTime = 20;
        this.baseManaCost = 100;
        this.baseSpellPower = 3;
        this.manaCostPerLevel = 10;
        this.spellPowerPerLevel = 2;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                Component.translatable("ui.irons_spellbooks.ring_count", getRingCount(spellLevel))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        int damage = (int) getSpellPower(spellLevel, caster);
        int ringCount = getRingCount(spellLevel);
        int spikeCount = getSpikesCount(ringCount);

        Vec3 center = caster.position();

        for (int i = 0; i < ringCount; i++) {
            double radius = 1.5 + i * 1.75f;
            int warmup = i * 2;

            List<Vec3> ringPoints = GeometryUtils.getCirclePoints(center, radius, spikeCount, caster.getYRot());

            for (Vec3 point : ringPoints) {
                Vec3 spawn = RaycastUtils.findGround(level, point, 6, 3);
                if (spawn == null) break;

                SpellIceSpikeEntity spike = new SpellIceSpikeEntity(level, spawn, (float) Math.toRadians(caster.getYRot()), warmup, caster, damage);
                level.addFreshEntity(spike);
            }
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getSpikesCount(int ringCount) { return 2 + ringCount * 2; }
    private int getRingCount(int spellLevel) { return Math.min(2 + spellLevel, 8); }
}
