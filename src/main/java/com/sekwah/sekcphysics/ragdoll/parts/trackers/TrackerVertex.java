package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.maths.PointF;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Created by on 30/06/2016.
 *
 * TODO possibly recode to use a constraint rather than set points
 *
 * @author sekwah41
 */
public class TrackerVertex extends Tracker {

    protected final SkeletonPoint anchor;

    protected final SkeletonPoint pointsTo;

    public TrackerVertex(ModelRenderer part, SkeletonPoint anchor, SkeletonPoint pointsTo) {
        super(part);
        this.anchor = anchor;
        this.pointsTo = pointsTo;

        // Resets the data so it doesnt get a sliding effect from 0 of every location.

        this.calcPosition();
        this.updateLastPos();
        this.updatePosDifference();
    }

    @Override
    public void render(float partialTicks, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn) {

        this.renderPart(partialTicks, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        // TODO Look at the length in comparison (store it when calculating physics) and stretch it based on the percentage xD
        //GlStateManager.scale(1,scaleFactorStretch,0);
    }

    @Override
    public void calcPosition() {

        this.updateLastPos();

        PointF constraintVert = new PointF((float) (pointsTo.posX - anchor.posX), (float) (pointsTo.posY - anchor.posY),
                (float) (pointsTo.posZ - anchor.posZ));

        // TODO need to flip these around, they are getting the location right but the opposite side then rotating backwards casing it to be flipped


        this.rotation.y = basicRotation(constraintVert.x, constraintVert.z);

        this.rotation.x = (float) (Math.PI * 0.5) + basicRotation(-constraintVert.y, (float) Math.sqrt(constraintVert.x * constraintVert.x + constraintVert.z * constraintVert.z));

        //this.rotation.x = (float) (Math.PI) / 2 + basicRotation(-constraintVert.y, (float) Math.sqrt(Math.pow(constraintVert.x,2) + Math.pow(constraintVert.z,2)));

        //this.rotation.y = basicRotation(constraintVert.x, constraintVert.z);

        this.updatePosition();

        this.updatePosDifference();
    }

    protected void updatePosition() {
        this.position = new PointD(anchor.posX, anchor.posY, anchor.posZ);
    }



    /**
     * Convert to using Math.atan2(y,x);
     * @param axis1
     * @param axis2
     * @return
     */
    public float basicRotation(float axis1, float axis2) {
        return (float) Math.atan2(axis1, axis2);
    }

}
