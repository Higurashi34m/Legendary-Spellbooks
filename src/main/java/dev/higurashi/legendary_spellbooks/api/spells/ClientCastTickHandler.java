package dev.higurashi.legendary_spellbooks.api.spells;

import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientCastTickHandler {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        var minecraft = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.START || minecraft.player == null || !ClientMagicData.isCasting()) return;

        Level level = minecraft.level;
        if (level == null) return;

        List<Entity> spellCasters = level.getEntities((Entity) null, minecraft.player.getBoundingBox().inflate(64), mob -> mob instanceof Player || mob instanceof IMagicEntity);

        for (Entity caster : spellCasters) {
            if (caster instanceof LivingEntity entity) {
                var spellData = ClientMagicData.getSyncedSpellData(entity);

                if (spellData.isCasting()) {
                    String spellId = spellData.getCastingSpellId();
                    int spellLevel = spellData.getCastingSpellLevel();
                    AbstractSpell spell = SpellRegistry.getSpell(spellId);

                    if (spell instanceof BaseSpell baseSpell) {
                        baseSpell.onClientCastTick(minecraft.level, spellLevel, entity);
                    }
                }
            }
        }
    }
}
