package com.sekwah.sekcphysics.ragdoll.parts;

/**
 * Created by sekawh on 8/6/2015.
 *
 * Creates links between points for physics, if its fixed its like creating sticks between the points
 * and if the constraint is not then it can be any length in a range sorta like a unpowered piston or some sorta
 * sliding thing.
 */
public class Constraint {

    // so far will only be working with rigid constraints,
    //public boolean isRigid = true;

    // if using a non rigid then
    // public float[] length = new float[2];

    // but till they are needed

    /**
     * The points the constraint is attached to so at each end of the constraint.
     */
    public SkeletonPoint[] end = new SkeletonPoint[2];

    public Constraint(SkeletonPoint start, SkeletonPoint end){
        this.end[0] = start;
        this.end[1] = end;
    }

    // TODO add rotation constraints to single links which arnt triangles, would be good for simulating shoulders
    // but for now just add some stuff so that way its sorta rough physics(shoulders will have no leeway due to constraints
    //public boolean hasAngleConstraint = false;

    /**
     * 0 is x
     * 1 is y
     *
     * maybe make a seperate set of objects for angle constraints and stuff. Try to make it as efficient as possible.
     */
    //public float[] angleConstraint = new float[2];

    /**
     * Will be calculated when the constraints are created to stop any potential problems(although itll be fun to test
     * what happens if the number is massively out or something else happens.
     */
    public float length;


}
