package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import net.minecraft.client.model.ModelRenderer;

/**
 * Created by on 30/06/2016.
 *
 * @author sekwah41
 */
public class Tracker {

    protected final ModelRenderer part;

    public ModelRenderer bodyPart = null;

    public float rotateOffsetX = 0;

    public float rotateOffsetY = 0;

    public float rotateOffsetZ = 0;

    public float rotationX = 0;

    public float rotationY = 0;

    public float rotationZ = 0;

    public float axisRotation = 0;

    public float offsetX = 0;

    public float offsetY = 0;

    public float offsetZ = 0;

    public Tracker(ModelRenderer part) {
        this.part = part;
    }

    public Tracker(ModelRenderer part, float rotateOffsetX, float rotateOffsetY, float rotateOffsetZ) {
        this(part);
        this.rotateOffsetX = rotateOffsetX;
        this.rotateOffsetY = rotateOffsetY;
        this.rotateOffsetZ = rotateOffsetZ;
    }

    public void render() {
        this.part.render(0.0625f);
    }

    public void calcRotation() {

    }

}
