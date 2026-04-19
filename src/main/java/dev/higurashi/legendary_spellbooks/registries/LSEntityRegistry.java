package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellCloudEntity;
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

    public static final RegistryObject<EntityType<CloudEntity>> SPELL_CLOUD_ENTITY = ENTITIES.register("spell_cloud", () -> EntityType.Builder.<CloudEntity>of(SpellCloudEntity::new, MobCategory.MISC)
            .sized(1.0f, 1.0f)
            .updateInterval(20)
            .clientTrackingRange(4)
            .build(ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "spell_cloud").toString()));
}
