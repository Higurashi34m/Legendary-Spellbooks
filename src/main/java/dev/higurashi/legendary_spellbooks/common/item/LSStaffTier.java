package dev.higurashi.legendary_spellbooks.common.item;

import dev.higurashi.legendary_spellbooks.registry.LSAttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.item.weapons.StaffTier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class LSStaffTier {
    public static StaffTier OBLIVIONMANCER = new StaffTier(4, -3,
            new AttributeContainer(AttributeRegistry.MANA_REGEN, 0.25, AttributeModifier.Operation.MULTIPLY_BASE),
            new AttributeContainer(LSAttributeRegistry.ANNIHILATION_SPELL_POWER, 0.15, AttributeModifier.Operation.MULTIPLY_BASE),
            new AttributeContainer(AttributeRegistry.SPELL_POWER, 0.05, AttributeModifier.Operation.MULTIPLY_BASE)
    );
}
