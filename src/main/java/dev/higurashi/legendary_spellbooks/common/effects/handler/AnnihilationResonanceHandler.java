package dev.higurashi.legendary_spellbooks.common.effects.handler;

import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.registries.LSEffectRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnnihilationResonanceHandler {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        LivingEntity target = event.getEntity();

        if (target.level().isClientSide || !(source.getDirectEntity() instanceof Player attacker) || source instanceof SpellDamageSource) return;
        if (!isCriticalLike(attacker)) return;

        MobEffectInstance effect = attacker.getEffect(LSEffectRegistry.ANNIHILATION_RESONANCE_EFFECT.get());
        if (effect != null) executeAnnihilationBlast(attacker, target, effect);
    }

    private static boolean isCriticalLike(Player player) {
        return player.fallDistance > 0.0F && !player.onGround() && !player.onClimbable() && !player.isInWater();
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

        float base = 2.0f;
        float levelScale = 1.0f + (amplifier * 0.5f);

        return base * levelScale * multiplier;
    }
}
