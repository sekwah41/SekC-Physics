package com.sekwah.sekcphysics.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityDeath extends EntityLivingBase {

    @Shadow
    public int deathTime;

    protected MixinEntityDeath(EntityType<?> p_i48577_1_, World p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @Inject(method = "onDeath", at = @At("RETURN"))
    public void onDeath(DamageSource p_onDeath_1_, CallbackInfo ci) {
        System.out.println("DEAD");
        //this.update();
    }

}
