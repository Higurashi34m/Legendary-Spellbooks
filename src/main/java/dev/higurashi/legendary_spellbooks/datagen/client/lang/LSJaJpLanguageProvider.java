package dev.higurashi.legendary_spellbooks.datagen.client.lang;

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
        // SPELL
        // --------------------

        // Ender
        addSpell(LSSpellRegistry.ANNIHILATION_GEYSER_SPELL, "終焉の噴火", "近くの敵を吸い込む巨大な重力場を周囲に発生させる。詠唱が完了すると、終焉のエネルギーの巨大な間欠泉が噴出し、壊滅的な爆発が起こる。(地形破壊は無い)");

        // Fire
        addSpell(LSSpellRegistry.FLAME_EATER_SPELL, "火炎喰らい", "複数の相手をターゲットにし、相手の足元に炎を噴出させる。相手の位置が高すぎると、炎を噴出させることができない。");

        // Lightning
        addSpell(LSSpellRegistry.CLOUD_RAIL_SPELL, "クラウドレール", "自分の見ている方向に直線状に魔法の雲を召喚する。この雲は落下し、当たった位置から範囲ダメージを与える。");
        addSpell(LSSpellRegistry.CLOUD_RING_SPELL, "クラウドリング", "自分を中心に複数のリング状に魔法の雲を召喚する。この雲は落下し、当たった位置から範囲ダメージを与える。");
        addSpell(LSSpellRegistry.NIMBUS_ARRAY_SPELL, "ニンバスアレー", "自分の見ている方向に直線状の雷を召喚する。この雷からは一定時間雷が落ち、当たった相手にダメージを与える。");
        addSpell(LSSpellRegistry.TRIPLE_NIMBUS_ARRAY_SPELL, "トリプルニンバスアレー", "自分の見ている方向、及びそこから左右に30度の方向に直線状の雷を召喚する。この雷からは一定時間雷が落ち、当たった相手にダメージを与える。");
        addSpell(LSSpellRegistry.THUNDER_FANBURST_SPELL, "サンダー・ファンバースト", "扇状に雷を召喚する。この雷はそれぞれの方向に向かって進み、この雷に当たった相手にダメージを与える。この雷は持続時間の間、無限貫通である。");

        // Nature
        addSpell(LSSpellRegistry.OVERGROWN_SHOCKWAVE_SPELL, "生い茂るショックウェーブ", "地面を叩きつけて、周囲の広い範囲に有毒なエリアを発生させ、巻き込まれたすべての敵にダメージと共に猛毒を与える。");

        // Death Attack
        addSpellDamageSource(LSSpellRegistry.CLOUD_RAIL_SPELL, "%1$sは %2$sの雲に巻き込まれて押しつぶされた");

        // UI
        addUi("nimbus_count", "雷雲の数: %d");
    }
}
