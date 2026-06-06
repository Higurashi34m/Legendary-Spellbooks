package dev.higurashi.legendary_spellbooks.mixin;

import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
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