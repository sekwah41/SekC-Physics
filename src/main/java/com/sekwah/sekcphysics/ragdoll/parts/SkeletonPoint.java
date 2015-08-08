package com.sekwah.sekcphysics.ragdoll.parts;

/**
 * Created by sekawh on 8/5/2015.
 */
public class SkeletonPoint {

    private final float size;

    public float posX;
    public float posY;
    public float posZ;

    public float lastPosX;
    public float lastPosY;
    public float lastPosZ;


    // basically distance from last point, just sounds nicer for when other forces interact with it or if you wanna set it
    //  from an explosion
    public float velX = 0;
    public float velY = 0;
    public float velZ = 0;

    /**
     * this will set positions to scale in terms of the model, also y coordinates on models are negative so reverse it manually
     */
    public SkeletonPoint(float x, float y, float z, float size){
        this(x,y,z,size,true);
    }

    public SkeletonPoint(float x, float y, float z){
        this(x,y,z,0.15f,true);
    }

    // note the position is in blocks not the model locations, and every 1 block is split into 16 for the model positions(i think)
    public SkeletonPoint(float x, float y, float z, float size, boolean shouldDoModelScale){
        this.posX = x;
        this.posY = y;
        this.posZ = z;

        this.lastPosX = x;
        this.lastPosY = y;
        this.lastPosZ = z;

        this.size = size;

        if(shouldDoModelScale){
            shiftPositionToModelScale();
        }
    }

    public void shiftPositionToModelScale(){
        this.setPosition(this.posX / 16f, this.posY / 16f, this.posZ / 16f);
    }

    public void shiftPositionToWorldScale(){
        this.setPosition(this.posX * 16f, this.posY * 16f, this.posZ * 16f);
    }

    public void setPosition(float x, float y, float z){
        this.posX = x;
        this.posY = y;
        this.posZ = z;

        this.lastPosX = x;
        this.lastPosY = y;
        this.lastPosZ = z;
    }
}
