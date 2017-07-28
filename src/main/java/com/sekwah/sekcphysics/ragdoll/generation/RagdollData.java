package com.sekwah.sekcphysics.ragdoll.generation;

import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Stores Ragdoll data for creation
 *
 * Created by sekwah41 on 28/06/2017.
 */
public class RagdollData {

    private HashMap<String, PointD> pointHashMap = new HashMap<>();

    private HashMap<String, TriangleData> triangleHashMap = new HashMap<>();
    
    private ModelBase ragdollModel;

    private ResourceLocation entityTexture;

    /**
     * Has no references back so best to do it this way for quick use.
     */
    private LinkedList<ConstraintData> constraintLinkedList = new LinkedList<ConstraintData>();

    private HashMap<String, Triangle> trangleHashMap = new HashMap<>();

    public float centerHeightOffset;

    public RagdollData() {
    }

    public void setSkeletonPoint(String pointName, double x, double y, double z) {
        this.pointHashMap.put(pointName, new PointD(x,y,z));
    }

    /*public void setTriangle(String triangleName, String point1, String point2, String point3) throws RagdollInvalidDataException {
        this.trangleHashMap.put(triangleName, new Triangle(this.getPoint(point1),
                this.getPoint(point2), this.getPoint(point3)));
    }*/

    public void addConstraint(String point1, String point2) throws RagdollInvalidDataException {
        this.constraintLinkedList.add(new ConstraintData(this.checkPoint(point1), this.checkPoint(point2)));
    }

    public PointD getPoint(String point) throws RagdollInvalidDataException {
        PointD skeletonPoint = this.pointHashMap.get(point);
        if(skeletonPoint == null) {
            throw new RagdollInvalidDataException("Invalid Skeleton Point Selected");
        }
        return skeletonPoint;
    }

    public String checkPoint(String point) throws RagdollInvalidDataException {
        if(this.pointHashMap.get(point) == null) {
            throw new RagdollInvalidDataException("Invalid Skeleton Point Selected");
        }
        return point;
    }

    public PointD[] getPoints() {
        return this.pointHashMap.values().toArray(new PointD[0]);
    }

    public ConstraintData[] getConstraints() {
        return this.constraintLinkedList.toArray(new ConstraintData[0]);
    }

    public HashMap<String, PointD> getPointMap(){
        return this.pointHashMap;
    }

    public void addTriangle(String name, String point1, String point2, String point3) throws RagdollInvalidDataException {
        if(this.triangleHashMap.get(name) != null) {
            throw new RagdollInvalidDataException("Already triangle with that name");
        }
        this.triangleHashMap.put(name, new TriangleData(point1, point2, point3));
    }

    public TriangleData[] getTriangles() {
        return this.triangleHashMap.values().toArray(new TriangleData[0]);
    }
}
