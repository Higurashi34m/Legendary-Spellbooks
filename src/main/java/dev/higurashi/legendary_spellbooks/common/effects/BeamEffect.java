package dev.higurashi.legendary_spellbooks.common.effects;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BeamEffect extends MobEffect {
    public BeamEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFFFFFF);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, LegendarySpellbooks.id("effect_beam"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, LegendarySpellbooks.id("effect_beam"), 1.0 , AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, LegendarySpellbooks.id("effect_beam"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        entity.setDeltaMovement(Vec3.ZERO);

        entity.stopUsingItem();
        entity.setNoActionTime(10);

        MobEffectInstance effect = entity.getEffect(LSEffectRegistry.BEAM_EFFECT);
        if (effect == null) return false;

        if (amplifier > 0 && effect.getDuration() == 1) entity.addEffect(new MobEffectInstance(ModEffects.STUN, 20 + amplifier * 10, 0));
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
