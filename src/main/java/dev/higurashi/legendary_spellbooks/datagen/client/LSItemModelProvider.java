package dev.higurashi.legendary_spellbooks.datagen.client;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class LSItemModelProvider extends ItemModelProvider {
    public LSItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, LegendarySpellbooks.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        spellBook(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get());
        spellBook(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get());

        basicItem(LSItemRegistry.PP_LUMIERE_HALO.get());
        basicItem(LSItemRegistry.PP_FALLEN_HALO.get());
    }

    public void spellBook(Item item) {
        if (item == null) return;
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
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
