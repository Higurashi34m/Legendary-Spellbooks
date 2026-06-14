package dev.higurashi.legendary_spellbooks.api.spells;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public abstract class BaseOverheadMeleeSwingSpell extends BaseSpell {
    public BaseOverheadMeleeSwingSpell(ResourceLocation spellResource, DefaultConfig spellConfig) {
        super(spellResource, spellConfig, false);
        this.castTime = 18;

        this.castStartAnimation = SpellAnimations.OVERHEAD_MELEE_SWING_ANIMATION;
        this.castFinishAnimation = AnimationHolder.pass();

        this.interrupted = false;
    }

    @Override public abstract List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster);
}
