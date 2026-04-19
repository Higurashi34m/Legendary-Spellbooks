package dev.higurashi.legendary_spellbooks.api.spells;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public abstract class BaseSpell extends AbstractSpell {
    private final ResourceLocation spellResource;
    private final DefaultConfig spellConfig;
    private final CastType castType;

    protected SoundEvent castStartSound = null;
    protected SoundEvent castFinishSound = null;

    protected AnimationHolder castStartAnimation = null;
    protected AnimationHolder castFinishAnimation = null;

    protected boolean interrupted = false;
    protected boolean reduceCast = false;

    public BaseSpell(ResourceLocation spellResource, DefaultConfig spellConfig, CastType castType) {
        this.spellResource = spellResource;
        this.spellConfig = spellConfig;
        this.castType = castType;
    }

    public BaseSpell(ResourceLocation spellResource, DefaultConfig spellConfig) {
        this.spellResource = spellResource;
        this.spellConfig = spellConfig;
        this.castType = CastType.INSTANT;
    }

    public BaseSpell(ResourceLocation spellResource, DefaultConfig spellConfig, boolean reduceCast) {
        this.spellResource = spellResource;
        this.spellConfig = spellConfig;
        this.castType = CastType.LONG;
        this.reduceCast = reduceCast;
    }

    @Override public ResourceLocation getSpellResource() { return spellResource; }
    @Override public DefaultConfig getDefaultConfig() { return spellConfig; }

    @Override public CastType getCastType() { return castType; }
    @Override public boolean canBeInterrupted(Player player) { return interrupted; }

    @Override public Optional<SoundEvent> getCastStartSound() {
        if (this.castStartSound == null) return super.getCastStartSound();
        return Optional.of(castStartSound);
    }

    @Override public Optional<SoundEvent> getCastFinishSound() {
        if (this.castFinishSound == null) return super.getCastFinishSound();
        return Optional.of(castFinishSound);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        if (this.castStartAnimation == null) return super.getCastStartAnimation();
        return castStartAnimation;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        if (this.castFinishAnimation == null) return super.getCastFinishAnimation();
        return castFinishAnimation;
    }

    @Override
    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity caster) {
        if (!reduceCast) return super.getEffectiveCastTime(spellLevel, caster);
        return getCastTime(spellLevel);
    }

    @Override public abstract List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster);
}
