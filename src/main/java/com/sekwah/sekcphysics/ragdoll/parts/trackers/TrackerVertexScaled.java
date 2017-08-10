package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.sekwah.sekcphysics.maths.PointF;
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

    private final SkeletonPoint anchor;

    private final SkeletonPoint pointsTo;

    private float scaleValue;

    private final float piFloat = (float) (Math.PI);

    public float rotationZ = 0;
    public TrackerVertexScaled(ModelRenderer part, SkeletonPoint anchor, SkeletonPoint pointsTo, float scaleValue) {
        super(part, anchor, pointsTo);
        this.anchor = anchor;
        this.pointsTo = pointsTo;

        this.scaleValue = scaleValue;
    }

    @Override
    public void render() {

        this.setPartLocation();

        this.calcRotation();
        this.setPartRotation();

        this.part.render(0.0625f);

        // TODO Look at the length in comparison (store it when calculating physics) and stretch it based on the percentage xD
        //GlStateManager.scale(1,scaleFactorStretch,0);
    }

    @Override
    public void setPartLocation() {
        this.part.setRotationPoint((float) anchor.posX * 16, (float) anchor.posY * 16, (float) anchor.posZ * 16);
    }

    /**
     * Convert to using Math.atan2(y,x);
     * @param axis1
     * @param axis2
     * @return
     */
    public float basicRotation(float axis1, float axis2) {
        return (float) (Math.PI + Math.atan2(axis1, axis2));
    }


    public float angleBetween(PointF point1, PointF point2) {

        return 0;
    }

}
