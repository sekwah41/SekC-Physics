package com.sekwah.sekcphysics.ragdoll;

import com.sekwah.sekcphysics.ragdoll.parts.Constraint;
import com.sekwah.sekcphysics.ragdoll.parts.Skeleton;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;

/**
 * Created by sekawh on 8/5/2015.
 */
public class BipedRagdoll extends BaseRagdoll {

    public SkeletonPoint leftLegBot;
    public SkeletonPoint rightLegBot;
    public SkeletonPoint centerTorso;
    public SkeletonPoint leftShoulder;
    public SkeletonPoint rightShoulder;
    public SkeletonPoint leftLegTop;
    public SkeletonPoint rightLegTop;
    public SkeletonPoint rightArm;
    public SkeletonPoint leftArm;

    public BipedRagdoll(){
        super(1.4f);
        skeleton = new Skeleton();

        centerHeightOffset = 24;

        centerTorso = new SkeletonPoint(0f,0f,0f);

        leftShoulder = new SkeletonPoint(5f,-2f,0f);

        rightShoulder = new SkeletonPoint(-5f,-2f,0f);

        leftLegTop = new SkeletonPoint(2f,-12f,0f);

        rightLegTop = new SkeletonPoint(-2f,-12f,0f);

        rightArm = new SkeletonPoint(-6f,-11f,0f);

        leftArm = new SkeletonPoint(6f,-11f,0f);

        leftLegBot = new SkeletonPoint(2f, -23f, 0f);

        rightLegBot = new SkeletonPoint(-2f, -23f, 0f);

        skeleton.points.add(centerTorso);
        skeleton.points.add(leftShoulder);
        skeleton.points.add(rightShoulder);
        skeleton.points.add(leftLegTop);
        skeleton.points.add(rightLegTop);

        skeleton.points.add(leftArm);
        skeleton.points.add(rightArm);

        skeleton.points.add(leftLegBot);
        skeleton.points.add(rightLegBot);

        skeleton.constraints.add(new Constraint(centerTorso, leftShoulder));
        skeleton.constraints.add(new Constraint(centerTorso, rightShoulder));

        skeleton.constraints.add(new Constraint(centerTorso, leftLegTop));
        skeleton.constraints.add(new Constraint(centerTorso, rightLegTop));
        skeleton.constraints.add(new Constraint(leftLegTop, rightLegTop));

        skeleton.constraints.add(new Constraint(leftLegTop, leftShoulder));
        skeleton.constraints.add(new Constraint(rightLegTop, rightShoulder));

        skeleton.constraints.add(new Constraint(leftShoulder, leftArm));
        skeleton.constraints.add(new Constraint(rightShoulder, rightArm));

        skeleton.constraints.add(new Constraint(leftLegTop, leftLegBot));
        skeleton.constraints.add(new Constraint(rightLegTop, rightLegBot));



        // write code to add a list to the array, it makes it easier.

    }

}
