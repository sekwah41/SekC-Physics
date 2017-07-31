package com.sekwah.sekcphysics.ragdoll.generation.data;

import java.util.HashMap;

/**
 * Store data about the entity
 *
 * Created by sekwah41 on 29/07/2017.
 */
public class ModelConstructData {

    /**
     * Can only contain string int boolean float double and long for now.
     *
     * More can be added as they need to have support added.
     *
     * Add them into the RagdollGenerator
     */
    private Object[] constructData = new Object[0];

    private HashMap<String, VertexTrackerData> vertexTrackers = new HashMap<>();

    private HashMap<String, TriangleTrackerData> triangleTrackers = new HashMap<>();

    private String className;

    /**
     * Ignores null values
     * @param className
     */
    public void setClassName(String className) {
        if(className == null) {
            return;
        }
        this.constructData = new Object[0];
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public Object[] getConstructData() {
        return constructData;
    }

    public void setConstructData(Object[] constructData) {
        this.constructData = constructData;
    }

    public void addVertexTracker(String key, VertexTrackerData vertexTrackerData) {
        this.vertexTrackers.put(key, vertexTrackerData);
    }

    public void addTriangleTracker(String key, TriangleTrackerData trackerData) {
        this.triangleTrackers.put(key, trackerData);
    }

    public VertexTrackerData[] getVertexTrackerData() {
        return this.vertexTrackers.values().toArray(new VertexTrackerData[0]);
    }

    public TriangleTrackerData[] getTriangleTrackerData() {
        return this.triangleTrackers.values().toArray(new TriangleTrackerData[0]);
    }

}
