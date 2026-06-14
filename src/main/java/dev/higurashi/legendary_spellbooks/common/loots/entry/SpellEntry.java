package dev.higurashi.legendary_spellbooks.common.loots.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record SpellEntry(ResourceLocation spellId, int minLevel, int maxLevel, int baseWeight) {
    public static final Codec<SpellEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("spell").forGetter(SpellEntry::spellId),
            Codec.INT.fieldOf("min_level").forGetter(SpellEntry::minLevel),
            Codec.INT.fieldOf("max_level").forGetter(SpellEntry::maxLevel),
            Codec.INT.fieldOf("weight").forGetter(SpellEntry::baseWeight)
    ).apply(instance, SpellEntry::new));
}
