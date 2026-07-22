package dev.higurashi.legendary_spellbooks.common.item.armor;

import dev.higurashi.legendary_spellbooks.client.model.item.armor.StormmancerArmorModel;
import io.redspace.ironsspellbooks.item.armor.ImbuableChestplateArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class StormmancerArmorItem extends ImbuableChestplateArmorItem {
    public StormmancerArmorItem(Type slot, Properties settings) {
        super(LSArmorMaterials.STORMMANCER, slot, settings);
    }

    @Override
    public GeoArmorRenderer<?> supplyRenderer() {
        return new GeoArmorRenderer<>(new StormmancerArmorModel());
    }
}
