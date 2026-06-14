package dev.higurashi.legendary_spellbooks.common.effects;

import dev.higurashi.legendary_spellbooks.common.spells.annihilation.FlamebornDriftSpell;
import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import io.redspace.ironsspellbooks.mixin.LivingEntityAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class FlamebornDriftEffect extends MagicMobEffect {
    public FlamebornDriftEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x40BA8D);
    }

    @Override
    public boolean applyEffectTick(LivingEntity caster, int amplifier) {
        Level level = caster.level();
        boolean hit = false;

        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, caster.getBoundingBox().inflate(1.0))) {
            AbstractSpell spell = LSSpellRegistry.FLAMEBORN_DRIFT_SPELL.get();
            if (target != caster && !caster.isAlliedTo(target)) {
                float damage = spell.getSpellPower(amplifier, caster) + target.getMaxHealth() * (FlamebornDriftSpell.getHPDamage(amplifier) * 0.01f);
                DamageSources.applyDamage(target, damage, spell.getDamageSource(caster).setFireTicks(60));
                hit = true;
            }
        }

        if (caster.horizontalCollision || hit) {
            caster.setDeltaMovement(caster.getDeltaMovement().normalize().scale(-0.5));
            caster.hurtMarked = true;
            level.playSound(null, caster, SoundEvents.ANVIL_LAND, caster.getSoundSource(), 1.0f, 1.0f);
            caster.removeEffect(LSEffectRegistry.FLAMEBORN_DASH_EFFECT);
        }
        caster.fallDistance = 0;
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public void onEffectAdded(LivingEntity pLivingEntity, int pAmplifier) {
        super.onEffectAdded(pLivingEntity, pAmplifier);
        ((LivingEntityAccessor) pLivingEntity).setLivingEntityFlagInvoker(4, true);
    }

    @Override
    public void onEffectRemoved(LivingEntity pLivingEntity, int pAmplifier) {
        super.onEffectRemoved(pLivingEntity, pAmplifier);
        ((LivingEntityAccessor) pLivingEntity).setLivingEntityFlagInvoker(4, false);
    }
}
