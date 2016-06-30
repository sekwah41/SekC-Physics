package com.sekwah.sekcphysics.ragdoll;

/**
 * Created by on 30/06/2016.
 *
 * @author sekwah41
 */
public class PointF {
    private float x;

    private float y;

    private float z;

    public PointF(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PointF(double v, double v1, double v2) {

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public PointF add(PointF vector){
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;
        return this;
    }

    public PointF sub(PointF vector){
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;
        return this;
    }

    public PointF multiply(float multi){
        this.x *= multi;
        this.y *= multi;
        this.z *= multi;
        return this;
    }

    public PointF clone() {
        return new PointF(x,y,z);
    }
}
