package dev.higurashi.legendary_spellbooks.mixin;

import dev.higurashi.legendary_spellbooks.registry.LSEffectRegistry;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LocalPlayer.class)
public class LocalPlayerMixin {
    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;canElytraFly(Lnet/minecraft/world/entity/LivingEntity;)Z"))
    private boolean tryToStartFallFlying(ItemStack itemStack, LivingEntity entity) {
        if (entity.hasEffect(LSEffectRegistry.POSSESSED_WING_EFFECT.get())) return true;
        return itemStack.canElytraFly(entity);
    }
}
