package dev.higurashi.legendary_spellbooks.common.spell.ice;

import dev.higurashi.daybreaklib.api.util.GeometryUtils;
import dev.higurashi.daybreaklib.api.util.TextUtils;
import dev.higurashi.daybreaklib.api.util.position.PositionSearch;
import dev.higurashi.daybreaklib_iss.api.common.spell.BaseLockOnSpell;
import dev.higurashi.daybreaklib_iss.api.common.spell.SpellConfigBuilder;
import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.SpellIceSpikeEntity;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GlacierEruptionSpell extends BaseLockOnSpell {
    private static final DefaultConfig CONFIG = new SpellConfigBuilder(SchoolRegistry.ICE_RESOURCE)
            .setCooldownSec(2)
            .build();

    public GlacierEruptionSpell() {
        super(CONFIG, false);
        this.baseManaCost = 30;
        this.baseSpellPower = 2;
        this.manaCostPerLevel = 5;
        this.spellPowerPerLevel = 1;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        String damage = TextUtils.truncate(this.getDamage(spellLevel, caster), 1);

        return List.of(
                TextUtils.uiKey(IronsSpellbooks.id("damage")).translate(damage),
                TextUtils.uiKey(IronsSpellbooks.id("spike_count")).translate(this.getSpikeCount(spellLevel))
        );
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity caster, MagicData magicData) {
        this.lockOnRange = (int) this.getRange(spellLevel) + 1;
        return super.checkPreCastConditions(level, spellLevel, caster, magicData);
    }

    @Override
    public void cast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData, @Nullable LivingEntity target) {
        float damage = this.getDamage(spellLevel, caster);
        double range = this.getRange(spellLevel);
        int spikeCount = this.getSpikeCount(spellLevel);

        // Line
        List<Vec3> lineSpawnPoints = PositionSearch.builder(level)
                .positions(dev.higurashi.daybreaklib.api.util.GeometryUtils.pointsOnHorizontalLine(caster.position().add(caster.getForward().scale(1.0)), caster.getForward(), range, spikeCount))
                .requireSpace(ModEntities.ICE_SPIKE_ENTITY.get().getDimensions())
                .ground(2, 5).build().findAll();

        for (int i = 0; i < lineSpawnPoints.size(); i++) {
            Vec3 spawnPos = lineSpawnPoints.get(i);
            float yaw = dev.higurashi.daybreaklib.api.util.GeometryUtils.getYawFromVec(dev.higurashi.daybreaklib.api.util.GeometryUtils.getDirection(caster.position(), spawnPos));

            SpellIceSpikeEntity spike = new SpellIceSpikeEntity(level, spawnPos, yaw, caster, damage, i);
            level.addFreshEntity(spike);
        }

        // LockOn Ring
        if (target == null) return;
        this.spawnLockOnRing(level, caster, target, lineSpawnPoints.size(), damage / 2.0f);
    }

    private void spawnLockOnRing(Level level, LivingEntity caster, @NotNull LivingEntity target, int warmup, float damage) {
        List<Vec3> ringPos = new ArrayList<>();
        ringPos.addAll(GeometryUtils.pointsOnCircle(target.position(), 1.5, 5));
        ringPos.addAll(GeometryUtils.pointsOnCircle(target.position(), 3.0, 8));

        List<Vec3> ringSpawnPoints = PositionSearch.builder(level)
                .positions(ringPos)
                .requireSpace(ModEntities.ICE_SPIKE_ENTITY.get().getDimensions())
                .ground(2, 5).build().findAll();

        for (Vec3 spawnPos : ringSpawnPoints) {
            float yaw = GeometryUtils.getYawFromVec(GeometryUtils.getDirection(target.position(), spawnPos));

            SpellIceSpikeEntity spike = new SpellIceSpikeEntity(level, spawnPos, yaw, caster, damage, warmup);
            level.addFreshEntity(spike);
        }
    }

    private float getDamage(int spellLevel, LivingEntity caster) { return this.getSpellPower(spellLevel, caster); }
    private double getRange(int spellLevel) { return 6.0 + spellLevel; }
    private int getSpikeCount(int spellLevel) { return 4 + spellLevel; }
}
