package com.sekwah.sekcphysics.ragdoll.generation;

import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;

import java.util.HashMap;

/**
 * Created by sekwah41 on 28/06/2017.
 */
public class RagdollData {

    private HashMap<String, SkeletonPoint> skeletonPointHashMap = new HashMap<String, SkeletonPoint>();

    public RagdollData(){

    }

    public void setSkeletonPoint(String pointName, float x, float y, float z){
        this.skeletonPointHashMap.put(pointName, new SkeletonPoint(x,y,z));
    }


}
