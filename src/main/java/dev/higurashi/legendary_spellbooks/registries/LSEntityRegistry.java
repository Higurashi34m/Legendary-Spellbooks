package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.*;
import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.*;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.Fractured.FracturedApostleEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.HauntedGuardEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.HauntedKnightEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Pets.SkeloraptorEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.SpaceStation.Flameborn.AnnihilationPursuer.AnnihilationPursuerEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.SpaceStation.Flameborn.FlamebornGuardEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.SpaceStation.Flameborn.FlamebornWarriorEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationBombEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.CloudEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SoulTridentEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LSEntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LegendarySpellbooks.MOD_ID);
    public static void register(IEventBus bus) { ENTITIES.register(bus); }

    public static final RegistryObject<EntityType<AnnihilationPursuerEntity>> SUMMONED_ANNIHILATION_PURSUER_ENTITY = ENTITIES.register("summoned_annihilation_pursuer", () -> EntityType.Builder.<AnnihilationPursuerEntity>of(SummonedAnnihilationPursuerEntity::new, MobCategory.CREATURE)
            .sized(1.5f, 5.0f)
            .fireImmune()
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "summoned_annihilation_pursuer").toString()));

    public static final RegistryObject<EntityType<FlamebornGuardEntity>> SUMMONED_FLAMEBORN_GUARD_ENTITY = ENTITIES.register("summoned_flameborn_guard", () -> EntityType.Builder.<FlamebornGuardEntity>of(SummonedFlamebornGuardEntity::new, MobCategory.CREATURE)
            .sized(1.0f, 3.0f)
            .fireImmune()
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "summoned_flameborn_guard").toString()));

    public static final RegistryObject<EntityType<FlamebornWarriorEntity>> SUMMONED_FLAMEBORN_WARRIOR_ENTITY = ENTITIES.register("summoned_flameborn_warrior", () -> EntityType.Builder.<FlamebornWarriorEntity>of(SummonedFlamebornWarriorEntity::new, MobCategory.CREATURE)
            .sized(1.0f, 3.0f)
            .fireImmune()
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "summoned_flameborn_guard").toString()));

    public static final RegistryObject<EntityType<HauntedGuardEntity>> SUMMONED_HAUNTED_GUARD_ENTITY = ENTITIES.register("summoned_haunted_guard", () -> EntityType.Builder.<HauntedGuardEntity>of(SummonedHauntedGuardEntity::new, MobCategory.CREATURE)
            .sized(1.0f, 2.5f)
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "summoned_haunted_guard").toString()));

    public static final RegistryObject<EntityType<HauntedKnightEntity>> SUMMONED_HAUNTED_KNIGHT_ENTITY = ENTITIES.register("summoned_haunted_knight", () -> EntityType.Builder.<HauntedKnightEntity>of(SummonedHauntedKnightEntity::new, MobCategory.CREATURE)
            .sized(1.0f, 2.5f)
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "summoned_haunted_knight").toString()));

    public static final RegistryObject<EntityType<SkeloraptorEntity>> SUMMONED_SKELORAPTOR_ENTITY = ENTITIES.register("summoned_skeloraptor", () -> EntityType.Builder.<SkeloraptorEntity>of(SummonedSkeloraptorEntity::new, MobCategory.CREATURE)
            .sized(1.25f, 2.6f)
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "summoned_skeloraptor").toString()));

    public static final RegistryObject<EntityType<CloudEntity>> SPELL_CLOUD_ENTITY = ENTITIES.register("spell_cloud", () -> EntityType.Builder.<CloudEntity>of(SpellCloudEntity::new, MobCategory.MISC)
            .sized(1.0f, 1.0f)
            .updateInterval(20)
            .clientTrackingRange(4)
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "spell_cloud").toString()));

    public static final RegistryObject<EntityType<SpellFireColumnEntity>> SPELL_FIRE_COLUMN_ENTITY = ENTITIES.register("spell_fire_column", () -> EntityType.Builder.<SpellFireColumnEntity>of(SpellFireColumnEntity::new, MobCategory.MISC)
            .fireImmune()
            .sized(0.75f, 0.5f)
            .updateInterval(2)
            .clientTrackingRange(6)
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "spell_fire_column").toString()));

    public static final RegistryObject<EntityType<SpellPoisonousShockwaveEntity>> SPELL_POISONOUS_SHOCKWAVE_ENTITY = ENTITIES.register("spell_poisonous_shockwave", () -> EntityType.Builder.<SpellPoisonousShockwaveEntity>of(SpellPoisonousShockwaveEntity::new, MobCategory.MISC)
            .sized(1.0f, 1.0f)
            .updateInterval(2)
            .clientTrackingRange(6)
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "spell_poisonous_shockwave").toString()));

    public static final RegistryObject<EntityType<SpellIceSpikeEntity>> SPELL_ICE_SPIKE_ENTITY = ENTITIES.register("spell_ice_spike", () -> EntityType.Builder.<SpellIceSpikeEntity>of(SpellIceSpikeEntity::new, MobCategory.MISC)
            .sized(0.6f, 1.95f)
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "spell_ice_spike").toString()));

    public static final RegistryObject<EntityType<CumuloChargeEntity>> CUMULO_CHARGE_ENTITY = ENTITIES.register("cumulo_charge", () -> EntityType.Builder.of(CumuloChargeEntity::new, MobCategory.MISC)
            .sized(1.5f, 2.5f)
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "cumulo_charge").toString()));


    public static final RegistryObject<EntityType<AnnihilationBombEntity>> SPELL_ANNIHILATION_BOMB_ENTITY = ENTITIES.register("spell_annihilation_bomb", () -> EntityType.Builder.<AnnihilationBombEntity>of(SpellAnnihilationBombEntity::new, MobCategory.MISC)
            .sized(2.0f, 2.0f)
            .updateInterval(2)
            .clientTrackingRange(6)
            .fireImmune()
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "spell_annihilation_bomb").toString()));

    public static final RegistryObject<EntityType<FracturedApostleEntity>> SUMMONED_FRACTURED_APOSTLE_ENTITY = ENTITIES.register("summoned_fractured_apostle", () -> EntityType.Builder.<FracturedApostleEntity>of(SummonedFracturedApostleEntity::new, MobCategory.CREATURE)
            .sized(1.25f, 2.0f)
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "summoned_fractured_apostle").toString()));

    public static final RegistryObject<EntityType<SoulTridentEntity>> SPELL_SOUL_TRIDENT_ENTITY = ENTITIES.register("spell_soul_trident_entity", () -> EntityType.Builder.<SoulTridentEntity>of(SpellSoulTridentEntity::new, MobCategory.MISC)
            .sized(1.0f, 1.0f)
            .updateInterval(20)
            .clientTrackingRange(4)
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "spell_soul_trident_entity").toString()));
}
