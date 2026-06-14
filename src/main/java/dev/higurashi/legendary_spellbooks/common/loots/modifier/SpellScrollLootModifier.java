package dev.higurashi.legendary_spellbooks.common.loots.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.higurashi.legendary_spellbooks.common.loots.entry.DifficultyWeights;
import dev.higurashi.legendary_spellbooks.common.loots.entry.SpellEntry;
import dev.higurashi.legendary_spellbooks.registries.LSLootModifierRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpellScrollLootModifier extends LootModifier {
    public static final MapCodec<SpellScrollLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> codecStart(instance)
            .and(SpellEntry.CODEC.listOf().fieldOf("spells").forGetter(m -> m.spellList))
            .and(Codec.FLOAT.fieldOf("drop_chance").forGetter(m -> m.dropChance))
            .and(Codec.INT.fieldOf("count_min").forGetter(m -> m.minScrolls))
            .and(Codec.INT.fieldOf("count_max").forGetter(m -> m.maxScrolls))
            .and(DifficultyWeights.CODEC.fieldOf("difficulty_weight_multiplier").forGetter(m -> m.difficultyWeights))
            .apply(instance, SpellScrollLootModifier::new));

    private final List<SpellEntry> spellList;
    private final int minScrolls;
    private final int maxScrolls;
    private final DifficultyWeights difficultyWeights;
    private final float dropChance;

    public SpellScrollLootModifier(LootItemCondition[] conditions, List<SpellEntry> spellList, float dropChance, int minScrolls, int maxScrolls, DifficultyWeights difficultyWeights) {
        super(conditions);
        this.dropChance = dropChance;
        this.spellList = spellList;
        this.minScrolls = minScrolls;
        this.maxScrolls = maxScrolls;
        this.difficultyWeights = difficultyWeights;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (spellList.isEmpty()) return generatedLoot;

        List<SpellEntry> pickedSpell = new ArrayList<>();

        RandomSource random = context.getRandom();
        float luck = context.getLuck();

        float effectiveChance = applyLuck(dropChance, luck);
        if (random.nextFloat() >= effectiveChance) return generatedLoot;

        int range = maxScrolls - minScrolls + 1;
        float roll = applyLuck(random.nextFloat(), luck);
        int dropCount = Mth.clamp(minScrolls + (int) (roll * range), minScrolls, maxScrolls);

        for (int i = 0; i < dropCount; i++) {
            SpellEntry selected = pickSpell(random, context, pickedSpell);
            pickedSpell.add(selected);
            int level = rollLevel(selected, random, luck);

            ItemStack scroll = new ItemStack(ItemRegistry.SCROLL.get());
            ISpellContainer.createScrollContainer(SpellRegistry.getSpell(selected.spellId()), level, scroll);
            generatedLoot.add(scroll);
        }

        return generatedLoot;
    }

    private SpellEntry pickSpell(RandomSource random, LootContext context, List<SpellEntry> pickedSpell) {
        float difficultyMultiplier = difficultyWeights.getMultiplier(context.getLevel().getDifficulty());

        double totalWeight = 0;
        for (SpellEntry spell : spellList) {
            double w = Math.max(0.1, spell.baseWeight() * difficultyMultiplier);
            if (pickedSpell.contains(spell)) w /= 3.0;

            totalWeight += w;
        }

        double r = random.nextDouble() * totalWeight;
        for (SpellEntry spell : spellList) {
            double w = Math.max(0.1, spell.baseWeight() * difficultyMultiplier);
            if (pickedSpell.contains(spell)) w /= 3.0;

            r -= w;
            if (r <= 0) return spell;
        }
        return spellList.get(spellList.size() - 1);
    }

    private int rollLevel(SpellEntry entry, RandomSource random, float luck) {
        int min = entry.minLevel();
        int max = entry.maxLevel();
        if (min >= max) return min;

        int range = max - min + 1;
        double[] weights = new double[range];
        float total = 0;

        for (int i = 0; i < range; i++) {
            float decay = Math.max(0.1f, 0.7f + Math.min(0.2f, luck * 0.02f));
            float w = (float) Math.pow(decay, i);
            weights[i] = w;
            total += w;
        }

        double r = random.nextDouble() * total;
        for (int i = 0; i < range; i++) {
            r -= weights[i];
            if (r <= 0) return min + i;
        }
        return min;
    }

    private float applyLuck(float base, float luck) {
        return Mth.clamp(base * Math.max(0.1f, 1.0f + 0.05f * luck), 0.0f, 1.0f);
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return LSLootModifierRegistry.SPELL_SCROLL.get();
    }
}
