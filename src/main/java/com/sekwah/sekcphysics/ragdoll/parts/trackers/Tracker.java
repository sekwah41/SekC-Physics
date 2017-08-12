package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.sekwah.sekcphysics.maths.PointF;
import com.sekwah.sekcphysics.maths.RotateF;
import net.minecraft.client.model.ModelRenderer;

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
     * How far to move to the last known rotation
     */
    public RotateF distToLastRotation = new RotateF();

    public float axisRotation = 0;

    public PointF offset = new PointF();

    protected Tracker(ModelRenderer part) {
        this.part = part;
    }

    public Tracker(ModelRenderer part, float rotateOffsetX, float rotateOffsetY, float rotateOffsetZ) {
        this(part);
        this.rotationOffset = new RotateF(rotateOffsetX, rotateOffsetY, rotateOffsetZ);
    }

    public abstract void render();

    protected void renderPart() {
        this.part.render(0.0625f);
    }

    public abstract void calcRotation();

    public abstract void setPartLocation();

    public abstract void setPartRotation();
}
