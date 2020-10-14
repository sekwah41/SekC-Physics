package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Created by on 30/06/2016.
 *
 * @author sekwah41
 */
public class TrackerTriangleScaled extends TrackerTriangle {

    private final float scale;
    private final float scaleInvert;

    public TrackerTriangleScaled(ModelRenderer part, Triangle triangle, float scale) {
        super(part, triangle);
        this.scale = scale;
        this.scaleInvert = 1f/scale;
    }

    public TrackerTriangleScaled(ModelRenderer part, Triangle triangle, float rotateOffsetX, float rotateOffsetY,
                                 float rotateOffsetZ, float scale) {
        super(part, triangle, rotateOffsetX, rotateOffsetY, rotateOffsetZ);
        this.scale = scale;
        this.scaleInvert = 1f/scale;
    }

    public void render(float partialTicks, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn) {
        this.renderPart(partialTicks, this.scale, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }

    @Override
    protected void updatePosition() {
        this.position = new PointD(triangle.points[0].posX * this.scaleInvert, triangle.points[0].posY * this.scaleInvert,
                triangle.points[0].posZ * this.scaleInvert);
    }

}
