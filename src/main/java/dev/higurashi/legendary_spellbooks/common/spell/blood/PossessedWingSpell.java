package dev.higurashi.legendary_spellbooks.common.spell.blood;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class PossessedWingSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "possessed_wing");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.BLOOD_RESOURCE)
            .setMinRarity(SpellRarity.LEGENDARY)
            .setCooldownSeconds(240)
            .setMaxLevel(2).build();

    public PossessedWingSpell() {
        super(spellResource, spellConfig, true);
        this.castTime = 20;
        this.baseManaCost = 250;
        this.manaCostPerLevel = 25;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", ComponentUtils.ticksToSeconds(this.getDuration(spellLevel)))
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        int duration = getDuration(spellLevel);
        caster.addEffect(new MobEffectInstance(LSEffectRegistry.POSSESSED_WING_EFFECT.get(), duration, spellLevel - 1));

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getDuration(int spellLevel) { return 20 * spellLevel * 20;  }
}
