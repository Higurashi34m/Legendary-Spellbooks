package dev.higurashi.legendary_spellbooks.common.spells.lightning;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.ElectricityEntity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ThunderFanburstSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "thunder_fanburst");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMinRarity(SpellRarity.COMMON)
            .setCooldownSeconds(8)
            .setMaxLevel(10).build();

    public ThunderFanburstSpell() {
        super(spellResource, spellConfig, false);
        this.castTime = 18;
        this.baseManaCost = 55;
        this.baseSpellPower = 2;
        this.manaCostPerLevel = 5;
        this.spellPowerPerLevel = 2;

        this.castStartAnimation = SpellAnimations.OVERHEAD_MELEE_SWING_ANIMATION;
        this.castFinishAnimation = AnimationHolder.pass();

        this.castStartSound = SoundRegistry.LIGHTNING_LANCE_CAST;
        this.castFinishSound = () -> SoundEvents.WITHER_SHOOT;

        this.interrupted = false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "projectile_count", getBoltsCount(spellLevel)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "duration", ComponentUtils.ticksToSecondsString(getLifeTick(spellLevel)))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        float damage = getSpellPower(spellLevel, caster);
        int boltsCount = getBoltsCount(spellLevel);
        int lifeTick = getLifeTick(spellLevel);

        Vec3 center = caster.position();
        List<Vec3> spawnPoints = GeometryUtils.getFanPoints(center, caster.getYRot(), 70.0f, 1.5, boltsCount);
        for (Vec3 spawnPoint : spawnPoints) {
            Vec3 spawnPos = RaycastUtils.findGround(level, spawnPoint, 4, 4);
            if (spawnPos == null) continue;

            Vec3 direction = GeometryUtils.getDirection(center, spawnPos);
            float yaw = GeometryUtils.getYawBetween(center, spawnPos);

            ElectricityEntity bolt = new ElectricityEntity(caster, direction.x, 0.0, direction.z, level, damage, yaw, lifeTick);
            bolt.setPos(spawnPos);

            level.addFreshEntity(bolt);
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getBoltsCount(int spellLevel) {
        return Math.min(2 + spellLevel, 20);
    }

    private int getLifeTick(int spellLevel) {
        return Math.min(25 + spellLevel * 5, 100);
    }
}
