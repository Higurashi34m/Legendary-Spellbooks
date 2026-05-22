package dev.higurashi.legendary_spellbooks.mixin;

import dev.higurashi.legendary_spellbooks.registries.LSAttributeRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Inject(method = "updateFallFlying", at = @At("TAIL"))
    private void onUpdateFallFlying(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (!self.onGround() && !self.isPassenger() && !self.hasEffect(MobEffects.LEVITATION) && self.hasEffect(LSEffectRegistry.POSSESSED_WING_EFFECT.get())) {
            int i = self.tickCount;
            if (!self.level().isClientSide && i % 10 == 0) self.gameEvent(GameEvent.ELYTRA_GLIDE);
            self.setSharedFlag(7, true);
        }
    }
}