package dev.higurashi.legendary_spellbooks.mixin.entities.spell;

import dev.higurashi.legendary_spellbooks.common.mixin.helper.ISpellSourceFlag;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationPortalEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = AnnihilationPortalEntity.class, remap = false)
public abstract class AnnihilationPortalEntityMixin implements ISpellSourceFlag {
    @Shadow public abstract float getDamage();
    @Unique private boolean legendarySpellbooks$isFromSpell = false;

    @ModifyArg(method = "tick", remap = true, at = @At(value = "INVOKE", target = "Lnet/miauczel/legendary_monsters/entity/AnimatedMonster/Projectile/AnnihilationPortalEntity;flameRadagonShockwave(FIFIFFFZ)V"), index = 6)
    private float changeDamageShockwave(float value) {
        if (legendarySpellbooks$isFromSpell()) return getDamage() * 1.25f;
        else return value;
    }

    @Override public void legendarySpellbooks$markSpell() { this.legendarySpellbooks$isFromSpell = true; }
    @Override public boolean legendarySpellbooks$isFromSpell() { return legendarySpellbooks$isFromSpell; }

    @Override public void legendarySpellbooks$setDamage(float value) {}
    @Override public float legendarySpellbooks$getDamage() { return 0; }
}
