package dev.higurashi.legendary_spellbooks.mixin;

import dev.higurashi.legendary_spellbooks.registries.LSAttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D", at = @At("RETURN"), cancellable = true)
    private void onGetAttributeValue(Attribute attribute, CallbackInfoReturnable<Double> cir) {
        if (attribute == LSAttributeRegistry.ANNIHILATION_SPELL_POWER.get()) {
            LivingEntity entity = (LivingEntity) (Object) this;

            double fireVal = entity.getAttributeValue(AttributeRegistry.FIRE_SPELL_POWER.get());
            double enderVal = entity.getAttributeValue(AttributeRegistry.ENDER_SPELL_POWER.get());

            double fireBonus = Math.max(0, fireVal - 1.0);
            double enderBonus = Math.max(0, enderVal - 1.0);

            cir.setReturnValue(cir.getReturnValue() + fireBonus + enderBonus);
        }
    }
}