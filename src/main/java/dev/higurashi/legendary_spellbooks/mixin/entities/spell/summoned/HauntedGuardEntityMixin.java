package dev.higurashi.legendary_spellbooks.mixin.entities.spell.summoned;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.CollapsedKingdom.HauntedGuardEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = HauntedGuardEntity.class, remap = false)
public class HauntedGuardEntityMixin {
    @ModifyArg(method = {"AreaAttack"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", remap = true), index = 1)
    private float modifyDamage(float originalDamage) {
        HauntedGuardEntity self = (HauntedGuardEntity) (Object)this;

        if (self instanceof ISummonedMob) {
            return (float) self.getAttributeValue(Attributes.ATTACK_DAMAGE);
        }

        return originalDamage;
    }

    @ModifyArg(method = "StraightLineAreaAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", remap = true), index = 1)
    private float modifyStraightLine(float originalDamage) {
        HauntedGuardEntity self = (HauntedGuardEntity) (Object)this;
        if (self instanceof ISummonedMob) {
            return (float) self.getAttributeValue(Attributes.ATTACK_DAMAGE);
        }
        return originalDamage;
    }
}
