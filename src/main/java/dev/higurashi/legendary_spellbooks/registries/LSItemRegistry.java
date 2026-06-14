package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.items.AnnihilatorsProtocolSpellbookItem;
import dev.higurashi.legendary_spellbooks.common.items.StormboundSpellbookItem;
import dev.higurashi.legendary_spellbooks.common.items.TempestUpgradeSmithingTemplateItem;
import dev.higurashi.legendary_spellbooks.common.items.armor.OblivionmancerArmorItem;
import dev.higurashi.legendary_spellbooks.common.items.armor.StormmancerArmorItem;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LSItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, LegendarySpellbooks.MOD_ID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static final Supplier<Item> ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM = ITEMS.register("annihilators_protocol", () -> new AnnihilatorsProtocolSpellbookItem(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM = ITEMS.register("stormbound_grimoire", () -> new StormboundSpellbookItem(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> ANNIHILATION_UPGRADE_ORB = ITEMS.register("upgrade_orb_annihilation", () -> new UpgradeOrbItem(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant().component(ComponentRegistry.UPGRADE_ORB_TYPE, LSUpgradeOrbTypeRegistry.ANNIHILATION_SPELL_POWER)));
    public static final Supplier<Item> ANNIHILATION_RUNE = ITEMS.register("annihilation_rune", () -> new Item(new Item.Properties()));

    public static final Supplier<Item> OBLIVIONMANCER_HAT = ITEMS.register("oblivionmancer_hat", () -> new OblivionmancerArmorItem(ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).durability(ArmorItem.Type.HELMET.getDurability(37))));
    public static final Supplier<Item> OBLIVIONMANCER_ROBE = ITEMS.register("oblivionmancer_robe", () -> new OblivionmancerArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(37))));
    public static final Supplier<Item> OBLIVIONMANCER_LEGGINGS = ITEMS.register("oblivionmancer_leggings", () -> new OblivionmancerArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1).durability(ArmorItem.Type.LEGGINGS.getDurability(37))));
    public static final Supplier<Item> OBLIVIONMANCER_BOOTS = ITEMS.register("oblivionmancer_boots", () -> new OblivionmancerArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).durability(ArmorItem.Type.BOOTS.getDurability(37))));

    public static final Supplier<Item> STORMMANCER_HOOD = ITEMS.register("stormmancer_hood", () -> new StormmancerArmorItem(ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).durability(ArmorItem.Type.HELMET.getDurability(79))));
    public static final Supplier<Item> STORMMANCER_ROBE = ITEMS.register("stormmancer_robe", () -> new StormmancerArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(79))));
    public static final Supplier<Item> STORMMANCER_LEGGINGS = ITEMS.register("stormmancer_leggings", () -> new StormmancerArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1).durability(ArmorItem.Type.LEGGINGS.getDurability(79))));
    public static final Supplier<Item> STORMMANCER_BOOTS = ITEMS.register("stormmancer_boots", () -> new StormmancerArmorItem(ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).durability(ArmorItem.Type.BOOTS.getDurability(79))));

    public static final Supplier<Item> TEMPEST_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("tempest_upgrade_template", TempestUpgradeSmithingTemplateItem::new);
}
