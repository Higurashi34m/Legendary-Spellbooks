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

        // Lightning
        addSpell(LSSpellRegistry.CLOUD_RAIL_SPELL, "クラウドレール", "自分の見ている方向に直線状に魔法の雲を召喚する。この雲は落下し、当たった位置から範囲ダメージを与える。");
        addSpell(LSSpellRegistry.CLOUD_RING_SPELL, "クラウドリング", "自分を中心に複数のリング状に魔法の雲を召喚する。この雲は落下し、当たった位置から範囲ダメージを与える。");
        addSpell(LSSpellRegistry.NIMBUS_ARRAY_SPELL, "ニンバスアレー", "自分の見ている方向に直線状の雷を召喚する。この雷からは一定時間雷が落ち、当たった相手にダメージを与える。");
        addSpell(LSSpellRegistry.TRIPLE_NIMBUS_ARRAY_SPELL, "トリプルニンバスアレー", "自分の見ている方向、及びそこから左右に30度の方向に直線状の雷を召喚する。この雷からは一定時間雷が落ち、当たった相手にダメージを与える。");
        addSpell(LSSpellRegistry.THUNDER_FANBURST_SPELL, "サンダー・ファンバースト", "扇状に雷を召喚する。この雷はそれぞれの方向に向かって進み、この雷に当たった相手にダメージを与える。この雷は持続時間の間、無限貫通である。");

        // Death Attack
        addSpellDamageSource(LSSpellRegistry.CLOUD_RAIL_SPELL, "%1$sは %2$sの雲に巻き込まれて押しつぶされた");

        // UI
        addUi("nimbus_count", "雷雲の数: %d");
    }
}
