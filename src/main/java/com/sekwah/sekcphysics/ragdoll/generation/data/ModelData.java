package com.sekwah.sekcphysics.ragdoll.generation.data;

import com.sekwah.sekcphysics.ragdoll.generation.data.tracker.TriangleTrackerData;
import com.sekwah.sekcphysics.ragdoll.generation.data.tracker.VertexTrackerData;
import net.minecraft.class_3879;
import net.minecraft.util.Identifier;

/**
 * Stores a copy of the model as well as data to link it to the ragdoll on creation
 *
 * Created by sekwah41 on 31/07/2017.
 */
public class ModelData {

    private class_3879 baseModel;

    private VertexTrackerData[] vertexTrackers = new VertexTrackerData[0];

    private TriangleTrackerData[] triangleTrackers = new TriangleTrackerData[0];

    private Identifier texture;

    public ModelData(class_3879 baseModel) {
        this.baseModel = baseModel;
    }

    public class_3879 getBaseModel() {
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

    public void setTexture(Identifier texture) {
        this.texture = texture;
    }

    public Identifier getTexture() {
        return texture;
    }
}
