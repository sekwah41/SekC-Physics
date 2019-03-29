package com.sekwah.sekcphysics.ragdoll;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.accessors.EntityTextureAccessor;
import com.sekwah.sekcphysics.client.cliententity.RagdollEntity;
import com.sekwah.sekcphysics.client.render.RenderRagdoll;
import com.sekwah.sekcphysics.ragdoll.generation.data.RagdollData;
import com.sekwah.sekcphysics.ragdoll.ragdolls.generated.FromDataRagdoll;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

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

    private static MinecraftClient mc = MinecraftClient.getInstance();

    public List<RagdollEntity> ragdolls = new ArrayList<>();

    public static EntityRenderDispatcher entityRenderDispatcher;

    /**
     * Need to add update counts to the ragdoll data rather than global also 10 is for cloths
     */
    //public static int maxUpdateCount = 10;

    //public static float gravity = 0.05F;
    public static float gravity = 0F;

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

    public void updateRagdolls() {
        this.ragdolls.removeIf(ragdoll -> ragdoll.invalid);

        for(RagdollEntity ragdoll : this.ragdolls) {
            ragdoll.updateLogic();
        }
    }

    public void spawnRagdoll(RagdollEntity ragdoll) {
        this.ragdolls.add(ragdoll);
    }

    public FromDataRagdoll createRagdoll(Entity entity) {

        FromDataRagdoll ragdoll = null;

        try
        {
            String entityClass = entity.getClass().getName();
            if(mc.options.debugEnabled) {
                SekCPhysics.logger.debug("Entity died: {}", entityClass);
            }
            RagdollData ragdollData = entityToRagdollHashmap.computeIfAbsent(entityClass, (key) -> {
                Class classc = entity.getClass();
                while(classc != Entity.class) {
                    classc = classc.getSuperclass();
                    RagdollData superRagdollData = entityToRagdollHashmap.get(classc.getName());
                    if (superRagdollData != null) {
                        RagdollData ragdollDataClone = superRagdollData.clone();
                        entityToRagdollHashmap.put(entityClass, ragdollDataClone);
                        return superRagdollData;
                    }
                }
                return null;
            });

            if (ragdollData != null) {
                ragdoll = new FromDataRagdoll(ragdollData);
                ragdoll.resourceLocation = ((EntityTextureAccessor) MinecraftClient.getInstance().getEntityRenderManager().getRenderer(entity)).getBaseTexture(entity);
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
