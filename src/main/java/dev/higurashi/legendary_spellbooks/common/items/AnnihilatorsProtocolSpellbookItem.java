package dev.higurashi.legendary_spellbooks.common.items;

import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.registries.LSAttributeRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.UniqueSpellBook;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AnnihilatorsProtocolSpellbookItem extends UniqueSpellBook {
    public AnnihilatorsProtocolSpellbookItem() {
        super(new SpellDataRegistryHolder[]{
                new SpellDataRegistryHolder(LSSpellRegistry.ANNIHILATION_GEYSER_SPELL, 1),
                new SpellDataRegistryHolder(LSSpellRegistry.ANNIHILATION_BEAM_SPELL, 3),
        }, 10);

        withSpellbookAttributes(
                new AttributeContainer(LSAttributeRegistry.ANNIHILATION_SPELL_POWER, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.MAX_MANA, 200, AttributeModifier.Operation.ADD_VALUE)
        );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.TooltipContext context, @NotNull List<Component> lines, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, lines, flag);

        int insertIndex = TooltipsUtils.indexOfComponent(lines, "tooltip.irons_spellbooks.spellbook_spell_count");
        int pos = insertIndex < 0 ? lines.size() : insertIndex + 1;

        lines.add(pos++, Component.empty());
        lines.add(pos, Component.translatable(ComponentUtils.itemTooltip(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get())).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}
