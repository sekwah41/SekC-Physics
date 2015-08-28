package com.sekwah.sekcphysics.ragdoll.parts;

import com.sekwah.sekcphysics.ragdoll.Point;

/**
 * Created by sekawh on 8/4/2015.
 * Triangles will be used to store rotations and calculate the constraints, if the triangles werent added then you could
 * only figure out the direction of a link and not the rotation which would make the render part pretty hard.
 * Also more control over rotational constraints, however i will try to add some rotational constraints like a ball and socket
 * joint. so if loads are needed for a rope. however its rotation will be useless. Try making stuff like ropes for physics
 * tests. So try making a better leads for horses as a test :3.
 *
 * Actually thinking about it now, that would be useful for stuff like keeping the top of the arm a distance away from
 * the torso as no rotation is needed for rendering but it would be nice for a bit of moving x and y a little like
 * shoulders.
 */
public class Triangle {

    public SkeletonPoint[] points = new SkeletonPoint[3];

    public Triangle(SkeletonPoint pos1, SkeletonPoint pos2, SkeletonPoint pos3){
        this.points[0] = pos1;
        this.points[1] = pos2;
        this.points[2] = pos3;
    }

    public void calcRotation(){

        // points being used to store vectors

        Point up = normalize(points[1].toPoint(), points[0].toPoint());
        Point right = subtract(points[2].toPoint(), points[0].toPoint()); // temporarily stores a value
        Point facing = normalize(new Point(0,0,0), crossProduct(up, right));
        right = normalize(new Point(0, 0, 0), crossProduct(facing, right));

       /* XYZ up, right, facing;
        up = Normalize(points[1]->position - points[0]->position);
        right = points[2]->position - points[0]->position;
        facing = Normalize(CrossProduct(up, right));
        right = Normalize(CrossProduct(facing, up));

        orientation.calcFromBasis(right,up,facing);*/
    }

    private Point crossProduct(Point point1, Point point2) {
        double currentLength1 = Math.sqrt(Math.pow(point1.getX(), 2) + Math.pow(point1.getY(), 2) + Math.pow(point1.getZ(), 2));

        double currentLength2 = Math.sqrt(Math.pow(point2.getX(), 2) + Math.pow(point2.getY(), 2) + Math.pow(point2.getZ(), 2));

        double posX = point1.getY() * point2.getZ() - point1.getZ() * point2.getY();

        double posY = point1.getZ() * point2.getX() - point1.getX() * point2.getZ();

        double posZ = point1.getX() * point2.getY() - point1.getY() * point2.getX();

        return new Point(posX, posY, posZ);
    }

    private Point subtract(Point point1, Point point2) {
        return new Point(point1.getX() - point2.getX(), point1.getY() - point2.getZ(), point1.getZ() - point2.getZ());
    }

    private Point normalize(Point point1, Point point2) {
        double currentLength = Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2) + Math.pow(point1.getZ() - point2.getZ(), 2));

        return new Point((point1.getX() - point2.getX()) / (float) currentLength,
                (point1.getY() - point2.getY()) / (float) currentLength, (point1.getZ() - point2.getZ()) / (float) currentLength);
    }

}
