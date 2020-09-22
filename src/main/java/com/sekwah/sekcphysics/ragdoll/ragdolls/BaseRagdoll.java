package com.sekwah.sekcphysics.ragdoll.ragdolls;

import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.maths.VectorMaths;
import com.sekwah.sekcphysics.ragdoll.parts.Skeleton;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.*;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sekawh on 8/4/2015.
 */
public class BaseRagdoll {


    public final Model baseModel;

    public Map<ModelRenderer, Tracker> trackerHashmap = new HashMap<>();

    public boolean trackersRegistered = false;

    // Current skeleton position and shape
    public Skeleton skeleton;

    // offset from the bottom of the desired entity to the main point of the ragdoll
    public double centerHeightOffset;

    public ResourceLocation resourceLocation;

    public BaseRagdoll(float centerHeightOffset, Model baseModel) {

        this.baseModel = baseModel;

        this.skeleton = new Skeleton();

        this.centerHeightOffset = centerHeightOffset;
    }

    public void rotateRagdoll(float rotYaw) {
        this.skeleton.rotate(rotYaw);
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

    public void setStanceToEntity(LivingEntity entity) {
        for(SkeletonPoint point : skeleton.points) {
            PointD vec = new PointD(point.posX, point.posY, point.posZ);
            VectorMaths.rotateOriginY(Math.toRadians(-entity.rotationYaw), vec);
            //vec.rotateYaw((float) Math.toRadians(-entity.rotationYaw));
            point.setPosition(vec.x, vec.y, vec.z);
        }
    }

    public void initTrackers(Model model) {
        trackersRegistered = true;
    }

    protected void addVertexTracker(ModelRenderer part, SkeletonPoint anchor, SkeletonPoint pointTo, float scale) {
        if(scale == 1) {
            trackerHashmap.put(part, new TrackerVertex(part, anchor, pointTo));
        }
        else {
            trackerHashmap.put(part, new TrackerVertexScaled(part, anchor, pointTo, scale));
        }
    }

    protected void addTriangleTracker(ModelRenderer part, Triangle triangle, float scale) {
        if(scale == 1) {
            trackerHashmap.put(part, new TrackerTriangle(part, triangle));
        }
        else {
            trackerHashmap.put(part, new TrackerTriangleScaled(part, triangle, scale));
        }
    }

    protected void addTriangleTracker(ModelRenderer part, Triangle triangle, float rotateOffsetX, float rotateOffsetY, float rotateOffsetZ, float scale) {
        if(scale == 1) {
            trackerHashmap.put(part, new TrackerTriangle(part, triangle, rotateOffsetX, rotateOffsetY, rotateOffsetZ));
        }
        else {
            trackerHashmap.put(part, new TrackerTriangleScaled(part, triangle, rotateOffsetX, rotateOffsetY, rotateOffsetZ, scale));
        }
    }

    public boolean isActive() {
        return this.skeleton.isActive();
    }

    public int activeStatus() {
        //System.out.println(this.skeleton.updateCount);
        if(this.isActive()) {
            return 0;
        }
        else {
            return this.skeleton.updateCount == 1 ? 2 : 1;
        }
    }
}
