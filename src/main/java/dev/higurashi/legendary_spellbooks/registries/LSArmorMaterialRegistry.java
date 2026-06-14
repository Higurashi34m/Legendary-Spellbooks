package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import io.redspace.ironsspellbooks.registries.ArmorMaterialRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class LSArmorMaterialRegistry {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, LegendarySpellbooks.MOD_ID);
    public static void register(IEventBus eventBus) { ARMOR_MATERIALS.register(eventBus); }

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> OBLIVIONMANCER = register(
            "oblivionmancer",
            ArmorMaterialRegistry.schoolArmorMap(),
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> Ingredient.of(ItemRegistry.MAGIC_CLOTH.get()),
            1.0f,
            0.05f
    );

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> STORMMANCER = register(
            "stormmancer",
            ArmorMaterialRegistry.schoolArmorMap(),
            20,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(ModItems.CLOUD_ROD.get()),
            1.0f,
            0.1f
    );

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient, float toughness, float knockbackResi) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(LegendarySpellbooks.id(name)));
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(defense, enchantmentValue, equipSound, repairIngredient, list, toughness, knockbackResi));
    }
}
