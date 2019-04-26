package com.sekwah.sekcphysics.mixins.client;

import com.sekwah.sekcphysics.SekCPhysics;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    // Tick method
    @Inject(method = "tickEntities", at = @At("RETURN"))
    public void tickEntities(CallbackInfo ci) {
        SekCPhysics.ragdolls.updateRagdolls();
    }

}
