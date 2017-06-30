package com.sekwah.sekcphysics.ragdoll.generation;

import com.sekwah.sekcphysics.ragdoll.parts.Constraint;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by sekwah41 on 28/06/2017.
 */
public class RagdollData {

    private HashMap<String, SkeletonPoint> skeletonPointHashMap = new HashMap<>();

    private ModelBase ragdollModel;

    private ResourceLocation entityTexture;

    /**
     * Has no references back so best to do it this way for quick use.
     */
    private LinkedList<Constraint> constraintLinkedList = new LinkedList<>();

    private HashMap<String, Triangle> trangleHashMap = new HashMap<>();

    public RagdollData() {
    }

    public void setSkeletonPoint(String pointName, float x, float y, float z){
        this.skeletonPointHashMap.put(pointName, new SkeletonPoint(x,y,z));
    }

    public void setTriangle(String triangleName, String point1, String point2, String point3) throws RagdollInvalidDataException {
        this.trangleHashMap.put(triangleName, new Triangle(this.getSkeletonPoint(point1),
                this.getSkeletonPoint(point2), this.getSkeletonPoint(point3)));
    }

    public SkeletonPoint getSkeletonPoint(String point) throws RagdollInvalidDataException {
        SkeletonPoint skeletonPoint = this.skeletonPointHashMap.get(point);
        if(skeletonPoint == null) {
            throw new RagdollInvalidDataException("Invalid Skeleton Point");
        }
        return skeletonPoint;
    }

}
