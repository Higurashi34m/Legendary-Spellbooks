package dev.higurashi.legendary_spellbooks.datagen.client;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class LSItemModelProvider extends ItemModelProvider {
    public LSItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, LegendarySpellbooks.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        basicItem(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "scroll_annihilation"));
        basicItem(LSItemRegistry.ANNIHILATION_RUNE.get());
        basicItem(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "affinity_ring_annihilation"));
        basicItem(LSItemRegistry.ANNIHILATION_UPGRADE_ORB.get());

        basicItem(LSItemRegistry.OBLIVIONMANCER_HAT.get());
        basicItem(LSItemRegistry.OBLIVIONMANCER_ROBE.get());
        basicItem(LSItemRegistry.OBLIVIONMANCER_LEGGINGS.get());
        basicItem(LSItemRegistry.OBLIVIONMANCER_BOOTS.get());

        basicItem(LSItemRegistry.STORMMANCER_HOOD.get());
        basicItem(LSItemRegistry.STORMMANCER_ROBE.get());
        basicItem(LSItemRegistry.STORMMANCER_LEGGINGS.get());
        basicItem(LSItemRegistry.STORMMANCER_BOOTS.get());

        basicItem(LSItemRegistry.TEMPEST_UPGRADE_SMITHING_TEMPLATE.get());


        spellBook(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get());
        spellBook(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get());
    }

    public void spellBook(Item item) {
        if (item == null) return;
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        String path = itemId.getPath();

        ItemModelBuilder itemBuilder = getBuilder(path).parent(getExistingFile(mcLoc("item/handheld")));
        SeparateTransformsModelBuilder<ItemModelBuilder> loader = itemBuilder.customLoader(SeparateTransformsModelBuilder::begin);

        loader.base(nested()
                .parent(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "item/template_spell_book_model")))
                .texture("book", modLoc("item/spell_book_models/" + path))
                .texture("particle", modLoc("item/spell_book_models/" + path))
        );

        loader.perspective(ItemDisplayContext.GUI, nested()
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", modLoc("item/" + path))
        );

        loader.perspective(ItemDisplayContext.HEAD, nested()
                .parent(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "item/template_spell_book_model_open")))
                .texture("book", modLoc("item/spell_book_models/" + path))
                .texture("particle", modLoc("item/spell_book_models/" + path))
        );
    }
}
