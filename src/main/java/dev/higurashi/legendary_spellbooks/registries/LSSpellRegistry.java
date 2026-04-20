package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.spells.lightning.*;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class LSSpellRegistry {
    private static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, LegendarySpellbooks.MOD_ID);
    public static void register(IEventBus bus) { SPELLS.register(bus); }
    private static RegistryObject<AbstractSpell> register(AbstractSpell spell) { return SPELLS.register(spell.getSpellName(), () -> spell); }

    // Lightning
    public static final RegistryObject<AbstractSpell> CLOUD_RAIL_SPELL = register(new CloudRailSpell());
    public static final RegistryObject<AbstractSpell> CLOUD_RING_SPELL = register(new CloudRingSpell());
    public static final RegistryObject<AbstractSpell> NIMBUS_ARRAY_SPELL = register(new NimbusArraySpell());
    public static final RegistryObject<AbstractSpell> TRIPLE_NIMBUS_ARRAY_SPELL = register(new TripleNimbusArraySpell());
    public static final RegistryObject<AbstractSpell> THUNDER_FANBURST_SPELL = register(new ThunderFanburstSpell());
}