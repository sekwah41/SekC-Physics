package com.sekwah.sekcphysics.ragdoll.parts;

import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.Point;
import com.sekwah.sekcphysics.ragdoll.Ragdolls;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

/**
 * Created by sekawh on 8/5/2015.
 */
public class SkeletonPoint {

    private final float size;

    public double posX;
    public double posY;
    public double posZ;

    public double lastPosX;
    public double lastPosY;
    public double lastPosZ;


    // basically distance from last point, just sounds nicer for when other forces interact with it or if you wanna set it
    //  from an explosion
    public double velX = 0;
    public double velY = 0;
    public double velZ = 0;

    /**
     * this will set positions to scale in terms of the model, also y coordinates on models are negative so reverse it manually
     */
    public SkeletonPoint(double x, double y, double z, float size){
        this(x,y,z,size,true);
    }

    public SkeletonPoint(double x, double y, double z){
        this(x,y,z,0.15f,true);
    }

    // note the position is in blocks not the model locations, and every 1 block is split into 16 for the model positions(i think)
    public SkeletonPoint(double x, double y, double z, float size, boolean shouldDoModelScale){
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

    public void setPosition(double x, double y, double z){
        this.posX = x;
        this.posY = y;
        this.posZ = z;

        this.lastPosX = x;
        this.lastPosY = y;
        this.lastPosZ = z;
    }

    public void movePoint(EntityRagdoll entity, double x, double y, double z) {
        this.posX += x;
        this.posY += y;
        this.posZ += z;

        /*if(posY < -24f / 16f){
            posY = -24f / 16f;
        }*/

        double pointPosX = entity.posX + this.posX;
        double pointPosY = entity.posY + this.posY;
        double pointPosZ = entity.posZ + this.posZ;

        AxisAlignedBB axisalignedbb = entity.boundingBox.copy();

        List list = entity.worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox.addCoord(x, y, z));

       /* double d3 = this.posX;
        double d4 = this.posY;
        double d5 = this.posZ;

        AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.posX - size, this.posY - size,this.posZ - size, this.posX + size, this.posY + size,this.posZ + size);
*/


    }

    public void update(EntityRagdoll entity) {
        this.lastPosX = this.posX;
        this.lastPosY = this.posY;
        this.lastPosZ = this.posZ;

        this.movePoint(entity, posX - lastPosX, posY - lastPosY - Ragdolls.gravity, posZ - lastPosZ);

        //next_old_position = position             // This position is the next frame's old_position
       // position += position - old_position;     // Verlet integration
        //position += gravity;                     // gravity == (0,-0.01,0)
    }

    public void moveTo(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;

    }


    public Point toPoint() {
        return new Point(this.posX, this.posY, this.posZ);
    }
}
