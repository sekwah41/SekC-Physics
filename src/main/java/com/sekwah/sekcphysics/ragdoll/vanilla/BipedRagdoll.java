package com.sekwah.sekcphysics.ragdoll.vanilla;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.ragdoll.BaseRagdoll;
import com.sekwah.sekcphysics.ragdoll.parts.Constraint;
import com.sekwah.sekcphysics.ragdoll.parts.Skeleton;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.TrackerTriangle;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.TrackerVertex;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by sekawh on 8/5/2015.
 */
public class BipedRagdoll extends BaseRagdoll {

    private final Triangle headTriangle;

    private final Triangle bodyTriangle;

    public SkeletonPoint headLeft;
    public SkeletonPoint headRight;

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

        headLeft = new SkeletonPoint(3f,7f,0f);

        headRight = new SkeletonPoint(-3f,7f,0f);

        leftShoulder = new SkeletonPoint(5f,-2f,0f);

        rightShoulder = new SkeletonPoint(-5f,-2f,0f);

        leftLegTop = new SkeletonPoint(2f,-12f,0f);

        rightLegTop = new SkeletonPoint(-2f,-12f,0f);

        /*rightArm = new SkeletonPoint(-6f,-11f,0f);

        leftArm = new SkeletonPoint(6f,-11f,0f);*/

        rightArm = new SkeletonPoint(-14f,-2f,1f);

        leftArm = new SkeletonPoint(14f,-2f,1f);

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

        skeleton.points.add(headLeft);
        skeleton.points.add(headRight);





        skeleton.constraints.add(new Constraint(centerTorso,headLeft));
        skeleton.constraints.add(new Constraint(centerTorso,headRight));
        skeleton.constraints.add(new Constraint(headLeft,headRight));

        skeleton.constraints.add(new Constraint(leftShoulder, leftArm));
        skeleton.constraints.add(new Constraint(rightShoulder, rightArm));

        skeleton.constraints.add(new Constraint(leftLegTop, leftLegBot));
        skeleton.constraints.add(new Constraint(rightLegTop, rightLegBot));




        skeleton.constraints.add(new Constraint(centerTorso, leftShoulder));
        skeleton.constraints.add(new Constraint(centerTorso, rightShoulder));

        skeleton.constraints.add(new Constraint(centerTorso, leftLegTop));
        skeleton.constraints.add(new Constraint(centerTorso, rightLegTop));
        skeleton.constraints.add(new Constraint(leftLegTop, rightLegTop));

        skeleton.constraints.add(new Constraint(leftLegTop, leftShoulder));
        skeleton.constraints.add(new Constraint(rightLegTop, rightShoulder));

        skeleton.constraints.add(new Constraint(leftShoulder, rightShoulder));

        skeleton.constraints.add(new Constraint(rightLegTop, leftShoulder));

        skeleton.constraints.add(new Constraint(leftLegTop, rightShoulder));

        headTriangle = new Triangle(centerTorso, headLeft, headRight);

        skeleton.triangles.add(headTriangle);

        bodyTriangle = new Triangle(centerTorso, leftLegTop, rightLegTop);

        skeleton.triangles.add(bodyTriangle);

        //skeleton.triangles.add(new Triangle(centerTorso, leftLegTop, leftShoulder));

        //skeleton.triangles.add(new Triangle(centerTorso, rightLegTop, rightShoulder));

        //trackers.add(new TrackerVertex())



        // write code to add a list to the array, it makes it easier.

    }

    public void initTrackers(ModelBase model) {
        super.initTrackers(model);
        if(model instanceof ModelBiped){
            ModelBiped modelBiped = (ModelBiped) model;
            this.addVertexTracker(modelBiped.bipedRightArm, this.rightShoulder, this.rightArm);
            this.addVertexTracker(modelBiped.bipedLeftArm, this.leftShoulder, this.leftArm);

            this.addVertexTracker(modelBiped.bipedRightLeg, this.rightLegTop, this.rightLegBot);
            this.addVertexTracker(modelBiped.bipedLeftLeg, this.leftLegTop, this.leftLegBot);

            this.addTriangleTrackerRot(modelBiped.bipedBody, this.bodyTriangle, (float) Math.PI, 0, 0);
            this.addTriangleTrackerRot(modelBiped.bipedHead, this.headTriangle, 0, 0, 0);
            //this.addTriangleTracker(modelBiped.bipedHead, this.headTriangle);
        }
        else{
            SekCPhysics.logger.error("Model type invalid!");
        }
    }

    private void addVertexTracker(ModelRenderer part, SkeletonPoint anchor, SkeletonPoint pointTo){
        trackerHashmap.put(part, new TrackerVertex(part, anchor, pointTo));
    }

    private void addTriangleTracker(ModelRenderer part, Triangle triangle){
        trackerHashmap.put(part, new TrackerTriangle(part, triangle));
    }

    private void addTriangleTrackerRot(ModelRenderer part, Triangle triangle, float rotateOffsetX, float rotateOffsetY, float rotateOffsetZ){
        trackerHashmap.put(part, new TrackerTriangle(part, triangle, rotateOffsetX, rotateOffsetY, rotateOffsetZ));
    }

}
