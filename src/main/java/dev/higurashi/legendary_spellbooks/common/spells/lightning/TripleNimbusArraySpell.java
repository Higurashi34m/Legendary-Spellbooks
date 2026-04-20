package dev.higurashi.legendary_spellbooks.common.spells.lightning;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseOverheadMeleeSwingSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.LightningBoltEntity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TripleNimbusArraySpell extends BaseOverheadMeleeSwingSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "triple_nimbus_array");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMinRarity(SpellRarity.RARE)
            .setCooldownSeconds(15)
            .setMaxLevel(5).build();

    public TripleNimbusArraySpell() {
        super(spellResource, spellConfig);
        this.baseManaCost = 140;
        this.baseSpellPower = 3;
        this.manaCostPerLevel = 20;
        this.spellPowerPerLevel = 2;

        this.castFinishSound = () -> SoundEvents.LIGHTNING_BOLT_THUNDER;
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

        Vec3 center = caster.position();
        for (int i = 0; i < nimbusCount; i++) {
            List<Vec3> spawnPoints = GeometryUtils.getFanPoints(center, caster.getYRot(), 70, (i + 1) * 1.5, 3);

            for (Vec3 spawnPoint : spawnPoints) {
                Vec3 spawnPos = RaycastUtils.findGround(level, spawnPoint, 6, 3);
                if (spawnPos == null) continue;

                LightningBoltEntity nimbus = new LightningBoltEntity(level, spawnPos.x, spawnPos.y, spawnPos.z, GeometryUtils.getYawBetween(center, spawnPos), i + 1, caster, lifeTick, damage);
                level.addFreshEntity(nimbus);
            }
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
