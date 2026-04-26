package dev.higurashi.legendary_spellbooks.mixin.entities.spell.summoned;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.AbandonedCrypt.HauntedKnightEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = HauntedKnightEntity.class, remap = false)
public class HauntedKnightEntityMixin {
    @Unique private final HauntedKnightEntity legendary_spellbooks$self = (HauntedKnightEntity) (Object)this;

    @ModifyArg(method = "AreaAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", remap = true), index = 1)
    private float modifyDamage(float originalDamage) {
        if (legendary_spellbooks$self instanceof ISummonedMob) {
            return (float) legendary_spellbooks$self.getAttributeValue(Attributes.ATTACK_DAMAGE);
        }

        return originalDamage;
    }
}
