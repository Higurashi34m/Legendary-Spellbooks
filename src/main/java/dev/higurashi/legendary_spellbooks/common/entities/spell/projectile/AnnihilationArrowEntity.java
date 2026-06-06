package dev.higurashi.legendary_spellbooks.common.entities.spell.projectile;

import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.Particle.custom.AnnihilationBombTrail;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.Particle.custom.LightningParticle;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Supplier;

public class AnnihilationArrowEntity extends AbstractMagicProjectile {
    private float hpDamage;

    public AnnihilationArrowEntity(EntityType<AnnihilationArrowEntity> type, Level level) {
        super(type, level);
    }

    public AnnihilationArrowEntity(Level level, LivingEntity caster, Vec3 spawnPos, float damage, float hpDamage) {
        this(LSEntityRegistry.ANNIHILATION_ARROW.get(), level);
        this.setPos(spawnPos);
        this.setOwner(caster);
        this.setDamage(damage);
        this.hpDamage = hpDamage;
    }

    @Override
    public void onHit(HitResult result) {
        super.onHit(result);
        if (this.level().isClientSide()) return;
        if (!(this.getOwner() instanceof LivingEntity caster)) return;

        Vec3 baseSpawn = result.getLocation();
        Vec3 endRay = baseSpawn.add(0.0, -3.0, 0.0);

        BlockHitResult blockResult = this.level().clip(new ClipContext(baseSpawn, endRay, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (blockResult.getType() == BlockHitResult.Type.BLOCK) {
            Vec3 hitPos = blockResult.getLocation();

            SpellAnnihilationExplosionEntity explosion = new SpellAnnihilationExplosionEntity(this.level(), caster, this.getDamage(), this.hpDamage, 4, 0);
            explosion.setPos(hitPos);
            explosion.setYRot(this.getYRot());
            this.level().addFreshEntity(explosion);
        }

        this.discardHelper(result);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (this.level().isClientSide()) return;
        if (!(result.getEntity() instanceof LivingEntity target) || !(this.getOwner() instanceof LivingEntity caster)) return;

        float damage = this.getDamage() + target.getMaxHealth() * this.hpDamage;
        AbstractSpell spell = LSSpellRegistry.ANNIHILATION_ARROW_SPELL.get();
        if (DamageSources.applyDamage(target, damage, spell.getDamageSource(this, caster))) {
            target.setSecondsOnFire(2);
            target.addEffect(new MobEffectInstance(ModEffects.ANNIHILATION.get(), 60));
        }

        ServerLevel level = (ServerLevel) this.level();
        RandomSource random = target.getRandom();
        LightningParticle.OrbData lightning = new LightningParticle.OrbData(25, 255, 0);
        level.sendParticles(lightning, target.getRandomX(0.5), target.getY() + target.getBbHeight() / 2, target.getRandomZ(0.5), 15 + random.nextInt(20), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0.5);
    }

    @Override
    public void trailParticles() {
        if (this.tickCount % 3 == 0) {
            this.level().addParticle(ModParticles.BIG_ANNIHILATION_FLAME.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.025, 0.0);
        }

        double x = this.getRandomX(0.5);
        double y = this.getRandomY() - 0.25;
        double z = this.getRandomZ(0.5);
        this.level().addParticle(new AnnihilationBombTrail.OrbData(0.0f, 0.765f, 0.0f, 0.5f, 0.8f, this.getId()), x, y, z, 0.0, 0.0, 0.0);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        Circle.RingData ringData = new Circle.RingData(0.0f, (float) Math.toRadians(90.0), 10, 0.0f, 1.0f, 0.0f, 1.0f, 40, false, Circle.EnumRingBehavior.GROW);
        MagicManager.spawnParticles(this.level(), ringData, x, y + 0.1, z, 1, 0.0, 0.0, 0.0, 0.0, false);
    }

    @Override
    public float getSpeed() {
        return 2;
    }

    @Override
    public Optional<Supplier<SoundEvent>> getImpactSound() {
        return Optional.of(ModSounds.DIMENSIONAL_BOMB_EXPLODE_SMALL);
    }

    @Override
    protected void doImpactSound(Supplier<SoundEvent> sound) {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), sound.get(), SoundSource.NEUTRAL, 0.75f, 0.9f + Utils.random.nextFloat() * 0.2f);
    }
}
