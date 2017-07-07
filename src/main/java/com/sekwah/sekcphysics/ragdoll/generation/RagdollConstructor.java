package com.sekwah.sekcphysics.ragdoll.generation;

import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.ragdoll.parts.Constraint;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Build the data for the ragdoll from the RagdollData into new ragdoll data
 *
 * Only constructs the data that needs to be cloned though such as constraints. The rest of the data which is immutable
 * is from the RagdollData
 *
 * Created by sekwah41 on 06/07/2017.
 */
public class RagdollConstructor {

    private HashMap<String, SkeletonPoint> skeletonPointHashMap = new HashMap<>();

    private LinkedList<Constraint> constraintLinkedList = new LinkedList<>();

    public RagdollConstructor(RagdollData creationData) {

        for(Map.Entry<String, PointD> entry : creationData.getPointMap().entrySet()) {
            PointD point = entry.getValue();
            this.skeletonPointHashMap.put(entry.getKey(), new SkeletonPoint(point.x, point.y, point.z));
        }

        for(ConstraintData constraintData : creationData.getConstraints()) {
            this.constraintLinkedList.add(new Constraint(this.getSkeletonPoint(constraintData.part1),
                    this.getSkeletonPoint(constraintData.part2)));
        }


    }

    /**
     * Data should never be null at this point, the null parts were from before while making the data for this
     * in RagdollData
     * @param point
     * @return
     */
    public SkeletonPoint getSkeletonPoint(String point) {
        SkeletonPoint skeletonPoint = this.skeletonPointHashMap.get(point);
        return skeletonPoint;
    }

    public SkeletonPoint[] getSkeletonPoints() {
        return this.skeletonPointHashMap.values().toArray(new SkeletonPoint[0]);
    }

    public Constraint[] getConstraints() {
        return this.constraintLinkedList.toArray(new Constraint[0]);
    }
}
