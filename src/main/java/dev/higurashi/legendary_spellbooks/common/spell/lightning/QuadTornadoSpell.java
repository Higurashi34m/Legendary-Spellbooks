package dev.higurashi.legendary_spellbooks.common.spell.lightning;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.Tornado;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class QuadTornadoSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "quad_tornado");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMinRarity(SpellRarity.EPIC)
            .setCooldownSeconds(12)
            .setAllowCrafting(false)
            .setMaxLevel(5).build();

    public QuadTornadoSpell() {
        super(spellResource, spellConfig, true);
        this.castTime = 40;
        this.baseManaCost = 120;
        this.manaCostPerLevel = 10;

        this.allowLooting = false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "projectile_count", getTornadoCount(spellLevel)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "duration", ComponentUtils.ticksToSecondsString(getDuration(spellLevel)))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        float duration = getDuration(spellLevel);
        int tornadoCount = getTornadoCount(spellLevel);

        Vec3 center = caster.position();
        float rotationOffset = (float) Math.toRadians(caster.getYRot());

        List<Vec3> spawnPoints = GeometryUtils.getCirclePoints(center, 1.5, tornadoCount, rotationOffset);

        for (Vec3 spawnPos : spawnPoints) {
            Vec3 direction = GeometryUtils.getDirection(center, spawnPos);
            float rot = GeometryUtils.getYawBetween(center, spawnPos);

            Tornado tornado = new Tornado(caster, direction.x * 0.1, 0.1, direction.z * 0.1, level, 0.0f, rot, duration);
            tornado.moveTo(spawnPos);

            level.addFreshEntity(tornado);
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getDuration(int spellLevel) { return Math.min(20 + spellLevel * 10, 100); }
    private int getTornadoCount(int spellLevel) { return 2 + spellLevel; }
}
