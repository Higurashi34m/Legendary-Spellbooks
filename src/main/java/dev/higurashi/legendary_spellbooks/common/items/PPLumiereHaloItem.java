package dev.higurashi.legendary_spellbooks.common.items;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class PPLumiereHaloItem extends Item implements ICurioItem {
    private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("ffcee304-2a3e-4754-89c2-5a5b34ba2207");
    private static final UUID HOLY_POWER_MODIFIER_UUID = UUID.fromString("1896f188-c452-4633-830a-2a101911862d");

    public PPLumiereHaloItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(ComponentUtils.itemTooltip(this)));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext context, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Lumiere Halo Attack Damage", 0.599, AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(AttributeRegistry.HOLY_SPELL_POWER.get(), new AttributeModifier(HOLY_POWER_MODIFIER_UUID, "Lumiere Halo Holy Spell Power", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));

        return modifiers;
    }
}