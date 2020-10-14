package com.sekwah.sekcphysics.client.cliententity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.Tracker;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Ragdoll renderer file
 */
public class RenderRagdoll<T extends EntityRagdoll> extends LivingRenderer<T, BipedModel<T>> {

    //private static Minecraft mc = Minecraft.getInstance();

    public RenderRagdoll(EntityRendererManager renderManager) {
        super(renderManager, new BipedModel(1.0f), 0.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(T entityIn) {
        return entityIn.ragdoll.resourceLocation;
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

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

        matrixStackIn.push();

        BaseRagdoll baseRagdoll = entityIn.ragdoll;

        /*if(mc.gameSettings.showDebugInfo) {
            GlStateManager.depthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            GL11.glColor4f(1,1,1,0.5f);
        }*/

        //GlStateManager.disableCull();
        for(Tracker tracker : baseRagdoll.trackerHashmap.values()) {
            //tracker.calcPosition();
            tracker.render(partialTicks);
        }

        /*if(mc.gameSettings.showDebugInfo) {
            GL11.glColor4f(1, 1, 1, 1);

            GlStateManager.depthMask(true);
        }*/

        //this.renderHandItems(entityIn, baseRagdoll);

        /*if(mc.gameSettings.showDebugInfo) {
            GlStateManager.disableDepth();
            entityIn.ragdoll.skeleton.renderSkeletonDebug(entityIn.ragdoll.activeStatus(), mc.getRenderManager().isDebugBoundingBox());
            GlStateManager.enableDepth();
        }*/
        matrixStackIn.pop();
    }

    /*private void renderHandItems(T entity, BaseRagdoll baseRagdoll) {
        *//*if(baseRagdoll.baseModel instanceof ModelBiped) {
            ModelBiped modelBiped = (ModelBiped) baseRagdoll.baseModel;
            ItemStack leftHand = entity.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
            ItemStack rightHand = entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
            if(!leftHand.isEmpty()) {
                this.renderHeldItem(entity, modelBiped, leftHand, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            }
            if(!rightHand.isEmpty()) {
                this.renderHeldItem(entity, modelBiped, rightHand, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            }
        }*//*
    }*/

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
}
