package com.sekwah.sekcphysics.ragdoll.parts.tracker;

import com.sekwah.sekcphysics.ragdoll.PointD;
import com.sekwah.sekcphysics.ragdoll.PointF;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by on 30/06/2016.
 *
 * @author sekwah41
 */
public class TrackerVertex extends Tracker {

    private final SkeletonPoint anchor;

    private final SkeletonPoint pointsTo;

    public float rotationZ = 0;
    public TrackerVertex(SkeletonPoint anchor, SkeletonPoint pointsTo){
        this.anchor = anchor;
        this.pointsTo = pointsTo;
    }

    public void render(ModelRenderer modelPart) {
        modelPart.setRotationPoint((float) anchor.posX * 16, (float) anchor.posY * 16, (float) anchor.posZ * 16);

        modelPart.rotateAngleX = 0;
        modelPart.rotateAngleY = 0;
        modelPart.rotateAngleZ = rotationZ;

        modelPart.render(0.0625f);
        // TODO Look at the length in comparison (store it when calculating physics) and stretch it based on the percentage xD
        //GL11.glScalef(1,scaleFactorStretch,0);
    }

    public void calcRotation() {
        PointF constraintVert = new PointF((float) (anchor.posX - pointsTo.posX), (float) (anchor.posY - pointsTo.posX),
                (float) (anchor.posX - pointsTo.posX));

        rotationZ = basicRotation(constraintVert.getZ(), constraintVert.getY());

    }

    public float basicRotation(double axis1, double axis2){
        if(axis2 == 0){
            return (float) (Math.PI / 2);
        }
        return (float) Math.atan(axis1/axis2);
    }


    public float angleBetween(PointF point1, PointF point2){

        return 0;
    }

}
