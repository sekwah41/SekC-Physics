package com.sekwah.sekcphysics.mixins;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.client.cliententity.RagdollEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VisibleRegion;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;


@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow
    private ClientWorld world;

    @Shadow
    @Final
    private EntityRenderDispatcher entityRenderDispatcher;

    @Inject(method = "renderEntities", at = @At(value = "INVOKE_STRING", args = "ldc=entities", target="Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    public void renderEntities(Camera camera_1, VisibleRegion visibleRegion_1, float float_1, CallbackInfo ci) {

        List<RagdollEntity> ragdollList = SekCPhysics.ragdolls.ragdolls;

        ragdollList.removeIf(ragdoll -> ragdoll.world != world);

        for(RagdollEntity ragdoll : ragdollList) {
            this.entityRenderDispatcher.render(ragdoll, float_1, false);
        }
    }
}
