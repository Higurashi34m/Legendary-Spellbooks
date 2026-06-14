package dev.higurashi.legendary_spellbooks.registries;

import dev.higurashi.legendary_spellbooks.LegendarySpellbooks;
import dev.higurashi.legendary_spellbooks.common.tags.LSTags;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.miauczel.legendary_monsters.damagetype.ModDamageTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LSSchoolRegistry {
    public static final DeferredRegister<SchoolType> SCHOOLS = DeferredRegister.create(SchoolRegistry.SCHOOL_REGISTRY_KEY, LegendarySpellbooks.MOD_ID);
    public static void register(IEventBus bus) { SCHOOLS.register(bus); }

    private static Supplier<SchoolType> registerSchool(SchoolType schoolType) {
        return SCHOOLS.register(schoolType.getId().getPath(), () -> schoolType);
    }

    public static final ResourceLocation ANNIHILATION_RESOURCE = ResourceLocation.fromNamespaceAndPath(LegendarySpellbooks.MOD_ID, "annihilation");

    public static final Supplier<SchoolType> ANNIHILATION = registerSchool(new SchoolType(
            ANNIHILATION_RESOURCE,
            LSTags.ANNIHILATION_FOCUS,
            Component.translatable("school.legendary_spellbooks.annihilation").withStyle(Style.EMPTY.withColor(0x7FFF00)),
            LSAttributeRegistry.ANNIHILATION_SPELL_POWER,
            LSAttributeRegistry.ANNIHILATION_MAGIC_RESIST,
            SoundRegistry.ENDER_CAST,
            ModDamageTypes.ANNIHILATION
    ));
}
