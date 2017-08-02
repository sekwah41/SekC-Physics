package com.sekwah.sekcphysics.ragdoll.generation.data.tracker;

import com.google.gson.JsonObject;
import net.minecraft.client.model.ModelBox;

/**
 * Created by sekwah41 on 30/07/2017.
 */
public class TriangleTrackerData extends TrackerData {

    public final String tracker;

    public TriangleTrackerData(String partName, String tracker, JsonObject vertexObj) {
        super(partName, vertexObj);
        this.tracker = tracker;
    }

}
