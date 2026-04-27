package dev.higurashi.legendary_spellbooks.mixin.entities.spell.summoned;

import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Pets.SkeloraptorEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SkeloraptorEntity.class, remap = false)
public interface SkeloraptorEntityAccessor {
    @Accessor("regenerationCooldown")
    void setRegenerationCooldown(int value);
}
