package dev.higurashi.legendary_spellbooks.common.spells.annihilation;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.ICastData;
import io.redspace.ironsspellbooks.api.spells.ICastDataSerializable;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.capabilities.magic.ImpulseCastData;
import io.redspace.ironsspellbooks.player.SpinAttackType;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FlamebornDriftSpell extends BaseSpell {
    public static final SpinAttackType FLAMEBORN = new SpinAttackType(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "textures/entity/flameborn_drift.png"), false);

    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "flameborn_drift");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(LSSchoolRegistry.ANNIHILATION_RESOURCE)
            .setMinRarity(SpellRarity.RARE)
            .setCooldownSeconds(8)
            .setMaxLevel(7).build();

    public FlamebornDriftSpell() {
        super(spellResource, spellConfig);
        this.baseManaCost = 120;
        this.baseSpellPower = 4;
        this.manaCostPerLevel = 10;
        this.spellPowerPerLevel = 1;

        this.castFinishSound = ModSounds.CANNON_SHOOT_1;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(LegendarySpellbooks.MOD_ID, "health_damage", ComponentUtils.format2f(getSpellPower(spellLevel, caster)), ComponentUtils.format1f(getHPDamage(spellLevel)))
        );
    }

    @Override
    public void onClientCast(Level level, int spellLevel, LivingEntity caster, ICastData castData) {
        if (castData instanceof ImpulseCastData data) {
            caster.setDeltaMovement(new Vec3(data.x, data.y, data.z));
            caster.hasImpulse = true;
        }
        super.onClientCast(level, spellLevel, caster, castData);
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        float power = getSpellPower(spellLevel, caster);

        Vec3 look = caster.getLookAngle();

        float dashPower = 2.0f + (power * 0.1f);
        Vec3 dashVec = look.scale(dashPower);

        if (caster.onGround()) dashVec = dashVec.add(0, 0.2, 0);
        magicData.setAdditionalCastData(new ImpulseCastData((float) dashVec.x, (float) dashVec.y, (float) dashVec.z, true));

        caster.setDeltaMovement(dashVec);
        caster.hurtMarked = true;

        caster.addEffect(new MobEffectInstance(LSEffectRegistry.FLAMEBORN_DASH_EFFECT.get(), 12, spellLevel, false, false));
        caster.invulnerableTime = 15;
        magicData.getSyncedData().setSpinAttackType(FLAMEBORN);

        super.onCast(world, spellLevel, caster, source, magicData);
    }

    @Override public ICastDataSerializable getEmptyCastData() { return new ImpulseCastData(); }

    public static float getHPDamage(int spellLevel) { return spellLevel * 0.25f; }
}
