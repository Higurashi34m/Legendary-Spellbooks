package dev.higurashi.legendary_spellbooks.common.items.armor;

import com.google.common.base.Suppliers;
import dev.higurashi.legendary_spellbooks.registries.LSAttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.armor.IronsExtendedArmorMaterial;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public enum LSArmorMaterials implements IronsExtendedArmorMaterial {
    OBLIVIONMANCER("oblivionmancer", 38, schoolArmorMap(), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.05f, () -> Ingredient.of(ItemRegistry.MAGIC_CLOTH.get()),
            Map.of(
                    AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max Mana", 125, AttributeModifier.Operation.ADDITION),
                    LSAttributeRegistry.ANNIHILATION_SPELL_POWER.get(), new AttributeModifier("Annihilation Power", 0.10, AttributeModifier.Operation.MULTIPLY_BASE)
            )
    );

    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = makeArmorMap(11, 16, 15, 13);

    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private final Map<Attribute, AttributeModifier> additionalAttributes;

    LSArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionMap, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResi, Supplier<Ingredient> repairItem, Map<Attribute, AttributeModifier> attributes) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionFunctionForType = protectionMap;
        this.enchantmentValue = enchantmentValue;
        this.sound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResi;
        this.repairIngredient = Suppliers.memoize(repairItem::get);
        this.additionalAttributes = attributes;
    }

    public static EnumMap<ArmorItem.Type, Integer> makeArmorMap(int helmet, int chestplate, int leggings, int boots) {
        return Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, boots);
            map.put(ArmorItem.Type.LEGGINGS, leggings);
            map.put(ArmorItem.Type.CHESTPLATE, chestplate);
            map.put(ArmorItem.Type.HELMET, helmet);
        });
    }

    public static EnumMap<ArmorItem.Type, Integer> schoolArmorMap() {
        return makeArmorMap(3, 8, 6, 3);
    }

    @Override
    public int getDurabilityForType(@NotNull ArmorItem.Type type) {
        return HEALTH_FUNCTION_FOR_TYPE.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(@NotNull ArmorItem.Type type) {
        return this.protectionFunctionForType.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override @NotNull
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override @NotNull
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override @NotNull
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public Map<Attribute, AttributeModifier> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}