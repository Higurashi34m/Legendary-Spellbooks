package dev.higurashi.legendary_spellbooks.api.entities.helper;

import net.minecraft.nbt.CompoundTag;

public interface IWarmupEntity {
    void setWarmup(int ticks);

    int getWarmup();

    default void warmupTick() {
        if (getWarmup() <= 0) return;

        if (getWarmup() > 0) {
            onWarmupTick();
        }

        if (getWarmup() == 1) {
            onWarmupFinished();
        }

        setWarmup(getWarmup() - 1);
    }

    default void saveWarmupData(CompoundTag tag) {
        tag.putInt("Warmup", getWarmup());
    }

    default void loadWarmupData(CompoundTag tag) {
        if (tag.contains("Warmup")) {
            setWarmup(tag.getInt("Warmup"));
        }
    }

    void onWarmupTick();
    void onWarmupFinished();
}
