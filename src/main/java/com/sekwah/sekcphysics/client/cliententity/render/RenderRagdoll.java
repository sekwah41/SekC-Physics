package com.sekwah.sekcphysics.client.cliententity.render;

import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.Tracker;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Ragdoll renderer file
 */
public class RenderRagdoll<T extends EntityRagdoll> extends Render<T> {

    private static Minecraft mc = Minecraft.getMinecraft();

    public RenderRagdoll(RenderManager renderManager) {
        super(renderManager);

    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {

        /**
         * TODO Check these locations on rendering armour
         * {@link net.minecraft.client.renderer.entity.RenderLivingBase#renderLayers}
         * {@link net.minecraft.client.renderer.entity.layers.LayerBipedArmor#getArmorModelHook}
         * {@link net.minecraft.client.renderer.entity.layers}
         * {@link net.minecraft.client.renderer.entity.RenderZombie}
         *
         * Need to make a layer renderer handler for methods like
         * {@link net.minecraft.client.renderer.entity.layers.LayerCustomHead#doRenderLayer}
         */

        GlStateManager.pushMatrix();

        // Sets the position offset for rendering
        GlStateManager.translate(x, y, z);

        BaseRagdoll bipedRagdoll = entity.ragdoll;

        ResourceLocation resourceLoc = bipedRagdoll.resourceLocation;
        if(resourceLoc != null) {
            this.bindTexture(bipedRagdoll.resourceLocation);
        }

        if(mc.gameSettings.showDebugInfo) {
            GlStateManager.pushMatrix();
            GlStateManager.depthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);

            GL11.glColor4f(1,1,1,0.5f);
        }

        for(Tracker tracker : bipedRagdoll.trackerHashmap.values()) {
            //tracker.calcPosition();
            tracker.render(partialTicks);
        }

        if(mc.gameSettings.showDebugInfo) {
            GL11.glColor4f(1, 1, 1, 1);

            GlStateManager.depthMask(true);

            GlStateManager.popMatrix();
        }

        if(mc.gameSettings.showDebugInfo) {
            entity.ragdoll.skeleton.renderSkeletonDebug(entity.ragdoll.activeStatus());
        }
        GlStateManager.popMatrix();
    }

    public void setPartLocation(ModelRenderer trackPart, SkeletonPoint skeletonPart) {
        trackPart.setRotationPoint((float) skeletonPart.posX * 16, (float) skeletonPart.posY * 16, (float) skeletonPart.posZ * 16);
        trackPart.render(0.0625F);
        //trackPart.rotateAngleZ=1;
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }
}
