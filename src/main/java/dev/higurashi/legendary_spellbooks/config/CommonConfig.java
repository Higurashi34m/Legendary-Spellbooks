package dev.higurashi.legendary_spellbooks.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Boolean> STUN_CASTING;
    public static final ForgeConfigSpec.ConfigValue<Double> ANNIHILATION_FIRE_BONUS;
    public static final ForgeConfigSpec.ConfigValue<Double> ANNIHILATION_ENDER_BONUS;

    public static final ForgeConfigSpec.ConfigValue<Double> ANNIHILATORS_PROTOCOL_DODGE_CHANCE;

    public static final ForgeConfigSpec.ConfigValue<Boolean> ANNIHILATION_ARROW_HP_DAMAGE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ANNIHILATION_BEAM_HP_DAMAGE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ANNIHILATION_BOMB_HP_DAMAGE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ANNIHILATION_SHOCKWAVE_HP_DAMAGE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> FLAMEBORN_DRIFT_HP_DAMAGE;
    public static final ForgeConfigSpec.ConfigValue<String> POSSESSED_SOUL_BLADE_SECOND_SCHOOL;

    public static final ForgeConfigSpec SPEC;

    static {
        // GamePlay
        BUILDER.push("GamePlay Config");

        BUILDER.comment("Can cast spells while stun (true/false) #default false");
        STUN_CASTING = BUILDER.define("can_cast_while_stun", false);

        BUILDER.comment("");

        BUILDER.comment("Annihilation School Fire/Ender Bonus Multiplier #default 0.5");
        ANNIHILATION_FIRE_BONUS = BUILDER.define("annihliation_fire_bonus", 0.5);
        ANNIHILATION_ENDER_BONUS = BUILDER.define("annihliation_ender_bonus", 0.5);

        BUILDER.pop();
        // GamePlay Finish

        // Equipment
        BUILDER.push("Equipment Config");

        BUILDER.comment("Annihilators Protocol Dodge Chance (0.0 ~ 1.0) #default 0.1");
        ANNIHILATORS_PROTOCOL_DODGE_CHANCE = BUILDER.define("annihilators_protocol_dodge_chance", 0.1);

        BUILDER.pop();
        // Equipment Finish

        // Spell Setting
        BUILDER.push("Spell Config");

        BUILDER.push("Annihilation Arrow");
        BUILDER.comment("Enable Annihilation Arrow HP Damage #default true");
        ANNIHILATION_ARROW_HP_DAMAGE = BUILDER.define("annihilation_arrow_hp_damage", true);
        BUILDER.pop();

        BUILDER.push("Annihilation Beam");
        BUILDER.comment("Enable Annihilation Beam HP Damage #default true");
        ANNIHILATION_BEAM_HP_DAMAGE = BUILDER.define("annihilation_beam_hp_damage", true);
        BUILDER.pop();

        BUILDER.push("Annihilation Bomb");
        BUILDER.comment("Enable Annihilation Bomb HP Damage #default true");
        ANNIHILATION_BOMB_HP_DAMAGE = BUILDER.define("annihilation_bomb_hp_damage", true);
        BUILDER.pop();

        BUILDER.push("Annihilation Shockwave");
        BUILDER.comment("Enable Annihilation Shockwave HP Damage #default true");
        ANNIHILATION_SHOCKWAVE_HP_DAMAGE = BUILDER.define("annihilation_shockwave_hp_damage", true);
        BUILDER.pop();

        BUILDER.push("Flameborn Drift");
        BUILDER.comment("Enable Flameborn Drift HP Damage #default true");
        FLAMEBORN_DRIFT_HP_DAMAGE = BUILDER.define("flameborn_drift_hp_damage", true);
        BUILDER.pop();

        BUILDER.push("Possessed Soul Blade");
        BUILDER.comment("Possessed Soul Blade Second School (String) #default irons_spellbooks:blood");
        POSSESSED_SOUL_BLADE_SECOND_SCHOOL = BUILDER.define("possessed_soul_blade_second_school", "irons_spellbooks:blood");
        BUILDER.pop();

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
