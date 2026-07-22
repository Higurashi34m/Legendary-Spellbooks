package dev.higurashi.legendary_spellbooks.common.spell.blood;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.SpellSoulTridentEntity;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class HematiteTrishulaSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "hematite_trishula");
    private static final DefaultConfig spellConfig = new  DefaultConfig()
            .setSchoolResource(SchoolRegistry.BLOOD_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setCooldownSeconds(30)
            .setMaxLevel(3).build();

    public HematiteTrishulaSpell() {
        super(spellResource, spellConfig, false);
        this.castTime = 60;
        this.baseManaCost = 200;
        this.baseSpellPower = 20;
        this.manaCostPerLevel = 50;
        this.spellPowerPerLevel = 10;

        this.castStartAnimation = SpellAnimations.ANIMATION_CHARGED_CAST;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster) / 2.0f))
        );
    }

    @Override
    public void onClientCastTick(Level level, int spellLevel, LivingEntity caster) {
        if (caster.tickCount % 10 == 0) {
            Circle.RingData ringData = new Circle.RingData(0.0f, (float) (Math.PI / 2.0f), 20, 1.0f, 0.0f, 0.0f, 1.0f, caster.getBbWidth() * 60.0f, false, Circle.EnumRingBehavior.SHRINK);
            caster.level().addParticle(ringData, caster.getX(), caster.getY(), caster.getZ(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        float damage = getSpellPower(spellLevel, caster);
        Vec3 spawnPos = GeometryUtils.getRelativePos(caster, 0.0, -1.25, 0.33);

        SpellSoulTridentEntity trident = new SpellSoulTridentEntity(level, caster, damage);

        trident.setOwner(caster);
        trident.setPos(spawnPos);
        trident.shootFromRotation(caster, caster.getXRot() - 20.0f, caster.getYRot(), 0.0f, 1.5f, 1.0f);

        level.addFreshEntity(trident);

        super.onCast(level, spellLevel, caster, source, magicData);
    }
}
