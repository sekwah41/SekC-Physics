package com.sekwah.sekcphysics.ragdoll.generation.runtime;

import com.sekwah.sekcphysics.SekCPhysics;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.StrayEntityModel;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;

import java.util.HashMap;
import java.util.Map;

public class VanillaModelMapping {

    public Map<String, String> map = new HashMap<>();

    /**
     * This has been added as the mappings dont seem to revert fully back to mc names
     * Also saves time rewriting all the classes. Though field names may be a pain
     */
    public VanillaModelMapping() {
        // Entities
        map.put("net.minecraft.entity.mob.SkeletonEntity", SkeletonEntity.class.getName());
        map.put("net.minecraft.entity.mob.ZombieEntity", ZombieEntity.class.getName());
        map.put("net.minecraft.entity.mob.GiantEntity", GiantEntity.class.getName());
        map.put("net.minecraft.entity.mob.HuskEntity", HuskEntity.class.getName());

        // Models
        map.put("net.minecraft.client.render.entity.model.BipedEntityModel", BipedEntityModel.class.getName());
        map.put("net.minecraft.client.render.entity.model.StrayEntityModel", StrayEntityModel.class.getName());
    }

    public String getClassName(String className) {
        String mapName = map.get(className);
        SekCPhysics.logger.info("Swapping to compiled mapping name: " + className + " -> " + (mapName == null ? className : mapName));
        return mapName == null ? className : mapName;
    }
}
