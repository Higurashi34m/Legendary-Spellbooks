package dev.higurashi.legendary_spellbooks.setups;

import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.CumuloChargeEntity;
import dev.higurashi.legendary_spellbooks.common.entity.spell.summoned.*;
import dev.higurashi.legendary_spellbooks.registry.LSEntityRegistry;
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
        event.put(LSEntityRegistry.SUMMONED_ANNIHILATION_PURSUER_ENTITY.getAs(), SummonedAnnihilationPursuerEntity.createAttributes().build());
        event.put(LSEntityRegistry.SUMMONED_FLAMEBORN_GUARD_ENTITY.getAs(), SummonedFlamebornGuardEntity.createAttributes().build());
        event.put(LSEntityRegistry.SUMMONED_FLAMEBORN_WARRIOR_ENTITY.getAs(), SummonedFlamebornWarriorEntity.createAttributes().build());
        event.put(LSEntityRegistry.SUMMONED_HAUNTED_KNIGHT_ENTITY.getAs(), SummonedHauntedKnightEntity.createAttributes().build());
        event.put(LSEntityRegistry.SUMMONED_HAUNTED_GUARD_ENTITY.getAs(), SummonedHauntedGuardEntity.createAttributes().build());
        event.put(LSEntityRegistry.SUMMONED_SKELORAPTOR_ENTITY.getAs(), SummonedSkeloraptorEntity.createAttributes().build());
        event.put(LSEntityRegistry.SUMMONED_FRACTURED_APOSTLE_ENTITY.getAs(), SummonedFracturedApostleEntity.createAttributes().build());

        event.put(LSEntityRegistry.CUMULO_CHARGE_ENTITY.getAs(), CumuloChargeEntity.createAttributes().build());
    }
}
