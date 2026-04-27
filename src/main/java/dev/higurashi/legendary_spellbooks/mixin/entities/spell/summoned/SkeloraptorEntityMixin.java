package dev.higurashi.legendary_spellbooks.mixin.entities.spell.summoned;

import dev.higurashi.legendary_spellbooks.common.entities.spell.summoned.SummonedSkeloraptorEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.Pets.SkeloraptorEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = SkeloraptorEntity.class, remap = false)
public class SkeloraptorEntityMixin {
    @ModifyArg(method = "spawnShockwaves", at = @At(value = "INVOKE", target = "Lnet/miauczel/legendary_monsters/entity/AnimatedMonster/Projectile/ShockwaveEntity;<init>(Lnet/minecraft/world/level/Level;DDDFILnet/minecraft/world/entity/LivingEntity;ZF)V"), index = 8)
    private float spawnShockwave(float value) {
        SkeloraptorEntity self = (SkeloraptorEntity) (Object) this;
        if (self instanceof SummonedSkeloraptorEntity) return (float) (self.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.45f);
        return value;
    }

    @ModifyArg(method = "UpdateWithAttack", at = @At(value = "INVOKE", target = "Lnet/miauczel/legendary_monsters/entity/AnimatedMonster/Effect/CameraShakeEntity;cameraShake(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;FFII)V"), index = 3)
    private float cameraShake(float value) {
        SkeloraptorEntity self = (SkeloraptorEntity) (Object) this;
        if (self instanceof SummonedSkeloraptorEntity) return 0.01f;
        return value;
    }
}
