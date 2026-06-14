package dev.higurashi.legendary_spellbooks.common.entities.spell.projectile;

import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.AnnihilationBombTrail;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class SpellAnnihilationBombEntity extends AbstractMagicProjectile {
    private float hpDamage;
    private int bulletAmount;

    public SpellAnnihilationBombEntity(EntityType<SpellAnnihilationBombEntity> type, Level level) {
        super(type, level);
    }

    public SpellAnnihilationBombEntity(Level level, LivingEntity caster, Vec3 spawnPos, float damage, float hpDamage, int bulletAmount) {
        this(LSEntityRegistry.SPELL_ANNIHILATION_BOMB_ENTITY.get(), level);
        this.setPos(spawnPos);
        this.setOwner(caster);
        this.setDamage(damage);
        this.hpDamage = hpDamage;
        this.bulletAmount = bulletAmount;
    }

    @Override
    public void onHit(HitResult result) {
        super.onHit(result);

        Level level = this.level();
        if (level.isClientSide || !(this.getOwner() instanceof LivingEntity caster)) return;

        applyDamage(caster);
        shootBullet(level, caster, 0.4f);
        shootBullet(level, caster, 0.6f);

        this.discard();
    }

    private void applyDamage(LivingEntity caster) {
        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0), target -> target != caster && !target.isAlliedTo(caster) && !caster.isAlliedTo(target) && target.isAlive() && !target.isInvulnerable());
        targets.forEach(target -> {
            float damage = this.getDamage() + target.getMaxHealth() * this.hpDamage;
            AbstractSpell spell = LSSpellRegistry.ANNIHILATION_BOMB_SPELL.get();
            if (DamageSources.applyDamage(target, damage, spell.getDamageSource(this, caster))) {
                target.setRemainingFireTicks(40);
                target.addEffect(new MobEffectInstance(ModEffects.ANNIHILATION, 60));
            }
        });
    }

    private void shootBullet(Level level, LivingEntity caster, float velocity) {
        float angleStep = (float) (Math.PI * 2.0 / this.bulletAmount);

        for (int i = 0; i < this.bulletAmount / 2; i++) {
            float angle = i * angleStep * 2;
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);

            Vec3 spawnPos = new Vec3(this.getX() + cos, this.getY() + 0.35, this.getZ() + sin);
            SpellSmallAnnihilationBombEntity bullet = new SpellSmallAnnihilationBombEntity(level, spawnPos, caster, this.getDamage() / 2, this.hpDamage);
            bullet.shoot(cos, 0.5 + caster.getRandom().nextDouble() * 0.3, sin, velocity, 1.0f);
            level.addFreshEntity(bullet);
        }
    }

    @Override
    public void trailParticles() {
        if (this.tickCount % 3 == 0) {
            this.level().addParticle(ModParticles.BIG_ANNIHILATION_FLAME.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.025, 0.0);
        }

        double x = this.getX() + 1.5 * (this.random.nextDouble() - 0.5);
        double y = this.getY() + 1.5 * (this.random.nextDouble() - 0.5);
        double z = this.getZ() + 1.5 * (this.random.nextDouble() - 0.5);
        this.level().addParticle(new AnnihilationBombTrail.OrbData(0.0f, 0.765f, 0.0f, 0.5f, 0.8f, this.getId()), x, y, z, 0.0, 0.0, 0.0);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        CameraShakeEntity.cameraShake(this.level(), new Vec3(x, y, z), 10.0f, 0.05f, 20, 5);
        MagicManager.spawnParticles(this.level(), ModParticles.GROUND_ANNIHILATION_NUKE.get(), x, y + 2.0, z, 1, 0.0, 0.0, 0.0, 0.0, false);
        MagicManager.spawnParticles(this.level(), ModParticles.ANNIHILATION_FLAME_STRIKE.get(), x, y + 2.0, z, 1, 0.0, 0.0, 0.0, 0.0, false);
    }

    @Override
    public float getSpeed() {
        return 1;
    }

    @Override
    public Optional<Holder<SoundEvent>> getImpactSound() {
        return Optional.of(ModSounds.DIMENSIONAL_BOMB_EXPLODE);
    }
}
