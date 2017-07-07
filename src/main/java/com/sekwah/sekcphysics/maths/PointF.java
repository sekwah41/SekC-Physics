package com.sekwah.sekcphysics.maths;

/**
 * Created by sekawh on 8/4/2015.
 */
public class PointF {

    public final float x;

    public final float y;

    public final float z;

    public PointF(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PointF add(PointF v) {
        return new PointF(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public PointF sub(PointF v) {
        return new PointF(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public PointF multiply(float multi) {
        return new PointF(this.x * multi, this.y * multi, this.z * multi);
    }
}
