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
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.LightningBoltEntity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class NimbusArraySpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "nimbus_array");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMinRarity(SpellRarity.RARE)
            .setCooldownSeconds(10)
            .setMaxLevel(7).build();

    public NimbusArraySpell() {
        super(spellResource, spellConfig, true);
        this.castTime = 18;
        this.baseManaCost = 80;
        this.baseSpellPower = 2;
        this.manaCostPerLevel = 10;
        this.spellPowerPerLevel = 1;

        this.castStartAnimation = SpellAnimations.OVERHEAD_MELEE_SWING_ANIMATION;
        this.castFinishAnimation = AnimationHolder.pass();

        this.castFinishSound = () -> SoundEvents.LIGHTNING_BOLT_THUNDER;

        this.interrupted = false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", (int) getSpellPower(spellLevel, caster)),
                ComponentUtils.getUIComponent("nimbus_count", getNimbusCount(spellLevel)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "duration", ComponentUtils.ticksToSecondsString(getLifeTick(spellLevel)))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        int damage = (int) getSpellPower(spellLevel, caster);
        int nimbusCount = getNimbusCount(spellLevel);
        int lifeTick = getLifeTick(spellLevel);

        List<Vec3> spawnPoints = GeometryUtils.getLinePoints(caster.position(), caster.getYRot(), nimbusCount, 1.5, 1.5);
        for (int i = 0; i < spawnPoints.size(); i++) {
            Vec3 spawnPos = RaycastUtils.findGround(level, spawnPoints.get(i), 6, 3);
            if (spawnPos == null) continue;

            LightningBoltEntity nimbus = new LightningBoltEntity(level, spawnPos.x, spawnPos.y, spawnPos.z, caster.getYRot(), i + 1, caster, lifeTick, damage);
            level.addFreshEntity(nimbus);
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getNimbusCount(int spellLevel) {
        return Math.min(spellLevel * 2, 15);
    }

    private int getLifeTick(int spellLevel) {
        return Math.min(20 + spellLevel * 5, 40);
    }
}
