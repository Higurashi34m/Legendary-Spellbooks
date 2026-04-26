package dev.higurashi.legendary_spellbooks.mixin.entities.spell;

import dev.higurashi.legendary_spellbooks.common.mixin.helper.ISpellSourceFlag;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationBeamEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationExplosionEntity;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = AnnihilationBeamEntity.class, remap = false)
public abstract class AnnihilationBeamEntityMixin implements ISpellSourceFlag {
    @Shadow public abstract float getDamage();

    @Unique private boolean legendarySpellbooks$isFromSpell = false;
    @Unique private float legendarySpellbooks$damage;

    @ModifyArg(method = "onAddedToWorld", at = @At(value = "INVOKE", target = "Lnet/miauczel/legendary_monsters/entity/AnimatedMonster/Projectile/AnnihilationBeamEntity;spawnExplosions(FID)V"), index = 0)
    private float modifyDamage(float damage) {
        if (legendarySpellbooks$isFromSpell()) return this.getDamage();
        return damage;
    }

    @ModifyArg(method = "spawnEnergyExplosions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", remap = true), index = 0)
    private Entity markExplosionFromSpell(Entity entity) {
        if (legendarySpellbooks$isFromSpell() && entity instanceof AnnihilationExplosionEntity explosion) {
            ((ISpellSourceFlag) explosion).legendarySpellbooks$markSpell();
            ((ISpellSourceFlag) explosion).legendarySpellbooks$setDamage(legendarySpellbooks$getDamage());
        }
        return entity;
    }

    @Override public void legendarySpellbooks$markSpell() { this.legendarySpellbooks$isFromSpell = true; }
    @Override public boolean legendarySpellbooks$isFromSpell() { return this.legendarySpellbooks$isFromSpell; }

    @Override public void legendarySpellbooks$setDamage(float value) { this.legendarySpellbooks$damage = value; }
    @Override public float legendarySpellbooks$getDamage() { return this.legendarySpellbooks$damage; }
}
