package com.sekwah.sekcphysics.ragdoll.ragdolls.generated;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollConstructor;
import com.sekwah.sekcphysics.ragdoll.generation.data.ModelData;
import com.sekwah.sekcphysics.ragdoll.generation.data.RagdollData;
import com.sekwah.sekcphysics.ragdoll.generation.data.tracker.TriangleTrackerData;
import com.sekwah.sekcphysics.ragdoll.generation.data.tracker.VertexTrackerData;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;

import java.util.Collections;

/**
 * Create ragdoll from json data.
 *
 * Needs code to be told to run server side or not if server functionality is added
 * Remove the render/tracker code
 *
 * Created by sekwah41 on 30/06/2017.
 */
public class FromDataRagdoll extends BaseRagdoll {



    public FromDataRagdoll(RagdollData creationData) {
        super(creationData.getCenterHeightOffset(), creationData.getModelData().getBaseModel());

        ModelData modelData = creationData.getModelData();

        RagdollConstructor ragdollConstruct = new RagdollConstructor(creationData);

        Collections.addAll(this.skeleton.points, ragdollConstruct.getSkeletonPoints());

        Collections.addAll(this.skeleton.constraints, ragdollConstruct.getConstraints());

        Collections.addAll(this.skeleton.triangles, ragdollConstruct.getTriangles());

        this.resourceLocation = creationData.getModelData().getTexture();

        for(VertexTrackerData vertexTrackerData : modelData.getVertexTrackers()) {
            this.addVertexTracker(vertexTrackerData.getPart(), ragdollConstruct.getSkeletonPoint(vertexTrackerData.anchor),
                    ragdollConstruct.getSkeletonPoint(vertexTrackerData.pointTo), creationData.getScale());
        }

        for(TriangleTrackerData triangleTrackerData : modelData.getTriangleTrackers()) {
            Triangle triangle = ragdollConstruct.getTriangle(triangleTrackerData.tracker);
            if(triangle != null) {
                if(triangleTrackerData.hasRotateData) {
                    this.addTriangleTracker(triangleTrackerData.getPart(), ragdollConstruct.getTriangle(triangleTrackerData.tracker),
                            triangleTrackerData.getRotOffsetX(), triangleTrackerData.getRotOffsetY(), triangleTrackerData.getRotOffsetZ(), creationData.getScale());
                }
                else {
                    this.addTriangleTracker(triangleTrackerData.getPart(), triangle, creationData.getScale());
                }
            }
            else {
                SekCPhysics.logger.error("Null triangle found for:" + triangleTrackerData.tracker);
            }
        }

    }

}
