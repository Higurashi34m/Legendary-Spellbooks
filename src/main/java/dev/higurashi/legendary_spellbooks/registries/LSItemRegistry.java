package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.items.AnnihilatorsProtocolSpellbookItem;
import dev.higurashi.legendary_spellbooks.common.items.StormboundSpellbookItem;
import dev.higurashi.legendary_spellbooks.common.items.armor.OblivionmancerArmorItem;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LSItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LegendarySpellbooks.MOD_ID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static final RegistryObject<Item> ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM = ITEMS.register("annihilators_protocol", AnnihilatorsProtocolSpellbookItem::new);
    public static final RegistryObject<Item> STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM = ITEMS.register("stormbound_grimoire", StormboundSpellbookItem::new);
    public static final RegistryObject<Item> ANNIHILATION_UPGRADE_ORB = ITEMS.register("upgrade_orb_annihilation", () -> new UpgradeOrbItem(ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON), LSUpgradeOrbTypeRegistry.ANNIHILATION_SPELL_POWER));
    public static final RegistryObject<Item> ANNIHILATION_RUNE = ITEMS.register("annihilation_rune", () -> new Item(ItemPropertiesHelper.material()));

    public static final RegistryObject<Item> OBLIVIONMANCER_HAT = ITEMS.register("oblivionmancer_hat", () -> new OblivionmancerArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1)/*.durability(ArmorItem.Type.HELMET.getDurability(37))*/));
    public static final RegistryObject<Item> OBLIVIONMANCER_ROBE = ITEMS.register("oblivionmancer_robe", () -> new OblivionmancerArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1)/*.durability(ArmorItem.Type.CHESTPLATE.getDurability(37))*/));
    public static final RegistryObject<Item> OBLIVIONMANCER_LEGGINGS = ITEMS.register("oblivionmancer_leggings", () -> new OblivionmancerArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1)/*.durability(ArmorItem.Type.LEGGINGS.getDurability(37))*/));
    public static final RegistryObject<Item> OBLIVIONMANCER_BOOTS = ITEMS.register("oblivionmancer_boots", () -> new OblivionmancerArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1)/*.durability(ArmorItem.Type.BOOTS.getDurability(37))*/));
}
