package dev.higurashi.legendary_spellbooks.common.item;

import dev.higurashi.daybreaklib.api.util.TextUtils;
import dev.higurashi.legendary_spellbooks.registry.LSAttributeRegistry;
import dev.higurashi.legendary_spellbooks.registry.LSItemRegistry;
import dev.higurashi.legendary_spellbooks.registry.LSSpellRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.UniqueSpellBook;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AnnihilatorsProtocolSpellbookItem extends UniqueSpellBook {
    private static float dodgePercent = 0.1f;

    public AnnihilatorsProtocolSpellbookItem() {
        super(new SpellDataRegistryHolder[]{
                new SpellDataRegistryHolder(LSSpellRegistry.ANNIHILATION_GEYSER_SPELL, 1),
                new SpellDataRegistryHolder(LSSpellRegistry.ANNIHILATION_BEAM_SPELL, 3),
        }, 10);

        withSpellbookAttributes(
                new AttributeContainer(LSAttributeRegistry.ANNIHILATION_SPELL_POWER, 0.1, AttributeModifier.Operation.MULTIPLY_BASE),
                new AttributeContainer(AttributeRegistry.MAX_MANA, 200, AttributeModifier.Operation.ADDITION)
        );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> lines, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, lines, flag);

        String spellCount = TextUtils.tooltipKey(IronsSpellbooks.id("spellbook_spell_count")).text();
        MutableComponent thisTooltip = TextUtils.tooltipKey(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.getId()).translate(TextUtils.truncate(dodgePercent * 100, 0));

        TextUtils.addAfterComponent(lines, spellCount, thisTooltip);
    }

    public static float getDodgePercent() {
        return dodgePercent;
    }
}
