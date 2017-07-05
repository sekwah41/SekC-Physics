package com.sekwah.sekcphysics.ragdoll.generation;

import com.google.gson.*;
import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollInvalidDataException;
import com.sekwah.sekcphysics.ragdoll.generation.RagdollData;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ProgressManager;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alastair on 24/01/2016.
 */
public class RagdollGenerator {

    public static void generateRagdollsFrom(String modid) {
        // TODO check for the mod id and if not found then report false. If found generate ragdolls.
        try {
            Reader fileIn = new InputStreamReader(SekCPhysics.class.getResourceAsStream("/assets/sekcphysics/ragdolldata/" + modid + ".json"));
            Gson jsonFile = new Gson();
            JsonObject ragdollFileJson = jsonFile.fromJson(fileIn, JsonObject.class);
            Set<Map.Entry<String, JsonElement>> entityEnteries = ragdollFileJson.entrySet();
            ProgressManager.ProgressBar bar = ProgressManager.push("Constructing", entityEnteries.size());
            for(Map.Entry<String, JsonElement> entry : entityEnteries) {
                bar.step(entry.getKey());
                try {
                    RagdollData ragdollData = new RagdollData();
                    ragdollData = addRagdollSkeletonPointData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    ragdollData = addRagdollConstraintData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    ragdollData = addRagdollTrackerData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    ragdollData = addRagdollOtherData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);

                    SekCPhysics.ragdolls.registerRagdoll(entry.getKey(), ragdollData);
                }
                catch(ClassCastException | RagdollInvalidDataException | IllegalStateException
                        | UnsupportedOperationException e) {
                    SekCPhysics.logger.error("Invalid data for: " + entry.getKey());
                    SekCPhysics.logger.error("Error message: " + e.getMessage());
                    SekCPhysics.logger.catching(Level.ERROR, e);
                }
            }
            ProgressManager.pop(bar);
            SekCPhysics.logger.info("Data loaded for: " + modid);
        }
        catch(JsonSyntaxException | UnsupportedOperationException  e) {
            SekCPhysics.logger.info("Error with data for: " + modid);
            SekCPhysics.logger.catching(Level.ERROR, e);
        }
        catch(JsonIOException | NullPointerException e) {
            SekCPhysics.logger.info("No ragdoll data found for: " + modid);
            // Use for finding errors in program when file should be found
            //e.printStackTrace();
        }
    }

    /**
     * Add the data about the skeleton points
     * @param ragdollJsonData
     * @param ragdollData
     * @param ragdollFileJson
     * @return
     * @throws UnsupportedOperationException
     * @throws RagdollInvalidDataException
     */
    private static RagdollData addRagdollSkeletonPointData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                           JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);
        if(inherit != null) {
            ragdollData = addRagdollSkeletonPointData(inherit.getAsJsonObject(), ragdollData,
                    ragdollFileJson);
        }

        JsonObject skeletonPoints = ragdollJsonData.getAsJsonObject("skeletonPoints");
        if(skeletonPoints != null) {
            Set<Map.Entry<String, JsonElement>> pointNames = skeletonPoints.entrySet();
            for(Map.Entry<String, JsonElement> pointName : pointNames) {
                JsonArray pointPosArray = skeletonPoints.get(pointName.getKey()).getAsJsonArray();
                ragdollData.setSkeletonPoint(pointName.getKey(), pointPosArray.get(0).getAsDouble(),
                        pointPosArray.get(1).getAsDouble(), pointPosArray.get(2).getAsDouble());
            }
        }
        else{
            throw new RagdollInvalidDataException("No skeleton points");
        }

        return ragdollData;
    }

    private static RagdollData addRagdollConstraintData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                        JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);
        if(inherit != null) {
            ragdollData = addRagdollConstraintData(inherit.getAsJsonObject(), ragdollData,
                    ragdollFileJson);
        }

        JsonArray constraints = ragdollJsonData.getAsJsonArray("constraints");

        if(constraints != null) {
            for(JsonElement constraintEle : constraints) {
                JsonArray constraint = constraintEle.getAsJsonArray();
                ragdollData.addConstraint(constraint.get(0).getAsString(), constraint.get(1).getAsString());
            }
        }
        else{
            throw new RagdollInvalidDataException("No constraints");
        }

        return ragdollData;
    }

    /**
     * Add the data about the trackers
     * @param ragdollJsonData
     * @param ragdollData
     * @param ragdollFileJson
     * @return
     * @throws UnsupportedOperationException
     */
    private static RagdollData addRagdollTrackerData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                     JsonObject ragdollFileJson) throws UnsupportedOperationException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);
        if(inherit != null) {
            ragdollData = addRagdollTrackerData(inherit.getAsJsonObject(), ragdollData,
                    ragdollFileJson);
        }
        return ragdollData;
    }

    /**
     * For adding the data that does not fit in the other general methods such as the spawn height
     * @param ragdollJsonData
     * @param ragdollData
     * @param ragdollFileJson
     * @return
     */
    private static RagdollData addRagdollOtherData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                   JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);
        if(inherit != null) {
            ragdollData = addRagdollOtherData(inherit.getAsJsonObject(), ragdollData,
                    ragdollFileJson);
        }

        JsonElement heightOffset = ragdollJsonData.get("centerHeightOffset");
        if(heightOffset != null){
            ragdollData.centerHeightOffset = heightOffset.getAsFloat();
        }

        return ragdollData;
    }

    /**
     * Get the inherit json data that is being pointed to.
     * @param enteryJson
     * @param ragdollFileJson
     * @return
     */
    private static JsonElement getInheritData(JsonObject enteryJson, JsonObject ragdollFileJson) {
        JsonElement inherit = enteryJson.get("inherit");
        if(inherit == null) return null;
        return ragdollFileJson.get(inherit.getAsString());
    }

    public static void loadRagdolls() {
        SekCPhysics.logger.debug("Loading ragdolls and checking for supported mods");
        List<ModContainer> modlist = Loader.instance().getActiveModList();
        ProgressManager.ProgressBar bar = ProgressManager.push("SekCPhysics", modlist.size());
        for(ModContainer mod : modlist) {
            bar.step("Processing " + mod.getModId());
            generateRagdollsFrom(mod.getModId());
        }
        ProgressManager.pop(bar);
    }
}
