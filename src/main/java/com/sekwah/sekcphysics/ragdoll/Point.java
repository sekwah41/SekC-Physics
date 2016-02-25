package com.sekwah.sekcphysics.ragdoll;

/**
 * Created by sekawh on 8/4/2015.
 */
public class Point {

    private double x;

    private double y;

    private double z;

    public Point(double x, double y, double z){
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

    public void add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public Point add(Point vector){
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;
        return this;
    }

    public Point sub(Point vector){
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;
        return this;
    }

    public Point multiply(double multi){
        this.x *= multi;
        this.y *= multi;
        this.z *= multi;
        return this;
    }

    public Point clone() {
        return new Point(x,y,z);
    }
}
