package dev.higurashi.legendary_spellbooks.common.spell.lightning;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class TornadoSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "tornado");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMinRarity(SpellRarity.COMMON)
            .setCooldownSeconds(6)
            .setMaxLevel(10).build();

    public TornadoSpell() {
        super(spellResource, spellConfig, true);
        this.castTime = 25;
        this.baseManaCost = 60;
        this.manaCostPerLevel = 5;
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        Utils.preCastTargetHelper(level, entity, playerMagicData, this, 12, 1.0f, false);
        return true;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "duration", ComponentUtils.ticksToSecondsString(getDuration(spellLevel)))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
//        int duration = getDuration(spellLevel);
//        Vec3 spawn = null;
//
//        if (magicData.getAdditionalCastData() instanceof TargetEntityCastData castData) {
//            if (level instanceof ServerLevel serverLevel) {
//                spawn = castData.getTargetPosition(serverLevel);
//            }
//        }
//
//        if (spawn == null) {
//            HitResult hitResult = RaycastBuilder.begin(level, caster).range(42).checkForBlocks(true).build();
//
//            if (hitResult.getType() == HitResult.Type.ENTITY) spawn = ((EntityHitResult) hitResult).getEntity().position();
//            else if (hitResult.getType() == HitResult.Type.BLOCK) spawn = RaycastUtils.findGround(level, hitResult.getLocation().subtract(caster.getForward().normalize()).add(0, 2, 0), 20, 10);
//        }
//
//        if (spawn != null) {
//            Tornado tornado = new Tornado(caster, 0.0f, 0.1f, 0.0f, level, 0.0f, caster.getYRot(), duration);
//            tornado.moveTo(spawn);
//            level.addFreshEntity(tornado);
//        }
//
//        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getDuration(int spellLevel) {
        return Math.min(10 + spellLevel * 5, 100);
    }
}
