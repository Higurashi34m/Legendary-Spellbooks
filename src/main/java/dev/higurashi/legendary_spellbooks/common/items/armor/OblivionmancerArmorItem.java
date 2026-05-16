package dev.higurashi.legendary_spellbooks.common.items.armor;

import dev.higurashi.legendary_spellbooks.client.model.item.armor.OblivionmancerArmorModel;
import io.redspace.ironsspellbooks.item.armor.ImbuableChestplateArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class OblivionmancerArmorItem extends ImbuableChestplateArmorItem {
    public OblivionmancerArmorItem(Type slot, Properties settings) {
        super(LSArmorMaterials.OBLIVIONMANCER, slot, settings);
    }

    @Override
    public GeoArmorRenderer<?> supplyRenderer() {
        return new GeoArmorRenderer<>(new OblivionmancerArmorModel());
    }
}
