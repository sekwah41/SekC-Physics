package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.maths.RotateF;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

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

    public void render() {
        this.render(1);
    }

    protected void renderPart(float partialTicks) {
        this.renderPart(partialTicks, 1);
    }

    protected void renderPart(float partialTicks, float scale) {
        GlStateManager.pushMatrix();
        this.smoothLocation(partialTicks, scale);
        this.smoothRotation(partialTicks);
        this.applyOffset();
        this.part.render(0.0625f * scale);
        GlStateManager.popMatrix();
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
        GlStateManager.translate((float) (this.lastPosition.x + this.positionDiff.x * partialTicks) * scale,
                (float) (this.lastPosition.y + this.positionDiff.y * partialTicks) * scale,
                (float) (this.lastPosition.z + this.positionDiff.z * partialTicks) * scale);
    }

    /**
     * For rendering, not generally setting
     */
    protected void smoothRotation(float partialTicks) {
        applyRotation(this.lastRotation.z + this.rotationDiff.z * partialTicks,
                this.lastRotation.y + this.rotationDiff.y * partialTicks,
                this.lastRotation.x + this.rotationDiff.x * partialTicks);
    }

    protected void applyOffset() {
        applyRotation(this.rotationOffset.x,
                this.rotationOffset.y,
                this.rotationOffset.z);
    }

    private void applyRotationDeg(float x, float y, float z) {
        if (z != 0.0F) {
            GlStateManager.rotate(z, 0,0,1);
        }

        if (y != 0.0F) {
            GlStateManager.rotate(y, 0,1,0);
        }

        if (x != 0.0F) {
            GlStateManager.rotate(x, 1,0,0);
        }
    }

    private void applyRotation(float x, float y, float z) {
        this.applyRotationDeg(z * (180F / (float)Math.PI), y * (180F / (float)Math.PI),x * (180F / (float)Math.PI));
    }

    public abstract void render(float partialTicks);
}
