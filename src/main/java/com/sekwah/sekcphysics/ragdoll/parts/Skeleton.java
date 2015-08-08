package com.sekwah.sekcphysics.ragdoll.parts;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by sekawh on 8/4/2015.
 */
public class Skeleton {

    //public SkeletonPoint[] points;

    public List<SkeletonPoint> points = new ArrayList<SkeletonPoint>();

    //public Constraint[] constraints;

    public List<Constraint> constraints = new ArrayList<Constraint>();

    //public Triangle[] triangles;

    public List<Triangle> triangles = new ArrayList<Triangle>();

    private boolean frozen = false;

    // Store a velocity which is the last position take away the current position but also add it so you can add velocity
    //  because if its added for a single update itll carry on that motion. So stuff like explosions or an arrow to the
    //  knee. Also if a player walks around a ragdoll you can add sorta a magnetic push for parts near entities away from the player.
    //  but do that after all physics works :3 (also for collisions with blocks, use the moving points like arrows do and stuff
    //  try to use current mc stuff to figure out where it can and cant go.

    public Skeleton(){

    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean isFrozen) {
        this.frozen = isFrozen;
    }

    /**
     * Applies the physics before constraints are taken into account.
     */
    public void update(){

    }

    /**
     * Stops points being too close or far from each other
     */
    public void updateLengthConstraints(){

    }

    /**
     * Moves the points if they are outside of the rotation bounds, we cant have a leg pushing into your torso.
     */
    public void updateRotationConstraints(){

    }

    /**
     * Renders all the constraints as lines, also maybe add the linked triangles next. Create a skleton for the
     */
    public void renderSkeletonDebug(){
        for(Constraint constraint : constraints){
            glDisable(GL11.GL_TEXTURE_2D);
            glColor3f(0.0f, 1.0f, 0.2f);
            drawLine(constraint.end[0], constraint.end[1]);
            glColor3f(1f,1f,1f);
            glEnable(GL11.GL_TEXTURE_2D);
        }
    }

    public void drawLine(SkeletonPoint point, SkeletonPoint point2){
        glBegin(GL_LINE_STRIP);
        glVertex3f(point.posX, point.posY, point.posZ);
        glVertex3f(point2.posX, point2.posY, point2.posZ);
        glEnd();
    }
}
