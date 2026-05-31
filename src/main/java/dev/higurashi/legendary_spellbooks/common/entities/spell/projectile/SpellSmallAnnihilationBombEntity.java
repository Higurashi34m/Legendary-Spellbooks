package dev.higurashi.legendary_spellbooks.common.entities.spell.projectile;

import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import net.miauczel.legendary_monsters.damagetype.ModDamageTypes;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SmallAnnihilationBombEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class SpellSmallAnnihilationBombEntity extends SmallAnnihilationBombEntity implements AntiMagicSusceptible {
    private float hpDamage;

    public SpellSmallAnnihilationBombEntity(EntityType<SmallAnnihilationBombEntity> type, Level level) {
        super(type, level);
    }

    public SpellSmallAnnihilationBombEntity(Level level, Vec3 spawnPos, LivingEntity caster, float damage, float hpDamage) {
        this(LSEntityRegistry.SPELL_SMALL_ANNIHILATION_BOMB.get(), level);
        this.setPos(spawnPos);
        this.setOwner(caster);
        this.setDamage(damage);
        this.hpDamage = hpDamage;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (this.level().isClientSide) return;

        Entity target = result.getEntity();
        Entity caster = this.getOwner();
        if (!(caster instanceof LivingEntity livingCaster) || !(target instanceof LivingEntity livingTarget)) return;

        if (target == caster || target.isAlliedTo(caster) || caster.isAlliedTo(target) || target.isInvulnerable() || !target.isAlive()) return;

        float damage = this.getDamage() + livingTarget.getMaxHealth() * this.hpDamage;
        DamageSources.applyDamage(target, damage, ModDamageTypes.causeAnnihilationDamage(this, livingCaster));
    }

    @Override
    public void onAntiMagic(MagicData magicData) {
        this.discard();
    }
}
