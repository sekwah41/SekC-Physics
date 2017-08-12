package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.maths.PointF;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Created by on 30/06/2016.
 *
 * @author sekwah41
 */
public class TrackerTriangle extends Tracker {

    protected final Triangle triangle;

    public TrackerTriangle(ModelRenderer part, Triangle triangle) {
        super(part);
        this.triangle = triangle;

    }

    public TrackerTriangle(ModelRenderer part, Triangle triangle, float rotateOffsetX, float rotateOffsetY, float rotateOffsetZ) {
        super(part, rotateOffsetX, rotateOffsetY, rotateOffsetZ);
        this.triangle = triangle;
    }

    @Override
    public void render(float partialTicks) {

        //SekCPhysics.logger.info((float) anchor.posX * 16);

        GlStateManager.pushMatrix();



        GlStateManager.rotate((float) Math.toDegrees(this.rotation.z) + this.rotationOffset.z, 0,0,1);
        GlStateManager.rotate((float) Math.toDegrees(this.rotation.y) + this.rotationOffset.y, 0,1,0);
        GlStateManager.rotate((float) Math.toDegrees(this.rotation.x) + this.rotationOffset.x, 1,0,0);

        //GlStateManager.rotate((float) Math.toDegrees(this.rotationY + this.rotateOffsetY), 0,1,0);

        //this.part.rotateAngleX = this.rotationX + this.rotateOffsetX;
        //this.part.rotateAngleY = this.rotationY + this.rotateOffsetY;
        //this.part.rotateAngleZ = this.rotationZ + this.rotateOffsetZ;

        //GlStateManager.rotate((float) (Math.random() * 180), 0,1,0);

        //this.part.rotateAngleY = 0;
        //GlStateManager.rotate((float) Math.toDegrees(this.axisRotation), 0,1,0);


        // TODO calculate wanted rotation and the rotation added from getting to the correct direction.

        this.renderPart(partialTicks);

        GlStateManager.popMatrix();

        // TODO Look at the length in comparison (store it when calculating physics) and stretch it based on the percentage xD
        //GlStateManager.scale(1,scaleFactorStretch,0);
    }

    public void calcPosition() {
        // TODO Find out why convertToF is broken

        this.updateLastPos();

        PointD triangleDir = triangle.getDirection();

        PointD trangleNorm = triangle.getNormal();

        // Works and gets it aligned

        this.rotation.x = (float) Math.PI / 2 + basicRotation((float) -triangleDir.y, (float) Math.sqrt(Math.pow(triangleDir.x,2) + Math.pow(triangleDir.z,2)));

        this.rotation.y = basicRotation((float) -triangleDir.x, (float) -triangleDir.z);

        this.updatePosDifference();

        // TODO figure out the rotation to get it to the right location.


        // Calculate what the vertex would be if it was just rotated to the direction, then find the angle between the two points.
        // Find a way to calculate if it should be positive or negative.

        // Or rotate the angle to the default and find it using the same way you find rotationX (will be a lot easier if it works)

        /*Matrix4f rotMatrix = new Matrix4f();
        rotMatrix.rotate(-rotationX, new Vector3f(1, 0, 0));
        rotMatrix.rotate(-rotationY, new Vector3f(0, 1, 0));*/

    }

    @Override
    public void setPartLocation(float partialTicks) {
        this.part.setRotationPoint((float) triangle.points[0].posX * 16, (float) triangle.points[0].posY * 16,
                (float) triangle.points[0].posZ * 16);
    }

    @Override
    public void setPartRotation(float partialTicks) {

    }

    public float basicRotation(float axis1, float axis2) {
        return (float) (Math.PI + Math.atan2(axis1, axis2));
    }


    public float angleBetween(PointF point1, PointF point2) {

        return 0;
    }

}
