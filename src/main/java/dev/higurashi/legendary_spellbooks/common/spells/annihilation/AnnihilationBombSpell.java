package dev.higurashi.legendary_spellbooks.common.spells.annihilation;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellAnnihilationBombEntity;
import dev.higurashi.legendary_spellbooks.registries.LSSchoolRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AnnihilationBombSpell extends BaseSpell {
    private static final ResourceLocation SPELL_ID = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation_bomb");
    private static final DefaultConfig CONFIG = new DefaultConfig()
            .setSchoolResource(LSSchoolRegistry.ANNIHILATION_RESOURCE)
            .setMinRarity(SpellRarity.RARE)
            .setCooldownSeconds(20)
            .setAllowCrafting(false)
            .setMaxLevel(6).build();

    public AnnihilationBombSpell() {
        super(SPELL_ID, CONFIG, false);
        this.castTime = 27;
        this.baseManaCost = 125;
        this.baseSpellPower = 6;
        this.manaCostPerLevel = 15;
        this.spellPowerPerLevel = 4;

        this.castStartSound = ModSounds.DIMENSIONAL_SHOOT_CHARGE;
        this.castFinishSound = ModSounds.THE_WARPED_ONE_SHOOT;

        this.stopSound = true;
        this.castStartAnimation = SpellAnimations.ANIMATION_CHARGED_CAST;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(LegendarySpellbooks.MOD_ID, "hp_damage", ComponentUtils.format1f(this.getHpDamage(spellLevel) * 100)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID,      "damage",    ComponentUtils.format1f(this.getDamage(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID,      "projectile_count", this.getBulletAmount(spellLevel))
        );
    }

    @Override
    public void onClientCastTick(Level level, int spellLevel, LivingEntity caster) {
        if (caster.tickCount % 10 == 0) {
            Circle.RingData ringData = new Circle.RingData(0.0f, (float) Math.toRadians(90.0), 20, 0.0f, 1.0f, 0.0f, 1.0f, 40.0f * caster.getBbWidth(), false, Circle.EnumRingBehavior.SHRINK);
            level.addParticle(ringData, caster.getX(), caster.getY(), caster.getZ(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        float damage = this.getDamage(spellLevel, caster);
        float hpDamage = this.getHpDamage(spellLevel);
        int bulletAmount = this.getBulletAmount(spellLevel);

        Vec3 spawnPos = caster.getEyePosition().add(caster.getLookAngle().scale(1.0));
        MagicManager.spawnParticles(level, ModParticles.ANNIHILATION_EXPLOSION.get(), spawnPos.x, spawnPos.y, spawnPos.z, 1, 0.0, 0.0, 0.0, 0.0, false);
        SpellAnnihilationBombEntity bomb = new SpellAnnihilationBombEntity(level, caster, spawnPos, damage, hpDamage, bulletAmount);
        bomb.shoot(caster.getLookAngle().add(0.0, 0.25, 0.0));
        level.addFreshEntity(bomb);

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private float getDamage(int spellLevel, LivingEntity caster) { return this.getSpellPower(spellLevel, caster); }
    private float getHpDamage(int spellLevel) { return 0.005f * spellLevel; }
    private int getBulletAmount(int spellLevel) { return 25 + 5 * spellLevel; }

    @Override
    public boolean shouldAIStopCasting(int spellLevel, Mob caster, LivingEntity target) {
        return caster.distanceToSqr(target) > 16 * 16;
    }
}
