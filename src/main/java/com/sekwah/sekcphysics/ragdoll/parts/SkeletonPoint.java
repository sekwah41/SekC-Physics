package com.sekwah.sekcphysics.ragdoll.parts;

import com.sekwah.sekcphysics.client.cliententity.RagdollEntity;
import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.ragdoll.Ragdolls;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.VerticalEntityPosition;
import net.minecraft.util.BooleanBiFunction;
import net.minecraft.util.LoopingStream;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.ViewableWorld;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by sekawh on 8/5/2015.
 */
public class SkeletonPoint {

    private final float size;

    public double posX;
    public double posY;
    public double posZ;

    public double lastPosX;
    public double lastPosY;
    public double lastPosZ;

    public double newPosX;
    public double newPosY;
    public double newPosZ;

    private double nonMoveThresh = 0.001;

    public boolean hasMoved = true;

    // Push force multiplier
    public float pushability = 1;


    // basically distance from last point, just sounds nicer for when other forces interact with it or if you wanna set it
    //  from an explosion
    public double velX = 0;
    public double velY = 0;
    public double velZ = 0;

    private boolean onGround = false;

    private boolean inWater;

    /**
     * this will set positions to scale in terms of the model, also y coordinates on models are negative so reverse it manually
     */
    public SkeletonPoint(double x, double y, double z, float size) {
        this(x,y,z,size,true);
    }

    public SkeletonPoint(double x, double y, double z, boolean shouldDoModelScale) {
        this(x,y,z,0.15f,shouldDoModelScale);
    }

    public SkeletonPoint(double x, double y, double z) {
        this(x,y,z,0.15f,true);
    }

    // note the position is in blocks not the model locations, and every 1 block is split into 16 for the model positions(i think)
    public SkeletonPoint(double x, double y, double z, float size, boolean shouldDoModelScale) {
        this.setPosition(x,y,z);

        // Added to stop ragdolls becoming lines or acting in only 1 plane after hitting a wall
        float sizeRandom = (float) Math.random();
        float maxOffset = 0.001f;
        size += -maxOffset + maxOffset * 2f * sizeRandom;

        this.size = size;

        if(shouldDoModelScale) {
            shiftPositionToModelScale();
        }
    }

    public void shiftPositionToModelScale() {
        this.setPosition(this.posX / 16f, this.posY / 16f, this.posZ / 16f);
    }

    public void shiftPositionToWorldScale() {
        this.setPosition(this.posX * 16f, this.posY * 16f, this.posZ * 16f);
    }

    public void setPosition(double x, double y, double z) {
        this.lastPosX = this.newPosX = this.posX = x;
        this.lastPosY = this.newPosY = this.posY = y;
        this.lastPosZ = this.newPosZ = this.posZ = z;
        this.checkWillMove();
    }

