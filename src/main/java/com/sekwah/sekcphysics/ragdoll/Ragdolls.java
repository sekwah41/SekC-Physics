package com.sekwah.sekcphysics.ragdoll;

import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sekawh on 8/4/2015.
 */
public class Ragdolls {
    // add code to store a hashmap of all entities to ragdolls

    // Key is entity class and stores a ragdoll class
    private static Map entityToRagdollHashmap = new HashMap();

    // To get a list of ragdolls go through all the alive entities in the world and check for an instace of
    //public List currentRagdolls = new ArrayList();

    // The number of times to process the distances each oldUpdate.
    // The ragdoll applies these a few times then moves the points
    public static int updateCount = 5;

    public static float gravity = 0.05F; // alter till it looks the best, also maybe add material values as mods use stuff like

    public void registerRagdoll(Class<? extends Entity> entityClass, Class<? extends BaseRagdoll> ragdollClass) {
        this.entityToRagdollHashmap.put(entityClass, ragdollClass);
    }

    public BaseRagdoll createRagdoll(Entity entity){
        BaseRagdoll ragdoll = null;

        // TODO add code to detect the baby versions and add new ragdolls

        try
        {
            Class rClass = (Class)entityToRagdollHashmap.get(entity.getClass());

            if (rClass != null)
            {
                // in case needed to add arguments to constructor in the future
                // it can be done like this for stuff like entities
                // (Entity)rClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {worldObj});
                ragdoll = (BaseRagdoll)rClass.getConstructor(new Class[] {}).newInstance(new Object[]{});
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
