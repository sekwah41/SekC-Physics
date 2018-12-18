package com.sekwah.sekcphysics.mixins;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import net.minecraft.container.Slot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(LivingEntity.class)
public abstract class MixinEntityDeath extends Entity {

    @Shadow
    public ItemStack getEquippedStack(EquipmentSlot equipmentSlot) {return null;}

    @Shadow
    public boolean isChild() {return false;}

    public MixinEntityDeath(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    @Inject(method = "onDeath", at = @At("RETURN"))
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        if(!this.world.isClient || this.isChild()) {
            return;
        }

        BaseRagdoll ragdoll = SekCPhysics.ragdolls.createRagdoll(this);

        if(ragdoll != null) {

            EntityRagdoll entityRagdoll = new EntityRagdoll(this.world, ragdoll);

            entityRagdoll.ragdoll.setStanceToEntity((LivingEntity) (Object) this);

            entityRagdoll.setSpawnPosition(this.x, this.y, this.z);

            this.world.spawnEntity(entityRagdoll);

            entityRagdoll.ragdoll.rotateRagdoll(this.yaw);

            entityRagdoll.ragdoll.skeleton.verifyPoints(entityRagdoll);

            entityRagdoll.ragdoll.update(entityRagdoll);

            for(EquipmentSlot slot : EquipmentSlot.values()) {
                entityRagdoll.setEquippedStack(slot, this.getEquippedStack(slot));
            }

            this.invalidate();
        }

    }

}
