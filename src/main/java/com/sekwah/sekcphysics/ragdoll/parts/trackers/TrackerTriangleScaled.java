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

    public TrackerTriangleScaled(ModelRenderer part, Triangle triangle) {
        super(part, triangle);
    }

    public TrackerTriangleScaled(ModelRenderer part, Triangle triangle, float rotateOffsetX, float rotateOffsetY, float rotateOffsetZ) {
        super(part, triangle, rotateOffsetX, rotateOffsetY, rotateOffsetZ);
    }

    public void render() {

        this.part.setRotationPoint((float) triangle.points[0].posX * 16, (float) triangle.points[0].posY * 16,
                (float) triangle.points[0].posZ * 16);

        //SekCPhysics.logger.info((float) anchor.posX * 16);

        GlStateManager.pushMatrix();



        GlStateManager.rotate((float) Math.toDegrees(this.rotationZ) + this.rotateOffsetZ, 0,0,1);
        GlStateManager.rotate((float) Math.toDegrees(this.rotationY) + this.rotateOffsetY, 0,1,0);
        GlStateManager.rotate((float) Math.toDegrees(this.rotationX) + this.rotateOffsetX, 1,0,0);

        //GlStateManager.rotate((float) Math.toDegrees(this.rotationY + this.rotateOffsetY), 0,1,0);

        //this.part.rotateAngleX = this.rotationX + this.rotateOffsetX;
        //this.part.rotateAngleY = this.rotationY + this.rotateOffsetY;
        //this.part.rotateAngleZ = this.rotationZ + this.rotateOffsetZ;

        //GlStateManager.rotate((float) (Math.random() * 180), 0,1,0);

        //this.part.rotateAngleY = 0;
        //GlStateManager.rotate((float) Math.toDegrees(this.axisRotation), 0,1,0);


        // TODO calculate wanted rotation and the rotation added from getting to the correct direction.


        this.part.render(0.0625f);

        GlStateManager.popMatrix();
    }

}
