package com.sekwah.sekcphysics.ragdoll.generation;

import com.google.gson.*;
import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.ragdoll.generation.data.*;
import com.sekwah.sekcphysics.ragdoll.generation.data.tracker.TriangleTrackerData;
import com.sekwah.sekcphysics.ragdoll.generation.data.tracker.VertexTrackerData;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ProgressManager;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
                    addRagdollSkeletonPointData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    addRagdollConstraintData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    addRagdollTrackerData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    addRagdollOtherData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    ModelConstructData modelConstructData = getRagdollModelData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    createModelAndAddTrackers(ragdollData, modelConstructData);

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
    private void addRagdollSkeletonPointData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                    JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);
        if(inherit != null) {
            addRagdollSkeletonPointData(inherit.getAsJsonObject(), ragdollData,
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

        //return ragdollData;
    }

    private void addRagdollConstraintData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                 JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);
        if(inherit != null) {
            addRagdollConstraintData(inherit.getAsJsonObject(), ragdollData,
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

        //return ragdollData;
    }

    /**
     * Add the data about the trackers (constraints are used as the other trackers atm)
     * @param ragdollJsonData
     * @param ragdollData
     * @param ragdollFileJson
     * @return
     * @throws UnsupportedOperationException
     */
    private void addRagdollTrackerData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                              JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);
        if(inherit != null) {
            addRagdollTrackerData(inherit.getAsJsonObject(), ragdollData,
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

        //return ragdollData;
    }

    /**
     * For adding the data that does not fit in the other general methods such as the spawn height
     * @param ragdollJsonData
     * @param ragdollData
     * @param ragdollFileJson
     * @return
     */
    private void addRagdollOtherData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                            JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);
        if(inherit != null) {
            addRagdollOtherData(inherit.getAsJsonObject(), ragdollData,
                    ragdollFileJson);
        }

        JsonElement heightOffset = ragdollJsonData.get("centerHeightOffset");
        if(heightOffset != null) {
            ragdollData.centerHeightOffset = heightOffset.getAsFloat();
        }

        //return ragdollData;
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
    private ModelConstructData getRagdollModelData(JsonObject ragdollJsonData, RagdollData ragdollData,
                                                   JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);

        ModelConstructData modelConstructData;

        if(inherit != null) {
            modelConstructData = getRagdollModelData(inherit.getAsJsonObject(), ragdollData, ragdollFileJson);
        }
        else {
            modelConstructData = new ModelConstructData();
        }

        JsonObject modelJSON = ragdollJsonData.getAsJsonObject("modelData");
        if(modelJSON != null) {
            modelConstructData.setClassName(modelJSON.get("class").getAsString());

            JsonArray constructData = modelJSON.getAsJsonArray("constructData");

            if(constructData != null) {
                Object[] constructObjects = new Object[constructData.size()];
                for(int i = 0; i < constructObjects.length; i++) {
                    JsonArray entry = constructData.get(i).getAsJsonArray();
                    String type = entry.get(0).getAsString();
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
                            throw new RagdollInvalidDataException("Invalid Construct Data Type: " + type);
                    }
                    constructObjects[i] = data;
                }
                modelConstructData.setConstructData(constructObjects);
            }

            JsonObject vertexTrackers = modelJSON.getAsJsonObject("vertexTrackers");

            if(vertexTrackers != null) {
                Set<Map.Entry<String, JsonElement>> vertexNames = vertexTrackers.entrySet();
                for(Map.Entry<String, JsonElement> vertexName : vertexNames) {
                    JsonObject vertexObj = vertexName.getValue().getAsJsonObject();
                    String anchor = vertexObj.get("anchor").getAsString();
                    String pointTo = vertexObj.get("pointTo").getAsString();
                    VertexTrackerData trackerData = new VertexTrackerData(vertexName.getKey(), anchor, pointTo, vertexObj);
                    modelConstructData.addVertexTracker(trackerData);
                }
            }


            JsonObject triangleTrackers = modelJSON.getAsJsonObject("triangleTrackers");

            if(vertexTrackers != null) {
                Set<Map.Entry<String, JsonElement>> triangleNames = triangleTrackers.entrySet();
                for(Map.Entry<String, JsonElement> triangleName : triangleNames) {
                    JsonObject vertexObj = triangleName.getValue().getAsJsonObject();
                    String tracker = vertexObj.get("tracker").getAsString();
                    TriangleTrackerData trackerData = new TriangleTrackerData(triangleName.getKey(), tracker, vertexObj);
                    modelConstructData.addTriangleTracker(trackerData);
                }
            }
        }

        return modelConstructData;
    }

    /**
     * Create the model and link tracker data.
     *
     * @param ragdollData
     * @param modelConstructData
     * @return
     * @throws RagdollInvalidDataException
     */
    private ModelData createModelAndAddTrackers(RagdollData ragdollData, ModelConstructData modelConstructData) throws RagdollInvalidDataException {

        if(modelConstructData.getClassName() == null) {
            throw new RagdollInvalidDataException("Model class was not given");
        }

        try {
            Class rClass = Class.forName(modelConstructData.getClassName());

            if(ModelBase.class.isInstance(rClass)) {
                throw new RagdollInvalidDataException("Invalid model class");
            }

            /* Could store object array before but this should make it more robust when it works.
            Though this makes it so object versions of primitives cant be used (though its safe to say it wont
             be the case)
            */
            Object[] constructObjects = modelConstructData.getConstructData();
            Class[] classArray = new Class[constructObjects.length];
            for(int i = 0; i < constructObjects.length; i++) {
                Class<? extends Object> classType = constructObjects[i].getClass();
                try {
                    Object tempClassObj = classType.getField("TYPE").get(null);
                    if(tempClassObj instanceof Class) {
                        classType = (Class) tempClassObj;
                    }
                }
                catch (NoSuchFieldException e) {
                    SekCPhysics.logger.info("Constructor non primitive.");
                }
                classArray[i] = classType;
            }

            ModelBase modelBase = (ModelBase) rClass.getConstructor(classArray).newInstance(constructObjects);

            ModelData modelData = new ModelData(modelBase);

            VertexTrackerData[] vertexTrackers = modelConstructData.getVertexTrackerData();
            for(VertexTrackerData vertexData : vertexTrackers) {
                ModelRenderer renderer = getRendererFromName(vertexData.getPartName(), modelBase, rClass);
                vertexData.setPart(renderer);
            }
            modelData.setVertexTrackers(vertexTrackers);

            TriangleTrackerData[] triangleTrackers = modelConstructData.getTriangleTrackerData();
            for(TriangleTrackerData triangleData : triangleTrackers) {
                ModelRenderer renderer = getRendererFromName(triangleData.getPartName(), modelBase, rClass);
                triangleData.setPart(renderer);
            }
            modelData.setTriangleTrackers(triangleTrackers);

        }
        catch (ClassNotFoundException e) {
            SekCPhysics.logger.error("Could not find specified class" + modelConstructData.getClassName());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            SekCPhysics.logger.error("Illegal access");
            e.printStackTrace();
        } catch (InstantiationException | InvocationTargetException e) {
            SekCPhysics.logger.error("Construction error");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            SekCPhysics.logger.error("Method not found, invalid class of construction data listed");
            e.printStackTrace();
        } catch (SecurityException e) {
            SekCPhysics.logger.error("The security manager has blocked access to the class"
                    + modelConstructData.getClassName());
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            SekCPhysics.logger.error("Field not found for "
                    + modelConstructData.getClassName());
            e.printStackTrace();
        }

        return null;
    }

    private ModelRenderer getRendererFromName(String partName, ModelBase modelBase, Class<?> rClass) throws NoSuchFieldException, RagdollInvalidDataException, IllegalAccessException {
        Field partRender = rClass.getField(partName);
        Object partObj = partRender.get(modelBase);
        if(partObj instanceof ModelRenderer) {
            return (ModelRenderer) partObj;
        }
        throw new RagdollInvalidDataException("Unexpected object type stored in location");
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
