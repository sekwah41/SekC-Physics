package com.sekwah.sekcphysics.cliententity.render;

import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.Point;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by sekawh on 8/2/2015.
 */
public class RenderRagdoll extends Render {
    // add code to render the lines between the links of nodes, and also the option to render boxes at each node.
    // this entity will never have any rotation from the entity but rather rotations based on the physics positions

    public void drawLine(Point point, Point point2){
        glColor3f(0.0f, 1.0f, 0.2f);
        glBegin(GL_LINE_STRIP);

        glVertex3d(point.getX(), point.getY(), point.getY());
        glVertex3d(point2.getX(), point2.getY(), point2.getY());
        glEnd();
        glColor3f(1f,1f,1f);
    }

    @Override
    public void doRender(Entity entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        if(entity instanceof EntityRagdoll){
            EntityRagdoll entityRagdoll = (EntityRagdoll) entity;
            GL11.glPushMatrix();

            // Sets the position offset for rendering
            GL11.glTranslated(p_76986_2_, p_76986_4_, p_76986_6_);

            entityRagdoll.ragdoll.skeleton.renderSkeletonDebug();

            GL11.glPopMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }
}
