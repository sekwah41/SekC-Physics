package com.sekwah.sekcphysics.ragdoll.parts.trackers;

import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.maths.PointF;
import com.sekwah.sekcphysics.ragdoll.parts.Triangle;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

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

    public void render() {

        this.setPartLocation();

        //SekCPhysics.logger.info((float) anchor.posX * 16);

        // TODO switch this to setting the rotation and values of the part and not the
        GlStateManager.pushMatrix();

        GlStateManager.rotate((float) Math.toDegrees(this.rotation.z) + this.rotationOffset.z, 0,0,1);
        GlStateManager.rotate((float) Math.toDegrees(this.rotation.y) + this.rotationOffset.y, 0,1,0);
        GlStateManager.rotate((float) Math.toDegrees(this.rotation.x) + this.rotationOffset.x, 1,0,0);

        //GlStateManager.rotate((float) Math.toDegrees(this.rotationY + this.rotateOffsetY), 0,1,0);

        //this.part.rotateAngleX = this.rotationX + this.rotateOffsetX;
        //this.part.rotateAngleY = this.rotationY + this.rotateOffsetY;
        //this.part.rotateAngleZ = this.rotationZ + this.rotateOffsetZ;

        //GlStateManager.rotate((float) (Math.random() * 180), 0,1,0);

        //this.part.rotateAngleY = 0;
        //GlStateManager.rotate((float) Math.toDegrees(this.axisRotation), 0,1,0);


        // TODO calculate wanted rotation and the rotation added from getting to the correct direction.


        this.part.render(0.0625f * this.scale);

        GlStateManager.popMatrix();
    }

    @Override
    public void setPartLocation() {
        this.part.setRotationPoint((float) triangle.points[0].posX * 16 * this.scaleInvert,
                (float) triangle.points[0].posY * 16 * this.scaleInvert,
                (float) triangle.points[0].posZ * 16 * this.scaleInvert);
    }

}
