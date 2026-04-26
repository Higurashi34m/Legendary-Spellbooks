package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.items.AnnihilatorsProtocolSpellbookItem;
import dev.higurashi.legendary_spellbooks.common.items.PPFallenHaloItem;
import dev.higurashi.legendary_spellbooks.common.items.PPLumiereHaloItem;
import dev.higurashi.legendary_spellbooks.common.items.StormboundSpellbookItem;
import net.minecraft.world.item.Item;
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

    public static final RegistryObject<Item> PP_LUMIERE_HALO = ITEMS.register("pp_lumiere_halo", PPLumiereHaloItem::new);
    public static final RegistryObject<Item> PP_FALLEN_HALO = ITEMS.register("pp_fallen_halo", PPFallenHaloItem::new);
}
