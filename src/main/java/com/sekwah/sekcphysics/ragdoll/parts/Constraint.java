package com.sekwah.sekcphysics.ragdoll.parts;

import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.PointD;

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
    public double length;

    /**
     * The points the constraint is attached to so at each end of the constraint.
     */
    public SkeletonPoint[] end = new SkeletonPoint[2];

    public Constraint(SkeletonPoint start, SkeletonPoint end){
        this.end[0] = start;
        this.end[1] = end;
        this.length = Math.sqrt(Math.pow(start.posX - end.posX, 2) + Math.pow(start.posY - end.posY, 2) + Math.pow(start.posZ - end.posZ, 2));
    }


    public void apply(EntityRagdoll entity) {

        PointD averageLoc = new PointD((end[0].posX + end[1].posX) / 2F,(end[0].posY + end[1].posY) / 2F,(end[0].posZ + end[1].posZ) / 2F);

        double currentLength = Math.sqrt(Math.pow(end[0].posX - end[1].posX, 2) + Math.pow(end[0].posY - end[1].posY, 2) + Math.pow(end[0].posZ - end[1].posZ, 2));
        // If its already the correct length theres no point in recalculating
        if(currentLength == length){
            return;
        }
        if(currentLength == 0){
            currentLength = 0.01;
        }
        PointD direction = new PointD((end[0].posX - end[1].posX) / (float) currentLength,
                (end[0].posY - end[1].posY) / (float) currentLength, (end[0].posZ - end[1].posZ) / (float) currentLength);

        //System.out.println(averageLoc);

        double halfLength = length / 2F;

        //System.out.println("");

        //System.out.println(length);

        end[0].moveTo(entity, (float) (averageLoc.getX() + (direction.getX() * halfLength)), (float) (averageLoc.getY() + (direction.getY() * halfLength)),
                (float) (averageLoc.getZ() + (direction.getZ() * halfLength)));

        end[1].moveTo(entity, (float) (averageLoc.getX() - (direction.getX() * halfLength)), (float) (averageLoc.getY() - (direction.getY() * halfLength)),
                (float) (averageLoc.getZ() - (direction.getZ() * halfLength)));

        //System.out.println(Math.sqrt(Math.pow(end[0].posX - end[1].posX, 2) + Math.pow(end[0].posY - end[1].posY, 2) + Math.pow(end[0].posZ - end[1].posZ, 2)));

        /*System.out.println("");
        System.out.println(direction.getX());
        System.out.println();*/


        // double check all the maths and everything, write it all out and find what is happening. Im not sure if its just a faulty physics system or what but somethings wrong
        // The length isnt worksing correct
        // Try taking 2 example points and write the working on paper and check them against it

        // end[0] and end[1] are pointers to the points at each end of the constraint
        //XYZ dir = Normalize(end[1]->position - end[0]->position);
        //XYZ avg = (end[1]->position + end[0]->position)/2;
        //end[0]->add_new_position(avg-dir*(length/2));
        //end[1]->add_new_position(avg+dir*(length/2));
    }

    public void calc(EntityRagdoll entity) {

        PointD averageLoc = new PointD((end[0].newPosX + end[1].newPosX) / 2F,(end[0].newPosY + end[1].newPosY) / 2F,(end[0].newPosZ + end[1].newPosZ) / 2F);

        double currentLength = Math.sqrt(Math.pow(end[0].newPosX - end[1].newPosX, 2) + Math.pow(end[0].newPosY - end[1].newPosY, 2) + Math.pow(end[0].newPosZ - end[1].newPosZ, 2));
        // If its already the correct length theres no point in recalculating
        if(currentLength == length){
            return;
        }
        if(currentLength == 0){
            currentLength = 0.01;
        }
        PointD direction = new PointD((end[0].newPosX - end[1].newPosX) / (float) currentLength,
                (end[0].newPosY - end[1].newPosY) / (float) currentLength, (end[0].newPosZ - end[1].newPosZ) / (float) currentLength);

        //System.out.println(averageLoc);

        double halfLength = length / 2F;

        //System.out.println("");

        //System.out.println(length);

        end[0].setNewPos((float) (averageLoc.getX() + (direction.getX() * halfLength)), (float) (averageLoc.getY() + (direction.getY() * halfLength)),
                (float) (averageLoc.getZ() + (direction.getZ() * halfLength)));

        end[1].setNewPos((float) (averageLoc.getX() - (direction.getX() * halfLength)), (float) (averageLoc.getY() - (direction.getY() * halfLength)),
                (float) (averageLoc.getZ() - (direction.getZ() * halfLength)));
    }
}