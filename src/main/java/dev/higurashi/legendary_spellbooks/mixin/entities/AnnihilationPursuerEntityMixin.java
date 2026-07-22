package dev.higurashi.legendary_spellbooks.mixin.entities;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import dev.higurashi.legendary_spellbooks.common.entity.spell.summoned.SummonedAnnihilationPursuerEntity;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.damagetype.ModDamageTypes;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.SpaceStation.Flameborn.AnnihilationPursuer.AnnihilationPursuerEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.OriginClasses.IAnimatedMonster;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationFlameStrike;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AnnihilationPursuerEntity.class, remap = false)
public class AnnihilationPursuerEntityMixin {
    @ModifyArg(method = "spawnFlames", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", remap = true), index = 0)
    private Entity modifyDamage(Entity entity) {
        AnnihilationPursuerEntity self = (AnnihilationPursuerEntity) (Object)this;

        if (self instanceof ISummonedMob) {
            ((AnnihilationFlameStrike) entity).setDamage((float) self.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.75f);
        }
        return entity;
    }

    @Inject(method = "SideGrab", at = @At(value = "HEAD"), cancellable = true)
    private void sideGrab(float range, float height, float arc, float boxOffset, float damage, int brokenShieldTicks, SoundEvent soundEvent, float pitch, CallbackInfo ci) {
        ci.cancel();

        AnnihilationPursuerEntity self = (AnnihilationPursuerEntity) (Object)this;

        if (!self.level().isClientSide) {
            boolean hitAny = false;

            for(LivingEntity entityHit : self.getEntityLivingBaseNearby(range, height, range, range)) {
                float entityRelativeAngle = legendary_spellbook$getEntityRelativeAngle(boxOffset, entityHit);
                float entityHitDistance = (float)Math.sqrt((entityHit.getZ() - self.getZ()) * (entityHit.getZ() - self.getZ()) + (entityHit.getX() - self.getX()) * (entityHit.getX() - self.getX()));
                if ((entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F || entityRelativeAngle >= 360.0F - arc / 2.0F || entityRelativeAngle <= -360.0F + arc / 2.0F) && !(entityHit instanceof AnnihilationPursuerEntity && !(entityHit instanceof SummonedAnnihilationPursuerEntity)) && !self.isAlliedTo(entityHit) && entityHit != self) {
                    hitAny = true;
                    boolean entityHitisTarget = entityHit == self.target();
                    DamageSource damageSource = new DamageSource(self.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), self);
                    if (entityHit.isDamageSourceBlocked(damageSource)) {
                        self.succedGrabbing = false;
                    }

                    boolean flag = entityHit.hurt(damageSource, (float)((double)damage * ModConfig.MOB_CONFIG.AnnihilationPursuerDamageMutliplier.get()));
                    if (flag) {
                        self.playSound(soundEvent, 1.0F, pitch);
                        boolean mounted = entityHitisTarget && entityHit.startRiding(self, true);
                        if (mounted) {
                            entityHit.setShiftKeyDown(false);
                            self.succedGrabbing = true;
                        } else {
                            self.succedGrabbing = false;
                        }
                    } else {
                        self.succedGrabbing = false;
                    }

                    if (entityHit instanceof Player && entityHit.isBlocking() && brokenShieldTicks > 0) {
                        IAnimatedMonster.disableShield(entityHit, brokenShieldTicks);
                    }
                }
            }

            if (!hitAny) {
                self.succedGrabbing = false;
            }
        }
    }

