package dev.higurashi.legendary_spellbooks.mixin.entities.spell.summoned;

import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Pets.SkeloraptorEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = SkeloraptorEntity.class, remap = false)
public interface SkeloraptorEntityAccessor {
    @Accessor("regenerationCooldown")
    void setRegenerationCooldown(int value);

    @Invoker("AreaAttack")
    void areaAttack(float range, float height, float arc, float damage, int shieldBreakTicks, boolean stun, float knockback, boolean konckback, float length, boolean launch);
}
