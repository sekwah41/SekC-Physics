package com.sekwah.sekcphysics.maths;

/**
 * Created by sekwah41 on 07/07/2017.
 */
public class VectorMaths {

    public static PointD normalize(PointD p1, PointD p2) {

        double norm = 1.0d/Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) + Math.pow(p1.z - p2.z, 2));

        return new PointD((p1.x - p2.x) * norm,
                (p1.y - p2.y) * norm, (p1.z - p2.z) * norm);
    }

}
