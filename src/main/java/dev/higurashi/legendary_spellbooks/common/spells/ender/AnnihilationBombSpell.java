package dev.higurashi.legendary_spellbooks.common.spells.ender;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.common.mixin.helper.ISpellSourceFlag;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationBombEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AnnihilationBombSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation_bomb");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.ENDER_RESOURCE)
            .setMinRarity(SpellRarity.RARE)
            .setCooldownSeconds(25)
            .setMaxLevel(6).build();

    public AnnihilationBombSpell() {
        super(spellResource, spellConfig, false);
        this.castTime = 45;
        this.baseManaCost = 150;
        this.baseSpellPower = 6;
        this.manaCostPerLevel = 15;
        this.spellPowerPerLevel = 8;

        this.stopSound = true;

        this.castStartSound = ModSounds.ANNIHILATION_LASER_CHARGE;
        this.castFinishSound = ModSounds.THE_WARPED_ONE_SHOOT;

        this.castStartAnimation = SpellAnimations.ANIMATION_CHARGED_CAST;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "projectile_count", getSmallBombCount(spellLevel))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        Vec3 spawnPos = GeometryUtils.getRelativePos(caster, 1.0, -0.15, 0.0);

        float damage = getSpellPower(spellLevel, caster);
        float smallDamage = damage * 0.4f;
        int smallBombCount = getSmallBombCount(spellLevel);

        AnnihilationBombEntity bomb = new AnnihilationBombEntity(ModEntities.ANNIHILATION_BOMB_ENTITY.get(), level, caster, damage, smallBombCount, false);
        ((ISpellSourceFlag) bomb).legendarySpellbooks$markSpell();
        ((ISpellSourceFlag) bomb).legendarySpellbooks$setDamage(smallDamage);
        bomb.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0.0f, 1.5f, 1.0f);
        bomb.setPos(spawnPos);

        level.addFreshEntity(bomb);

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getSmallBombCount(int spellLevel) {
        return Math.min(20 + spellLevel * 5, 100);
    }
}
