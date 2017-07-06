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

        RagdollConstructor ragdoll = new RagdollConstructor(creationData);

        for(SkeletonPoint point : ragdoll.getSkeletonPoints()) {
            this.skeleton.points.add(point);
        }

        for(Constraint constraint : ragdoll.getConstraints()) {
            this.skeleton.constraints.add(constraint);
        }


    }

}
