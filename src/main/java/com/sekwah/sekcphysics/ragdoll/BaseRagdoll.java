package com.sekwah.sekcphysics.ragdoll;

import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.parts.Skeleton;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

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

    /**
     * The old oldUpdate means
     * @param entity
     */
    public void oldUpdate(EntityRagdoll entity) {
        skeleton.oldUpdate(entity);

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
            //newPoint.translate(new Vector3f((float) point.posX, (float) point.posY, (float) point.posZ));
            //SekCPhysics.logger.info(entity.rotationYaw);
            Vec3d vec = new Vec3d(point.posX, point.posY, point.posZ);
            vec.rotateYaw((float) Math.toRadians(-entity.rotationYaw));
            point.setPosition(vec.xCoord, vec.yCoord, vec.zCoord);
        }
    }
}
