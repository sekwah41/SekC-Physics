package com.sekwah.sekcphysics.ragdoll;

import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.parts.Skeleton;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.Tracker;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.TrackerTriangle;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.TrackerVertex;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sekawh on 8/4/2015.
 */
public class BaseRagdoll {


    public Map<ModelRenderer, Tracker> trackerHashmap = new HashMap<ModelRenderer, Tracker>();

    public boolean trackersRegistered = false;

    /**
     * If it is doing full updates or if it has settled on the floor waiting for larger amounts of updates
     */
    public boolean isActive = true;

    // Current skeleton position and shape
    public Skeleton skeleton;

    // Will be used once the physics is sorted, then can render all the stuff to the correct positions
    public ModelBase entityModel;

    // offset from the bottom of the desired entity to the main point of the ragdoll
    public double centerHeightOffset;

    public BaseRagdoll(float centerHeightOffset) {
        this.centerHeightOffset = centerHeightOffset;
    }

    public void rotateRagdoll(float rotYaw) {
        // add some matrix code to calaulate positions for all of the new points to be alligned with the rotation of
        // the entity
    }

    /**
     * Called whenever an update is needed
     * @param entity
     */
    public void update(EntityRagdoll entity) {
        skeleton.update(entity);

    }

    public void shiftPos(double x, double y, double z) {
        skeleton.shiftPos(x,y,z);
    }

    public void setStanceToEntity(EntityLivingBase entity) {
        for(SkeletonPoint point : skeleton.points) {
            // Finish rotation maths
            //newPoint.translate(new Vector3f((float) point.posX, (float) point.posY, (float) point.posZ));
            //SekCPhysics.logger.info(entity.rotationYaw);
            Vec3d vec = new Vec3d(point.posX, point.posY, point.posZ);
            vec.rotateYaw((float) Math.toRadians(-entity.rotationYaw));
            point.setPosition(vec.x, vec.y, vec.z);
        }
    }

    public void initTrackers(ModelBase model) {
        trackersRegistered = true;
    }

    protected void addVertexTracker(ModelRenderer part, SkeletonPoint anchor, SkeletonPoint pointTo) {
        trackerHashmap.put(part, new TrackerVertex(part, anchor, pointTo));
    }

    private void addTriangleTracker(ModelRenderer part, Triangle triangle) {
        trackerHashmap.put(part, new TrackerTriangle(part, triangle));
    }

    protected void addTriangleTrackerRot(ModelRenderer part, Triangle triangle, float rotateOffsetX, float rotateOffsetY, float rotateOffsetZ) {
        trackerHashmap.put(part, new TrackerTriangle(part, triangle, rotateOffsetX, rotateOffsetY, rotateOffsetZ));
    }
}
