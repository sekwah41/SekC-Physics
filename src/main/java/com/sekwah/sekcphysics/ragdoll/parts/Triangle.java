package com.sekwah.sekcphysics.ragdoll.parts;

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
}
