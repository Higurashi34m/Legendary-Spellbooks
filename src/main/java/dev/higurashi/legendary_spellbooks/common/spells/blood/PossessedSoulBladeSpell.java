package dev.higurashi.legendary_spellbooks.common.spells.blood;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.miauczel.legendary_monsters.Particle.custom.Circle;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.AnimatedEntity.SoulBladeEntity;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect.CameraShakeEntity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class PossessedSoulBladeSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "possessed_soul_blade");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.BLOOD_RESOURCE)
            .setMinRarity(SpellRarity.EPIC)
            .setCooldownSeconds(20)
            .setMaxLevel(4).build();

    public PossessedSoulBladeSpell() {
        super(spellResource, spellConfig, true);
        this.castTime = 35;
        this.baseManaCost = 125;
        this.baseSpellPower = 6;
        this.manaCostPerLevel = 25;
        this.spellPowerPerLevel = 4;

        this.castFinishSound = () -> SoundEvents.TOTEM_USE;

        this.castStartAnimation = SpellAnimations.CHARGE_SPIT_ANIMATION;
        this.castFinishAnimation = SpellAnimations.CAST_T_POSE;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of();
    }

    @Override
    public void playSound(Optional<SoundEvent> sound, Entity entity) {
        sound.ifPresent((soundEvent -> entity.playSound(soundEvent, 2.0f, 0.7f)));
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        if (level instanceof ServerLevel serverLevel) serverLevel.sendParticles(new Circle.RingData(0.0f, (float) (Math.PI / 2.0f), 30, 1.0f, 0.0f, 0.0f, 1.0f, caster.getBbWidth() * 60.0f, false, Circle.EnumRingBehavior.GROW), caster.getX(), caster.getY(), caster.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
        CameraShakeEntity.cameraShake(level, caster.position(), 20.0f, 0.1f, 0, 20);

        int damage = (int) getSpellPower(spellLevel, caster);
        int ringCount = getRingCount(spellLevel);
        int bladeCount = (int) (ringCount * 1.5);

        for (int i = 0; i < ringCount; i++) {
            double radius = (i + 1) * 2.0;

            List<Vec3> spawnPoints = GeometryUtils.getCirclePoints(caster.position(), radius, bladeCount, caster.getYRot());
            for (Vec3 spawnPoint : spawnPoints) {
                Vec3 spawnPos = RaycastUtils.findGround(level, spawnPoint, 5, 3);
                if (spawnPos == null) continue;

                float yaw = GeometryUtils.getYawBetween(caster.position(), spawnPos);
                SoulBladeEntity blade = new SoulBladeEntity(level, spawnPos.x, spawnPos.y, spawnPos.z, yaw, i * 2, caster, damage, true);

                level.addFreshEntity(blade);

                if (level instanceof ServerLevel serverLevel) serverLevel.sendParticles(new Circle.RingData(0,  ((float)Math.PI / 2.0f), 35, 1.0f, 0.0f, 0.0f, 0.8f, 15.0f, false, Circle.EnumRingBehavior.GROW), spawnPos.x, spawnPos.y + 0.2f, spawnPos.z, 1, 0, 0, 0, 0);
            }
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }

    private int getRingCount(int spellLevel) {
        return Math.min(spellLevel + 2, 10);
    }
}
