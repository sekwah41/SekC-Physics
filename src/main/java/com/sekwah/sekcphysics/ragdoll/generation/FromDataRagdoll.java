package com.sekwah.sekcphysics.ragdoll.generation;

import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import com.sekwah.sekcphysics.ragdoll.parts.Constraint;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;

/**
 * Created by sekwah41 on 30/06/2017.
 */
public class FromDataRagdoll extends BaseRagdoll {

    public FromDataRagdoll(RagdollData creationData) {
        super(creationData.centerHeightOffset);

        for(SkeletonPoint point : creationData.getSkeletonPoints()) {
            this.skeleton.points.add(point.clone());
        }

        for(Constraint constraint : creationData.getConstraints()) {
            this.skeleton.constraints.add(constraint);
        }


    }

}
