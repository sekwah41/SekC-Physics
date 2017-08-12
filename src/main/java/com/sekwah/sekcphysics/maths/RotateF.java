package com.sekwah.sekcphysics.maths;

public class RotateF {

    public float x;

    public float y;

    public float z;

    public RotateF(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RotateF() {
        this( 0, 0, 0);
    }

    public RotateF add(RotateF v) {
        return new RotateF(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public RotateF sub(RotateF v) {
        return new RotateF(this.x - v.x, this.y - v.y, this.z - v.z);
    }
}
