package dev.higurashi.legendary_spellbooks.api.spells;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Optional;

public abstract class BaseSpell extends AbstractSpell {
    private final ResourceLocation spellResource;
    private final DefaultConfig spellConfig;
    private final CastType castType;

    protected SoundEvent castStartSound = null;
    protected SoundEvent castFinishSound = null;

    protected BaseSpell(ResourceLocation spellResource, DefaultConfig spellConfig, CastType castType) {
        this.spellResource = spellResource;
        this.spellConfig = spellConfig;
        this.castType = castType;
    }

    protected BaseSpell(ResourceLocation spellResource, DefaultConfig spellConfig) {
        this.spellResource = spellResource;
        this.spellConfig = spellConfig;
        this.castType = CastType.INSTANT;
    }

    @Override public ResourceLocation getSpellResource() { return spellResource; }
    @Override public DefaultConfig getDefaultConfig() { return spellConfig; }

    @Override public CastType getCastType() { return castType; }

    @Override public Optional<SoundEvent> getCastStartSound() {
        if (this.castStartSound == null) return super.getCastStartSound();
        return Optional.of(castStartSound);
    }

    @Override public Optional<SoundEvent> getCastFinishSound() {
        if (this.castFinishSound == null) return super.getCastFinishSound();
        return Optional.of(castFinishSound);
    }

    @Override public abstract List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster);
}
