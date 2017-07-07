package com.sekwah.sekcphysics.maths;

/**
 * Created by sekwah41 on 07/07/2017.
 */
public class VectorMaths {

    public static PointD normalize(PointD p1, PointD p2) {
        double norm = Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) + Math.pow(p1.z - p2.z, 2));

        return normalize(p1, p2, norm);
    }

    public static PointD normalize(PointD p1, PointD p2, double precalcNorm) {
        double norm = 1.0d/precalcNorm;

        return new PointD((p1.x - p2.x) * norm,
                (p1.y - p2.y) * norm, (p1.z - p2.z) * norm);
    }

    public static PointD getTriangleNormal(PointD p1, PointD p2, PointD p3) {
        PointD v1 = p1.sub(p2);
        PointD v2 = p1.sub(p3);
        double normX = v1.y * v2.z - v1.z * v2.y;
        double normY = v1.z * v2.x - v1.x * v2.z;
        double normZ = v1.x * v2.y - v1.y * v2.x;

        return new PointD(normX, normY, normZ);
    }

    public static PointD rotateOriginX(double angle, PointD point) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        double y = point.y * cos + point.y * sin;
        double z = point.z * -sin + point.z * cos;

        return new PointD(point.x, y, z);
    }

}
