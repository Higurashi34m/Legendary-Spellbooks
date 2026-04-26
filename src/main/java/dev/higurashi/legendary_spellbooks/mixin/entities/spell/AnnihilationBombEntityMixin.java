package dev.higurashi.legendary_spellbooks.mixin.entities.spell;

import dev.higurashi.legendary_spellbooks.common.mixin.helper.ISpellSourceFlag;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationBombEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = AnnihilationBombEntity.class, remap = false)
public class AnnihilationBombEntityMixin implements ISpellSourceFlag {
    @Unique private boolean legendarySpellbooks$isFromSpell = false;
    @Unique private float legendarySpellbooks$damage;

    @ModifyArg(method = "onHit", remap = true, at = @At(value = "INVOKE", target = "Lnet/miauczel/legendary_monsters/entity/AnimatedMonster/Projectile/SmallAnnihilationBombEntity;<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;F)V"), index = 3)
    private float modifyDamage(float damage) {
        if (legendarySpellbooks$isFromSpell()) return legendarySpellbooks$getDamage();
        return damage;
    }

    @Override public void legendarySpellbooks$markSpell() { this.legendarySpellbooks$isFromSpell = true; }
    @Override public boolean legendarySpellbooks$isFromSpell() { return this.legendarySpellbooks$isFromSpell; }

    @Override public void legendarySpellbooks$setDamage(float value) { this.legendarySpellbooks$damage = value; }
    @Override public float legendarySpellbooks$getDamage() { return this.legendarySpellbooks$damage; }
}
