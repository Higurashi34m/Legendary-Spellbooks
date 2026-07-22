package dev.higurashi.legendary_spellbooks.common.effect.handler;

import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.registry.LSEffectRegistry;
import dev.higurashi.legendary_spellbooks.registry.LSSpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnnihilationResonanceHandler {
    private static final ThreadLocal<Boolean> IS_CRITICAL = ThreadLocal.withInitial(() -> false);

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        boolean isCritical = event.getResult() == Event.Result.ALLOW || (event.getResult() == Event.Result.DEFAULT && event.isVanillaCritical());

        IS_CRITICAL.set(isCritical);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        try {
            if (!IS_CRITICAL.get()) return;

            DamageSource source = event.getSource();
            LivingEntity target = event.getEntity();
            Entity attacker = source.getDirectEntity();

            if (target == null || target.level().isClientSide || source instanceof SpellDamageSource || !(attacker instanceof LivingEntity livingAttacker)) return;
            if (!livingAttacker.isAlive()) return;

            MobEffectInstance effect = livingAttacker.getEffect(LSEffectRegistry.ANNIHILATION_RESONANCE_EFFECT.get());
            if (effect != null) executeAnnihilationBlast(livingAttacker, target, effect);
        } finally {
            IS_CRITICAL.set(false);
        }
    }

    private static void executeAnnihilationBlast(LivingEntity attacker, LivingEntity target, MobEffectInstance effect) {
        if (!(attacker.level() instanceof ServerLevel level)) return;

        float damage = getDamage(effect.getAmplifier(), attacker);
        AbstractSpell spell = LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL.get();

        level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(3.0), entity -> entity != attacker && entity != target && !attacker.isAlliedTo(target)).forEach(enemy -> {
            DamageSources.applyDamage(enemy, damage, spell.getDamageSource(attacker));
        });

        level.playSound(null, target.blockPosition(), ModSounds.ENERGY_EXPLOSION.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        spawnVisualParticles(level, target);
    }

    private static void spawnVisualParticles(ServerLevel level, LivingEntity target) {
        Vec3 pos = target.position();

        ParticleOptions ringData = new Circle.RingData(0.0f, (float) Math.PI / 2.0f, 30, 0.0f, 1.0f, 0.0f, 1.0f, 100.0f, false, Circle.EnumRingBehavior.GROW);
        level.sendParticles(ringData, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);

        ParticleOptions flameData = ModParticles.ANNIHILATION_FLAME_STRIKE.get();
        GeometryUtils.getCirclePoints(pos, 3.0, 7, (float) (Math.PI * 2.0 / 10.0f)).forEach(spawnPos -> level.sendParticles(flameData, spawnPos.x, spawnPos.y + 2.0, spawnPos.z, 1, 0, 0, 0, 0));
    }

    public static float getDamage(int amplifier, @Nullable LivingEntity caster) {
        float multiplier = (caster == null) ? 1.0f : LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL.get().getEntityPowerMultiplier(caster);

        float base = 4.0f;
        float levelScale = 1.0f + (amplifier * 0.75f);

        return base * levelScale * multiplier;
    }
}
