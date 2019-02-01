package com.sekwah.sekcphysics.client.cliententity.render;

import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.Tracker;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Ragdoll renderer file
 */
public class RenderRagdoll<T extends EntityRagdoll> extends RenderLiving<T> {

    private static Minecraft mc = Minecraft.getMinecraft();

    public RenderRagdoll(RenderManager renderManager) {
        super(renderManager, new ModelBiped(), 0.0f);

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
         * {@link net.minecraft.client.renderer.entity.RenderLivingBase#doRender}
         *
         * Need to make a layer renderer handler for methods like
         * {@link net.minecraft.client.renderer.entity.layers.LayerCustomHead#doRenderLayer}
         *
         * {@link net.minecraft.client.renderer.entity.layers.LayerArmorBase#renderArmorLayer}
         *
         * // TODO worry about bipeds for now with their custom rendered armour with special models though do textures for stuff like horeses
         * // TODO get inventory being stored first. (COPY ARRAY FROM DEAD MOB)
         * {@link net.minecraftforge.client.ForgeHooksClient#getArmorModel]
         *
         * Possibly make a fake entity storing certain data able to be pushed into the location of the entity here
         *
         *
         * Possibly make it so there is a boolean instead some dont like being rendered directly as it for the layers
         */

        GlStateManager.pushMatrix();

        // Sets the position offset for rendering
        GlStateManager.translate(x, y, z);

        BaseRagdoll baseRagdoll = entity.ragdoll;

        ResourceLocation resourceLoc = baseRagdoll.resourceLocation;
        if(resourceLoc != null) {
            this.bindTexture(baseRagdoll.resourceLocation);
        }

        if(mc.gameSettings.showDebugInfo) {
            GlStateManager.depthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            GL11.glColor4f(1,1,1,0.5f);
        }

        GlStateManager.disableCull();
        for(Tracker tracker : baseRagdoll.trackerHashmap.values()) {
            //tracker.calcPosition();
            tracker.render(partialTicks);
        }

        if(mc.gameSettings.showDebugInfo) {
            GL11.glColor4f(1, 1, 1, 1);

            GlStateManager.depthMask(true);
        }

        this.renderHandItems(entity, baseRagdoll);

        if(mc.gameSettings.showDebugInfo) {
            GlStateManager.disableDepth();
            entity.ragdoll.skeleton.renderSkeletonDebug(entity.ragdoll.activeStatus());
            GlStateManager.enableDepth();
        }
        GlStateManager.popMatrix();
    }

    private void renderHandItems(T entity, BaseRagdoll baseRagdoll) {
        /*if(baseRagdoll.baseModel instanceof ModelBiped) {
            ModelBiped modelBiped = (ModelBiped) baseRagdoll.baseModel;
            ItemStack leftHand = entity.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
            ItemStack rightHand = entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
            if(!leftHand.isEmpty()) {
                this.renderHeldItem(entity, modelBiped, leftHand, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            }
            if(!rightHand.isEmpty()) {
                this.renderHeldItem(entity, modelBiped, rightHand, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            }
        }*/
    }

    /*private void renderHeldItem(T entity, ModelBiped modelBiped, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, EnumHandSide handSide)
    {
        if (!itemStack.isEmpty())
        {
            GlStateManager.pushMatrix();

            // Forge: moved this call down, fixes incorrect offset while sneaking.
            this.translateToHand(modelBiped, handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, itemStack, transformType, flag);
            GlStateManager.popMatrix();
        }
    }*/

    /*private void translateToHand(ModelBiped modelBiped, EnumHandSide handSide)
    {
        modelBiped.postRenderArm(0.0625F, handSide);
    }*/

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }
}
