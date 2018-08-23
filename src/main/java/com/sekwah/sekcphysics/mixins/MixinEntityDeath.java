package com.sekwah.sekcphysics.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
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
    public int deathTime;

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

        this.setDead();
        System.out.println("Client Death");

    }

    /*@Inject(method = "onDeathUpdate", at = @At("HEAD"))
    public void onDeathUpdate(CallbackInfo ci) {
        System.out.println("DeathUpdate");
        System.out.println(deathTime);
    }*/

}
