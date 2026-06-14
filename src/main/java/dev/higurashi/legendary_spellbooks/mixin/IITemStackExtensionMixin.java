package dev.higurashi.legendary_spellbooks.mixin;

import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = IItemStackExtension.class, remap = false)
public interface IITemStackExtensionMixin {
    @Inject(method = "canElytraFly", at = @At(value = "RETURN"), cancellable = true)
    default void canElytraFly(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity.hasEffect(LSEffectRegistry.POSSESSED_WING_EFFECT)) cir.setReturnValue(true);
    }

    @Inject(method = "elytraFlightTick", at = @At(value = "RETURN"), cancellable = true)
    default void elytraFlightTick(LivingEntity entity, int flightTicks, CallbackInfoReturnable<Boolean> cir) {
        if (entity.hasEffect(LSEffectRegistry.POSSESSED_WING_EFFECT)) cir.setReturnValue(true);
    }
}