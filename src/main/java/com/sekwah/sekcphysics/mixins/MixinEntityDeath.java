package com.sekwah.sekcphysics.mixins;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(EntityLivingBase.class)
public abstract class MixinEntityDeath extends Entity {

    @Shadow
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot p_getItemStackFromSlot_1_) {return null;}

    @Shadow
    public boolean isChild() {return false;}

    public MixinEntityDeath(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    @Inject(method = "onDeath", at = @At("RETURN"))
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        if(!this.world.isRemote || this.isChild()) {
            return;
        }

        BaseRagdoll ragdoll = SekCPhysics.ragdolls.createRagdoll(this);

        if(ragdoll != null) {

            EntityRagdoll entityRagdoll = new EntityRagdoll(this.world, ragdoll);

            entityRagdoll.ragdoll.setStanceToEntity((EntityLivingBase) (Entity) this);

            entityRagdoll.setSpawnPosition(this.posX, this.posY, this.posZ);

            this.world.spawnEntity(entityRagdoll);

            entityRagdoll.ragdoll.rotateRagdoll(this.rotationYaw);

            entityRagdoll.ragdoll.skeleton.verifyPoints(entityRagdoll);

            entityRagdoll.ragdoll.update(entityRagdoll);

            for(EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
                entityRagdoll.setItemStackToSlot(slot, this.getItemStackFromSlot(slot));
            }

            this.setDead();
        }

    }

}
