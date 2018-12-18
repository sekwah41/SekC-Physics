package com.sekwah.sekcphysics.client.cliententity;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.Tracker;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sortme.OptionMainHand;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.world.World;

public class EntityRagdoll extends LivingEntity {

    private final DefaultedList<ItemStack> handItems;
    private final DefaultedList<ItemStack> armorItems;

    public BaseRagdoll ragdoll;

    private int remainingLife = 600;

    public EntityRagdoll(World world) {
        this(SekCPhysics.RAGDOLL, world);
    }

    public EntityRagdoll(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.handItems = DefaultedList.create(2, ItemStack.EMPTY);
        this.armorItems = DefaultedList.create(4, ItemStack.EMPTY);
    }

    @Override
    public Iterable<ItemStack> getItemsArmor() {
        return null;
    }

    public ItemStack getEquippedStack(EquipmentSlot var1) {
        switch(var1) {
            case HAND_MAIN:
                return this.handItems.get(0);
            case HAND_OFF:
                return this.handItems.get(1);
            case FEET:
                return this.armorItems.get(0);
            case LEGS:
                return this.armorItems.get(1);
            case CHEST:
                return this.armorItems.get(2);
            case HEAD:
                return this.armorItems.get(3);
            default:
                return ItemStack.EMPTY;
        }
    }

    @Override
    public void setEquippedStack(EquipmentSlot var1, ItemStack var2) {
        switch(var1) {
            case HAND_MAIN:
                this.handItems.set(0, var2);
                break;
            case HAND_OFF:
                this.handItems.set(1, var2);
                break;
            case FEET:
                this.armorItems.set(0, var2);
                break;
            case LEGS:
                this.armorItems.set(1, var2);
                break;
            case CHEST:
                this.armorItems.set(2, var2);
                break;
            case HEAD:
                this.armorItems.set(3, var2);
                break;
        }
    }

    @Override
    public OptionMainHand getMainHand() {
        return null;
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

    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(0D);
    }

    @Override
    public void updateLogic()
    {
        if(this.ragdoll == null) {
            this.destroy();
            return;
        }

        if(remainingLife-- < 0) {

            for (int i = 0; i < 10; ++i) {
                float poofSize = 1.0f;
                double d0 = this.random.nextGaussian() * 0.04D;
                double d1 = this.random.nextGaussian() * 0.04D;
                double d2 = this.random.nextGaussian() * 0.04D;

                this.world.addParticle(ParticleTypes.CLOUD, this.x + (double) (this.random.nextFloat() * poofSize * 2.0F) - (double) poofSize, this.y + this.height / 2 + (double) (this.random.nextFloat() * this.height), this.z + (double) (this.random.nextFloat() * poofSize * 2.0F) - (double) poofSize, d0, d1, d2);
            }

            this.invalidate();
        }

        this.ragdoll.update(this);

        PointD ragdollPos = this.ragdoll.skeleton.points.get(0).toPoint();

        this.setPosition(this.x + ragdollPos.x, this.y + ragdollPos.y, this.z + ragdollPos.z);

        this.ragdoll.shiftPos(-ragdollPos.x, -ragdollPos.y, -ragdollPos.z);
    }

    public void setRemainingLife(int ticks) {
        this.remainingLife = ticks;
    }

    @Override
    public void readCustomDataFromTag(CompoundTag compoundTag) {

    }

    @Override
    public void writeCustomDataToTag(CompoundTag compoundTag) {

    }

    public void setSpawnPosition(double posX, double posY, double posZ) {
        posY += (ragdoll.centerHeightOffset / 16f);
        this.x = posX;
        this.y = posY;
        this.z = posZ;
        float f = this.width / 2.0F;
        float f1 = this.height;
        this.setBoundingBox(new BoundingBox(posX - (double)f, posY, posZ - (double)f, posX + (double)f, posY + (double)f1, posZ + (double)f));
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
