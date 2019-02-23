package com.sekwah.sekcphysics.mixins;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.client.cliententity.RagdollEntity;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VisibleRegion;
import net.minecraft.client.render.WorldRenderer;
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
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Inject(method = "renderEntities", at = @At("RETURN"))
    public void renderEntities(Entity entity_1, VisibleRegion visibleRegion_1, float float_1, CallbackInfo ci) {

    }
}