    @Inject(method = "SideAreaAttack", at = @At(value = "HEAD"), cancellable = true)
    private void sideAreaAttack(float range, float height, float arc, float boxOffset, float forwardOffset, float damage, int brokenShieldTicks, boolean canStun, boolean canlaunch, SoundEvent soundEvent, float pitch, CallbackInfo ci) {
        ci.cancel();

        AnnihilationPursuerEntity self = (AnnihilationPursuerEntity) (Object)this;

        double theta = Math.toRadians(self.yBodyRot) + (Math.PI / 2D);
        double forwardX = Math.cos(theta) * (double)forwardOffset;
        double forwardZ = Math.sin(theta) * (double)forwardOffset;

        for(LivingEntity entityHit : self.getEntityLivingBaseNearby(range, height, range, range)) {
            double dx = entityHit.getX() - (self.getX() + forwardX);
            double dz = entityHit.getZ() - (self.getZ() + forwardZ);
            float entityHitAngle = (float)((Math.toDegrees(Math.atan2(dz, dx)) - (double)90.0F) % (double)360.0F);
            if (entityHitAngle < 0.0F) {
                entityHitAngle += 360.0F;
            }

            float entityAttackingAngle = (self.yBodyRot - boxOffset) % 360.0F;
            if (entityAttackingAngle < 0.0F) {
                entityAttackingAngle += 360.0F;
            }

            float entityHitDistance = (float)Math.sqrt(dx * dx + dz * dz);
            float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
            if (entityHitDistance <= range && (entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F || entityRelativeAngle >= 360.0F - arc / 2.0F || entityRelativeAngle <= -360.0F + arc / 2.0F) && !(entityHit instanceof AnnihilationPursuerEntity && !(entityHit instanceof SummonedAnnihilationPursuerEntity)) && !self.isAlliedTo(entityHit) &&  entityHit != self) {
                boolean flag = entityHit.hurt(self.getAttackState() == 15 ? ModDamageTypes.causeAnnihilationDamage(self, self) : self.damageSources().mobAttack(self), (float)((double)damage * ModConfig.MOB_CONFIG.AnnihilationPursuerDamageMutliplier.get()));
                if (flag) {
                    self.hasHit = true;
                    if (canlaunch) {
                        if (self.getAttackState() == 8) {
                            self.launch(entityHit, true);
                        } else {
                            self.launch(entityHit, true);
                        }
                    }

                    self.playSound(soundEvent, 1.0F, pitch);
                    if (canStun) {
                        entityHit.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 40, 1));
                    }
                }

                if (entityHit instanceof Player && entityHit.isBlocking() && brokenShieldTicks > 0) {
                    IAnimatedMonster.disableShield(entityHit, brokenShieldTicks);
                }
            }
        }
    }

    @Inject(method = "nextSideAreaAttack", at = @At(value = "HEAD"), cancellable = true)
    public void NextSideAreaAttack(float range, float height, float arc, float boxOffset, float damage, int brokenShieldTicks, SoundEvent soundEvent, float pitch, CallbackInfo ci) {
        ci.cancel();

        AnnihilationPursuerEntity self = (AnnihilationPursuerEntity) (Object)this;

        if (!self.level().isClientSide) {
            boolean hitAny = false;

            for(LivingEntity entityHit : self.getEntityLivingBaseNearby(range, height, range, range)) {
                float entityHitAngle = (float)((Math.atan2(entityHit.getZ() - self.getZ(), entityHit.getX() - self.getX()) * (180D / Math.PI) - (double)90.0F) % (double)360.0F);
                float entityAttackingAngle = (self.yBodyRot - boxOffset) % 360.0F;
                if (entityHitAngle < 0.0F) {
                    entityHitAngle += 360.0F;
                }

                if (entityAttackingAngle < 0.0F) {
                    entityAttackingAngle += 360.0F;
                }

                float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
                float entityHitDistance = (float)Math.sqrt((entityHit.getZ() - self.getZ()) * (entityHit.getZ() - self.getZ()) + (entityHit.getX() - self.getX()) * (entityHit.getX() - self.getX()));
                if ((entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F || entityRelativeAngle >= 360.0F - arc / 2.0F || entityRelativeAngle <= -360.0F + arc / 2.0F) && !self.isAlliedTo(entityHit) && !(entityHit instanceof AnnihilationPursuerEntity && !(entityHit instanceof SummonedAnnihilationPursuerEntity)) && entityHit != self) {
                    hitAny = true;
                    boolean flag = entityHit.hurt(self.damageSources().mobAttack(self), (float)((double)damage * ModConfig.MOB_CONFIG.AnnihilationPursuerDamageMutliplier.get()));
                    if (flag) {
                        entityHit.setShiftKeyDown(false);
                        self.playSound(soundEvent, 1.0F, pitch);
                        self.hasHit = true;
                    } else {
                        self.hasHit = false;
                    }

                    if (entityHit instanceof Player && entityHit.isBlocking() && brokenShieldTicks > 0) {
                        IAnimatedMonster.disableShield(entityHit, brokenShieldTicks);
                    }
                }
            }

            if (!hitAny) {
                self.hasHit = false;
            }

        }
    }

    @Unique
    private float legendary_spellbook$getEntityRelativeAngle(float boxOffset, LivingEntity entityHit) {
        AnnihilationPursuerEntity self = (AnnihilationPursuerEntity) (Object)this;

        float entityHitAngle = (float)((Math.atan2(entityHit.getZ() - self.getZ(), entityHit.getX() - self.getX()) * (180D / Math.PI) - (double)90.0F) % (double)360.0F);
        float entityAttackingAngle = (self.yBodyRot - boxOffset) % 360.0F;
        if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
        }

        if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
        }

        return entityHitAngle - entityAttackingAngle;
    }
}
