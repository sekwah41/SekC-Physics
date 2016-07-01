package com.sekwah.sekcphysics.ragdoll.parts.tracker;

import com.sekwah.sekcphysics.ragdoll.PointF;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import net.minecraft.client.model.ModelRenderer;

import javax.sound.midi.Track;

/**
 * Created by on 30/06/2016.
 *
 * @author sekwah41
 */
public class TrackerTriangle extends Tracker {

    private final float piFloat = (float) (Math.PI);

    private final Triangle triangle;

    public float rotationZ = 0;
    public TrackerTriangle(ModelRenderer part, Triangle triangle){
        super(part);
        this.triangle = triangle;

    }

    public void render() {

        this.part.setRotationPoint((float) triangle.points[0].posX * 16, (float) triangle.points[0].posY * 16,
                (float) triangle.points[0].posZ * 16);

        //SekCPhysics.logger.info((float) anchor.posX * 16);

        this.part.rotateAngleX = rotationX;
        this.part.rotateAngleY = rotationY;
        this.part.rotateAngleZ = rotationZ;

        this.part.render(0.0625f);

        // TODO Look at the length in comparison (store it when calculating physics) and stretch it based on the percentage xD
        //GL11.glScalef(1,scaleFactorStretch,0);
    }

    public void calcRotation() {
        PointF triangleDir = triangle.getDirection().convertToF();

       // rotationZ = basicRotation(constraintVert.getX(), constraintVert.getY());

        rotationX = piFloat / 2 + basicRotation(-triangleDir.getY(), (float) Math.sqrt(Math.pow(triangleDir.getX(),2) + Math.pow(triangleDir.getZ(),2)));

        rotationY = basicRotation(-triangleDir.getX(), -triangleDir.getZ());

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
