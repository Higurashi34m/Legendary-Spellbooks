package dev.higurashi.legendary_spellbooks.common.items.armor;

import dev.higurashi.legendary_spellbooks.client.model.item.armor.OblivionmancerArmorModel;
import dev.higurashi.legendary_spellbooks.registries.LSArmorMaterialRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSAttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.armor.ImbuableChestplateArmorItem;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class OblivionmancerArmorItem extends ImbuableChestplateArmorItem {
    public OblivionmancerArmorItem(Type slot, Properties properties) {
        super(LSArmorMaterialRegistry.OBLIVIONMANCER, slot, properties, attribute());
    }

    @Override
    public GeoArmorRenderer<?> supplyRenderer() {
        return new GeoArmorRenderer<>(new OblivionmancerArmorModel());
    }

    private static AttributeContainer[] attribute() {
        return new AttributeContainer[]{
                new AttributeContainer(AttributeRegistry.MAX_MANA, 125, AttributeModifier.Operation.ADD_VALUE),
                new AttributeContainer(LSAttributeRegistry.ANNIHILATION_SPELL_POWER, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
        };
    }
}
