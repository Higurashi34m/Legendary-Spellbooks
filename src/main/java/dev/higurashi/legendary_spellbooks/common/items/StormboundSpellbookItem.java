package dev.higurashi.legendary_spellbooks.common.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.item.UniqueSpellBook;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StormboundSpellbookItem extends UniqueSpellBook {
    private static final UUID BASE_POWER_UUID = UUID.fromString("c28be49c-157e-4530-9267-6504b92a7f49");
    private static final UUID THUNDER_POWER_UUID = UUID.fromString("3e4cb26d-b7b1-483a-9f66-1f994f3bb367");

    private static final AttributeModifier BASE_POWER = new AttributeModifier(BASE_POWER_UUID, "stormbound_base_power", 0.15, AttributeModifier.Operation.MULTIPLY_BASE);
    private static final AttributeModifier THUNDER_POWER = new AttributeModifier(THUNDER_POWER_UUID, "stormbound_thunder_power", 0.3, AttributeModifier.Operation.MULTIPLY_BASE);

    private static AffinityData SUNNY, THUNDER;
    private boolean forcedThunder = false;

    public StormboundSpellbookItem() {
        super(SpellDataRegistryHolder.of(), 12);
        withSpellbookAttributes(new AttributeContainer(AttributeRegistry.MAX_MANA, 200, AttributeModifier.Operation.ADDITION));
    }

    private static AffinityData getAffinity(boolean thundering) {
        if (thundering) {
            if (THUNDER == null) THUNDER = new AffinityData(Map.of(
                    LSSpellRegistry.NIMBUS_ARRAY_SPELL.get().getSpellResource(), 1,
                    LSSpellRegistry.TRIPLE_NIMBUS_ARRAY_SPELL.get().getSpellResource(), 2,
                    LSSpellRegistry.ENERGY_BEAM_SPELL.get().getSpellResource(), 1
//                    LSSpellRegistry.CUMULO_CHARGE_SPELl.get().getSpellResource(), 2
            ));
            return THUNDER;
        } else {
            if (SUNNY == null) SUNNY = new AffinityData(Map.of(
                    LSSpellRegistry.CLOUD_RAIL_SPELL.get().getSpellResource(), 1,
                    LSSpellRegistry.CLOUD_RING_SPELL.get().getSpellResource(), 2,
                    LSSpellRegistry.TORNADO_SPELL.get().getSpellResource(), 1,
                    LSSpellRegistry.THUNDER_FANBURST_SPELL.get().getSpellResource(), 1
//                    LSSpellRegistry.CUMULO_CHARGE_SPELl.get().getSpellResource(), 2
            ));
            return SUNNY;
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if (slotContext.entity().level().isClientSide || !(slotContext.entity() instanceof Player player)) return;

        Level level = player.level();

        if (level instanceof ServerLevel serverLevel) {
            if (level.isRaining() && !level.isThundering() && !forcedThunder) {
                serverLevel.setWeatherParameters(0, 6000, true, true);
                forcedThunder = true;
            } else if (!level.isRaining()) {
                forcedThunder = false;
            }
        }

        AffinityData nextAffinity = getAffinity(level.isThundering());
        if (!nextAffinity.equals(AffinityData.getAffinityData(stack))) {
            AffinityData.set(stack, nextAffinity);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext context, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create(super.getAttributeModifiers(context, uuid, stack));

        if (context.entity() != null) {
            Attribute lightningPower = AttributeRegistry.LIGHTNING_SPELL_POWER.get();

            modifiers.removeAll(lightningPower);

            modifiers.put(lightningPower, context.entity().level().isThundering() ? THUNDER_POWER : BASE_POWER);
        }

        return modifiers;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> lines, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, lines, flag);
        if (level == null) return;

        boolean isThundering = level.isThundering();

        int insertIndex = TooltipsUtils.indexOfComponent(lines, "tooltip.irons_spellbooks.spellbook_spell_count");
        int pos = insertIndex < 0 ? lines.size() : insertIndex + 1;

        lines.add(pos++, Component.empty());
        lines.add(pos++, Component.translatable(ComponentUtils.itemTooltip(this)).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

        lines.add(pos++, buildHeader("on_sunny", !isThundering, ChatFormatting.GOLD));
        lines.add(pos++, makeBonusLine(1, !isThundering, LSSpellRegistry.CLOUD_RAIL_SPELL.get()));
        lines.add(pos++, makeBonusLine(2, !isThundering, LSSpellRegistry.CLOUD_RING_SPELL.get()));
        lines.add(pos++, makeBonusLine(1, !isThundering, LSSpellRegistry.TORNADO_SPELL.get()));
        lines.add(pos++, makeBonusLine(2, !isThundering, LSSpellRegistry.THUNDER_FANBURST_SPELL.get()));
//        lines.add(pos++, makeBonusLine(2, !isThundering, LSSpellRegistry.CUMULO_CHARGE_SPELl.get()));

        lines.add(pos++, buildHeader("on_thunder", isThundering, ChatFormatting.AQUA));
        lines.add(pos++, makeBonusLine(1, isThundering, LSSpellRegistry.NIMBUS_ARRAY_SPELL.get()));
        lines.add(pos++, makeBonusLine(2, isThundering, LSSpellRegistry.TRIPLE_NIMBUS_ARRAY_SPELL.get()));
        lines.add(pos++, makeBonusLine(1, isThundering, LSSpellRegistry.ENERGY_BEAM_SPELL.get()));
//        lines.add(pos++, makeBonusLine(2, isThundering, LSSpellRegistry.CUMULO_CHARGE_SPELl.get()));
    }

    private Component buildHeader(String key, boolean active, ChatFormatting color) {
        String prefix = active ? "> " : "  ";
        ChatFormatting format = active ? color : ChatFormatting.DARK_GRAY;

        return Component.literal(prefix)
                .append(Component.translatable("tooltip.legendary_spellbooks." + key))
                .withStyle(format);
    }

    private Component makeBonusLine(int levelBonus, boolean active, AbstractSpell... spells) {
        MutableComponent names = Component.empty();

        for (int i = 0; i < spells.length; i++) {
            AbstractSpell spell = spells[i];
            Style spellStyle = spell.getSchoolType().getDisplayName().getStyle();

            if (!active) spellStyle = spellStyle.withColor(ChatFormatting.DARK_GRAY);

            names.append(Component.translatable(spell.getComponentId()).withStyle(spellStyle));

            if (i < spells.length - 1) names.append(Component.literal(", ").withStyle(ChatFormatting.DARK_GRAY));
        }

        ChatFormatting textColor = active ? ChatFormatting.YELLOW : ChatFormatting.DARK_GRAY;

        return Component.translatable("tooltip.irons_spellbooks.enhance_spell_level_plural", levelBonus, names).withStyle(textColor);
    }
}
