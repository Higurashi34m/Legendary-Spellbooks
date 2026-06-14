package dev.higurashi.legendary_spellbooks.common.entities.spell.projectile;

import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.IceSpikeEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SpellIceSpikeEntity extends IceSpikeEntity {
    private float damage;

    public SpellIceSpikeEntity(EntityType<SpellIceSpikeEntity> type, Level level) {
        super(type, level);
    }

    public SpellIceSpikeEntity(Level level, Vec3 position, float rot, int warmup, LivingEntity caster, float damage) {
        super(level, position.x, position.y, position.z, rot, warmup, caster);
        this.damage = damage;
    }

    public float getDamage() {
        return this.damage;
    }
}
