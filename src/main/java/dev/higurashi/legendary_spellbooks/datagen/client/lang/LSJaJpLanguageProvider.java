package dev.higurashi.legendary_spellbooks.datagen.client.lang;

import dev.higurashi.legendary_spellbooks.registries.LSItemRegistry;
import dev.higurashi.legendary_spellbooks.registries.LSSpellRegistry;
import net.minecraft.data.PackOutput;

import java.util.Locale;

public class LSJaJpLanguageProvider extends BaseLanguageProvider {
    public LSJaJpLanguageProvider(PackOutput output) {
        super(output, Locale.JAPAN.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        // --------------------
        // ITEM
        // --------------------
        addItem(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM, "アナイアレイター・プロトコル");

        // --------------------
        // SPELL
        // --------------------

        // Ender
        addSpell(LSSpellRegistry.ANNIHILATION_BEAM_SPELL, "アナイアレイション・ビーム", "破壊的なエネルギーを持つビームを放つ。撃っている間は動くことができない。地面の近く、かつ、地面と水平に近い角度で放つと小さい球をばら撒く。");
        addSpell(LSSpellRegistry.ANNIHILATION_BOMB_SPELL, "アナイアレイション・ボム", "凝縮された破壊的な爆弾を、自分の見ている方向に向けて放つ。この爆弾は、エンティティやブロックに当たると爆発し、当たった相手にダメージを与え、周囲に大量の小さい球をばら撒く。");
        addSpell(LSSpellRegistry.ANNIHILATION_SHOCKWAVE_SPELL, "アナイアレイション・ショックウェーブ", "地面を踏みつけて、扇状に緑色の火の衝撃波を発生させる。炎は波のように噴出し、巻き込まれたすべての敵の生命力を焼き尽くす。当たった相手の最大体力に応じてダメージが増加する。");
        addSpell(LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL, "アナイアレイション・レゾナンス", "自身に不安定な周波数を付与する。効果時間中、クリティカルヒットを与えると与えた相手の位置から強力な消滅の爆発を発生させ、周囲の敵にダメージを与える。。");
        addSpell(LSSpellRegistry.ANNIHILATION_GEYSER_SPELL, "終焉の噴火", "近くの敵を吸い込む巨大な重力場を周囲に発生させる。詠唱が完了すると、終焉のエネルギーの巨大な間欠泉が噴出し、壊滅的な爆発が起こる。(地形破壊は無い)");

        // Fire
        addSpell(LSSpellRegistry.FLAME_EATER_SPELL, "火炎喰らい", "複数の相手をターゲットにし、相手の足元に炎を噴出させる。相手の位置が高すぎると、炎を噴出させることができない。");
        addSpell(LSSpellRegistry.FLAME_SECTOR_SPELL, "フレイムセクター", "自分を中心に等間隔に放射状の炎を噴出させる。これらの炎はブロックから噴出する。詠唱者が地面から離れすぎていると、これらの炎は現れない。");

        // Lightning
        addSpell(LSSpellRegistry.CLOUD_RAIL_SPELL, "クラウドレール", "自分の見ている方向に直線状に魔法の雲を召喚する。この雲は落下し、当たった位置から範囲ダメージを与える。");
        addSpell(LSSpellRegistry.CLOUD_RING_SPELL, "クラウドリング", "自分を中心に複数のリング状に魔法の雲を召喚する。この雲は落下し、当たった位置から範囲ダメージを与える。");
        addSpell(LSSpellRegistry.NIMBUS_ARRAY_SPELL, "ニンバスアレー", "自分の見ている方向に直線状の雷を召喚する。この雷からは一定時間雷が落ち、当たった相手にダメージを与える。");
        addSpell(LSSpellRegistry.TRIPLE_NIMBUS_ARRAY_SPELL, "トリプルニンバスアレー", "自分の見ている方向、及びそこから左右に30度の方向に直線状の雷を召喚する。この雷からは一定時間雷が落ち、当たった相手にダメージを与える。");
        addSpell(LSSpellRegistry.THUNDER_FANBURST_SPELL, "サンダー・ファンバースト", "扇状に雷を召喚する。この雷はそれぞれの方向に向かって進み、この雷に当たった相手にダメージを与える。この雷は持続時間の間、無限貫通である。");
        addSpell(LSSpellRegistry.TORNADO_SPELL, "トルネード", "周囲の生物を強制的に吸い寄せる竜巻を召喚する。この竜巻に捕らわれた者は移動の自由を奪われ、嵐の中心で身動きが取れなくなる。");
        addSpell(LSSpellRegistry.ENERGY_BEAM_SPELL, "エナジービーム", "空中へと浮上・静止し、超高出力のレーザーを放つ。その圧倒的なエネルギー出力により、放ち終えた後、一定時間スタンし、行動不可となる。");

        // Nature
        addSpell(LSSpellRegistry.OVERGROWN_SHOCKWAVE_SPELL, "生い茂るショックウェーブ", "地面を叩きつけて、周囲の広い範囲に有毒なエリアを発生させ、巻き込まれたすべての敵にダメージと共に猛毒を与える。");

        // Death Attack
        addSpellDamageSource(LSSpellRegistry.CLOUD_RAIL_SPELL, "%1$sは %2$sの雲に巻き込まれて押しつぶされた");
        addSpellDamageSource(LSSpellRegistry.FLAME_EATER_SPELL, "%1$sは %2$s が地から呼び出した業火に喰らわれた");
        addSpellDamageSource(LSSpellRegistry.ANNIHILATION_RESONANCE_SPELL, "%1$sは %2$sの共鳴による消滅の爆発に消し飛ばされた");

        // UI
        addUi("nimbus_count", "雷雲の数: %d");
        addUi("health_damage", "ダメージ: %s + 相手の最大体力の%s%%");

        // --------------------
        // Tooltip
        // --------------------
        addTooltip(LSItemRegistry.ANNIHILATORS_PROTOCOL_SPELLBOOK_ITEM.get(), "§6特殊能力: §7ダメージを受けた際、10%の確率でテレポートを実行し攻撃を回避する。");
    }
}
