package dev.higurashi.legendary_spellbooks.common.spells.ender;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.common.effects.handler.AnnihilationResonanceHandler;
import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class AnnihilationResonanceSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation_resonance");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.ENDER_RESOURCE)
            .setMinRarity(SpellRarity.RARE)
            .setCooldownSeconds(180)
            .setMaxLevel(6).build();

    public AnnihilationResonanceSpell() {
        super(spellResource, spellConfig, true);
        this.castTime = 35;
        this.baseManaCost = 120;
        this.manaCostPerLevel = 20;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(AnnihilationResonanceHandler.getDamage(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "radius", 4),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "effect_length", ComponentUtils.ticksToSecondsString(getDuration(spellLevel)))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        int duration = getDuration(spellLevel);
        caster.addEffect(new MobEffectInstance(LSEffectRegistry.ANNIHILATION_RESONANCE_EFFECT.get(), duration, spellLevel));
        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getDuration(int spellLevel) {
        return ComponentUtils.secondsToTicks(Math.min(20 + spellLevel * 5, 100));
    }
}
