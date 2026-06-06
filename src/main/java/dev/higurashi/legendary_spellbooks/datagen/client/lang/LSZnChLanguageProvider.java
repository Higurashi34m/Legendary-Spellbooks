package dev.higurashi.legendary_spellbooks.datagen.client.lang;

import dev.higurashi.legendary_spellbooks.registries.*;
import net.minecraft.data.PackOutput;

import java.util.Locale;

public class LSZnChLanguageProvider extends BaseLanguageProvider {
    public LSZnChLanguageProvider(PackOutput output) {
        super(output, Locale.CHINA.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        // Advancement
        addAdvancement("annihilator_protocol", "当你凝视深渊……", "获得湮灭者协议");
        addAdvancement("stormbound_grimoire", "二元之空", "获得缚风暴典");

        // Attribute
        addAttribute(LSAttributeRegistry.ANNIHILATION_SPELL_POWER, "湮灭法术强度");
        addAttribute(LSAttributeRegistry.ANNIHILATION_MAGIC_RESIST, "湮灭法术抗性");

        // death attack
        addSpellDamageSource(LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL, "%1$s 被 %2$s 释放的湮灭共振冲击彻底消灭了");
        addSpellDamageSource(LSSpellRegistry.CLOUD_RAIL_SPELL, "%1$s 被 %2$s 召唤的云层吞噬并碾压致死");
        addSpellDamageSource(LSSpellRegistry.FLAME_EATER_SPELL, "%1$s 被 %2$s 召唤的地下烈焰吞噬殆尽");

        // Effect
        addEffect(LSEffectRegistry.AMBUSH_THORNS_EFFECT, "伏击荆棘");
        addEffect(LSEffectRegistry.ANNIHILATION_RESONANCE_EFFECT, "湮灭共振");
        addEffect(LSEffectRegistry.BEAM_EFFECT, "光束");

        // Entity
        addEntityType(LSEntityRegistry.SUMMONED_ANNIHILATION_PURSUER_ENTITY, "召唤的湮灭猎影");
        addEntityType(LSEntityRegistry.SUMMONED_FLAMEBORN_GUARD_ENTITY, "召唤的淬焰守卫");
        addEntityType(LSEntityRegistry.SUMMONED_FLAMEBORN_WARRIOR_ENTITY, "召唤的淬焰战士");
        addEntityType(LSEntityRegistry.SUMMONED_HAUNTED_GUARD_ENTITY, "召唤的缠魂守卫");
        addEntityType(LSEntityRegistry.SUMMONED_HAUNTED_KNIGHT_ENTITY, "召唤的缠魂骑士");
        addEntityType(LSEntityRegistry.SUMMONED_SKELORAPTOR_ENTITY, "召唤的骸骨幼龙");

        // Item
        addItem(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM, "湮灭者协议");
        addItem(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM, "缚风暴典");
        addItem(LSItemRegistry.ANNIHILATION_RUNE, "湮灭符文");
        addItem(LSItemRegistry.ANNIHILATION_UPGRADE_ORB, "湮灭升级法球");

        // School
        addSchool(LSSchoolRegistry.ANNIHILATION, "湮灭");

        // Spell
        // Annihilation
        addSpell(LSSpellRegistry.ANNIHILATION_BEAM_SPELL, "湮灭光束", "引导一道毁灭性的能量光束。发射期间，你会被固定在原地。若瞄准地面附近，光束撞击时会散射出小型能量球。");
        addSpell(LSSpellRegistry.ANNIHILATION_BOMB_SPELL, "湮灭炸弹", "发射一枚凝聚了毁灭性能量的炸弹，撞击后发生爆炸，造成伤害并在区域内散射小型能量球。");
        addSpell(LSSpellRegistry.ANNIHILATION_SHOCKWAVE_SPELL, "湮灭冲击波", "重踏地面，释放一道扇形的绿色火焰冲击波。");
        addSpell(LSSpellRegistry.ANNIHILATION_GEYSER_SPELL, "湮灭泉涌", "创造一个引力井，将附近的生物吸入其中。完成蓄力后，一道巨大的能量泉涌会喷发，造成巨额伤害。");
        addSpell(LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL, "湮灭共振", "将自己浸没于不稳定的湮灭频率中，防御与攻击降低 25%。持续期间，打出暴击会在目标位置触发一次强力爆炸。");
        addSpell(LSSpellRegistry.RELEASE_RIFTWALKER_PREDATOR_SPELL, "释放裂隙行者协议", "召唤出湮灭猎影——一种冷酷无情的猎手，会通过维度裂隙追踪目标，直至将其消灭。");
        addSpell(LSSpellRegistry.SUMMON_FLAMEBORN_KNIGHTS_SPELL, "召唤淬焰骑士", "召唤由永恒烈焰构成的骑士为你作战。");

        // Fire
        addSpell(LSSpellRegistry.FLAME_EATER_SPELL, "噬焰术", "选定多个生物，在其脚下引发火焰爆发。若目标离地过高，该法术会失效。");
        addSpell(LSSpellRegistry.FLAME_SECTOR_SPELL, "烈焰环射", "向多个等距方向释放放射状的喷射火焰。火焰会沿地面蔓延。");

        // Ice
        addSpell(LSSpellRegistry.GLACIER_ERUPTION_SPELL, "冰川喷发", "在你面前召唤一排冰刺，冻结所有被其击中的生物。");
        addSpell(LSSpellRegistry.GLACIER_RINGBURST_SPELL, "冰川环爆", "以你的位置为中心召唤放射状的冰刺，冻结附近的生物。");

        // Lightning
        addSpell(LSSpellRegistry.CLOUD_RAIL_SPELL, "云轨", "朝你注视的方向召唤一排魔法云层。这些云层会坠落，撞击时造成范围伤害。");
        addSpell(LSSpellRegistry.CLOUD_RING_SPELL, "云环", "以你的位置为中心召唤多层魔法云层环。这些云层会坠落，撞击时造成范围伤害。");
        addSpell(LSSpellRegistry.CUMULO_CHARGE_SPELL, "积云冲击", "召唤一只云筑魔像的幻影向前冲锋。若已锁定目标，该幻影会追击目标。");
        addSpell(LSSpellRegistry.ENERGY_BEAM_SPELL, "能量光束", "升入空中释放一道高功率激光。能量的大量消耗会让施法者在光束结束后陷入短暂的晕眩状态。");
        addSpell(LSSpellRegistry.NIMBUS_ARRAY_SPELL, "雨云阵列", "在你面前召唤一排雷云，雷云会持续向其下方劈出闪电。");
        addSpell(LSSpellRegistry.QUAD_TORNADO_SPELL, "四重龙卷风", "释放多道放射状的龙卷风，将路径上的生物吸入并使其无法移动。");
        addSpell(LSSpellRegistry.THUNDER_FANBURST_SPELL, "雷霆扇形爆发", "释放一道扇形向外扩散的闪电爆发，穿透路径上的所有生物。");
        addSpell(LSSpellRegistry.TORNADO_SPELL, "龙卷风", "召唤一个狂暴的漩涡，将附近的生物吸入并使其无法移动。");
        addSpell(LSSpellRegistry.TRIPLE_NIMBUS_ARRAY_SPELL, "三重雨云阵列", "三重雨云阵列");

        // Nature
        addSpell(LSSpellRegistry.AMBUSH_THORNS_SPELL, "伏击荆棘", "将自身灌注荆棘之力，效果如同荆棘附魔，会对攻击者造成伤害。");
        addSpell(LSSpellRegistry.OVERGROWN_SHOCKWAVE_SPELL, "蔓生冲击波", "猛击地面，释放一阵有毒孢子爆发，使大范围区域内的附近生物中毒。");
        addSpell(LSSpellRegistry.FOSSILIZED_FURY_SPELL, "化石狂怒", "召唤一群骸骨幼龙猎杀并撕碎你的敌人。法术强度越高，召唤出的骸骨幼龙数量越多。");

        addCreativeTab("equipments", "传奇法术书装备");
        addCreativeTab("scrolls", "传奇法术书卷轴");

        addTooltip(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get(), "受到伤害时有10%的几率传送离开，以此有效规避威胁。");
        addTooltip(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get(), "这本法术书能召唤雷霆，让降雨演变为猛烈的风暴。");
        addTooltip("on_sunny", "晴朗天气下：");
        addTooltip("on_thunder", "雷雨天气期间：");

        // Ui
        addUi("hp_damage", "目标最大生命值的 %s%% 伤害");
        addUi("nimbus_count", "%d 个雨云");
        addUi("cast_error_exclusive_book", "%s 仅能配合 %s 施放！");
        addUi("health_damage", "%s 点伤害 + 目标最大生命值的 %s%%");
        addUi("hp", "%d 点 %s 的生命值");
        addUi("thorn_damage", "%d 点荆棘伤害 + 所受伤害的20%%");
    }
}
