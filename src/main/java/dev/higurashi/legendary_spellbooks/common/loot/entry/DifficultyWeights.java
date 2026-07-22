package dev.higurashi.legendary_spellbooks.common.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.Difficulty;

public record DifficultyWeights(float easy, float normal, float hard, float hardcore) {
    public static final Codec<DifficultyWeights> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("easy").forGetter(DifficultyWeights::easy),
            Codec.FLOAT.fieldOf("normal").forGetter(DifficultyWeights::normal),
            Codec.FLOAT.fieldOf("hard").forGetter(DifficultyWeights::hard),
            Codec.FLOAT.fieldOf("hardcore").forGetter(DifficultyWeights::hardcore)
    ).apply(instance, DifficultyWeights::new));

    public float getMultiplier(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> easy;
            case NORMAL -> normal;
            case HARD -> hard;
            default -> hardcore;
        };
    }
}
