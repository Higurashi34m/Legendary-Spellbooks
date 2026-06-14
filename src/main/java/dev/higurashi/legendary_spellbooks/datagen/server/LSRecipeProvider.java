package dev.higurashi.legendary_spellbooks.datagen.server;

import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.util.ModTags;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class LSRecipeProvider extends RecipeProvider {
    public LSRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, LSItemRegistry.OBLIVIONMANCER_HAT.get())
                .requires(ModTags.BASE_WIZARD_HELMET)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(LSItemRegistry.ANNIHILATION_RUNE.get())
                .unlockedBy("has_wizard_helmet", has(ModTags.BASE_WIZARD_HELMET))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, LSItemRegistry.OBLIVIONMANCER_ROBE.get())
                .requires(ModTags.BASE_WIZARD_CHESTPLATE)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(LSItemRegistry.ANNIHILATION_RUNE.get())
                .unlockedBy("has_wizard_chestplate", has(ModTags.BASE_WIZARD_CHESTPLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, LSItemRegistry.OBLIVIONMANCER_LEGGINGS.get())
                .requires(ModTags.BASE_WIZARD_LEGGINGS)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(LSItemRegistry.ANNIHILATION_RUNE.get())
                .unlockedBy("has_wizard_leggings", has(ModTags.BASE_WIZARD_LEGGINGS))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, LSItemRegistry.OBLIVIONMANCER_BOOTS.get())
                .requires(ModTags.BASE_WIZARD_BOOTS)
                .requires(ItemRegistry.ARCANE_ESSENCE.get())
                .requires(LSItemRegistry.ANNIHILATION_RUNE.get())
                .unlockedBy("has_wizard_boots", has(ModTags.BASE_WIZARD_BOOTS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LSItemRegistry.ANNIHILATION_RUNE.get())
                .pattern("BBB")
                .pattern("BRB")
                .pattern("BBB")
                .define('B', ModItems.BOTTLE_OF_ANNIHILATION.get())
                .define('R', ItemRegistry.BLANK_RUNE.get())
                .unlockedBy("has_rune", has(ItemRegistry.BLANK_RUNE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LSItemRegistry.ANNIHILATION_UPGRADE_ORB.get())
                .pattern("RRR")
                .pattern("ROR")
                .pattern("RRR")
                .define('R', LSItemRegistry.ANNIHILATION_RUNE.get())
                .define('O', ItemRegistry.UPGRADE_ORB.get())
                .unlockedBy("has_rune", has(ItemRegistry.UPGRADE_ORB.get()))
                .save(recipeOutput);

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(LSItemRegistry.TEMPEST_UPGRADE_SMITHING_TEMPLATE.get()),
                Ingredient.of(ItemRegistry.NETHERITE_MAGE_HELMET.get()),
                Ingredient.of(ModItems.AIR_RUNE.get()),
                RecipeCategory.MISC,
                LSItemRegistry.STORMMANCER_HOOD.get())
                .unlocks("has_rune", has(ModItems.AIR_RUNE.get()))
                .save(recipeOutput, "stormmancer_hood_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(LSItemRegistry.TEMPEST_UPGRADE_SMITHING_TEMPLATE.get()),
                        Ingredient.of(ItemRegistry.NETHERITE_MAGE_CHESTPLATE.get()),
                        Ingredient.of(ModItems.AIR_RUNE.get()),
                        RecipeCategory.MISC,
                        LSItemRegistry.STORMMANCER_ROBE.get())
                .unlocks("has_rune", has(ModItems.AIR_RUNE.get()))
                .save(recipeOutput, "stormmancer_robe_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(LSItemRegistry.TEMPEST_UPGRADE_SMITHING_TEMPLATE.get()),
                        Ingredient.of(ItemRegistry.NETHERITE_MAGE_LEGGINGS.get()),
                        Ingredient.of(ModItems.AIR_RUNE.get()),
                        RecipeCategory.MISC,
                        LSItemRegistry.STORMMANCER_LEGGINGS.get())
                .unlocks("has_rune", has(ModItems.AIR_RUNE.get()))
                .save(recipeOutput, "stormmancer_leggings_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(LSItemRegistry.TEMPEST_UPGRADE_SMITHING_TEMPLATE.get()),
                        Ingredient.of(ItemRegistry.NETHERITE_MAGE_BOOTS.get()),
                        Ingredient.of(ModItems.AIR_RUNE.get()),
                        RecipeCategory.MISC,
                        LSItemRegistry.STORMMANCER_BOOTS.get())
                .unlocks("has_rune", has(ModItems.AIR_RUNE.get()))
                .save(recipeOutput, "stormmancer_boots_smithing");

        copySmithingTemplate(recipeOutput, LSItemRegistry.TEMPEST_UPGRADE_SMITHING_TEMPLATE.get(), ModItems.CLOUD_ROD.get());
    }
}
