package dev.higurashi.legendary_spellbooks.setups;

import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.CumuloChargeEntity;
import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.*;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(LSEntityRegistry.SUMMONED_ANNIHILATION_PURSUER_ENTITY.get(), SummonedAnnihilationPursuerEntity.createAttributes().build());
        event.put(LSEntityRegistry.SUMMONED_FLAMEBORN_GUARD_ENTITY.get(), SummonedFlamebornGuardEntity.createAttributes().build());
        event.put(LSEntityRegistry.SUMMONED_FLAMEBORN_WARRIOR_ENTITY.get(), SummonedFlamebornWarriorEntity.createAttributes().build());
        event.put(LSEntityRegistry.SUMMONED_HAUNTED_KNIGHT_ENTITY.get(), SummonedHauntedKnightEntity.createAttributes().build());
        event.put(LSEntityRegistry.SUMMONED_HAUNTED_GUARD_ENTITY.get(), SummonedHauntedGuardEntity.createAttributes().build());
        event.put(LSEntityRegistry.SUMMONED_SKELORAPTOR_ENTITY.get(), SummonedSkeloraptorEntity.createAttributes().build());

        event.put(LSEntityRegistry.CUMULO_CHARGE_ENTITY.get(), CumuloChargeEntity.createAttributes().build());
    }
}
