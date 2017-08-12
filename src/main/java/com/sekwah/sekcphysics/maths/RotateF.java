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

    public RotateF add(RotateF r) {
        this.x += r.x;
        this.y += r.y;
        this.z += r.z;
        return this;
    }

    public RotateF sub(RotateF r) {
        this.x -= r.x;
        this.y -= r.y;
        this.z -= r.z;
        return this;
    }

    public RotateF clone() {
        return new RotateF(this.x, this.y, this.z);
    }

    public RotateF copy(RotateF rotation) {
        this.x = rotation.x;
        this.y = rotation.y;
        this.z = rotation.z;
        return this;
    }
}
