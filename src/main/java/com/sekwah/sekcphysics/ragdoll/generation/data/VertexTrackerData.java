package com.sekwah.sekcphysics.ragdoll.generation.data;

/**
 * Created by sekwah41 on 30/07/2017.
 */
public class VertexTrackerData extends TrackerData {

    public final String anchor;
    public final String pointTo;

    public VertexTrackerData(String anchor, String pointTo) {
        this.anchor = anchor;
        this.pointTo = pointTo;
    }

}
