package com.sekwah.sekcphysics.cliententity.render;

import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.PointD;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.Tracker;
import com.sekwah.sekcphysics.ragdoll.vanilla.BipedRagdoll;
import com.sekwah.sekcphysics.ragdoll.vanilla.ZombieRagdoll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by sekawh on 8/2/2015.
 */
public class RenderRagdoll<T extends EntityRagdoll> extends Render<T> {
    // add code to render the lines between the links of nodes, and also the option to render boxes at each node.
    // this entity will never have any rotation from the entity but rather rotations based on the physics positions

    private static final ResourceLocation zombieTexture = new ResourceLocation("textures/entity/zombie/zombie.png");

    private static final ResourceLocation huskTexture = new ResourceLocation("textures/entity/zombie/husk.png");

    private static final ResourceLocation steveTextures = new ResourceLocation("textures/entity/steve.png");

    private ModelBiped bipedModel;
    private ModelBiped bipedModel64;

    private ModelBiped zombieModel;

    private static Minecraft mc = Minecraft.getMinecraft();

    public RenderRagdoll(RenderManager renderManager){
        super(renderManager);
        bipedModel = new ModelBiped();

        bipedModel64 = new ModelBiped(0.0f, 0, 64, 64);

        zombieModel = new ModelBiped(0.0f, 0, 64, 64);
    }

    public void drawLine(PointD point, PointD point2){
        glColor3f(0.0f, 1.0f, 0.2f);
        glBegin(GL_LINE_STRIP);

        glVertex3d(point.getX(), point.getY(), point.getY());
        glVertex3d(point2.getX(), point2.getY(), point2.getY());
        glEnd();
        glColor3f(1f,1f,1f);
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if(entity instanceof EntityRagdoll){
            EntityRagdoll entityRagdoll = (EntityRagdoll) entity;

            GL11.glPushMatrix();

            // Sets the position offset for rendering
            GL11.glTranslated(x, y, z);

            ModelBiped currentModel;

            if(entityRagdoll.ragdoll instanceof BipedRagdoll){

                if(!entityRagdoll.ragdoll.trackersRegistered){
                    if(entityRagdoll.ragdoll instanceof ZombieRagdoll){
                        entityRagdoll.ragdoll.initTrackers(zombieModel);
                    }
                    else{
                        entityRagdoll.ragdoll.initTrackers(bipedModel64);
                    }
                }

                // add husk texure and also some other stuff for rendering properly
                if(entityRagdoll.ragdoll instanceof ZombieRagdoll){

                    this.bindTexture(zombieTexture);
                    currentModel = this.zombieModel;

                }
                else{
                    this.bindTexture(steveTextures);
                    currentModel = this.bipedModel;
                }

                BipedRagdoll bipedRagdoll = (BipedRagdoll) entityRagdoll.ragdoll;

                if(mc.gameSettings.showDebugInfo){
                    GL11.glPushMatrix();
                    GL11.glDepthMask(false);
                    GL11.glEnable(GL11.GL_BLEND);
                    //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);

                    GL11.glColor4f(1,1,1,0.5f);
                }


                for(Tracker tracker : bipedRagdoll.trackerHashmap.values()){
                    //SekCPhysics.logger.info("Test");
                    tracker.calcRotation();
                    tracker.render();
                }

                if(mc.gameSettings.showDebugInfo) {
                    GL11.glColor4f(1, 1, 1, 1);

                    GL11.glDepthMask(true);

                    GL11.glPopMatrix();
                }

                /*setPartLocation(currentModel.bipedRightArm, bipedRagdoll.leftShoulder);

                setPartLocation(currentModel.bipedRightArm, bipedRagdoll.rightShoulder);

                setPartLocation(currentModel.bipedHead, bipedRagdoll.centerTorso);
                setPartLocation(currentModel.bipedBody, bipedRagdoll.centerTorso);

                setPartLocation(currentModel.bipedLeftLeg, bipedRagdoll.leftLegTop);

                setPartLocation(currentModel.bipedRightLeg, bipedRagdoll.rightLegTop);*/

            }

            //SekCPhysics.logger.info(p_76986_9_);

            if(mc.gameSettings.showDebugInfo){
                entityRagdoll.ragdoll.skeleton.renderSkeletonDebug(entityRagdoll.ragdoll.isActive);
            }
            GL11.glPopMatrix();
        }
    }

    public void setPartLocation(ModelRenderer trackPart, SkeletonPoint skeletonPart){
        trackPart.setRotationPoint((float) skeletonPart.posX * 16, (float) skeletonPart.posY * 16, (float) skeletonPart.posZ * 16);
        trackPart.render(0.0625F);
        //trackPart.rotateAngleZ=1;
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }
}
