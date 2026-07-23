package dev.higurashi.legendary_spellbooks.registry;

import dev.higurashi.daybreaklib.api.annotation.AutoRegister;
import dev.higurashi.daybreaklib.api.client.model.item.ItemModelFactories;
import dev.higurashi.daybreaklib.api.registry.ItemRegistryManager;
import dev.higurashi.daybreaklib.api.registry.reference.ItemReference;
import dev.higurashi.daybreaklib_iss.api.client.model.item.ISSItemModelFactories;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.item.AnnihilatorsProtocolSpellbookItem;
import dev.higurashi.legendary_spellbooks.common.item.LSStaffTier;
import dev.higurashi.legendary_spellbooks.common.item.StormboundSpellbookItem;
import dev.higurashi.legendary_spellbooks.common.item.TempestUpgradeSmithingTemplateItem;
import dev.higurashi.legendary_spellbooks.common.item.armor.OblivionmancerArmorItem;
import dev.higurashi.legendary_spellbooks.common.item.armor.StormmancerArmorItem;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;

@AutoRegister
public class LSItemRegistry {
    public static final ItemRegistryManager ITEMS = new ItemRegistryManager(LegendarySpellbooks.MOD_ID);

    public static final ItemReference ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM = ITEMS.create("annihilators_protocol", props -> new AnnihilatorsProtocolSpellbookItem()).setModel(ISSItemModelFactories.SPELLBOOK).build();
    public static final ItemReference STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM = ITEMS.create("stormbound_grimoire", props -> new StormboundSpellbookItem()).setModel(ISSItemModelFactories.SPELLBOOK).build();
    public static final ItemReference ANNIHILATION_UPGRADE_ORB = ITEMS.create("upgrade_orb_annihilation", props -> new UpgradeOrbItem(props, LSUpgradeOrbTypeRegistry.ANNIHILATION_POWER.key())).setRarity(Rarity.UNCOMMON).setModel(ItemModelFactories.GENERATED).build();
    public static final ItemReference ANNIHILATION_RUNE = ITEMS.createSimple("annihilation_rune").setModel(ItemModelFactories.GENERATED).build();

    public static final ItemReference OBLIVIONMANCER_HAT = ITEMS.create("oblivionmancer_hat", props -> new OblivionmancerArmorItem(ArmorItem.Type.HELMET, props)).setStacks(1).setModel(ItemModelFactories.GENERATED).build();
    public static final ItemReference OBLIVIONMANCER_ROBE = ITEMS.create("oblivionmancer_robe", props -> new OblivionmancerArmorItem(ArmorItem.Type.CHESTPLATE, props)).setStacks(1).setModel(ItemModelFactories.GENERATED).build();
    public static final ItemReference OBLIVIONMANCER_LEGGINGS = ITEMS.create("oblivionmancer_leggings", props -> new OblivionmancerArmorItem(ArmorItem.Type.LEGGINGS, props)).setStacks(1).setModel(ItemModelFactories.GENERATED).build();
    public static final ItemReference OBLIVIONMANCER_BOOTS = ITEMS.create("oblivionmancer_boots", props -> new OblivionmancerArmorItem(ArmorItem.Type.BOOTS, props)).setStacks(1).setModel(ItemModelFactories.GENERATED).build();

    public static final ItemReference STORMMANCER_HOOD = ITEMS.create("stormmancer_hood", props -> new StormmancerArmorItem(ArmorItem.Type.HELMET, props)).setStacks(1).setModel(ItemModelFactories.GENERATED).build();
    public static final ItemReference STORMMANCER_ROBE = ITEMS.create("stormmancer_robe", props -> new StormmancerArmorItem(ArmorItem.Type.CHESTPLATE, props)).setStacks(1).setModel(ItemModelFactories.GENERATED).build();
    public static final ItemReference STORMMANCER_LEGGINGS = ITEMS.create("stormmancer_leggings", props -> new StormmancerArmorItem(ArmorItem.Type.LEGGINGS, props)).setStacks(1).setModel(ItemModelFactories.GENERATED).build();
    public static final ItemReference STORMMANCER_BOOTS = ITEMS.create("stormmancer_boots", props -> new StormmancerArmorItem(ArmorItem.Type.BOOTS, props)).setStacks(1).setModel(ItemModelFactories.GENERATED).build();

    public static final ItemReference OBLIVIONMANCER_STAFF = ITEMS.create("oblivionmancer_staff", props -> new StaffItem(props, LSStaffTier.OBLIVIONMANCER)).setStacks(1).setRarity(Rarity.EPIC).build();

    public static final ItemReference TEMPEST_UPGRADE_SMITHING_TEMPLATE = ITEMS.create("tempest_upgrade_template", props -> new TempestUpgradeSmithingTemplateItem()).setModel(ItemModelFactories.GENERATED).build();

    public static Collection<RegistryObject<Item>> getEntries() {
        return ITEMS.getEntries();
    }
}
