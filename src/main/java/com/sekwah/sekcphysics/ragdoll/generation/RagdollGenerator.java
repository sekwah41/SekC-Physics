package com.sekwah.sekcphysics.ragdoll.generation;

import com.google.gson.*;
import com.sekwah.sekcphysics.SekCPhysics;
import net.minecraft.client.model.ModelBase;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ProgressManager;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Ragdoll data generator
 * Created by Alastair on 24/01/2016.
 */
public class RagdollGenerator {

    private void generateRagdollsFrom(String modid) {
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
                    ModelData modelData = addRagdollModelData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    ragdollData = createModel(ragdollData, modelData);

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
    private RagdollData addRagdollSkeletonPointData(JsonObject ragdollJsonData, RagdollData ragdollData,
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

    private RagdollData addRagdollConstraintData(JsonObject ragdollJsonData, RagdollData ragdollData,
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
     * Add the data about the trackers (constraints are used as the other trackers atm)
     * @param ragdollJsonData
     * @param ragdollData
     * @param ragdollFileJson
     * @return
     * @throws UnsupportedOperationException
     */
    private RagdollData addRagdollTrackerData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                              JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);
        if(inherit != null) {
            ragdollData = addRagdollTrackerData(inherit.getAsJsonObject(), ragdollData,
                    ragdollFileJson);
        }

        JsonObject triangleJSON = ragdollJsonData.getAsJsonObject("triangles");
        if(triangleJSON != null) {
            Set<Map.Entry<String, JsonElement>> triangleNames = triangleJSON.entrySet();
            for(Map.Entry<String, JsonElement> triangleName : triangleNames) {
                JsonArray pointPosArray = triangleJSON.get(triangleName.getKey()).getAsJsonArray();
                ragdollData.addTriangle(triangleName.getKey(), pointPosArray.get(0).getAsString(),
                        pointPosArray.get(1).getAsString(), pointPosArray.get(2).getAsString());
            }
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
    private RagdollData addRagdollOtherData(JsonObject ragdollJsonData, RagdollData ragdollData,
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
     * Fetches the data from the ragdoll json for the model.
     *
     * @param ragdollJsonData
     * @param ragdollData
     * @param ragdollFileJson
     * @return
     * @throws UnsupportedOperationException
     */
    private ModelData addRagdollModelData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                            JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);

        ModelData modelData;

        if(inherit != null) {
            modelData = addRagdollModelData(inherit.getAsJsonObject(), ragdollData, ragdollFileJson);
        }
        else {
            modelData = new ModelData();
        }

        JsonObject modelJSON = ragdollJsonData.getAsJsonObject("modelData");
        if(modelJSON != null) {
            modelData.setClassName(modelJSON.get("class").getAsString());

            JsonArray constructData = modelJSON.getAsJsonArray("constructData");

            if(constructData != null) {
                Object[] constructObjects = new Object[constructData.size()];
                for(int i = 0; i < constructObjects.length; i++) {
                    JsonArray entry = constructData.get(i).getAsJsonArray();
                    String type = entry.get(0).toString();
                    Object data = null;
                    JsonElement dataEle = entry.get(1);
                    switch(type) {
                        case "double":
                            data = dataEle.getAsDouble();
                            break;
                        case "float":
                            data = dataEle.getAsFloat();
                            break;
                        case "int":
                            data = dataEle.getAsInt();
                            break;
                        case "long":
                            data = dataEle.getAsLong();
                            break;
                        case "boolean":
                            data = dataEle.getAsBoolean();
                            break;
                        case "String":
                            data = dataEle.getAsString();
                            break;
                        default:
                            throw new RagdollInvalidDataException("Invalid Construct Data Type");
                    }
                    constructObjects[i] = data;
                }
                modelData.setConstructData(constructObjects);
            }

            JsonObject vertexTrackers = ragdollJsonData.getAsJsonObject("vertexTrackers");

            if(vertexTrackers != null) {
                Set<Map.Entry<String, JsonElement>> pointNames =
            }


            JsonObject triangleTrackers = ragdollJsonData.getAsJsonObject("triangleTrackers");

            /*JsonObject skeletonPoints = ragdollJsonData.getAsJsonObject("skeletonPoints");
            if(skeletonPoints != null) {
                Set<Map.Entry<String, JsonElement>> pointNames = skeletonPoints.entrySet();
                for(Map.Entry<String, JsonElement> pointName : pointNames) {
                    JsonArray pointPosArray = skeletonPoints.get(pointName.getKey()).getAsJsonArray();
                    ragdollData.setSkeletonPoint(pointName.getKey(), pointPosArray.get(0).getAsDouble(),
                            pointPosArray.get(1).getAsDouble(), pointPosArray.get(2).getAsDouble());
                }
            }*/



            // Add tracker data grabber here
        }

        return modelData;
    }

    private RagdollData createModel(RagdollData ragdollData, ModelData modelData) throws RagdollInvalidDataException {

        /*try
        {
            Class rClass = Class.forName(classLoc);

            if (rClass != null)
            {
                // Need to make a set of construction data for the model in the json.

                // Extra data trackers can come later.

                //zombieModel = new ModelBiped(0.0f, 0, 64, 64);



                // in case needed to add arguments to constructor in the future
                // it can be done like this for stuff like entities
                // (Entity)rClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {worldObj});
                //ragdoll = (BaseRagdoll)rClass.getConstructor(new Class[] {}).newInstance(new Object[]{});
            }
            else{

            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            throw new RagdollInvalidDataException("Could not find specified class");
        }*/

        return null;
    }

    /**
     * Get the inherit json data that is being pointed to.
     * @param enteryJson
     * @param ragdollFileJson
     * @return
     */
    private JsonElement getInheritData(JsonObject enteryJson, JsonObject ragdollFileJson) {
        JsonElement inherit = enteryJson.get("inherit");
        if(inherit == null) return null;
        return ragdollFileJson.get(inherit.getAsString());
    }

    public void loadRagdolls() {
        SekCPhysics.logger.debug("Loading ragdolls and checking for supported mods");
        List<ModContainer> modlist = Loader.instance().getActiveModList();
        ProgressManager.ProgressBar bar = ProgressManager.push("SekCPhysics", modlist.size());
        for(ModContainer mod : modlist) {
            bar.step("Processing " + mod.getModId());
            this.generateRagdollsFrom(mod.getModId());
        }
        ProgressManager.pop(bar);
    }
}
