package dev.higurashi.legendary_spellbooks.client.model.entities.spell.projectile;

import dev.higurashi.legendary_spellbooks.common.entity.spell.projectile.CumuloChargeEntity;
import net.miauczel.legendary_monsters.entity.animations.replacer.CGAnims;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

public class CumuloChargeModel extends HierarchicalModel<CumuloChargeEntity> {
    private final ModelPart root;

    public CumuloChargeModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    @NotNull
    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(@NotNull CumuloChargeEntity entity, float swing, float swingAmount, float ticks, float headYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.getAnimationState("charge"), CGAnims.charge, ticks, 1.0f);
        this.animate(entity.getAnimationState("endcharge"), CGAnims.chargeEnd, ticks, 1.0f);
        this.animate(entity.getAnimationState("aendcharge"), CGAnims.chargeEndAggresive, ticks, 1.0f);
    }
}
