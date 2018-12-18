package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.maths.RotateF;
import net.minecraft.client.model.Cuboid;
/**
 * Created by on 30/06/2016.
 *
 * @author sekwah41
 */
public abstract class Tracker {

    protected final Cuboid part;

    public Cuboid bodyPart = null;

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

    protected Tracker(Cuboid part) {
        this.part = part;
    }

    public Tracker(Cuboid part, float rotateOffsetX, float rotateOffsetY, float rotateOffsetZ) {
        this(part);
        this.rotationOffset = new RotateF(rotateOffsetX, rotateOffsetY, rotateOffsetZ);
    }

    public void render() {
        this.render(1);
    }

    protected void renderPart(float partialTicks) {
        this.renderPart(partialTicks, 0.0625f);
    }

    protected void renderPart(float partialTicks, float scale) {
        this.setPartLocation(partialTicks);
        this.setPartRotation(partialTicks);
        this.part.render(scale);
    }

    protected void updateLastPos() {
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

        this.lastRotation.add(this.rotationOffset);
        this.lastPosition = this.lastPosition.add(this.offset);
    }

    public abstract void calcPosition();

    /**
     * For rendering, not generally setting
     * @param partialTicks
     */
    public void setPartLocation(float partialTicks) {
        this.part.setRotationPoint((float) (this.lastPosition.x + this.positionDiff.x * partialTicks) * 16f,
                (float) (this.lastPosition.y + this.positionDiff.y * partialTicks) * 16f,
                (float) (this.lastPosition.z + this.positionDiff.z * partialTicks) * 16f);
        /*this.part.setRotationPoint((float) (this.position.x) * 16f,
                (float) (this.position.y) * 16f,(float) (this.position.z) * 16f);*/
    }

    /**
     * For rendering, not generally setting
     */
    public void setPartRotation(float partialTicks) {
        this.part.pitch = this.lastRotation.x + this.rotationDiff.x * partialTicks;
        this.part.yaw = this.lastRotation.y + this.rotationDiff.y * partialTicks;
        this.part.roll = this.lastRotation.z + this.rotationDiff.z * partialTicks;

        /*this.part.pitch = this.rotation.x;
        this.part.yaw = this.rotation.y;
        this.part.roll = this.rotation.z;*/
    }

    public abstract void render(float partialTicks);
}
