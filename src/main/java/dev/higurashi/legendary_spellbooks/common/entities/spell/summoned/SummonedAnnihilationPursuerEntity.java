package dev.higurashi.legendary_spellbooks.common.entities.spell.summoned;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import dev.higurashi.legendary_spellbooks.common.mixin.helper.ISpellSourceFlag;
import dev.higurashi.legendary_spellbooks.common.spells.ender.ReleaseRiftwalkerPredatorSpell;
import dev.higurashi.legendary_spellbooks.registries.LSEntityRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import net.miauczel.legendary_monsters.damagetype.ModDamageTypes;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.SpaceStation.Flameborn.AnnihilationPursuer.AnnihilationPursuerEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Mobs.SpaceStation.Flameborn.AnnihilationPursuer.goals.*;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationExplosionEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SmallAnnihilationBombEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.entity.ai.goal.IAttackGoal;
import net.miauczel.legendary_monsters.entity.ai.goal.IMoveGoal;
import net.miauczel.legendary_monsters.entity.ai.goal.IStateGoal;
import net.miauczel.legendary_monsters.util.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class SummonedAnnihilationPursuerEntity extends AnnihilationPursuerEntity implements ISummonedMob {
    public SummonedAnnihilationPursuerEntity(EntityType<? extends AnnihilationPursuerEntity> entity, Level level) {
        super(entity, level);
        xpReward = 0;
    }

    public SummonedAnnihilationPursuerEntity(Level level, LivingEntity owner) {
        super(LSEntityRegistry.SUMMONED_ANNIHILATION_PURSUER_ENTITY.get(), level);
        SummonManager.setOwner(this, owner);
    }

    @Override
    public void setupAttributes(int spellLevel, float spellPower) {
        int hp = ReleaseRiftwalkerPredatorSpell.getHealth(spellLevel);

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(hp);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(ReleaseRiftwalkerPredatorSpell.getDamage(spellPower));
        this.setHealth(hp);
    }

    @Override
    public Entity getSummoner() {
        return ISummonedMob.super.getSummoner();
    }

    @Override
    public boolean isAlliedTo(Entity target) {
        Entity owner = getSummoner();
        return owner == target || this.isAlliedHelper(target);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(1, new BuckshotGoal(this, 0, 21, 0, MathUtils.toSeconds(1.42F), MathUtils.toSeconds(1.42F), 15.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && getRandom().nextFloat() < 0.32f && buckshot_cooldown <= 0;
            }
        });

        this.goalSelector.addGoal(0, new IStateGoal(this, 22, 22, 0, MathUtils.toSeconds(1.08F), 10) {
            @Override
            public void stop() {
                buckshot_cooldown = 40;
                super.stop();
            }
        });

        this.goalSelector.addGoal(0, new IStateGoal(this, 23, 23, 0, MathUtils.toSeconds(1.33F), MathUtils.toSeconds(1.33F)) {
            @Override
            public void stop() {
                buckshot_cooldown = 40;
                super.stop();
            }
        });

        this.goalSelector.addGoal(1, new SwordTeleportSlamGoal(this, 0, 13, 0, 70, 25, 13.0F, 3.0F) {
            @Override
            public void stop() {
                teleport_slam_cooldown = 100;
                super.stop();
            }

            @Override
            public boolean canUse() {
                return super.canUse() && getRandom().nextFloat() < 0.16f && teleport_slam_cooldown <= 0;
            }
        });

        this.goalSelector.addGoal(1, new TeleportChaseGoal(this, 0, 18, 0, 21, 25, 20.0F, 6.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && getRandom().nextFloat() < 0.32f;
            }
        });

        this.goalSelector.addGoal(0, new StabFinisherGrabStateGoal(this, 19, 19, 0, 38, 35) {});

        this.goalSelector.addGoal(1, new SingleSlashFromGoal(this, 0, 8, 10, 29, 29, 7.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && getRandom().nextFloat() < 0.16f;
            }
        });

        this.goalSelector.addGoal(0, new IStateGoal(this, 9, 9, 0, 48, 25) {
            @Override
            public void stop() {
                shield_stun_cooldown = 100;
                super.stop();
            }
        });

        this.goalSelector.addGoal(0, new IStateGoal(this, 10, 10, 0, 19, 0) {});

        this.goalSelector.addGoal(1, new SingleSlashGoal(this, 0, 5, 12, 28, 28, 7.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && getRandom().nextFloat() < 0.16f;
            }
        });

        this.goalSelector.addGoal(0, new IStateGoal(this, 11, 11, 0, 37, 30) {});
        this.goalSelector.addGoal(0, new IStateGoal(this, 12, 12, 0, 21, 0) {});

        this.goalSelector.addGoal(0, new StabFinisherGrabStateGoal(this, 14, 14, 0, 39, 30) {});

        this.goalSelector.addGoal(0, new IStateGoal(this, 15, 15, 0, 112, 0) {
            @Override
            public void stop() {
                stab_finisher_cooldown = 200;
                super.stop();
            }
        });

        this.goalSelector.addGoal(0, new IStateGoal(this, 16, 16, 0, 23, 0) {
            @Override
            public void stop() {
                stab_finisher_cooldown = 200;
                super.stop();
            }
        });

        this.goalSelector.addGoal(1, new StompComboGoal(this, 0, 2, 0, 46, 34, 7.0F) {
            @Override
            public boolean canUse() {
                return super.canUse() && getRandom().nextFloat() < 0.16f && stomp_combo_cooldown <= 0;
            }
        });

        this.goalSelector.addGoal(0, new IStateGoal(this, 3, 3, 0, 22, 0) {
            @Override
            public void stop() {
                stomp_combo_cooldown = 100;
                super.stop();
            }
        });

        this.goalSelector.addGoal(0, new IStateGoal(this, 4, 4, 0, 28, 0) {
            @Override
            public void stop() {
                stomp_combo_cooldown = 100;
                super.stop();
            }
        });

        this.goalSelector.addGoal(1, new IStateGoal(this, 6, 6, 0, 0, 0) {
            public void tick() {
                setDeltaMovement(0.0f, getDeltaMovement().y, 0.0f);
            }
        });

        this.goalSelector.addGoal(0, new IAttackGoal(this, 6, 7, 0, 30, 0, 10.0F));
        this.goalSelector.addGoal(1, new IStateGoal(this, 17, 17, 0, 85, 0) {});

        this.goalSelector.addGoal(2, new IMoveGoal(this, false, 3.0f));

        this.defaultGoalSelector(this);
        this.defaultTargetSelector(this);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (shouldIgnoreDamage(source)) return false;
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        this.onDeathHelper();
        super.die(source);
    }

    @Override
    public void onUnSummon() {
        if (!this.level().isClientSide) {
            MagicManager.spawnParticles(this.level(), ParticleTypes.POOF, getX(), getY(), getZ(), 25, 0.4, 0.8, 0.4, 0.03, false);
            this.discard();
        }
    }

    @Override
    public void onRemovedFromWorld() {
        this.onRemovedHelper(this);
        super.onRemovedFromWorld();
    }

    @Override
    public void SideAreaAttack(float range, float height, float arc, float boxOffset, float forwardOffset, float damage, int brokenShieldTicks, boolean canStun, boolean canlaunch, SoundEvent soundEvent, float pitch) {
        double theta = Math.toRadians(this.yBodyRot) + (Math.PI / 2D);
        double forwardX = Math.cos(theta) * (double)forwardOffset;
        double forwardZ = Math.sin(theta) * (double)forwardOffset;

        for(LivingEntity entityHit : this.getEntityLivingBaseNearby(range, height, range, range)) {
            double dx = entityHit.getX() - (this.getX() + forwardX);
            double dz = entityHit.getZ() - (this.getZ() + forwardZ);
            float entityHitAngle = (float)((Math.toDegrees(Math.atan2(dz, dx)) - (double)90.0F) % (double)360.0F);
            if (entityHitAngle < 0.0F) {
                entityHitAngle += 360.0F;
            }

            float entityAttackingAngle = (this.yBodyRot - boxOffset) % 360.0F;
            if (entityAttackingAngle < 0.0F) {
                entityAttackingAngle += 360.0F;
            }

            float entityHitDistance = (float)Math.sqrt(dx * dx + dz * dz);
            float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
            if (entityHitDistance <= range && (entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F || entityRelativeAngle >= 360.0F - arc / 2.0F || entityRelativeAngle <= -360.0F + arc / 2.0F) && !this.isAlliedTo(entityHit) &&  entityHit != this) {
                boolean flag = entityHit.hurt(this.getAttackState() == 15 ? ModDamageTypes.causeAnnihilationDamage(this, this) : this.damageSources().mobAttack(this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
                if (flag) {
                    this.hasHit = true;
                    if (canlaunch) {
                        if (this.getAttackState() == 8) {
                            this.launch(entityHit, true);
                        } else {
                            this.launch(entityHit, true);
                        }
                    }

                    this.playSound(soundEvent, 1.0F, pitch);
                    if (canStun) {
                        entityHit.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 40, 1));
                    }
                }

                if (entityHit instanceof Player && entityHit.isBlocking() && brokenShieldTicks > 0) {
                    disableShield(entityHit, brokenShieldTicks);
                }
            }
        }

    }

    public void nextSideAreaAttack(float range, float height, float arc, float boxOffset, float damage, int brokenShieldTicks, SoundEvent soundEvent, float pitch) {
        if (!this.level().isClientSide) {
            boolean hitAny = false;

            for(LivingEntity entityHit : this.getEntityLivingBaseNearby(range, height, range, range)) {
                float entityRelativeAngle = getEntityRelativeAngle(boxOffset, entityHit);
                float entityHitDistance = (float)Math.sqrt((entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ()) + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX()));
                if ((entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F || entityRelativeAngle >= 360.0F - arc / 2.0F || entityRelativeAngle <= -360.0F + arc / 2.0F) && !this.isAlliedTo(entityHit) && entityHit != this) {
                    hitAny = true;
                    boolean flag = entityHit.hurt(this.damageSources().mobAttack(this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
                    if (flag) {
                        entityHit.setShiftKeyDown(false);
                        this.playSound(soundEvent, 1.0F, pitch);
                        this.hasHit = true;
                    } else {
                        this.hasHit = false;
                    }

                    if (entityHit instanceof Player && entityHit.isBlocking() && brokenShieldTicks > 0) {
                        disableShield(entityHit, brokenShieldTicks);
                    }
                }
            }

            if (!hitAny) {
                this.hasHit = false;
            }
        }
    }

    private float getEntityRelativeAngle(float boxOffset, LivingEntity entityHit) {
        float entityHitAngle = (float)((Math.atan2(entityHit.getZ() - this.getZ(), entityHit.getX() - this.getX()) * (180D / Math.PI) - (double)90.0F) % (double)360.0F);
        float entityAttackingAngle = (this.yBodyRot - boxOffset) % 360.0F;
        if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
        }

        if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
        }

        return entityHitAngle - entityAttackingAngle;
    }

    @Override
    public void SideGrab(float range, float height, float arc, float boxOffset, float damage, int brokenShieldTicks, SoundEvent soundEvent, float pitch) {
        if (!this.level().isClientSide) {
            boolean hitAny = false;

            for(LivingEntity entityHit : this.getEntityLivingBaseNearby(range, height, range, range)) {
                float entityRelativeAngle = getEntityRelativeAngle(boxOffset, entityHit);
                float entityHitDistance = (float)Math.sqrt((entityHit.getZ() - this.getZ()) * (entityHit.getZ() - this.getZ()) + (entityHit.getX() - this.getX()) * (entityHit.getX() - this.getX()));
                if ((entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F || entityRelativeAngle >= 360.0F - arc / 2.0F || entityRelativeAngle <= -360.0F + arc / 2.0F) && !this.isAlliedTo(entityHit) && entityHit != this) {
                    hitAny = true;
                    boolean entityHitisTarget = entityHit == this.target();
                    DamageSource damageSource = new DamageSource(this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), this);
                    if (entityHit.isDamageSourceBlocked(damageSource)) {
                        this.succedGrabbing = false;
                    }

                    boolean flag = entityHit.hurt(damageSource, (float) getAttributeValue(Attributes.ATTACK_DAMAGE));
                    if (flag) {
                        this.playSound(soundEvent, 1.0F, pitch);
                        boolean mounted = entityHitisTarget && entityHit.startRiding(this, true);
                        if (mounted) {
                            entityHit.setShiftKeyDown(false);
                            this.succedGrabbing = true;
                        } else {
                            this.succedGrabbing = false;
                        }
                    } else {
                        this.succedGrabbing = false;
                    }

                    if (entityHit instanceof Player && entityHit.isBlocking() && brokenShieldTicks > 0) {
                        disableShield(entityHit, brokenShieldTicks);
                    }
                }
            }

            if (!hitAny) {
                this.succedGrabbing = false;
            }

        }
    }

    @Override
    public void shootAnnihilationBomb(float velocity, float x, float y, float z, int inaccuracy) {
        if (this.targetIsNotNull()) {
            SmallAnnihilationBombEntity annihilationBomb = new SmallAnnihilationBombEntity(ModEntities.SMALL_ANNIHILATION_BOMB_ENTITY.get(), this.level(), this, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2);
            annihilationBomb.setPosRaw(x, y, z);

            double targetY = this.target().getY() + this.target().getBbHeight() / 2.0;

            double diffX = this.target().getX() - x;
            double diffY = targetY - y;
            double diffZ = this.target().getZ() - z;

            double horizontalDistance = Math.sqrt(diffX * diffX + diffZ * diffZ);

            double finalTargetY = diffY + horizontalDistance * 0.2;

            annihilationBomb.shoot(diffX, finalTargetY, diffZ, velocity, inaccuracy - 8.0f);
            annihilationBomb.setOwner(this);
            this.level().addFreshEntity(annihilationBomb);
        }
    }

    @Override
    public void spawnExplosions(float damage, int bulletAmount, double explosionAmount, double range, int tickDelay) {
        damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE) / 2;

        float yaw = (float) Math.toRadians(this.yBodyRot);

        double dirX = -Math.sin(yaw);
        double dirZ = Math.cos(yaw);

        for(int i = 0; i < explosionAmount; i++) {
            double distance = 4.0 * (i + 1);

            double targetX = this.getX() + dirX * distance;
            double targetZ = this.getZ() + dirZ * distance;

            Vec3 groundPos = RaycastUtils.findGround(this.level(), new Vec3(targetX, Math.floor(this.getY()), targetZ), 2, 2);

            if (groundPos != null) {
                AnnihilationExplosionEntity explosion = new AnnihilationExplosionEntity(this.level(), groundPos.x, groundPos.y, groundPos.z, yBodyRot, tickDelay, this, 20, damage, bulletAmount);
                ((ISpellSourceFlag) explosion).legendarySpellbooks$setDamage(damage / 1.5f);
                ((ISpellSourceFlag) explosion).legendarySpellbooks$markSpell();
                this.level().addFreshEntity(explosion);
            }
        }

    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 85) {
            this.remove(RemovalReason.KILLED);
            this.gameEvent(GameEvent.ENTITY_DIE);
        }
    }

    @Override public void regainHealthWithoutTarget(float health, float speed) {}
    @Override public void moveToBlockPos(BlockPos blockPos) {}
}
