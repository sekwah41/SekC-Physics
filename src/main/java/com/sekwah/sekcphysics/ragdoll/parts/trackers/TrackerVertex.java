package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.sekwah.sekcphysics.ragdoll.PointF;
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

    private final SkeletonPoint anchor;

    private final SkeletonPoint pointsTo;

    private final float piFloat = (float) (Math.PI);

    public float rotationZ = 0;
    public TrackerVertex(ModelRenderer part, SkeletonPoint anchor, SkeletonPoint pointsTo){
        super(part);
        this.anchor = anchor;
        this.pointsTo = pointsTo;

    }

    public void render() {

        this.part.setRotationPoint((float) anchor.posX * 16, (float) anchor.posY * 16, (float) anchor.posZ * 16);

        //SekCPhysics.logger.info((float) anchor.posX * 16);

        this.part.rotateAngleX = rotationX;
        this.part.rotateAngleY = rotationY;
        this.part.rotateAngleZ = rotationZ;

        this.part.render(0.0625f);

        // TODO Look at the length in comparison (store it when calculating physics) and stretch it based on the percentage xD
        //GL11.glScalef(1,scaleFactorStretch,0);
    }

    public void calcRotation() {
        PointF constraintVert = new PointF((float) (anchor.posX - pointsTo.posX), (float) (anchor.posY - pointsTo.posY),
                (float) (anchor.posZ - pointsTo.posZ));

       // rotationZ = basicRotation(constraintVert.getX(), constraintVert.getY());

        rotationX = piFloat / 2 + basicRotation(-constraintVert.getY(), (float) Math.sqrt(Math.pow(constraintVert.getX(),2) + Math.pow(constraintVert.getZ(),2)));

        rotationY = basicRotation(-constraintVert.getX(), -constraintVert.getZ());

    }

    /**
     * Convert to using Math.atan2(y,x);
     * @param axis1
     * @param axis2
     * @return
     */
    public float basicRotation(float axis1, float axis2){
        return (float) (Math.PI + Math.atan2(axis1, axis2));
    }


    public float angleBetween(PointF point1, PointF point2){

        return 0;
    }

}
