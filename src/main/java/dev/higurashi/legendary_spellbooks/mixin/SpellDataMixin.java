package dev.higurashi.legendary_spellbooks.mixin;

import dev.higurashi.legendary_spellbooks.api.spells.BaseDoubleSchoolSpell;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SpellData.class, remap = false)
public abstract class SpellDataMixin {
    @Shadow @Final protected int spellLevel;
    @Shadow @Final protected AbstractSpell spell;

    @Inject(method = "getSpell", at = @At("HEAD"), cancellable = true)
    private void onGetSpell(CallbackInfoReturnable<AbstractSpell> cir) {
        if (this.spell instanceof BaseDoubleSchoolSpell baseDoubleSchoolSpell) {
            cir.setReturnValue(baseDoubleSchoolSpell.newDoubleSchoolSpell(this.spellLevel));
        }
    }
}