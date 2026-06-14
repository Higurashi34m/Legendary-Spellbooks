package dev.higurashi.legendary_spellbooks.mixin.entities.spell;

import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.IceSpikeEntity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = IceSpikeEntity.class, remap = false)
public class IceSpikeEntityMixin {
    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    protected void onDamage(LivingEntity target, CallbackInfo ci) {
        this.legendary_spellbooks$newDamage(target, ci);
    }

    @Unique
    protected void legendary_spellbooks$newDamage(LivingEntity target, CallbackInfo ci) {}
}
