package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.maths.RotateF;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Quaternion;
import org.lwjgl.opengl.GL11;

/**
 * Created by on 30/06/2016.
 *
 * @author sekwah41
 */
public abstract class Tracker {

    protected final ModelRenderer part;

    public ModelRenderer bodyPart = null;

    public RotateF rotationOffset = new RotateF();

    public RotateF rotation = new RotateF();

    /**
     * How far to go from the old one to get to the new one
     */
    public RotateF rotationDiff = new RotateF();

    public RotateF lastRotation = new RotateF();

    public PointD position = new PointD();

    public PointD lastPosition = new PointD();

    /**
     * How far to go from the old one to get to the new one
     */
    public PointD positionDiff = new PointD();

    /**
     * How far to move to the last known rotation
     */
    public RotateF distToLastRotation = new RotateF();

    /**
     * Not used yet TODO add this properly
     */
    public PointD offset = new PointD();

    protected Tracker(ModelRenderer part) {
        part.setRotationPoint(0,0,0);
        part.rotateAngleX = 0;
        part.rotateAngleY = 0;
        part.rotateAngleZ = 0;
        this.part = part;
    }

    public Tracker(ModelRenderer part, float rotateOffsetX, float rotateOffsetY, float rotateOffsetZ) {
        this(part);
        this.rotationOffset = new RotateF(rotateOffsetX, rotateOffsetY, rotateOffsetZ);
    }

    @Deprecated
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn) {
        this.render(1, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }

    protected void renderPart(float partialTicks, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn) {
        this.renderPart(partialTicks, 1, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }

    // TODO remake to get the matrix data needed to use the new rendering
    protected void renderPart(float partialTicks, float scale, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn) {
        matrixStackIn.push();
        this.smoothLocation(partialTicks, scale);
        this.smoothRotation(partialTicks, matrixStackIn);
        this.applyOffset(matrixStackIn);
        // Maybe take a look at changing so that you add color effects e.g. burning or changing colors.
        this.part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        matrixStackIn.pop();
    }

    public void updateLastPos() {
        this.lastRotation.copy(this.rotation);
        this.lastPosition = this.position;
    }

    /**
     * Calculates the offsets then adds the offset value (reduces amount of calculations needed)
     * This is so the offset data is just added after all calculations.
     *
     * TODO check how offsets and calculations work together or if they even do as all the calculations are based off default pos
     */
    public void updatePosDifference() {
        this.rotationDiff.copy(this.rotation).sub(this.lastRotation);
        this.positionDiff = this.position.sub(this.lastPosition);
        this.rotationDiff.changeToShortestAngle();

    }

    public abstract void calcPosition();

    /**
     * For rendering, not generally setting
     * @param partialTicks
     */
    protected void smoothLocation(float partialTicks, float scale) {
        GL11.glTranslatef((float) (this.lastPosition.x + this.positionDiff.x * partialTicks) * scale,
                (float) (this.lastPosition.y + this.positionDiff.y * partialTicks) * scale,
                (float) (this.lastPosition.z + this.positionDiff.z * partialTicks) * scale);
    }

    /**
     * For rendering, not generally setting
     */
    protected void smoothRotation(float partialTicks, MatrixStack matrixStackIn) {
        applyRotation(this.lastRotation.z + this.rotationDiff.z * partialTicks,
                this.lastRotation.y + this.rotationDiff.y * partialTicks,
                this.lastRotation.x + this.rotationDiff.x * partialTicks, matrixStackIn);
    }

    protected void applyOffset(MatrixStack matrixStackIn) {
        applyRotation(this.rotationOffset.x,
                this.rotationOffset.y,
                this.rotationOffset.z, matrixStackIn);
    }

    private void applyRotationDeg(float x, float y, float z, MatrixStack matrixStackIn) {
        if (z != 0.0F) {
            matrixStackIn.rotate(new Quaternion(0,0,1, z));
        }

        if (y != 0.0F) {
            matrixStackIn.rotate(new Quaternion(0,1,0, y));
        }

        if (x != 0.0F) {
            matrixStackIn.rotate(new Quaternion(1,0,0, x));
        }
    }

    private void applyRotation(float x, float y, float z, MatrixStack matrixStackIn) {
        this.applyRotationDeg(z * (180F / (float)Math.PI), y * (180F / (float)Math.PI),x * (180F / (float)Math.PI), matrixStackIn);
    }
 // , MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn
    public abstract void render(float partialTicks, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn);
}