    private void movePoint(RagdollEntity entity, Vec3d moveVec) {

        BoundingBox boundingBox_1 = this.getBoundingBox(entity);
        VerticalEntityPosition verticalEntityPosition_1 = VerticalEntityPosition.fromEntity(entity);
        VoxelShape voxelShape_1 = entity.world.getWorldBorder().asVoxelShape();
        Stream<VoxelShape> stream_1 = VoxelShapes.compareShapes(voxelShape_1, VoxelShapes.cube(boundingBox_1.contract(1.0E-7D)), BooleanBiFunction.AND) ? Stream.empty() : Stream.of(voxelShape_1);
        BoundingBox boundingBox_2 = boundingBox_1.method_18804(moveVec).expand(1.0E-7D);
        Stream var10000 = entity.world.getVisibleEntities(entity, boundingBox_2).stream().filter((entity_1) -> !entity.method_5794(entity_1))
                .flatMap((entity_1) -> Stream.of(entity_1.method_5827(), entity.method_5708(entity_1))).filter(Objects::nonNull);
        Stream<VoxelShape> stream_2 = var10000.filter((box) -> boundingBox_2.intersects((BoundingBox) box)).map((box) -> VoxelShapes.cube((BoundingBox) box));
        LoopingStream<VoxelShape> loopingStream_1 = new LoopingStream(Stream.concat(stream_2, stream_1));
        Vec3d vec3d_2 = moveVec.lengthSquared() == 0.0D ? moveVec : Entity.method_17833(moveVec, boundingBox_1, entity.world, verticalEntityPosition_1, loopingStream_1);
        boolean boolean_1 = moveVec.x != vec3d_2.x;
        boolean boolean_2 = moveVec.y != vec3d_2.y;
        boolean boolean_3 = moveVec.z != vec3d_2.z;
        boolean boolean_4 = entity.onGround || boolean_2 && moveVec.y < 0.0D;
        if (entity.stepHeight > 0.0F && boolean_4 && (boolean_1 || boolean_3)) {
            Vec3d vec3d_3 = Entity.method_17833(new Vec3d(moveVec.x, (double)entity.stepHeight, moveVec.z), boundingBox_1, entity.world, verticalEntityPosition_1, loopingStream_1);
            Vec3d vec3d_4 = Entity.method_17833(new Vec3d(0.0D, (double)entity.stepHeight, 0.0D), boundingBox_1.stretch(moveVec.x, 0.0D, moveVec.z), entity.world, verticalEntityPosition_1, loopingStream_1);
            if (vec3d_4.y < (double)entity.stepHeight) {
                Vec3d vec3d_5 = Entity.method_17833(new Vec3d(moveVec.x, 0.0D, moveVec.z), boundingBox_1.offset(vec3d_4), entity.world, verticalEntityPosition_1, loopingStream_1).add(vec3d_4);
                if (Entity.squaredHorizontalLength(vec3d_5) > Entity.squaredHorizontalLength(vec3d_3)) {
                    vec3d_3 = vec3d_5;
                }
            }

            if (Entity.squaredHorizontalLength(vec3d_3) > Entity.squaredHorizontalLength(vec3d_2)) {
                applyMove(vec3d_3.add(movePoint(new Vec3d(0.0D, -vec3d_3.y + moveVec.y, 0.0D), boundingBox_1.offset(vec3d_3), entity.world, verticalEntityPosition_1, loopingStream_1)));
                return;
            }
        }

        //applyMove(vec3d_2);
        applyMove(moveVec);
    }

    public static Vec3d movePoint(Vec3d moveVec, BoundingBox boundingBox_1, ViewableWorld viewableWorld_1, VerticalEntityPosition verticalEntityPosition_1, LoopingStream<VoxelShape> loopingStream_1) {
        double double_1 = moveVec.x;
        double double_2 = moveVec.y;
        double double_3 = moveVec.z;
        if (double_2 != 0.0D) {
            double_2 = VoxelShapes.method_17945(Direction.Axis.Y, boundingBox_1, viewableWorld_1, double_2, verticalEntityPosition_1, loopingStream_1.getStream());
            if (double_2 != 0.0D) {
                boundingBox_1 = boundingBox_1.offset(0.0D, double_2, 0.0D);
            }
        }

        boolean boolean_1 = Math.abs(double_1) < Math.abs(double_3);
        if (boolean_1 && double_3 != 0.0D) {
            double_3 = VoxelShapes.method_17945(Direction.Axis.Z, boundingBox_1, viewableWorld_1, double_3, verticalEntityPosition_1, loopingStream_1.getStream());
            if (double_3 != 0.0D) {
                boundingBox_1 = boundingBox_1.offset(0.0D, 0.0D, double_3);
            }
        }

        if (double_1 != 0.0D) {
            double_1 = VoxelShapes.method_17945(Direction.Axis.X, boundingBox_1, viewableWorld_1, double_1, verticalEntityPosition_1, loopingStream_1.getStream());
            if (!boolean_1 && double_1 != 0.0D) {
                boundingBox_1 = boundingBox_1.offset(double_1, 0.0D, 0.0D);
            }
        }

        if (!boolean_1 && double_3 != 0.0D) {
            double_3 = VoxelShapes.method_17945(Direction.Axis.Z, boundingBox_1, viewableWorld_1, double_3, verticalEntityPosition_1, loopingStream_1.getStream());
        }

        return new Vec3d(double_1, double_2, double_3);
    }

