package dev.higurashi.legendary_spellbooks.common.spells.fire;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseOverheadMeleeSwingSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellFireColumnEntity;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FlameSectorSpell extends BaseOverheadMeleeSwingSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "flame_sector");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMinRarity(SpellRarity.EPIC)
            .setCooldownSeconds(15)
            .setAllowCrafting(false)
            .setMaxLevel(4).build();

    public FlameSectorSpell() {
        super(spellResource, spellConfig);
        this.baseManaCost = 80;
        this.baseSpellPower = 3;
        this.manaCostPerLevel = 20;
        this.spellPowerPerLevel = 3;

        this.allowLooting = false;

        this.castFinishSound = () -> SoundEvents.DRAGON_FIREBALL_EXPLODE;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", getSpellPower(spellLevel, caster)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "ring_count", getRingCount(spellLevel)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "duration", ComponentUtils.ticksToSecondsString(getLifeTick(spellLevel)))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        int damage = (int) getSpellPower(spellLevel, caster);
        int lifeTick = getLifeTick(spellLevel);
        int ringCount = getRingCount(spellLevel);
        int flameCount = (int) (ringCount * 1.5);

        for (int i = 0; i < ringCount; i++) {
            double radius = 1.25 + i * 1.75;

            List<Vec3> spawnPoints = GeometryUtils.getCirclePoints(caster.position(), radius, flameCount, caster.getYRot());
            for (Vec3 spawnPoint : spawnPoints) {
                Vec3 spawnPos = RaycastUtils.findGround(level, spawnPoint, 5, 3);
                if (spawnPos == null) continue;

                float yaw = GeometryUtils.getYawBetween(caster.position(), spawnPos);
                SpellFireColumnEntity flame = new SpellFireColumnEntity(level, spawnPos.x, spawnPos.y, spawnPos.z, yaw, damage, i * 2, lifeTick, caster);

                level.addFreshEntity(flame);
            }
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getRingCount(int spellLevel) {
        return Math.min(spellLevel * 2 + 2, 10);
    }

    private int getLifeTick(int spellLevel) {
        return Math.min(15 + spellLevel * 5, 60);
    }
}
