package dev.higurashi.legendary_spellbooks.mixin;

import dev.higurashi.legendary_spellbooks.registry.LSEffectRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public class PlayerMixin {
    @Redirect(method = "tryToStartFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;canElytraFly(Lnet/minecraft/world/entity/LivingEntity;)Z"))
    private boolean tryToStartFallFlying(ItemStack itemStack, LivingEntity entity) {
        if (entity.hasEffect(LSEffectRegistry.POSSESSED_WING_EFFECT.get())) return true;
        return itemStack.canElytraFly(entity);
    }
}
