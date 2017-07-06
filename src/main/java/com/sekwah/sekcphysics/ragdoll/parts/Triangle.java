package com.sekwah.sekcphysics.ragdoll.parts;

import com.sekwah.sekcphysics.ragdoll.location.PointD;

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

    public Triangle(SkeletonPoint pos1, SkeletonPoint pos2, SkeletonPoint pos3) {
        this.points[0] = pos1;
        this.points[1] = pos2;
        this.points[2] = pos3;
    }

    public void calcRotation() {

        // points being used to store vectors

        PointD up = normalize(points[1].toPoint(), points[0].toPoint());
        PointD right = subtract(points[2].toPoint(), points[0].toPoint()); // temporarily stores a value
        PointD facing = normalize(new PointD(0,0,0), crossProduct(up, right));
        right = normalize(new PointD(0, 0, 0), crossProduct(facing, right));

       /* XYZ up, right, facing;
        up = Normalize(points[1]->position - points[0]->position);
        right = points[2]->position - points[0]->position;
        facing = Normalize(CrossProduct(up, right));
        right = Normalize(CrossProduct(facing, up));

        orientation.calcFromBasis(right,up,facing);*/


    }

    private PointD crossProduct(PointD point1, PointD point2) {
        double currentLength1 = Math.sqrt(Math.pow(point1.getX(), 2) + Math.pow(point1.getY(), 2) + Math.pow(point1.getZ(), 2));

        double currentLength2 = Math.sqrt(Math.pow(point2.getX(), 2) + Math.pow(point2.getY(), 2) + Math.pow(point2.getZ(), 2));

        double posX = point1.getY() * point2.getZ() - point1.getZ() * point2.getY();

        double posY = point1.getZ() * point2.getX() - point1.getX() * point2.getZ();

        double posZ = point1.getX() * point2.getY() - point1.getY() * point2.getX();

        return new PointD(posX, posY, posZ);
    }

    private PointD subtract(PointD point1, PointD point2) {
        return new PointD(point1.getX() - point2.getX(), point1.getY() - point2.getZ(), point1.getZ() - point2.getZ());
    }

    private PointD normalize(PointD point1, PointD point2) {
        double currentLength = Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2) + Math.pow(point1.getZ() - point2.getZ(), 2));

        return new PointD((point1.getX() - point2.getX()) / (float) currentLength,
                (point1.getY() - point2.getY()) / (float) currentLength, (point1.getZ() - point2.getZ()) / (float) currentLength);
    }

    // Get the normalised vector for the direction.
    public PointD getDirectionNorm() {
        // Center between left and right
        PointD averageLoc = new PointD((points[1].posX + points[2].posX) / 2F,(points[1].posY + points[2].posY) / 2F,(points[1].posZ + points[2].posZ) / 2F);

        double currentLength = Math.sqrt(Math.pow(points[0].posX - averageLoc.getX(), 2) + Math.pow(points[0].posY - averageLoc.getY(), 2) + Math.pow(points[0].posZ - averageLoc.getZ(), 2));
        // Direction from the base directly down the center of the triangle
        PointD direction = new PointD((averageLoc.getX() - points[0].posX) / (float) currentLength,
                (averageLoc.getY() - points[0].posY) / (float) currentLength, (averageLoc.getZ() - points[0].posZ) / (float) currentLength);

        // Calculate angle around the direction, may be best way to calculate orentation and make basic constraints on
        // but using the direction and getting an x and y(or whichever 2) to get the direction aligned before rotation
        // would be great for rendering :)
        return direction;
    }

    public PointD getDirection() {
        // Center between left and right vector
        PointD averageLoc = new PointD((points[1].posX + points[2].posX) / 2F,(points[1].posY + points[2].posY) / 2F,(points[1].posZ + points[2].posZ) / 2F);

        // Direction from the base directly down the center of the triangle
        PointD direction = new PointD((averageLoc.getX() - points[0].posX),
                (averageLoc.getY() - points[0].posY), (averageLoc.getZ() - points[0].posZ));

        return direction;
    }

    /**
     * Calculate the rotated angle around the direction.
     * @return angle, either 0 to 360 or 0 to 2Pi
     */
    public float getAngle() {
        return 0;
    }

    public PointD getNormal() {

        PointD basePoint = this.points[0].toPoint();
        PointD vec1 = basePoint.clone().sub(this.points[1].toPoint());
        PointD vec2 = basePoint.clone().sub(this.points[2].toPoint());
        double normX = vec1.getY() * vec2.getZ() - vec1.getZ() * vec2.getY();
        double normY = vec1.getZ() * vec2.getX() - vec1.getX() * vec2.getZ();
        double normZ = vec1.getX() * vec2.getY() - vec1.getY() * vec2.getX();

        return new PointD(normX, normY, normZ);
    }
}
