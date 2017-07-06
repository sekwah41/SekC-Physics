package com.sekwah.sekcphysics.ragdoll.location;

/**
 * Created by sekawh on 8/4/2015.
 */
public class PointD {

    private double x;

    private double y;

    private double z;

    public PointD(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public PointD add(PointD vector) {
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;
        return this;
    }

    public PointD sub(PointD vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;
        return this;
    }

    public PointD multiply(double multi) {
        this.x *= multi;
        this.y *= multi;
        this.z *= multi;
        return this;
    }

    public PointF convertToF() {
        return new PointF(x,y,z);
    }

    public PointD clone() {
        return new PointD(x,y,z);
    }
}
