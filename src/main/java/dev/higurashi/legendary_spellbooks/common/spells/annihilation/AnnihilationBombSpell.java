package dev.higurashi.legendary_spellbooks.common.spells.annihilation;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellAnnihilationBombEntity;
import dev.higurashi.legendary_spellbooks.registries.LSSchoolRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationBombEntity;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AnnihilationBombSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation_bomb");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(LSSchoolRegistry.ANNIHILATION_RESOURCE)
            .setMinRarity(SpellRarity.RARE)
            .setCooldownSeconds(25)
            .setMaxLevel(6).build();

    public AnnihilationBombSpell() {
        super(spellResource, spellConfig, false);
        this.castTime = 25;
        this.baseManaCost = 150;
        this.baseSpellPower = 6;
        this.manaCostPerLevel = 25;
        this.spellPowerPerLevel = 2;

        this.stopSound = true;

        this.castStartSound = ModSounds.DIMENSIONAL_SHOOT_CHARGE;
        this.castFinishSound = ModSounds.THE_WARPED_ONE_SHOOT;

        this.castStartAnimation = SpellAnimations.ANIMATION_CHARGED_CAST;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(LegendarySpellbooks.MOD_ID, "health_damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster)), ComponentUtils.format1f(getHPDamage(spellLevel))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "projectile_count", getSmallBombCount(spellLevel))
        );
    }

    @Override
    public void onClientCastTick(Level level, int spellLevel, LivingEntity caster) {
        if (caster.tickCount % 10 == 0) {
            Circle.RingData ringData = new Circle.RingData(0.0f, (float) (Math.PI / 2.0f), 20, 0.0f, 1.0f, 0.0f, 1.0f, caster.getBbWidth() * 60.0f, false, Circle.EnumRingBehavior.SHRINK);
            caster.level().addParticle(ringData, caster.getX(), caster.getY(), caster.getZ(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        Vec3 spawnPos = GeometryUtils.getRelativePos(caster, 1.0, -0.15, 0.0);

        float damage = getSpellPower(spellLevel, caster);
        float hpDamage = getHPDamage(spellLevel) * 0.01f;
        int smallBombCount = getSmallBombCount(spellLevel);

        AnnihilationBombEntity bomb = new SpellAnnihilationBombEntity(level, caster, damage, hpDamage, smallBombCount);
        bomb.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0.0f, 1.5f, 1.0f);
        bomb.setPos(spawnPos);

        if (level instanceof ServerLevel serverLevel) serverLevel.sendParticles(ModParticles.ANNIHILATION_EXPLOSION.get(), spawnPos.x, spawnPos.y, spawnPos.z, 1, 0.0, 0.0, 0.5, 0.0);

        level.addFreshEntity(bomb);

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getSmallBombCount(int spellLevel) {
        return Math.min(20 + spellLevel * 5, 100);
    }
    private float getHPDamage(int spellLevel) { return 0.25f * spellLevel; }
}
