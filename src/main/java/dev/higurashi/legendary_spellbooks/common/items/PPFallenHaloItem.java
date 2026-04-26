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

public class PPFallenHaloItem extends Item implements ICurioItem {
    private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("ffcee304-2a3e-4754-89c2-5a5b34ba2207");
    private static final UUID HOLY_POWER_MODIFIER_UUID = UUID.fromString("09c1473a-87d4-4c76-a66e-3b041099d7e2");

    public PPFallenHaloItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(ComponentUtils.itemTooltip(this)));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext context, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Fallen Halo Attack Damage", 0.599, AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(AttributeRegistry.ENDER_SPELL_POWER.get(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Fallen Halo Ender Spell Power", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));

        return modifiers;
    }
}
