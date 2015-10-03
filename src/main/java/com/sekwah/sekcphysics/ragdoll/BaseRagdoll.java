package com.sekwah.sekcphysics.ragdoll;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.parts.Skeleton;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by sekawh on 8/4/2015.
 */
public class BaseRagdoll {

    // Current skeleton position and shape
    public Skeleton skeleton;

    // Will be used once the physics is sorted, then can render all the stuff to the correct positions
    public ModelBase entityModel;

    // offset from the bottom of the desired entity to the main point of the ragdoll
    public double centerHeightOffset;

    public BaseRagdoll(){
    }

    public BaseRagdoll(float centerHeightOffset){
        this.centerHeightOffset = centerHeightOffset;
    }


    private void updateRagdoll() {

    }

    public void rotateRagdoll(float rotYaw) {
        // add some matrix code to calaulate positions for all of the new points to be alligned with the rotation of
        // the entity
    }

    public void update(EntityRagdoll entity) {
        skeleton.update(entity);

    }

    public void shiftPos(double x, double y, double z) {
        skeleton.shiftPos(x,y,z);
    }

    public void setStanceToEntity(EntityLivingBase entity) {
        for(SkeletonPoint point : skeleton.points){
            // Finish rotation maths
            Matrix4f pointTrans = new Matrix4f();
            //newPoint.translate(new Vector3f((float) point.posX, (float) point.posY, (float) point.posZ));
            SekCPhysics.LOGGER.info(entity.rotationYaw);
            pointTrans.rotate((float) Math.toRadians(entity.rotationYaw), new Vector3f(0, 1, 0));
            double newPosX = (pointTrans.m00 * point.posX + pointTrans.m10 * point.posX + pointTrans.m20 * point.posX + pointTrans.m30);
            double newPosY = (pointTrans.m01 * point.posY + pointTrans.m11 * point.posY + pointTrans.m21 * point.posY + pointTrans.m31);
            double newPosZ = (pointTrans.m02 * point.posZ + pointTrans.m12 * point.posZ + pointTrans.m22 * point.posZ + pointTrans.m32);
            point.setPosition(newPosX, newPosY, newPosZ);
        }
    }
}