    private void applyMove(Vec3d vec3d) {
        this.posX += vec3d.x;
        this.posY += vec3d.y;
        this.posZ += vec3d.z;
    }

    /**
     * Check if it will move and store a variable saying if it has. and set a min move amount
     * @return
     */
    private boolean checkWillMove() {
        this.nonMoveThresh = 0.0001f;

        double move = Math.abs(this.newPosX - this.posX) + Math.abs(this.newPosY - this.posY) + Math.abs(this.newPosZ - this.posZ);
        boolean moved = move < this.nonMoveThresh;
        //System.out.println(move);
        //System.out.println(moved);
        if(!moved) {
            this.newPosX = this.posX;
            this.newPosY = this.posY;
            this.newPosZ = this.posZ;
        }
        return this.hasMoved = moved;
    }

    private boolean isAboveSpeedThreashold(double vel) {
        return Math.abs(vel) > nonMoveThresh;
    }

    public void update(RagdollEntity entity) {
        this.velX = this.posX - this.lastPosX;
        if(!isAboveSpeedThreashold(this.velX)) {
            this.velX = 0;
        }
        this.velY = this.posY - this.lastPosY;
        if(!isAboveSpeedThreashold(this.velX)) {
            this.velX = 0;
        }
        this.velZ = this.posZ - this.lastPosZ;
        if(!isAboveSpeedThreashold(this.velX)) {
            this.velX = 0;
        }
        float speedMulti = 0.9999f;

        this.velY *= speedMulti;

        if(onGround) {
            float groundMulti = 0.85f;
            this.velX *= groundMulti;
            this.velZ *= groundMulti;
        }
        else{
            this.velX *= speedMulti;
            this.velZ *= speedMulti;
        }


        this.onGround = false;

        this.lastPosX = this.posX;
        this.lastPosY = this.posY;
        this.lastPosZ = this.posZ;

        BoundingBox axisalignedbb = this.getBoundingBox(entity);

        this.velY -= Ragdolls.gravity;

        // TODO check how the player gets this, i has roughly been coded for 1.13 as a test
        if (entity.world.containsBlockWithMaterial(axisalignedbb.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.WATER)) {
            this.addVelocity(0, 0.06f, 0);
            if(!this.inWater) {
                this.inWater = true;
                this.velX *= 0.9f;
                this.velY *= 0.5f;
                this.velZ *= 0.9f;
            }
            else {
                this.velX *= 0.9f;
                this.velY *= 0.85f;
                this.velZ *= 0.9f;
            }
            //entity.setVelocity(0,0,0);
        }
        else {
            this.inWater = false;
        }

        this.updateCollisions(entity);

        this.movePoint(entity, new Vec3d(this.velX, this.velY, this.velZ));

        // TODO collisions work but something not updating right

        //next_old_position = position             // This position is the next frame'mixins old_position
        // position += position - old_position;     // Verlet integration
        //position += gravity;                     // gravity == (0,-0.01,0)

        if(this.checkWillMove()) {
            this.newPosX = this.posX;
            this.newPosY = this.posY;
            this.newPosZ = this.posZ;
        }
    }

    private BoundingBox getBoundingBox(RagdollEntity entity) {
        double pointPosX = entity.x + this.posX;
        double pointPosY = entity.y + this.posY;
        double pointPosZ = entity.z + this.posZ;

        return new BoundingBox(pointPosX - size, pointPosY - size, pointPosZ - size,
                pointPosX + size, pointPosY + size, pointPosZ + size);
    }

