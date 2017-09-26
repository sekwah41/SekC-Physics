package com.sekwah.sekcphysics.maths;

import javax.vecmath.Matrix3d;

public final class MatrixMaths {

    public static PointD addRotAroundAxis(double x, double y, double z, double axis) {

        // May be faster to rewrite with your own to reduce the number of calculations

        Matrix3d rotationMatrix = new Matrix3d();
        rotationMatrix.rotZ(z); // Add z rotation
        Matrix3d yRot = new Matrix3d();
        yRot.rotY(y);
        Matrix3d xRot = new Matrix3d();
        xRot.rotX(x);

        Matrix3d axisRot = new Matrix3d();
        axisRot.rotY(axis);

        rotationMatrix.mul(yRot);
        rotationMatrix.mul(xRot);
        rotationMatrix.mul(axisRot);

        return new PointD(Math.atan2(rotationMatrix.m21, rotationMatrix.m22),
                Math.atan2(-rotationMatrix.m20,Math.sqrt(rotationMatrix.m21 * rotationMatrix.m21 + rotationMatrix.m22 * rotationMatrix.m22)),
                Math.atan2(rotationMatrix.m10, rotationMatrix.m00));
    }

}
