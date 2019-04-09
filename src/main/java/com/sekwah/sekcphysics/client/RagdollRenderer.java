package com.sekwah.sekcphysics.client;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.List;

public class RagdollRenderer {

    public static RenderManager renderManager;

    public static Minecraft mc = Minecraft.getMinecraft();
    public static boolean hasRendered = true;

    public static void renderRagdolls(String profileName) {

        if(!profileName.equals("entities")) return;

        int pass = MinecraftForgeClient.getRenderPass();

        if(pass != -1) return;

        List<EntityRagdoll> ragdollList = SekCPhysics.ragdolls.ragdolls;

        //System.out.println(ragdollList.size());

        World world = FMLClientHandler.instance().getClientPlayerEntity().world;

        float partialTicks = mc.getRenderPartialTicks();

        synchronized (SekCPhysics.ragdolls.sync) {
            ragdollList.removeIf(ragdoll -> ragdoll.world != world);

            for (EntityRagdoll ragdoll : ragdollList) {
                renderManager.renderEntityStatic(ragdoll, partialTicks, false);
            }
        }
    }
}
