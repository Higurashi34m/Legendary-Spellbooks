package dev.higurashi.legendary_spellbooks.datagen.client.lang;

import dev.higurashi.legendary_spellbooks.registries.*;
import net.minecraft.data.PackOutput;

import java.util.Locale;

public class LSJaJpLanguageProvider extends BaseLanguageProvider {
    public LSJaJpLanguageProvider(PackOutput output) {
        super(output, Locale.JAPAN.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        addAttribute(LSAttributeRegistry.ANNIHILATION_SPELL_POWER, "殲滅魔法の威力");
        addAttribute(LSAttributeRegistry.ANNIHILATION_MAGIC_RESIST, "殲滅魔法の耐性");

        // --------------------
        // CREATIVE TAB
        // --------------------
        addCreativeTab("equipments", "Legendary Spellbooksの装備品");
        addCreativeTab("scrolls", "Legendary Spellbooksのスクロール");

        // --------------------
        // ENTITY
        // --------------------
        addEntityType(LSEntityRegistry.SUMMONED_ANNIHILATION_PURSUER_ENTITY, "召喚されたアナイアレイション・パーサー");
        addEntityType(LSEntityRegistry.SUMMONED_FLAMEBORN_GUARD_ENTITY, "召喚されたフレイムボーン・ガード");
        addEntityType(LSEntityRegistry.SUMMONED_FLAMEBORN_WARRIOR_ENTITY, "召喚されたフレイムボーン・ウォーリアー");
        addEntityType(LSEntityRegistry.SUMMONED_HAUNTED_KNIGHT_ENTITY, "召喚された騎士の亡霊");
        addEntityType(LSEntityRegistry.SUMMONED_HAUNTED_GUARD_ENTITY, "召喚された衛兵の亡霊");
        addEntityType(LSEntityRegistry.SUMMONED_SKELORAPTOR_ENTITY, "召喚されたスケロラプトル");

        // --------------------
        // EFFECT
        // --------------------
        addEffect(LSEffectRegistry.ANNIHILATION_RESONANCE_EFFECT, "アナイアレイション・レゾナンス");
        addEffect(LSEffectRegistry.BEAM_EFFECT, "ビーム");
        addEffect(LSEffectRegistry.AMBUSH_THORNS_EFFECT, "アンバッシュ・ソーン");
        addEffect(LSEffectRegistry.FLAMEBORN_DASH_EFFECT, "フレイムボーンドリフト");

        // --------------------
        // ITEM
        // --------------------
        addItem(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM, "アナイアレイター・プロトコル");
        addItem(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM, "嵐に結ばれし魔導書");
        addItem(LSItemRegistry.OBLIVIONMANCER_HAT, "無に帰す者の帽子");
        addItem(LSItemRegistry.OBLIVIONMANCER_ROBE, "無に帰す者のローブ");
        addItem(LSItemRegistry.OBLIVIONMANCER_LEGGINGS, "無に帰す者のレギンス");
        addItem(LSItemRegistry.OBLIVIONMANCER_BOOTS, "無に帰す者のブーツ");
        addItem(LSItemRegistry.ANNIHILATION_RUNE, "殲滅のルーン");
        addItem(LSItemRegistry.ANNIHILATION_UPGRADE_ORB, "殲滅のアップグレードオーブ");
        addItem(LSItemRegistry.STORMMANCER_HOOD, "嵐術師のフード");
        addItem(LSItemRegistry.STORMMANCER_ROBE, "嵐術師のローブ");
        addItem(LSItemRegistry.STORMMANCER_LEGGINGS, "嵐術師のレギンス");
        addItem(LSItemRegistry.STORMMANCER_BOOTS, "嵐術師のブーツ");

        // --------------------
        // SPELL
        // --------------------

        // Annihilation
        addSpell(LSSpellRegistry.ANNIHILATION_BEAM_SPELL, "アナイアレイション・ビーム", "破壊的なエネルギーのビームを放つ。撃っている間は動くことができない。地面の近く、活水平に近い角度で放つと小さなエネルギー弾をばら撒く。");
        addSpell(LSSpellRegistry.ANNIHILATION_BOMB_SPELL, "アナイアレイション・ボム", "凝縮されたエネルギー弾を前方に放つ。命中すると爆発してダメージを与え、周囲に多数の小さな球をまき散らす。");
        addSpell(LSSpellRegistry.ANNIHILATION_SHOCKWAVE_SPELL, "アナイアレイション・ショックウェーブ", "地面を叩きつけ、扇状に緑色の炎の衝撃波を発生させる。ダメージは相手の最大体力に応じて増加する。");
        addSpell(LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL, "アナイアレイション・レゾナンス", "自身に不安定な周波数を付与する。効果中、防御力と攻撃力が25%下がる代わりに、クリティカルヒットを与えると、相手の位置で強力な爆発を引き起こす。");
        addSpell(LSSpellRegistry.ANNIHILATION_GEYSER_SPELL, "終焉の噴火", "周囲の敵を吸い込む巨大な重力場を発生させる。詠唱が完了するとエネルギーの間欠泉が噴出し、致命的なダメージを与える。");
        addSpell(LSSpellRegistry.SUMMON_FLAMEBORN_KNIGHTS_SPELL, "召喚 フレイムボーン騎士", "永遠に燃え続ける火炎から作られた騎士を召喚し、共に戦わせる。");
        addSpell(LSSpellRegistry.RELEASE_RIFTWALKER_PREDATOR_SPELL, "リフトウォーカー・プロトコル", "アナイアレイション・パーサーを現界させる。このハンターはターゲットを破壊するまで、どこまでも執拗に追い続ける。");
        addSpell(LSSpellRegistry.FLAMEBORN_DRIFT_SPELL, "フレイムボーンドリフト", "緑色の炎を纏って前方に高速で突進する。敵に衝突すると停止し、その周囲に範囲ダメージを与える。");

        // Blood
        addSpell(LSSpellRegistry.POSSESSED_SOUL_BLADE_SPELL, "憑依された魂の剣", "自分を中心に、地面から複数の円状に広がる幻影の剣を突き出させる。魔法のレベルによって剣の色が変化する。");
        addSpell(LSSpellRegistry.POSSESSED_FALLING_SOUL_BLADE_SPELL, "憑依された落下する魂の剣", "自分を中心に、地面から複数の円状に広がる幻影の剣を落とす。魔法のレベルによって剣の色が変化する。");
        addSpell(LSSpellRegistry.HEMATITE_TRISHULA_SPELL, "ヘマタイト・トリシューラ", "大きな赤い三叉矛を前方に放つ。地面に当たるとすると消滅し、着弾地点の周囲に複数の爆発を巻き起こす。");
        addSpell(LSSpellRegistry.POSSESSED_WING_SPELL, "憑依された翼", "自身に赤い翼を一定時間つける。この翼がついている間、エリトラ飛行が可能になり、ジャンプ力、移動速度、段の高さが上昇する。また、攻撃時にソウルフラクチャーのデバフを与える。");

        // Evocation
        addSpell(LSSpellRegistry.COLLAPSED_KINGDOMS_LEGION_SPELL, "崩壊せし王国の軍勢", "崩壊した王国の幻影の騎士たちを召喚する。彼らは古の義務に縛られ、灰の中から立ち上がり、汝の敵を打ち倒すだろう。");

        // Fire
        addSpell(LSSpellRegistry.FLAME_EATER_SPELL, "火炎喰らい", "複数の相手の足元から炎を噴出させる。相手の位置が高すぎると、炎を届かせることができない。");
        addSpell(LSSpellRegistry.FLAME_SECTOR_SPELL, "フレイムセクター", "自分を中心に、放射状に広がる炎を噴出させる。炎は地面を伝って進む。");
        addSpell(LSSpellRegistry.SENTINEL_SATURATION_SPELL, "センチネル・サチュレーション", "背後に複数のデューン・センチネルの幻影を召喚し、大量の爆弾を一斉に放って特定位置を絨毯爆撃する。");

        // Ice
        addSpell(LSSpellRegistry.GLACIER_ERUPTION_SPELL, "グレイシア・イラプション", "前方の直線上に巨大な氷の楔を次々と突き出させ、触れたものを凍らせる。");
        addSpell(LSSpellRegistry.GLACIER_RINGBURST_SPELL, "グレイシア・リングバースト", "自分を中心に、幾重もの氷の輪を広範囲に展開して周囲の敵を凍らせる。");

        // Lightning
        addSpell(LSSpellRegistry.CLOUD_RAIL_SPELL, "クラウドレール", "自分の見ている方向に直線状に魔法の雲を召喚する。この雲は落下し、当たった位置から範囲ダメージを与える。");
        addSpell(LSSpellRegistry.CLOUD_RING_SPELL, "クラウドリング", "自分を中心に複数のリング状に魔法の雲を召喚する。この雲は落下し、当たった位置から範囲ダメージを与える。");
        addSpell(LSSpellRegistry.NIMBUS_ARRAY_SPELL, "ニンバスアレー", "自分の見ている方向に直線状の雷を召喚する。この雷からは一定時間雷が落ち、当たった相手にダメージを与える。");
        addSpell(LSSpellRegistry.TRIPLE_NIMBUS_ARRAY_SPELL, "トリプルニンバスアレー", "自分の見ている方向、及びそこから左右に30度の方向に直線状の雷を召喚する。この雷からは一定時間雷が落ち、当たった相手にダメージを与える。");
        addSpell(LSSpellRegistry.THUNDER_FANBURST_SPELL, "サンダー・ファンバースト", "扇状に雷を召喚する。この雷はそれぞれの方向に向かって進み、この雷に当たった相手にダメージを与える。この雷は持続時間の間、無限貫通である。");
        addSpell(LSSpellRegistry.TORNADO_SPELL, "トルネード", "周囲の生物を強制的に吸い寄せる竜巻を召喚する。この竜巻に捕らわれた者は移動の自由を奪われ、嵐の中心で身動きが取れなくなる。");
        addSpell(LSSpellRegistry.QUAD_TORNADO_SPELL, "クアッド・トルネード", "自分を中心に複数の激しい竜巻を等間隔に全方位へ放つ。それぞれの竜巻は周囲の生物を強制的に吸い寄せ、進路上のすべての敵の動きを封じながら移動する。");
        addSpell(LSSpellRegistry.ENERGY_BEAM_SPELL, "エナジービーム", "空中へと浮上・静止し、超高出力のレーザーを放つ。その圧倒的なエネルギー出力により、放ち終えた後、一定時間スタンし、行動不可となる。");
        addSpell(LSSpellRegistry.CUMULO_CHARGE_SPELL, "キュムロチャージ", "雷雲の幻影「キュムロニンバス」を召喚し、前方に突進させる。ロックオン時は標的に向かって執拗に追尾する。");

        // Nature
        addSpell(LSSpellRegistry.AMBUSH_THORNS_SPELL, "アンバッシュ・ソーン", "自身に棘を付与する。この棘は棘の鎧のエンチャントと同じ効果を発生させる。");
        addSpell(LSSpellRegistry.OVERGROWN_SHOCKWAVE_SPELL, "生い茂るショックウェーブ", "地面を叩きつけて、周囲の広い範囲に有毒なエリアを発生させ、巻き込まれたすべての敵にダメージと共に猛毒を与える。");
        addSpell(LSSpellRegistry.FOSSILIZED_FURY_SPELL, "化石の逆鱗", "標的を追い詰め、切り刻む骨の恐竜の群れを呼び出す。レベルが上がるほど、召喚される個体数が増加する。");

        // Death Attack
        addSpellDamageSource(LSSpellRegistry.CLOUD_RAIL_SPELL, "%1$sは %2$sの雲に巻き込まれて押しつぶされた");
        addSpellDamageSource(LSSpellRegistry.FLAME_EATER_SPELL, "%1$sは %2$s が地から呼び出した業火に喰らわれた");
        addSpellDamageSource(LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL, "%1$sは %2$sの共鳴による消滅の爆発に消し飛ばされた");

        // UI
        addUi("nimbus_count", "雷雲の数: %d");
        addUi("health_damage", "ダメージ: %s + 相手の最大体力の%s%%");
        addUi("thorn_damage", "棘のダメージ: %d + 20%% の被ダメージ");
        addUi("hp", "%dの%sのHP");

        addUi("cast_error_exclusive_book", "%sは%sからしかキャストできない!");

        // --------------------
        // Tooltip
        // --------------------
        addTooltip("on_sunny", "晴れの時:");
        addTooltip("on_thunder", "雷雨の時:");

        addTooltip(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get(), "§6特殊能力: §7ダメージを受けた際、10%の確率でテレポートを実行し攻撃を回避する。");
        addTooltip(LSItemRegistry.STORMBOUND_GRIMOIRE_SPELLBOOK_ITEM.get(), "この魔導書は雷を呼び、降りしきる雨を嵐へと育て上げる");

        // --------------------
        // Advancement
        // --------------------
        addAdvancement("stormbound_grimoire", "双極の空", "嵐に結ばれし魔導書を手に入れる");
        addAdvancement("annihilator_protocol", "深淵を覗くとき...", "アナイアレイター・プロトコルを手に入れる");

        addSchool(LSSchoolRegistry.ANNIHILATION, "殲滅");

        add("upgrade.legendary_spellbooks.stormmancer_upgrade", "テンペスト強化");
        add("item.legendary_spellbooks.smithing_template.stormmancer_upgrade.applies_to", "ネザライトの戦魔術師装備");
        add("item.legendary_spellbooks.smithing_template.stormmancer_upgrade.ingredients", "空気のルーン");
    }
}
