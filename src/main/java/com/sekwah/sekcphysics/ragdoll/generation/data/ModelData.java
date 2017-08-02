package com.sekwah.sekcphysics.ragdoll.generation.data;

import net.minecraft.client.model.ModelBase;

import java.util.HashMap;

/**
 * Stores a copy of the model as well as data to link it to the ragdoll on creation
 *
 * Created by sekwah41 on 31/07/2017.
 */
public class ModelData {

    private ModelBase baseModel;

    private HashMap<String, VertexTrackerData> vertexTrackers = new HashMap<>();

    private HashMap<String, TriangleTrackerData> triangleTrackers = new HashMap<>();

    public ModelData(ModelBase baseModel) {
        this.baseModel = baseModel;
    }

    public ModelBase getBaseModel() {
        return baseModel;
    }
}
