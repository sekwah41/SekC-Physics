package com.sekwah.sekcphysics.ragdoll;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.parts.Skeleton;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.tracker.Tracker;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sekawh on 8/4/2015.
 */
public class BaseRagdoll {


    public Map<ModelRenderer, Tracker> trackerHashmap = new HashMap<ModelRenderer, Tracker>();

    public boolean trackersRegistered = true;


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
            //newPoint.translate(new Vector3f((float) point.posX, (float) point.posY, (float) point.posZ));
            //SekCPhysics.logger.info(entity.rotationYaw);
            Vec3 vec = Vec3.createVectorHelper(point.posX, point.posY, point.posZ);
            vec.rotateAroundY((float) Math.toRadians(-entity.rotationYaw));
            point.setPosition(vec.xCoord, vec.yCoord, vec.zCoord);
        }
    }

    public void initTrackers(ModelBiped bipedModel) {
        trackersRegistered = true;
    }
}
