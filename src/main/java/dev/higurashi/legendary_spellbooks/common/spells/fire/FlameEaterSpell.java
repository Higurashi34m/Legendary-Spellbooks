package dev.higurashi.legendary_spellbooks.common.spells.fire;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.api.spells.BaseSpell;
import dev.higurashi.legendary_spellbooks.api.utils.ComponentUtils;
import dev.higurashi.legendary_spellbooks.api.utils.RaycastUtils;
import dev.higurashi.legendary_spellbooks.common.entities.spell.projectile.SpellFireColumnEntity;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.ICastDataSerializable;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class FlameEaterSpell extends BaseSpell {
    private static final ResourceLocation spellResource = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "flame_eater");
    private static final DefaultConfig spellConfig = new DefaultConfig()
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMinRarity(SpellRarity.RARE)
            .setCooldownSeconds(10)
            .setMaxLevel(6).build();

    public FlameEaterSpell() {
        super(spellResource, spellConfig);
        this.baseManaCost = 100;
        this.baseSpellPower = 10;
        this.manaCostPerLevel = 20;
        this.spellPowerPerLevel = 3;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "damage", ComponentUtils.format1f(getSpellPower(spellLevel, caster))),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "max_victims", getRecastCount(spellLevel, caster)),
                ComponentUtils.getUIComponent(IronsSpellbooks.MODID, "duration", ComponentUtils.ticksToSecondsString(getLifeTick(spellLevel)))
        );
    }

    @Override
    public int getRecastCount(int spellLevel, @Nullable LivingEntity caster) {
        return 1 + spellLevel;
    }

    @Override
    public ICastDataSerializable getEmptyCastData() {
        return new MultiTargetEntityCastData();
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity caster, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, caster, playerMagicData, this, 32, .15f);
    }


    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource castSource, MagicData magicData) {
        if (magicData.getAdditionalCastData() instanceof TargetEntityCastData targetEntityCastData) {
            PlayerRecasts recasts = magicData.getPlayerRecasts();

            if (!recasts.hasRecastForSpell(getSpellId())) {
                recasts.addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, caster), 40, castSource, new MultiTargetEntityCastData(targetEntityCastData.getTarget((ServerLevel) level))), magicData);
            } else {
                RecastInstance recast = recasts.getRecastInstance(this.getSpellId());

                if (recast != null && recast.getCastData() instanceof MultiTargetEntityCastData targetingData) {
                    targetingData.addTarget(targetEntityCastData.getTargetUUID());
                }
            }
        }

        super.onCast(level, spellLevel, caster, castSource, magicData);
    }

    @Override
    public void onRecastFinished(ServerPlayer caster, RecastInstance instance, RecastResult result, ICastDataSerializable castData) {
        if (castData instanceof MultiTargetEntityCastData targetingData) {
            float damage = getSpellPower(instance.getSpellLevel(), caster);
            int lifeTick = getLifeTick(instance.getSpellLevel());

            List<UUID> targets = targetingData.getTargets();
            ServerLevel level = caster.serverLevel();

            for (UUID target : targets) {
                Entity targetEntity = level.getEntity(target);

                if (targetEntity instanceof LivingEntity livingTarget) {
                    Vec3 groundPos = RaycastUtils.findGround(level, livingTarget.position(), 6, 2);
                    if (groundPos == null) return;

                    BlockPos spawnPos = BlockPos.containing(groundPos);

                    SpellFireColumnEntity flame = new SpellFireColumnEntity(level, spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0, damage, 0, lifeTick, caster);

                    level.addFreshEntity(flame);
                }
            }
        }

        super.onRecastFinished(caster, instance, result, castData);
    }

    private int getLifeTick(int spellLevel) {
        return Math.min(20 + spellLevel * 10, 400);
    }
}
