package com.sekwah.sekcphysics.ragdoll.parts.tracker;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.ragdoll.PointD;
import com.sekwah.sekcphysics.ragdoll.PointF;
import com.sekwah.sekcphysics.ragdoll.Ragdolls;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

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

    public float basicRotation(float axis1, float axis2){
        if(axis2 == 0){
            axis2 = 0.001f;
        }

        if(axis1 == 0){
            axis1 = 0.001f;
        }


       /* float mod1 = Math.abs(axis1);
        float mod2 = Math.abs(axis2);

        if(axis1 < 0 && axis2 > 0){

        }*/

        if(axis2 < 0){
            return (float) Math.atan(axis1/axis2);
        }
        else{
            return (float) (Math.PI + Math.atan(axis1/axis2));
        }

    }


    public float angleBetween(PointF point1, PointF point2){

        return 0;
    }

}
