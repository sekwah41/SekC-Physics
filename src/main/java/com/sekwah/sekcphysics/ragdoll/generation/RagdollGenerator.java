package com.sekwah.sekcphysics.ragdoll.generation;

import com.google.gson.*;
import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.maths.PointD;
import com.sekwah.sekcphysics.ragdoll.generation.data.*;
import com.sekwah.sekcphysics.ragdoll.generation.data.tracker.TriangleTrackerData;
import com.sekwah.sekcphysics.ragdoll.generation.data.tracker.VertexTrackerData;
import com.sekwah.sekcphysics.ragdoll.generation.runtime.VanillaModelMapping;
import net.fabricmc.loader.FabricLoader;
import net.fabricmc.loader.ModContainer;
import net.minecraft.class_3879;
import net.minecraft.client.model.Cuboid;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;

import java.io.InputStreamReader;
import java.io.Reader;
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

    public static final VanillaModelMapping modelMapping = new VanillaModelMapping();

    private void generateRagdollsFrom(String modid) {
        // TODO check for the mod id and if not found then report false. If found generate ragdolls.
        try {
            Reader fileIn = new InputStreamReader(SekCPhysics.class.getResourceAsStream("/assets/sekcphysics/ragdolldata/" + modid + ".json"));
            Gson jsonFile = new Gson();
            JsonObject ragdollFileJson = jsonFile.fromJson(fileIn, JsonObject.class);
            Set<Map.Entry<String, JsonElement>> entityEnteries = ragdollFileJson.entrySet();
            for(Map.Entry<String, JsonElement> entry : entityEnteries) {
                try {
                    RagdollData ragdollData = new RagdollData();
                    addRagdollSkeletonPointData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    applyModifiers(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    addRagdollConstraintData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    addRagdollTrackerData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    addRagdollOtherData(entry.getValue().getAsJsonObject(), ragdollData, ragdollFileJson);
                    ModelConstructData modelConstructData = getRagdollModelData(entry.getValue().getAsJsonObject(), ragdollFileJson);
                    ModelData modelData = createModelAndAddTrackers(ragdollData, modelConstructData);
                    ragdollData.addModelData(modelData);

                    String className = entry.getKey();
                    try {
                        Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        className = modelMapping.getClassName(className);
                    }
                    SekCPhysics.ragdolls.registerRagdoll(className, ragdollData);
                    SekCPhysics.logger.info("Registered ragdoll for entity: {}", entry.getKey());

                }
                catch(ClassCastException | RagdollInvalidDataException | IllegalStateException
                        | UnsupportedOperationException e) {
                    SekCPhysics.logger.error("Invalid data for: " + entry.getKey());
                    SekCPhysics.logger.error("Error message: " + e.getMessage());
                    SekCPhysics.logger.catching(Level.ERROR, e);
                }
            }

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
     * Applies the changer data such as scale values to the points
     * @param ragdollJsonData
     * @param ragdollData
     * @param ragdollFileJson
     */
    private void applyModifiers(JsonObject ragdollJsonData, RagdollData ragdollData,
                                JsonObject ragdollFileJson) {

        for(Map.Entry<String, PointD> entry : ragdollData.getPointMap().entrySet()) {
            PointD point = entry.getValue();
            ragdollData.setSkeletonPoint(entry.getKey(), point.x * ragdollData.getScale(),
                    point.y * ragdollData.getScale(), point.z * ragdollData.getScale());
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
            for(Map.Entry<String, JsonElement> pointName : skeletonPoints.entrySet()) {
                JsonArray pointPosArray = skeletonPoints.get(pointName.getKey()).getAsJsonArray();
                ragdollData.setSkeletonPoint(pointName.getKey(), pointPosArray.get(0).getAsDouble(),
                        pointPosArray.get(1).getAsDouble(), pointPosArray.get(2).getAsDouble());
            }
        }
        else{
            if(ragdollData.getPointMap().size() == 0) {
                throw new RagdollInvalidDataException("No skeleton points");
            }
        }

        JsonElement scaleValue = ragdollJsonData.get("entityScale");
        if(scaleValue != null) {
            ragdollData.setScale(scaleValue.getAsFloat());
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
        else {
            if(ragdollData.getConstraints().length == 0) {
                throw new RagdollInvalidDataException("No constraints");
            }
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
                                            JsonObject ragdollFileJson) throws UnsupportedOperationException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);
        if(inherit != null) {
            addRagdollOtherData(inherit.getAsJsonObject(), ragdollData,
                    ragdollFileJson);
        }

        JsonElement heightOffset = ragdollJsonData.get("centerHeightOffset");
        if(heightOffset != null) {
            ragdollData.setCenterHeightOffset(heightOffset.getAsFloat() * ragdollData.getScale());
        }

        //return ragdollData;
    }

    /**
     * Fetches the data from the ragdoll json for the model.
     *
     * @param ragdollJsonData
     * @param ragdollFileJson
     * @return
     * @throws UnsupportedOperationException
     */
    private ModelConstructData getRagdollModelData(JsonObject ragdollJsonData,
                                                   JsonObject ragdollFileJson) throws UnsupportedOperationException, RagdollInvalidDataException {
        JsonElement inherit = getInheritData(ragdollJsonData, ragdollFileJson);

        ModelConstructData modelConstructData;

        if(inherit != null) {
            modelConstructData = getRagdollModelData(inherit.getAsJsonObject(), ragdollFileJson);
        }
        else {
            modelConstructData = new ModelConstructData();
        }

        // TODO work on system for multiple skins, e.g. changing villagers based on a tracker or nbt data
        JsonElement textureEle = ragdollJsonData.get("texture");
        if(textureEle != null) {
            JsonArray texture = textureEle.getAsJsonArray();
            modelConstructData.setTextureDomain(texture.get(0).getAsString(), texture.get(1).getAsString());
        }

        JsonObject modelJSON = ragdollJsonData.getAsJsonObject("modelData");
        if(modelJSON != null) {

            // TODO example of a place where checking if its a dev environment is needed so switch it over

            String className = modelJSON.get("class").getAsString();
            try {
                Class.forName(className);
            } catch (ClassNotFoundException e) {
                SekCPhysics.logger.info("Swapping to compiled mapping name");
                className = modelMapping.getClassName(className);
            }
            modelConstructData.setClassName(className);

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
                    String name;
                    if(SekCPhysics.isDeObf || !vertexObj.has("obfName")) {
                        name = vertexName.getKey();
                    }
                    else {
                        name = vertexObj.get("obfName").getAsString();
                    }
                    VertexTrackerData trackerData = new VertexTrackerData(name, anchor, pointTo, vertexObj);
                    modelConstructData.addVertexTracker(trackerData);
                }
            }


            JsonObject triangleTrackers = modelJSON.getAsJsonObject("triangleTrackers");

            if(triangleTrackers != null) {
                Set<Map.Entry<String, JsonElement>> triangleNames = triangleTrackers.entrySet();
                for(Map.Entry<String, JsonElement> triangleName : triangleNames) {
                    JsonObject vertexObj = triangleName.getValue().getAsJsonObject();
                    String tracker = vertexObj.get("tracker").getAsString();
                    String name;
                    if(SekCPhysics.isDeObf || !vertexObj.has("obfName")) {
                        name = triangleName.getKey();
                    }
                    else {
                        name = vertexObj.get("obfName").getAsString();
                    }
                    TriangleTrackerData trackerData = new TriangleTrackerData(name, tracker, vertexObj);
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

            if(!class_3879.class.isAssignableFrom(rClass)) {
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

            class_3879 modelBase = (class_3879) rClass.getConstructor(classArray).newInstance(constructObjects);

            ModelData modelData = new ModelData(modelBase);

            this.addExtraModelData(ragdollData, modelData, modelConstructData);

            VertexTrackerData[] vertexTrackers = modelConstructData.getVertexTrackerData();
            for(VertexTrackerData vertexData : vertexTrackers) {
                Cuboid renderer = getRendererFromName(vertexData.getPartName(), modelBase, rClass);
                vertexData.setPart(renderer);
            }
            modelData.setVertexTrackers(vertexTrackers);

            TriangleTrackerData[] triangleTrackers = modelConstructData.getTriangleTrackerData();
            for(TriangleTrackerData triangleData : triangleTrackers) {
                Cuboid renderer = getRendererFromName(triangleData.getPartName(), modelBase, rClass);
                triangleData.setPart(renderer);
            }
            modelData.setTriangleTrackers(triangleTrackers);

            return modelData;

        }
        catch (ClassNotFoundException e) {
            SekCPhysics.logger.error("Could not find specified class " + modelConstructData.getClassName());
            SekCPhysics.logger.info("Is DeObf: " + SekCPhysics.isDeObf);
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

    private void addExtraModelData(RagdollData ragdollData, ModelData modelData, ModelConstructData modelConstructData) {
        ResourceData textureDomain = modelConstructData.getTextureDomain();
        modelData.setTexture(new Identifier(textureDomain.getTextureDomain(), textureDomain.getTexture()));
    }

    private Cuboid getRendererFromName(String partName, class_3879 modelBase, Class<?> rClass) throws NoSuchFieldException, RagdollInvalidDataException, IllegalAccessException {
        Field partRender = rClass.getField(partName);
        Object partObj = partRender.get(modelBase);
        if(partObj instanceof Cuboid) {
            return (Cuboid) partObj;
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

        SekCPhysics.logger.info("Processing: minecraft");
        this.generateRagdollsFrom("minecraft");
        List<ModContainer> modlist = FabricLoader.INSTANCE.getMods();
        for(ModContainer mod : modlist) {
            SekCPhysics.logger.info("Processing: {}", mod.getInfo().getName());
            this.generateRagdollsFrom(mod.getInfo().getId());
        }
    }
}
