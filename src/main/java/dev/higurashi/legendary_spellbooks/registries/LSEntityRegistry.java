package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellCloudEntity;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellFireColumnEntity;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellPoisonousShockwaveEntity;
import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.SummonedAnnihilationPursuerEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.SpaceStation.Flameborn.AnnihilationPursuer.AnnihilationPursuerEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.CloudEntity;
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
}
