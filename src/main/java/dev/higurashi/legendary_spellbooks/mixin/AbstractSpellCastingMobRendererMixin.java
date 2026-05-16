package dev.higurashi.legendary_spellbooks.mixin;

import dev.higurashi.legendary_spellbooks.client.renderer.spell.animation.AnnihilationBombChargeGeoLayer;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobModel;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSpellCastingMobRenderer.class)
public class AbstractSpellCastingMobRendererMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(EntityRendererProvider.Context rendererProvider, AbstractSpellCastingMobModel model, CallbackInfo ci) {
        AbstractSpellCastingMobRenderer self = (AbstractSpellCastingMobRenderer) (Object) this;
        self.addRenderLayer(new AnnihilationBombChargeGeoLayer(self));
    }
}
