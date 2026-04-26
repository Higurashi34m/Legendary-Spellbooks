package dev.higurashi.legendary_spellbooks.common.spells.lightning;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.CumuloChargeEntity;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CumuloChargeSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "cumulo_charge");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setAllowCrafting(false)
            .setCooldownSeconds(15)
            .setMaxLevel(3).build();

    public CumuloChargeSpell() {
        super(spellResource, spellConfig, true);
        this.castTime = 40;
        this.baseManaCost = 200;
        this.baseSpellPower = 10;
        this.manaCostPerLevel = 50;
        this.spellPowerPerLevel = 8;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                Component.translatable("ui.irons_spellbooks.duration", ComponentUtils.ticksToSecondsString(getDuration(spellLevel)))
        );
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        Utils.preCastTargetHelper(level, entity, playerMagicData, this, 48, 1.0f, false);
        return true;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        LivingEntity target = null;
        if (magicData.getAdditionalCastData() instanceof TargetEntityCastData castData) {
            if (level instanceof ServerLevel serverLevel) {
                target = castData.getTarget(serverLevel);
            }
        }

        Vec3 spawn = GeometryUtils.getRelativePos(caster, -1.0, 0.5, 0.0);
        float yaw = caster.getYRot();

        float damage = getSpellPower(spellLevel, caster);
        int duration = getDuration(spellLevel);

        CumuloChargeEntity charge = new CumuloChargeEntity(LSEntityRegistry.CUMULO_CHARGE_ENTITY.get(), level);
        charge.setSummoner(caster);
        charge.setYRot(yaw);
        charge.setPos(spawn);
        charge.setTarget(target);
        charge.setDamage(damage);
        charge.setDuration(duration);

        level.addFreshEntity(charge);

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getDuration(int spellLevel) { return Math.min(100 + spellLevel * 20, 300); }
}
