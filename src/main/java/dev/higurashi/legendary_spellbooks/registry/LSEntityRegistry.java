package dev.higurashi.legendary_spellbooks.registry;

import dev.higurashi.daybreaklib.api.annotation.AutoRegister;
import dev.higurashi.daybreaklib.api.registry.EntityRegistryManager;
import dev.higurashi.daybreaklib.api.registry.reference.EntityReference;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.*;
import dev.higurashi.legendary_spellbooks.common.entity.spell.summoned.*;
import net.minecraft.world.entity.MobCategory;

@AutoRegister
public class LSEntityRegistry {
    private static final EntityRegistryManager ENTITIES = new EntityRegistryManager(LegendarySpellbooks.MOD_ID);

    public static final EntityReference SUMMONED_ANNIHILATION_PURSUER_ENTITY = ENTITIES.create("summoned_annihilation_pursuer", SummonedAnnihilationPursuerEntity.class)
            .setCategory(MobCategory.CREATURE)
            .setSize(1.5f, 5.0f)
            .setFireImmune().build();

    public static final EntityReference SUMMONED_FLAMEBORN_GUARD_ENTITY = ENTITIES.create("summoned_flameborn_guard", SummonedFlamebornGuardEntity.class)
            .setCategory(MobCategory.CREATURE)
            .setSizeY(3.0f)
            .setFireImmune().build();

    public static final EntityReference SUMMONED_FLAMEBORN_WARRIOR_ENTITY = ENTITIES.create("summoned_flameborn_warrior", SummonedFlamebornWarriorEntity.class)
            .setCategory(MobCategory.CREATURE)
            .setSizeY(3.0f)
            .setFireImmune().build();

    public static final EntityReference SUMMONED_HAUNTED_GUARD_ENTITY = ENTITIES.create("summoned_haunted_guard", SummonedHauntedGuardEntity.class)
            .setCategory(MobCategory.CREATURE)
            .setSizeY(2.5f).build();

    public static final EntityReference SUMMONED_HAUNTED_KNIGHT_ENTITY = ENTITIES.create("summoned_haunted_knight", SummonedHauntedKnightEntity.class)
            .setCategory(MobCategory.CREATURE)
            .setSizeY(2.5f).build();

    public static final EntityReference SUMMONED_FRACTURED_APOSTLE_ENTITY = ENTITIES.create("summoned_fractured_apostle", SummonedFracturedApostleEntity.class)
            .setCategory(MobCategory.CREATURE)
            .setSize(1.25f, 2.0f).build();

    public static final EntityReference SUMMONED_SKELORAPTOR_ENTITY = ENTITIES.create("summoned_skeloraptor", SummonedSkeloraptorEntity.class)
            .setCategory(MobCategory.CREATURE)
            .setSize(1.25f, 2.6f).build();

    public static final EntityReference SPELL_CLOUD_ENTITY = ENTITIES.create("spell_cloud", SpellCloudEntity.class)
            .setCategory(MobCategory.MISC)
            .setUpdateInterval(20).build();

    public static final EntityReference SPELL_FIRE_COLUMN_ENTITY = ENTITIES.create("spell_fire_column", SpellFireColumnEntity.class)
            .setCategory(MobCategory.MISC)
            .setSize(0.75f, 0.5f)
            .setUpdateInterval(2)
            .setTrackingRange(6).build();

    public static final EntityReference SPELL_POISONOUS_SHOCKWAVE_ENTITY = ENTITIES.create("spell_poisonous_shockwave", SpellPoisonousShockwaveEntity.class)
            .setCategory(MobCategory.MISC)
            .setUpdateInterval(2)
            .setTrackingRange(6).build();

    public static final EntityReference SPELL_ICE_SPIKE_ENTITY = ENTITIES.create("spell_ice_spike", SpellIceSpikeEntity.class)
            .setCategory(MobCategory.MISC)
            .setSizeY(2.0f).build();

    public static final EntityReference CUMULO_CHARGE_ENTITY = ENTITIES.create("cumulo_charge", CumuloChargeEntity.class)
            .setCategory(MobCategory.MISC)
            .setSize(1.5f, 2.5f).build();


    public static final EntityReference SPELL_ANNIHILATION_BOMB_ENTITY = ENTITIES.create("spell_annihilation_bomb", SpellAnnihilationBombEntity.class)
            .setCategory(MobCategory.MISC)
            .setSize(2.0f, 2.0f)
            .setUpdateInterval(2)
            .setTrackingRange(6).build();

    public static final EntityReference SPELL_SOUL_TRIDENT_ENTITY = ENTITIES.create("spell_soul_trident_entity", SpellSoulTridentEntity.class)
            .setCategory(MobCategory.MISC)
            .setUpdateInterval(20).build();

    public static final EntityReference DUNE_SENTINEL_PHANTOM_ENTITY = ENTITIES.create("dune_sentinel_phantom", DuneSentinelPhantomEntity.class)
            .setCategory(MobCategory.MISC)
            .setSize(1.25f, 4.0f).build();

    public static final EntityReference SPELL_BOMB_ENTITY = ENTITIES.create("spell_bomb", SpellBombEntity.class)
            .setCategory(MobCategory.MISC)
            .setSize(0.5f, 0.5f)
            .setUpdateInterval(20)
            .setTrackingRange(6).build();

    public static final EntityReference SPELL_ANNIHILATION_BEAM = ENTITIES.create("spell_annihilation_beam", SpellAnnihilationBeamEntity.class)
            .setCategory(MobCategory.MISC)
            .setTrackingRange(6).build();

    public static final EntityReference SPELL_ANNIHILATION_EXPLOSION = ENTITIES.create("spell_annihilation_explosion", SpellAnnihilationExplosionEntity.class)
            .setCategory(MobCategory.MISC)
            .setSize(2.0f, 2.5f).build();

    public static final EntityReference SPELL_SMALL_ANNIHILATION_BOMB = ENTITIES.create("spell_small_annihilation_bomb", SpellSmallAnnihilationBombEntity.class)
            .setCategory(MobCategory.MISC)
            .setSize(0.5f, 0.5f).build();

    public static final EntityReference ANNIHILATION_ARROW = ENTITIES.create("annihilation_arrow", AnnihilationArrowEntity.class)
            .setCategory(MobCategory.MISC)
            .setSize(0.5f, 0.5f)
            .setTrackingRange(6).build();
}
