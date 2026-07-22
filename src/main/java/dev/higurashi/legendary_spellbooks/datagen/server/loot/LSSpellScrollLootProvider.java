package dev.higurashi.legendary_spellbooks.datagen.server.loot;

import dev.higurashi.daybreaklib.api.annotation.AutoDatagen;
import dev.higurashi.daybreaklib.api.datagen.DatagenContext;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.loot.entry.DifficultyWeights;
import dev.higurashi.legendary_spellbooks.common.loot.entry.SpellEntry;
import dev.higurashi.legendary_spellbooks.common.loot.modifier.ItemLootModifier;
import dev.higurashi.legendary_spellbooks.common.loot.modifier.SpellScrollLootModifier;
import dev.higurashi.legendary_spellbooks.registry.LSItemRegistry;
import dev.higurashi.legendary_spellbooks.registry.LSSpellRegistry;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

import java.util.List;

@AutoDatagen(dist = Dist.DEDICATED_SERVER)
public class LSSpellScrollLootProvider extends GlobalLootModifierProvider {
    public LSSpellScrollLootProvider(DatagenContext context) {
        super(context.output(), LegendarySpellbooks.MOD_ID);
    }

    @Override
    protected void start() {
        add("cloud_golem_scrolls", new SpellScrollLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.Cloud_golem.get())).build() },
                List.of(
                        new SpellEntry(LSSpellRegistry.CLOUD_RAIL_SPELL.getId(), 4, 10, 10),
                        new SpellEntry(LSSpellRegistry.CLOUD_RING_SPELL.getId(), 1, 4, 10),
                        new SpellEntry(LSSpellRegistry.NIMBUS_ARRAY_SPELL.getId(), 1, 6, 10),
                        new SpellEntry(LSSpellRegistry.TRIPLE_NIMBUS_ARRAY_SPELL.getId(), 1, 4, 12),
                        new SpellEntry(LSSpellRegistry.TORNADO_SPELL.getId(), 3, 10, 6),
                        new SpellEntry(LSSpellRegistry.QUAD_TORNADO_SPELL.getId(), 1, 4, 11),
                        new SpellEntry(LSSpellRegistry.ENERGY_BEAM_SPELL.getId(), 1, 3, 8),
                        new SpellEntry(LSSpellRegistry.THUNDER_FANBURST_SPELL.getId(), 3, 10, 10),
                        new SpellEntry(LSSpellRegistry.CUMULO_CHARGE_SPELL.getId(), 1, 3, 12)
                ),
                1.0f,
                1,
                3,
                new DifficultyWeights(0.75f, 1.0f, 1.25f, 1.25f)
        ));

        add("obliterator_scrolls", new SpellScrollLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.THE_OBLITERATOR.get())).build() },
                List.of(
                        new SpellEntry(LSSpellRegistry.ANNIHILATION_BEAM_SPELL.getId(), 1, 3, 10),
                        new SpellEntry(LSSpellRegistry.ANNIHILATION_BOMB_SPELL.getId(), 1, 4, 14),
                        new SpellEntry(LSSpellRegistry.ANNIHILATION_SHOCKWAVE_SPELL.getId(), 1, 5, 18),
                        new SpellEntry(LSSpellRegistry.SUMMON_FLAMEBORN_KNIGHTS_SPELL.getId(), 1, 4, 18),
                        new SpellEntry(LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL.getId(), 1, 4, 14)
                ),
                1.0f,
                1,
                2,
                new DifficultyWeights(0.75f, 1.0f, 1.25f, 1.25f)
        ));

        add("annihilation_pursuer_scrolls", new SpellScrollLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.ANNIHILATION_PURSUER.get())).build() },
                List.of(
                        new SpellEntry(LSSpellRegistry.RELEASE_RIFTWALKER_PREDATOR_SPELL.getId(), 1, 3, 10)
                ),
                0.5f,
                1,
                1,
                new DifficultyWeights(0.75f, 1.0f, 1.25f, 1.25f)
        ));

        add("frostbitten_golem_scrolls", new SpellScrollLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.Frostbitten_Golem.get())).build() },
                List.of(
                        new SpellEntry(LSSpellRegistry.GLACIER_ERUPTION_SPELL.getId(), 2, 7, 10),
                        new SpellEntry(LSSpellRegistry.GLACIER_RINGBURST_SPELL.getId(), 1, 5, 10)
                ),
                0.8f,
                1,
                1,
                new DifficultyWeights(0.75f, 1.0f, 1.25f, 1.25f)
        ));

        add("possessed_paladin_scrolls", new SpellScrollLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.Posessed_Paladin.get())).build() },
                List.of(
                        new SpellEntry(LSSpellRegistry.COLLAPSED_KINGDOMS_LEGION_SPELL.getId(), 1, 3, 10),
                        new SpellEntry(LSSpellRegistry.POSSESSED_SOUL_BLADE_SPELL.getId(), 1, 3, 10),
                        new SpellEntry(LSSpellRegistry.POSSESSED_WING_SPELL.getId(), 1, 2, 10),
                        new SpellEntry(LSSpellRegistry.HEMATITE_TRISHULA_SPELL.getId(), 1, 3, 10)
                ),
                1.0f,
                1,
                3,
                new DifficultyWeights(0.75f, 1.0f, 1.25f, 1.25f)
        ));

        add("ancient_guardian_scrolls", new SpellScrollLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.Ancient_Guardian.get())).build() },
                List.of(
                        new SpellEntry(LSSpellRegistry.AMBUSH_THORNS_SPELL.getId(), 3, 10, 10)
                ),
                0.5f,
                1,
                1,
                new DifficultyWeights(0.75f, 1.0f, 1.25f, 1.25f)
        ));

        add("lava_eater_scrolls", new SpellScrollLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.Lava_eater.get())).build() },
                List.of(
                        new SpellEntry(LSSpellRegistry.FLAME_EATER_SPELL.getId(), 3, 6, 10),
                        new SpellEntry(LSSpellRegistry.FLAME_SECTOR_SPELL.getId(), 1, 4, 18)
                ),
                1.0f,
                1,
                2,
                new DifficultyWeights(0.75f, 1.0f, 1.25f, 1.25f)
        ));

        add("overglown_colosus_scrolls", new SpellScrollLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.Overgrown_colossus.get())).build() },
                List.of(
                        new SpellEntry(LSSpellRegistry.OVERGROWN_SHOCKWAVE_SPELL.getId(), 1, 4, 10)
                ),
                1.0f,
                1,
                2,
                new DifficultyWeights(0.75f, 1.0f, 1.25f, 1.25f)
        ));

        add("skeletosaurus_scrolls", new SpellScrollLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.Skeletosaurus.get())).build() },
                List.of(
                        new SpellEntry(LSSpellRegistry.FOSSILIZED_FURY_SPELL.getId(), 1, 6, 10)
                ),
                1.0f,
                1,
                2,
                new DifficultyWeights(0.75f, 1.0f, 1.25f, 1.25f)
        ));

        add("dune_sentinel_scrolls", new SpellScrollLootModifier(
                new LootItemCondition[] { LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.BlastCannon.get())).build() },
                List.of(
                        new SpellEntry(LSSpellRegistry.SENTINEL_SATURATION_SPELL.getId(), 1, 2, 10)
                ),
                0.5f,
                1,
                1,
                new DifficultyWeights(0.75f, 1.0f, 1.25f, 1.25f)
        ));

        // === ITEM ===
        add("cloud_golem_spellbook", new ItemLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.Cloud_golem.get())).build() },
                new ItemStack(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get()),
                0.5f
        ));

        add("cloud_golem_more_air_rune", new ItemLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.Cloud_golem.get())).build() },
                new ItemStack(ModItems.AIR_RUNE.get()),
                2.0f
        ));

        add("cloud_golem_template", new ItemLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.Cloud_golem.get())).build() },
                new ItemStack(LSItemRegistry.TEMPEST_UPGRADE_SMITHING_TEMPLATE.get()),
                1.0f
        ));

        add("annihilators_protocol_spellbook", new ItemLootModifier(
                new LootItemCondition[]{ LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(ModEntities.THE_OBLITERATOR.get())).build() },
                new ItemStack(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get()),
                1.0f
        ));
    }
}
