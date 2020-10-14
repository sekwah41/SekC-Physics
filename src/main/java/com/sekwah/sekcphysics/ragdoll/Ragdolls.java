package com.sekwah.sekcphysics.ragdoll;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.generation.data.RagdollData;
import com.sekwah.sekcphysics.ragdoll.ragdolls.generated.FromDataRagdoll;
import com.sekwah.sekcphysics.settings.RagdollConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sekawh on 8/4/2015.
 */
public class Ragdolls {
    // add code to store a hashmap of all entities to ragdolls

    // Key is entity class and stores a ragdoll class
    private static Map<String, RagdollData> entityToRagdollHashmap = new HashMap<String, RagdollData>();


    private static Minecraft mc = Minecraft.getInstance();

    public List<EntityRagdoll> ragdolls = new ArrayList<>();



    /**
     * Need to add update counts to the ragdoll data rather than global also 10 is for cloths
     */
    //public static int maxUpdateCount = 10;

    public static float gravity = 0.045F; // alter till it looks the best, also maybe add material values as mods use stuff like

    public void registerRagdoll(Class<? extends Entity> entityClass, RagdollData ragdollData) {
        entityToRagdollHashmap.put(entityClass.getName(), ragdollData);
    }

    /**
     * Will overwrite ragdolls if there is one already there
     * @param entityClass
     * @param ragdollData
     */
    public void registerRagdoll(String entityClass, RagdollData ragdollData) {
        entityToRagdollHashmap.put(entityClass, ragdollData);
    }

    public void updateRagdolls() {
        synchronized (this.ragdolls) {
            Entity player = FMLClientHandler.instance().getClientPlayerEntity();
            this.ragdolls.removeIf(ragdoll -> ragdoll.isDead || ragdoll.posY < -64 || ragdoll.getDistanceSq(player) > 64 * 64);
            if(RagdollConfig.maxRagdolls != -1) {
                int removeCount = this.ragdolls.size() - RagdollConfig.maxRagdolls;
                if(removeCount > 0) {
                    for (int i = 0; i < removeCount; i++) {
                        this.ragdolls.remove(0);
                    }
                }
            }

            for(EntityRagdoll ragdoll : this.ragdolls) {
                if(ragdoll.getEntityWorld().getChunkFromChunkCoords((int) ragdoll.posX / 16, (int) ragdoll.posZ / 16).isLoaded()) {
                    ragdoll.onUpdate();
                }
            }
        }
    }

    public void spawnRagdoll(EntityRagdoll ragdoll) {
        synchronized (this.ragdolls) {
            this.ragdolls.add(ragdoll);
        }
    }

    public FromDataRagdoll createRagdoll(Entity entity) {

        FromDataRagdoll ragdoll = null;

        // TODO add code to detect the baby versions and add new ragdolls

        try
        {
            if(mc.gameSettings.showDebugInfo) {
                SekCPhysics.LOGGER.info("Entity died: {}", entity.getClass().getName());
            }
            RagdollData ragdollData = entityToRagdollHashmap.computeIfAbsent(entity.getClass().getName(), (key) -> {
                Class classc = entity.getClass();
                while(classc != Entity.class) {
                    classc = classc.getSuperclass();
                    RagdollData superRagdollData = entityToRagdollHashmap.get(classc.getName());
                    if (superRagdollData != null) {
                        RagdollData ragdollDataClone = superRagdollData.clone();
                        entityToRagdollHashmap.put(entity.getClass().getName(), ragdollDataClone);
                        return superRagdollData;
                    }
                }
                return null;
            });

            if (ragdollData != null) {
                ragdoll = new FromDataRagdoll(ragdollData);
                ragdoll.resourceLocation = SekCPhysics.REFLECTION.getResource(FMLClientHandler.instance().getClient().getRenderManager().getEntityRenderObject(entity), entity);//FMLClientHandler.instance().getClient().getRenderManager().getEntityRenderObject(entity).getEntityTexture(entity);//
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return ragdoll;
    }

    public void reset() {
        entityToRagdollHashmap.clear();
    }


    // Material.rock for the sounds, could add a bit of bouncing from materials rather than instantly losing velocity when it hits soemthing.

    // Also store an array of current ragdolls(dont forget to monitor lag and if its lagging MASSIVELY remove 50% of oldest ragdolls
    //  however this may all change, add a debug item like diamond or a client command(probs best) for removing all.

    /*
    look at the code for moveEntity in entities for the collisions, also for future stuff it may be usefull to use it for future
    physics stuff in mods(stupid for not using this in the past probably)
     */

    // This is all going to be called from the entities and renders.

    /*public void updateRagdolls() {
        for(Object ragdollObj : currentRagdolls) {
            if(ragdollObj instanceof BaseRagdoll) {
                BaseRagdoll ragdoll = (BaseRagdoll) ragdollObj;

            }
        }
    }
    // May be needed if you cant do it as seperate entities although it could be more efficient.
    public void renderRagdolls() {
        for(Object ragdollObj : currentRagdolls) {
            if(ragdollObj instanceof BaseRagdoll) {
                BaseRagdoll ragdoll = (BaseRagdoll) ragdollObj;
                ragdoll.skeleton.renderSkeletonDebug();
            }
        }
    }*/
}