    // Wont push other entities but make it get pushed by others.
    private void updateCollisions(RagdollEntity entity) {
        double pointPosX = entity.x + this.posX;
        double pointPosY = entity.y + this.posY;
        double pointPosZ = entity.z + this.posZ;

        BoundingBox boundingBox = new BoundingBox(pointPosX - size, pointPosY - size, pointPosZ - size,
                pointPosX + size, pointPosY + size, pointPosZ + size);

        List list = entity.world.getVisibleEntities(entity, boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        if (list != null && !list.isEmpty())
        {
            for (int i = 0; i < list.size(); ++i)
            {
                Entity entityCol = (Entity)list.get(i);

                if(entityCol.isPushable()) {
                    this.collideWithEntity(entity, entityCol);
                }
            }
        }

        this.checkWillMove();

    }

    private void collideWithEntity(RagdollEntity entity, Entity entityCol) {
        double pointPosX = entity.x + this.posX;
        double pointPosZ = entity.z + this.posZ;
        double d0 = pointPosX - entityCol.x;
        double d1 = pointPosZ - entityCol.z;
        double d2 = MathHelper.absMax(d0, d1);

        if (d2 >= 0.009999999776482582D)
        {
            d2 = (double)MathHelper.sqrt(d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0D / d2;

            if (d3 > 1.0D)
            {
                d3 = 1.0D;
            }

            d0 *= d3;
            d1 *= d3;
            d0 *= 0.05000000074505806D;
            d1 *= 0.05000000074505806D;
            // field_5968 should be pushSpeedModifier
            d0 *= (double)(1.0F - entityCol.pushSpeedReduction);
            d1 *= (double)(1.0F - entityCol.pushSpeedReduction);
            //SekCPhysics.logger.info(entityCol.motionX);
            //entityCol.addVelocity(-d0, 0.0D, -d1);
            this.addVelocity(d0 + entityCol.getVelocity().x, 0.0D, d1 + entityCol.getVelocity().z);
        }
    }

    public void setNewPos(double x, double y, double z) {
        this.newPosX = x;
        this.newPosY = y;
        this.newPosZ = z;
    }

    public void updatePos(RagdollEntity entity) {
        moveTo(entity, this.newPosX, this.newPosY, this.newPosZ);
    }

    // TODO check the movements and which values are global vs local
    public void moveTo(RagdollEntity entity, double x, double y, double z) {

        this.movePoint(entity, new Vec3d(x - this.posX, y - this.posY, z - this.posZ));

        /*this.posX = x;
        this.posY = y;
        this.posZ = z;*/

    }


    public PointD toPoint() {
        return new PointD(this.posX, this.posY, this.posZ);
    }

    public void verify(RagdollEntity entity) {
        double tempPosX = this.posX;
        double tempPosY = this.posY;
        double tempPosZ = this.posZ;

        this.posX = 0;
        this.posY = 0;
        this.posZ = 0;

        this.moveTo(entity, tempPosX, tempPosY, tempPosZ);

        this.lastPosX = this.posX;
        this.lastPosY = this.posY;
        this.lastPosZ = this.posZ;
    }

    public void shiftPosition(double x, double y, double z) {
        this.posX += x;
        this.posY += y;
        this.posZ += z;

        this.lastPosX += x;
        this.lastPosY += y;
        this.lastPosZ += z;
    }

    public void setVelocity(double motionX, double motionY, double motionZ) {
        this.lastPosX = this.posX - motionX;
        this.lastPosY = this.posY - motionY;
        this.lastPosZ = this.posZ - motionZ;
    }

    public void addVelocity(double motionX, double motionY, double motionZ) {
        this.lastPosX -= motionX;
        this.lastPosY -= motionY;
        this.lastPosZ -= motionZ;
    }


    public void setNewPos(PointD newLoc) {
        this.setNewPos(newLoc.x, newLoc.y, newLoc.z);
    }

    public void setPosition(PointD newLoc) {
        this.setPosition(newLoc.x, newLoc.y, newLoc.z);
    }
}
