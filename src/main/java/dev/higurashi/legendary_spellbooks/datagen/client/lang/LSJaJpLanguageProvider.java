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

        // Death Attack
        addSpellDamageSource(LSSpellRegistry.CLOUD_RAIL_SPELL, "%1$sは %2$sの雲に巻き込まれて押しつぶされた");
    }
}
