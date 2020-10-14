package com.sekwah.sekcphysics.client;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.List;

public class RagdollRenderer {

    public static EntityRendererManager renderManager;

    public static Minecraft mc = Minecraft.getInstance();
    public static int renderCounter = 0;

    public static void renderRagdolls(String profileName) {

        if(!profileName.equals("entities")) return;

        int pass = MinecraftForgeClient.getRenderPass();

        if(pass == -1) {
            renderCounter = 0;
        }

        if(renderCounter++ != 1) return;

        //if(pass != -1) return;

        List<EntityRagdoll> ragdollList = SekCPhysics.RAGDOLLS.ragdolls;

        //System.out.println(ragdollList.size());

        World world = mc.player.getEntityWorld();

        float partialTicks = mc.getRenderPartialTicks();

        synchronized (SekCPhysics.RAGDOLLS.ragdolls) {
            ragdollList.removeIf(ragdoll -> ragdoll.world != world);

            for (EntityRagdoll ragdoll : ragdollList) {
                renderManager.renderEntityStatic(ragdoll, partialTicks, false);
            }
        }
    }
}
