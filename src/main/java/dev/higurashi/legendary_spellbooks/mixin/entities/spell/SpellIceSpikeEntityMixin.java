package dev.higurashi.legendary_spellbooks.mixin.entities.spell;


import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.SpellIceSpikeEntity;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SpellIceSpikeEntity.class, remap = false)
public class SpellIceSpikeEntityMixin extends IceSpikeEntityMixin {
    @Override
    @SoftOverride
    protected void legendary_spellbooks$newDamage(LivingEntity target, CallbackInfo ci) {
        SpellIceSpikeEntity self = (SpellIceSpikeEntity) (Object) this;
        LivingEntity caster = self.getCaster();

        if (!target.isAlive() || target.isInvulnerable() || target == caster) return;
        if (caster != null && caster.isAlliedTo(target)) return;
        if (self.tickCount % 5 != 0) return;

        if (!self.level().isClientSide) {
            target.addEffect(new MobEffectInstance(ModEffects.FREEZE.get(), 60, 0));
        }

        target.hurt(new DamageSource(self.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), caster), self.getDamage());

        ci.cancel();
    }
}
