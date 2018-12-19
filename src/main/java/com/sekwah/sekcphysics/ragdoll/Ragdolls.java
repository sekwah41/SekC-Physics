package com.sekwah.sekcphysics.ragdoll;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.ragdoll.generation.data.RagdollData;
import com.sekwah.sekcphysics.ragdoll.ragdolls.generated.FromDataRagdoll;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sekawh on 8/4/2015.
 */
public class Ragdolls {
    // add code to store a hashmap of all entities to ragdolls

    // Key is entity class and stores a ragdoll class
    private static Map<String, RagdollData> entityToRagdollHashmap = new HashMap<String, RagdollData>();

    private static MinecraftClient mc = MinecraftClient.getInstance();

    /**
     * Need to add update counts to the ragdoll data rather than global also 10 is for cloths
     */
    //public static int maxUpdateCount = 10;

    public static float gravity = 0.05F; // alter till it looks the best, also maybe add material values as mods use stuff like

    public void registerRagdoll(Class<? extends Entity> entityClass, RagdollData ragdollData) {
        this.entityToRagdollHashmap.put(entityClass.getName(), ragdollData);

    }

    /**
     * Will overwrite ragdolls if there is one already there
     * @param entityClass
     * @param ragdollData
     */
    public void registerRagdoll(String entityClass, RagdollData ragdollData) {
        this.entityToRagdollHashmap.put(entityClass, ragdollData);
    }

    public FromDataRagdoll createRagdoll(Entity entity) {

        FromDataRagdoll ragdoll = null;

        // TODO add code to detect the baby versions and add new ragdolls

        try
        {
            if(mc.options.debugEnabled) {
                SekCPhysics.logger.info("Entity died: {}", entity.getClass().getName());
            }
            RagdollData ragdollData = entityToRagdollHashmap.get(entity.getClass().getName());

            if (ragdollData != null) {
                ragdoll = new FromDataRagdoll(ragdollData);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return ragdoll;
    }

    // Material.rock for the sounds, could add a bit of bouncing from materials rather than instantly losing velocity when it hits soemthing.
}
