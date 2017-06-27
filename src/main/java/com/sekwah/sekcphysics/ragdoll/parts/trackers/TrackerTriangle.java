package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.sekwah.sekcphysics.ragdoll.PointD;
import com.sekwah.sekcphysics.ragdoll.PointF;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
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

    public TrackerTriangle(ModelRenderer part, Triangle triangle, float rotateOffsetX, float rotateOffsetY, float rotateOffsetZ){
        super(part, rotateOffsetX, rotateOffsetY, rotateOffsetZ);
        this.triangle = triangle;
    }

    public void render() {

        this.part.setRotationPoint((float) triangle.points[0].posX * 16, (float) triangle.points[0].posY * 16,
                (float) triangle.points[0].posZ * 16);

        //SekCPhysics.logger.info((float) anchor.posX * 16);

        GL11.glPushMatrix();



        GL11.glRotatef((float) Math.toDegrees(this.rotationZ + this.rotateOffsetZ), 0,0,1);
        GL11.glRotatef((float) Math.toDegrees(this.rotationY + this.rotateOffsetY), 0,1,0);
        GL11.glRotatef((float) Math.toDegrees(this.rotationX + this.rotateOffsetX), 1,0,0);

        //GL11.glRotatef((float) Math.toDegrees(this.rotationY + this.rotateOffsetY), 0,1,0);

        //this.part.rotateAngleX = this.rotationX + this.rotateOffsetX;
        //this.part.rotateAngleY = this.rotationY + this.rotateOffsetY;
        //this.part.rotateAngleZ = this.rotationZ + this.rotateOffsetZ;

        //GL11.glRotatef((float) (Math.random() * 180), 0,1,0);

        //this.part.rotateAngleY = 0;
        //GL11.glRotatef((float) Math.toDegrees(this.axisRotation), 0,1,0);


        // TODO calculate wanted rotation and the rotation added from getting to the correct direction.


        this.part.render(0.0625f);

        GL11.glPopMatrix();

        // TODO Look at the length in comparison (store it when calculating physics) and stretch it based on the percentage xD
        //GL11.glScalef(1,scaleFactorStretch,0);
    }

    public void calcRotation() {
        // TODO Find out why convertToF is broken
        PointD triangleDir = triangle.getDirection();

        PointD trangleNorm = triangle.getNormal();

        // Works and gets it aligned

        rotationX = piFloat / 2 + basicRotation((float) -triangleDir.getY(), (float) Math.sqrt(Math.pow(triangleDir.getX(),2) + Math.pow(triangleDir.getZ(),2)));

        rotationY = basicRotation((float) -triangleDir.getX(), (float) -triangleDir.getZ());



        // TODO figure out the rotation to get it to the right location.


        // Calculate what the vertex would be if it was just rotated to the direction, then find the angle between the two points.
        // Find a way to calculate if it should be positive or negative.

        // Or rotate the angle to the default and find it using the same way you find rotationX (will be a lot easier if it works)

        /*Matrix4f rotMatrix = new Matrix4f();
        rotMatrix.rotate(-rotationX, new Vector3f(1, 0, 0));
        rotMatrix.rotate(-rotationY, new Vector3f(0, 1, 0));*/

    }

    public void calcOldRotation() {
        // TODO Find out why convertToF is broken
        PointD triangleDir = triangle.getDirection();

        //SekCPhysics.logger.info(triangle.getDirection().getX());

       // rotationZ = basicRotation(constraintVert.getX(), constraintVert.getY());

        rotationX = piFloat / 2 + basicRotation((float) -triangleDir.getY(), (float) Math.sqrt(Math.pow(triangleDir.getX(),2) + Math.pow(triangleDir.getZ(),2)));

        //SekCPhysics.logger.info(rotationX);

        rotationY = basicRotation((float) -triangleDir.getX(), (float) -triangleDir.getZ());

        PointD trangleNorm = triangle.getNormal();

        Vec3d normVec = new Vec3d(trangleNorm.getX(), trangleNorm.getY(), trangleNorm.getZ());

        ////normVec.rotateAroundZ(-rotationZ);

        normVec.rotateYaw(-rotationY);

        normVec.rotateYaw(-rotationX);

        /*GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex3d(0,0,0);
        GL11.glVertex3d(trangleNorm.getX() * 40, trangleNorm.getY() * 40, trangleNorm.getZ() * 40);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex3d(0,0,0);
        GL11.glVertex3d(normVec.x * 40, normVec.y * 40, normVec.z * 40);
        GL11.glEnd();*/

        axisRotation = basicRotation((float) normVec.x, (float) normVec.z);

        /*Matrix4f rotMatrix = new Matrix4f();
        rotMatrix.rotate(-rotationX, new Vector3f(1, 0, 0));
        rotMatrix.rotate(-rotationY, new Vector3f(0, 1, 0));*/

    }

    public float basicRotation(float axis1, float axis2){
        return (float) (Math.PI + Math.atan2(axis1, axis2));
    }


    public float angleBetween(PointF point1, PointF point2){

        return 0;
    }

}
