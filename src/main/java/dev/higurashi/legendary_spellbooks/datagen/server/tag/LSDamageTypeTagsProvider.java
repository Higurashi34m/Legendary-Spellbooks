package dev.higurashi.legendary_spellbooks.datagen.server.tag;

import dev.higurashi.daybreaklib.api.annotation.AutoDatagen;
import dev.higurashi.daybreaklib.api.datagen.DatagenContext;
import dev.higurashi.daybreaklib.api.datagen.provider.server.tag.BaseDamageTypeTagsProvider;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.tag.LSTags;
import dev.higurashi.legendary_spellbooks.registry.LSDamageTypeRegistry;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;

@AutoDatagen(dist = Dist.DEDICATED_SERVER)
public class LSDamageTypeTagsProvider extends BaseDamageTypeTagsProvider {
    public LSDamageTypeTagsProvider(DatagenContext context) {
        super(context.output(), context.provider(), LegendarySpellbooks.MOD_ID, context.helper());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        this.tag(LSTags.ANNIHILATION_MAGIC).add(LSDamageTypeRegistry.ANNIHILATION_MAGIC.key());

        this.tag(LSTags.BYPASS_ANNIHILATORS_PROTOCOL).add(
                DamageTypes.ON_FIRE,
                DamageTypes.WITHER,
                DamageTypes.FREEZE,
                DamageTypes.STARVE,
                DamageTypes.DROWN,
                DamageTypes.STALAGMITE,
                DamageTypes.OUTSIDE_BORDER,
                DamageTypes.FELL_OUT_OF_WORLD,
                DamageTypes.DRY_OUT,
                DamageTypes.IN_WALL,
                ISSDamageTypes.CAULDRON,
                ISSDamageTypes.HEARTSTOP
        ).addTags(
                DamageTypeTags.IS_FALL,
                DamageTypeTags.BYPASSES_INVULNERABILITY
        );
    }
}
