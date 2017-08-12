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
public class TrackerVertex extends Tracker {

    protected final SkeletonPoint anchor;

    protected final SkeletonPoint pointsTo;

    public TrackerVertex(ModelRenderer part, SkeletonPoint anchor, SkeletonPoint pointsTo) {
        super(part);
        this.anchor = anchor;
        this.pointsTo = pointsTo;

        // Resets the data so it doesnt get a sliding effect from 0 of every location.
        /*this.calcPosition();
        this.updateLastPos();
        this.updatePosDifference();*/
    }

    @Override
    public void render(float partialTicks) {

        this.renderPart(partialTicks);

        // TODO Look at the length in comparison (store it when calculating physics) and stretch it based on the percentage xD
        //GlStateManager.scale(1,scaleFactorStretch,0);
    }

    @Override
    public void calcPosition() {

        this.updateLastPos();

        PointF constraintVert = new PointF((float) (anchor.posX - pointsTo.posX), (float) (anchor.posY - pointsTo.posY),
                (float) (anchor.posZ - pointsTo.posZ));

        this.rotation.x = (float) (Math.PI) / 2 + basicRotation(-constraintVert.y, (float) Math.sqrt(Math.pow(constraintVert.x,2) + Math.pow(constraintVert.z,2)));

        this.rotation.y = basicRotation(-constraintVert.x, -constraintVert.z);

        this.updatePosDifference();
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
