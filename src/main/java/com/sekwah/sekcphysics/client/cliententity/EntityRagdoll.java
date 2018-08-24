package com.sekwah.sekcphysics.client.cliententity;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityRagdoll extends EntityLiving {

    public BaseRagdoll ragdoll;

    private int remainingLife = 600;

    public EntityRagdoll(World world) {
        this(SekCPhysics.RAGDOLL, world);
    }

    public EntityRagdoll(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    public EntityRagdoll(World world, BaseRagdoll ragdoll) {
        this(world);
        this.noClip = true;

        this.setSize(0.15F, 0.15F);

        //remainingLife = 16000;

        this.ragdoll = ragdoll;

        //this.ignoreFrustumCheck = true;
    }

    public boolean canBeCollidedWith()
    {
        return false;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        super.entityInit();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(0D);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    public void onEntityUpdate() {

    }

    @Override
    public void onUpdate()
    {
        if(this.ragdoll == null) {
            this.setDead();
            return;
        }

        if(remainingLife-- < 0) {

            for (int i = 0; i < 10; ++i) {
                float poofSize = 1.0f;
                double d0 = this.rand.nextGaussian() * 0.04D;
                double d1 = this.rand.nextGaussian() * 0.04D;
                double d2 = this.rand.nextGaussian() * 0.04D;
                this.world.addParticle(Particles.CLOUD, this.posX + (double) (this.rand.nextFloat() * poofSize * 2.0F) - (double) poofSize, this.posY + this.height / 2 + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * poofSize * 2.0F) - (double) poofSize, d0, d1, d2);
            }

            this.setDead();
        }

        this.ragdoll.update(this);

        /*if(this.remainingLife-- >= 595) {
            this.ragdoll.update(this);
        }*/

        PointD ragdollPos = this.ragdoll.skeleton.points.get(0).toPoint();

        this.setPosition(this.posX + ragdollPos.x, this.posY + ragdollPos.y, this.posZ + ragdollPos.z);

        this.ragdoll.shiftPos(-ragdollPos.x, -ragdollPos.y, -ragdollPos.z);
    }

    public void setRemainingLife(int ticks) {
        this.remainingLife = ticks;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbtTagCompound) {

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbtTagCompound) {

    }

    public void setSpawnPosition(double posX, double posY, double posZ) {
        posY += (ragdoll.centerHeightOffset / 16f);
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        float f = this.width / 2.0F;
        float f1 = this.height;
        this.setEntityBoundingBox(new AxisAlignedBB(posX - (double)f, posY, posZ - (double)f, posX + (double)f, posY + (double)f1, posZ + (double)f));

        // the entity position will probably follow the simulated ragdoll position and not the other way.
        //this.ragdoll.setRagdollPos(this.posX, this.posY, this.posZ);
    }

    /**
     * Sets the rotation of the entity
     */
    public void setRotation(float rotYaw/*, float p_70101_2_*/)
    {
        this.ragdoll.rotateRagdoll(rotYaw);
        //this.rotationPitch = p_70101_2_ % 360.0F;
    }

}
