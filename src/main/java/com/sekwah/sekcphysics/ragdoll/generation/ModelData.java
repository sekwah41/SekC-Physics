package com.sekwah.sekcphysics.ragdoll.generation;

/**
 * Store data about the entity
 *
 * Created by sekwah41 on 29/07/2017.
 */
public class ModelData {

    /**
     * Can only contain string int boolean float double and long for now.
     *
     * More can be added as they need to have support added.
     *
     * Add them into the RagdollGenerator
     */
    private Object[] constructData = new Object[0];

    private String className;

    /**
     * Ignores null values
     * @param className
     */
    public void setClassName(String className) {
        if(className == null) {
            return;
        }
        this.constructData = new Object[0];
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public Object[] getConstructData() {
        return constructData;
    }

    public void setConstructData(Object[] constructData) {
        this.constructData = constructData;
    }
}
