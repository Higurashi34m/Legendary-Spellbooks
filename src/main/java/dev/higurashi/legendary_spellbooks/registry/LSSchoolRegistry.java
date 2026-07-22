package dev.higurashi.legendary_spellbooks.registry;

import dev.higurashi.daybreaklib.api.annotation.AutoRegister;
import dev.higurashi.daybreaklib_iss.api.registry.SchoolRegistryManager;
import dev.higurashi.daybreaklib_iss.api.registry.reference.SchoolReference;
import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.tag.LSTags;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

@AutoRegister
public class LSSchoolRegistry {
    public static final SchoolRegistryManager SCHOOLS = new SchoolRegistryManager(LegendarySpellbooks.MOD_ID);

    public static final ResourceLocation ANNIHILATION_RESOURCE = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation");

    public static final SchoolReference ANNIHILATION = SCHOOLS.create("annihilation", LSTags.ANNIHILATION_FOCUS)
            .setDamageType(LSDamageTypeRegistry.ANNIHILATION_MAGIC.key())
            .setDefaultCastSound(SoundRegistry.ENDER_CAST)
            .setStyle(Style.EMPTY.withColor(0x7FFF00))
            .setPowerAttribute(LSAttributeRegistry.ANNIHILATION_MAGIC_RESIST)
            .setResistAttribute(LSAttributeRegistry.ANNIHILATION_MAGIC_RESIST)
            .build();
}
