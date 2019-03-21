package com.sekwah.sekcphysics.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.sekwah.sekcphysics.client.cliententity.RagdollEntity;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.Tracker;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AbsoluteHand;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

/**
 * Ragdoll renderer file
 */
public class RenderRagdoll<T extends RagdollEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {

    private static MinecraftClient mc = MinecraftClient.getInstance();

    public RenderRagdoll(EntityRenderDispatcher renderManager, M model) {
        super(renderManager, model, 0.0f);

    }

    // This is doRender
    @Override
    public void render(T entity, double x, double y, double z, float entityYaw, float partialTicks) {

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
        GlStateManager.translated(x, y, z);

        BaseRagdoll baseRagdoll = entity.ragdoll;

        Identifier identifier = baseRagdoll.resourceLocation;
        if(identifier != null) {
            this.bindTexture(baseRagdoll.resourceLocation);
        }

        if(mc.options.debugEnabled) {
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

        if(mc.options.debugEnabled) {
            GL11.glColor4f(1, 1, 1, 1);

            GlStateManager.depthMask(true);
        }

        this.renderHandItems(entity, baseRagdoll);

        if(mc.options.debugEnabled) {
            GlStateManager.disableDepthTest();
            entity.ragdoll.skeleton.renderSkeletonDebug(entity.ragdoll.activeStatus());
            GlStateManager.enableDepthTest();
        }
        GlStateManager.popMatrix();
    }

    private void renderHandItems(T entity, BaseRagdoll baseRagdoll) {
        if(baseRagdoll.baseModel instanceof BipedEntityModel) {
            BipedEntityModel modelBiped = (BipedEntityModel) baseRagdoll.baseModel;
            ItemStack leftHand = entity.getEquippedStack(EquipmentSlot.HAND_OFF);
            ItemStack rightHand = entity.getEquippedStack(EquipmentSlot.HAND_MAIN);
            if(!leftHand.isEmpty()) {
                this.renderHeldItem(entity, modelBiped, leftHand, ModelTransformation.Type.THIRD_PERSON_LEFT_HAND, AbsoluteHand.LEFT);
            }
            if(!rightHand.isEmpty()) {
                this.renderHeldItem(entity, modelBiped, rightHand, ModelTransformation.Type.THIRD_PERSON_RIGHT_HAND, AbsoluteHand.RIGHT);
            }
        }
    }

    private void renderHeldItem(T entity, BipedEntityModel modelBiped, ItemStack itemStack, ModelTransformation.Type transformType, AbsoluteHand handSide)
    {
        if (!itemStack.isEmpty())
        {
            GlStateManager.pushMatrix();

            // Forge: moved this call down, fixes incorrect offset while sneaking.
            this.translateToHand(modelBiped, handSide);
            GlStateManager.rotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == AbsoluteHand.LEFT;
            GlStateManager.translatef((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
            MinecraftClient.getInstance().getFirstPersonRenderer().renderItemFromSide(entity, itemStack, transformType, flag);
            GlStateManager.popMatrix();
        }
    }

    private void translateToHand(BipedEntityModel modelBiped, AbsoluteHand handSide)
    {
        // method_2803 applyTransformation to thhe opengl matrix
        modelBiped.setArmAngle(0.0625F, handSide);
    }

    public void setPartLocation(T entity, Cuboid trackPart, SkeletonPoint skeletonPart) {
        trackPart.setRotationPoint((float) skeletonPart.posX * 16, (float) skeletonPart.posY * 16, (float) skeletonPart.posZ * 16);
        trackPart.render(0.0625F);
        //trackPart.rotateAngleZ=1;
    }

    @Override
    protected Identifier getTexture(T entity) {
        return null;
    }
}
