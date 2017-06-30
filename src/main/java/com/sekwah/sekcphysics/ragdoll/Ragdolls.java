package com.sekwah.sekcphysics.ragdoll;

import com.sekwah.sekcphysics.ragdoll.generation.RagdollData;
import net.minecraft.entity.Entity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sekawh on 8/4/2015.
 */
public class Ragdolls {
    // add code to store a hashmap of all entities to ragdolls

    // Key is entity class and stores a ragdoll class
    private static Map<String, RagdollData> entityToRagdollHashmap = new HashMap<String, RagdollData>();

    // To get a list of ragdolls go through all the alive entities in the world and check for an instace of
    //public List currentRagdolls = new ArrayList();

    public static int updateCount = 10;

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

    public FromDataRagdoll createRagdoll(Entity entity){
        FromDataRagdoll ragdoll = null;

        // TODO add code to detect the baby versions and add new ragdolls

        try
        {
            RagdollData ragdollData = entityToRagdollHashmap.get(entity.getClass().getName());

            if (ragdollData != null)
            {
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

    // Also store an array of current ragdolls(dont forget to monitor lag and if its lagging MASSIVELY remove 50% of oldest ragdolls
    //  however this may all change, add a debug item like diamond or a client command(probs best) for removing all.

    /*
    look at the code for moveEntity in entities for the collisions, also for future stuff it may be usefull to use it for future
    physics stuff in mods(stupid for not using this in the past probably)
     */

    // This is all going to be called from the entities and renders.

    /*public void updateRagdolls(){
        for(Object ragdollObj : currentRagdolls){
            if(ragdollObj instanceof BaseRagdoll){
                BaseRagdoll ragdoll = (BaseRagdoll) ragdollObj;

            }
        }
    }
    // May be needed if you cant do it as seperate entities although it could be more efficient.
    public void renderRagdolls(){
        for(Object ragdollObj : currentRagdolls){
            if(ragdollObj instanceof BaseRagdoll){
                BaseRagdoll ragdoll = (BaseRagdoll) ragdollObj;
                ragdoll.skeleton.renderSkeletonDebug();
            }
        }
    }*/
}
