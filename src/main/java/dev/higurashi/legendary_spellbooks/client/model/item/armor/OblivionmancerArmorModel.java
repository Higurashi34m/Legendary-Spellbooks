package dev.higurashi.legendary_spellbooks.client.model.item.armor;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.items.armor.OblivionmancerArmorItem;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class OblivionmancerArmorModel extends GeoModel<OblivionmancerArmorItem> {
    @Override
    public ResourceLocation getModelResource(OblivionmancerArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "geo/oblivionmancer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OblivionmancerArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "textures/models/armor/oblivionmancer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OblivionmancerArmorItem animatable) {
        return IronsSpellbooks.id("animations/wizard_armor_animation.json");
    }
}
