package dev.higurashi.legendary_spellbooks.common.effects;

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
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "f513d163-b942-42a6-bbb6-4cbf7bd0bc03", -1.0, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "8398b875-27a0-4bb9-bcc2-81e1f13eea05", 1.0 , AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "76541941-8c25-4f80-86c8-6061916c1ccf", -1.0, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        entity.setDeltaMovement(Vec3.ZERO);
        entity.hasImpulse = false;

        entity.stopUsingItem();
        entity.setNoActionTime(10);

        MobEffectInstance effect = entity.getEffect(this);
        if (effect == null) return;

        if (amplifier > 0 && effect.getDuration() == 1) entity.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 20 + amplifier * 10, 0));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
