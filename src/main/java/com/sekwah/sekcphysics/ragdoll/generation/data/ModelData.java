package com.sekwah.sekcphysics.ragdoll.generation.data;

import com.sekwah.sekcphysics.ragdoll.generation.data.tracker.TriangleTrackerData;
import com.sekwah.sekcphysics.ragdoll.generation.data.tracker.VertexTrackerData;
import net.minecraft.client.model.ModelBase;

/**
 * Stores a copy of the model as well as data to link it to the ragdoll on creation
 *
 * Created by sekwah41 on 31/07/2017.
 */
public class ModelData {

    private ModelBase baseModel;

    private VertexTrackerData[] vertexTrackers = new VertexTrackerData[0];

    private TriangleTrackerData[] triangleTrackers = new TriangleTrackerData[0];

    public ModelData(ModelBase baseModel) {
        this.baseModel = baseModel;
    }

    public ModelBase getBaseModel() {
        return baseModel;
    }

    public void setVertexTrackers(VertexTrackerData[] vertexTrackerData) {
        this.vertexTrackers = vertexTrackerData;
    }

    public void setTriangleTrackers(TriangleTrackerData[] triangleTrackerData) {
        this.triangleTrackers = triangleTrackerData;
    }

    public VertexTrackerData[] getVertexTrackers() {
        return vertexTrackers;
    }

    public TriangleTrackerData[] getTriangleTrackers() {
        return triangleTrackers;
    }
}
