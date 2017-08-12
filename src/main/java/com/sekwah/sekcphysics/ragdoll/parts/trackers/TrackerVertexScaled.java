package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by on 30/06/2016.
 *
 * TODO possibly recode to use a constraint rather than set points
 *
 * @author sekwah41
 */
public class TrackerVertexScaled extends TrackerVertex {

    private final float scale;

    private final float scaleInvert;

    public TrackerVertexScaled(ModelRenderer part, SkeletonPoint anchor, SkeletonPoint pointsTo, float scale) {
        super(part, anchor, pointsTo);

        this.scale = scale;
        this.scaleInvert = 1f/scale;
    }

    @Override
    public void render(float partialTicks) {

        this.calcPosition();
        this.setPartRotation();

        this.part.render(0.0625f * this.scale);

        // TODO Look at the length in comparison (store it when calculating physics) and stretch it based on the percentage xD
        //GlStateManager.scale(1,scaleFactorStretch,0);
    }

    @Override
    public void setPartLocation(float partialTicks) {
        this.part.setRotationPoint((float) this.anchor.posX * 16 * this.scaleInvert,
                (float) this.anchor.posY * 16 * this.scaleInvert,
                (float) this.anchor.posZ * 16 * this.scaleInvert);
    }

}
