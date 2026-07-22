package dev.higurashi.legendary_spellbooks.client.model.item.armor;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.item.armor.StormmancerArmorItem;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class StormmancerArmorModel extends GeoModel<StormmancerArmorItem> {
    @Override
    public ResourceLocation getModelResource(StormmancerArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "geo/stormmancer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StormmancerArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "textures/models/armor/stormmancer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StormmancerArmorItem animatable) {
        return IronsSpellbooks.id("animations/wizard_armor_animation.json");
    }
}
