package dev.higurashi.legendary_spellbooks.common.spells.nature;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.common.effects.handler.AmbushThornsEffectHandler;
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

public class AmbushThornsSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "ambush_thorns");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.NATURE_RESOURCE)
            .setMinRarity(SpellRarity.COMMON)
            .setCooldownSeconds(120)
            .setMaxLevel(10).build();

    public AmbushThornsSpell() {
        super(spellResource, spellConfig, true);
        this.castTime = 30;
        this.baseManaCost = 65;
        this.manaCostPerLevel = 5;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent("thorn_damage", ComponentUtils.format1f(AmbushThornsEffectHandler.getDamage(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "effect_length", ComponentUtils.ticksToSecondsString(getEffectDuration(spellLevel)))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        int duration = getEffectDuration(spellLevel);
        caster.addEffect(new MobEffectInstance(LSEffectRegistry.AMBUSH_THORNS_EFFECT.get(), duration, spellLevel));
        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getEffectDuration(int spellLevel) {
        return Math.min(ComponentUtils.secondsToTicks(10 + spellLevel * 5), ComponentUtils.secondsToTicks(120));
    }
}
