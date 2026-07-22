package dev.higurashi.legendary_spellbooks.common.loot.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.higurashi.legendary_spellbooks.registry.LSLootModifierRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class ItemLootModifier extends LootModifier {
    public static final Codec<ItemLootModifier> CODEC = RecordCodecBuilder.create(instance -> codecStart(instance)
            .and(ItemStack.CODEC.fieldOf("item").forGetter(m -> m.item))
            .and(Codec.FLOAT.fieldOf("chance").forGetter(m -> m.chance))
            .apply(instance, ItemLootModifier::new));

    private final ItemStack item;
    private final float chance;

    public ItemLootModifier(LootItemCondition[] conditions, ItemStack item, float chance) {
        super(conditions);
        this.item = item;
        this.chance = chance;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < chance) {
            generatedLoot.add(item.copy());
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return LSLootModifierRegistry.ITEM_DROP.get();
    }
}
