package dev.higurashi.legendary_spellbooks.datagen.server.tag;

import dev.higurashi.daybreaklib.api.datagen.provider.server.tag.BaseItemTagsProvider;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.tag.LSTags;
import dev.higurashi.legendary_spellbooks.registry.LSItemRegistry;
import io.redspace.ironsspellbooks.util.ModTags;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class LSItemTagsProvider extends BaseItemTagsProvider {
    public LSItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, LegendarySpellbooks.MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        this.tag(LSTags.SPELLBOOK_SLOT).add(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get());
        this.tag(LSTags.SPELLBOOK_SLOT).add(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get());

        this.tag(ModTags.INSCRIBED_RUNES).add(LSItemRegistry.ANNIHILATION_RUNE.get());

        this.tag(LSTags.ANNIHILATION_FOCUS).add(ModItems.BOTTLE_OF_ANNIHILATION.get());
        this.tag(ModTags.SCHOOL_FOCUS).addTag(LSTags.ANNIHILATION_FOCUS);
    }
}
