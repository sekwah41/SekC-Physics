package com.sekwah.sekcphysics.ragdoll;

/**
 * Created by sekawh on 8/4/2015.
 */
public class Point {

    private final float x;

    private final float y;

    private final float z;

    public Point(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
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
}
