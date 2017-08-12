package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import net.minecraft.client.model.ModelRenderer;

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
        this.renderPart(partialTicks);
    }

    public void calcPosition() {
        // TODO Find out why convertToF is broken

        this.updateLastPos();

        PointD triangleDir = triangle.getDirection();

        PointD trangleNorm = triangle.getNormal();

        this.rotation.x = (float) (Math.PI) / 2 + basicRotation(-triangleDir.y, (float) Math.sqrt(Math.pow(triangleDir.x,2) + Math.pow(triangleDir.z,2)));

        this.rotation.y = basicRotation(-triangleDir.x, -triangleDir.z);

        // TODO rotate around the axis to meet the normal.

        this.updatePosition();

        this.updatePosDifference();

        // TODO figure out the rotation to get it to the right location.


        // Calculate what the vertex would be if it was just rotated to the direction, then find the angle between the two points.
        // Find a way to calculate if it should be positive or negative.

        // Or rotate the angle to the default and find it using the same way you find rotationX (will be a lot easier if it works)

        /*Matrix4f rotMatrix = new Matrix4f();
        rotMatrix.rotate(-rotationX, new Vector3f(1, 0, 0));
        rotMatrix.rotate(-rotationY, new Vector3f(0, 1, 0));*/

    }

    protected void updatePosition() {
        this.position = new PointD(triangle.points[0].posX, triangle.points[0].posY,
                triangle.points[0].posZ);
    }

    public float basicRotation(double axis1, double axis2) {
        return (float) (Math.PI + Math.atan2(axis1, axis2));
    }

}
