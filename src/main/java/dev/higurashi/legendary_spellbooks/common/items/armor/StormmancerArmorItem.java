package dev.higurashi.legendary_spellbooks.common.items.armor;

import dev.higurashi.legendary_spellbooks.client.model.item.armor.StormmancerArmorModel;
import dev.higurashi.legendary_spellbooks.registries.LSArmorMaterialRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.armor.ImbuableChestplateArmorItem;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class StormmancerArmorItem extends ImbuableChestplateArmorItem {
    public StormmancerArmorItem(Type slot, Properties settings) {
        super(LSArmorMaterialRegistry.STORMMANCER, slot, settings, attribute());
    }

    @Override
    public GeoArmorRenderer<?> supplyRenderer() {
        return new GeoArmorRenderer<>(new StormmancerArmorModel());
    }

    private static AttributeContainer[] attribute() {
        return new AttributeContainer[]{
                new AttributeContainer(AttributeRegistry.MAX_MANA, 225, AttributeModifier.Operation.ADD_VALUE),
                new AttributeContainer(AttributeRegistry.LIGHTNING_SPELL_POWER, 0.20, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.LIGHTNING_MAGIC_RESIST, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
        };
    }
}
