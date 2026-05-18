package dev.higurashi.legendary_spellbooks.api.spells;

import dev.higurashi.legendary_spellbooks.api.entities.helper.ISummonedMob;
import dev.higurashi.legendary_spellbooks.api.utils.GeometryUtils;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.ICastDataSerializable;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonedEntitiesCastData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class BaseSummonSpell extends BaseSpell {
    public BaseSummonSpell(ResourceLocation spellResource, DefaultConfig config) {
        super(spellResource, config, true);
    }

    @Override public int getRecastCount(int spellLevel, LivingEntity caster) { return 2; }
    @Override public ICastDataSerializable getEmptyCastData() { return new SummonedEntitiesCastData(); }

    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        if (SummonManager.recastFinishedHelper(serverPlayer, recastInstance, recastResult, castDataSerializable)) {
            super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);
        }
    }

    protected abstract Entity[] getEntitiesToSummon(Level level, LivingEntity caster, int spellLevel);

    protected Vec3 getSpawnOffset(int index, int totalCount, float yaw, LivingEntity caster) {
        return GeometryUtils.getPointInCircle(Vec3.ZERO, 1.5, totalCount, index, caster.getYRot());
    }

    protected int getSummonTime(int spellLevel, LivingEntity caster) {
        return 20 * 60 * 10;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity caster, CastSource source, MagicData magicData) {
        var recasts = magicData.getPlayerRecasts();

        if (!recasts.hasRecastForSpell(this)) {
            SummonedEntitiesCastData castData = new SummonedEntitiesCastData();
            int summonTime = getSummonTime(spellLevel, caster);
            float yaw = caster.getYRot();

            Entity[] entities = getEntitiesToSummon(level, caster, spellLevel);
            int total = entities.length;

            for (int i = 0; i < total; i++) {
                Vec3 offset = getSpawnOffset(i, total, yaw, caster);
                Vec3 spawnPos = caster.position().add(offset);

                Entity entity = entities[i];
                if (entity instanceof ISummonedMob summonedMob) {
                    summonedMob.setupAttributes(spellLevel, getSpellPower(spellLevel, caster));
                }

                entity.moveTo(spawnPos);
                entity.setYRot(yaw);
                level.addFreshEntity(entity);

                SummonManager.initSummon(caster, entity, summonTime, castData);
            }

            RecastInstance recastInstance = new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, caster), summonTime, source, castData);
            recasts.addRecast(recastInstance, magicData);
        }

        super.onCast(level, spellLevel, caster, source, magicData);
    }
}
