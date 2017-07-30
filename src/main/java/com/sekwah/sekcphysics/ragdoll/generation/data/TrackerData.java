package com.sekwah.sekcphysics.ragdoll.generation.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by sekwah41 on 06/07/2017.
 */
public class TrackerData {

    protected float posOffsetX;
    protected float posOffsetY;
    protected float posOffsetZ;

    protected float rotOffsetX;
    protected float rotOffsetY;
    protected float rotOffsetZ;

    public void getOffsetData(JsonObject vertexObj) {
        this.setPosOffsetX(vertexObj.get("posOffX"));
        this.setPosOffsetY(vertexObj.get("posOffY"));
        this.setPosOffsetZ(vertexObj.get("posOffZ"));

        this.setRotOffsetX(vertexObj.get("rotOffX"));
        this.setRotOffsetY(vertexObj.get("rotOffY"));
        this.setRotOffsetZ(vertexObj.get("rotOffZ"));
    }

    public void setRotOffsetX(JsonElement rotOffsetX) {
        if(rotOffsetX != null) {
            this.rotOffsetX = rotOffsetX.getAsFloat();
        }
    }

    public float getRotOffsetX() {
        return rotOffsetX;
    }

    public float getRotOffsetY() {
        return rotOffsetY;
    }

    public void setRotOffsetY(JsonElement rotOffsetY) {
        if(rotOffsetY != null) {
            this.rotOffsetY = rotOffsetY.getAsFloat();
        }
    }

    public float getRotOffsetZ() {
        return rotOffsetZ;
    }

    public void setRotOffsetZ(JsonElement rotOffsetZ) {
        if(rotOffsetZ != null) {
            this.rotOffsetZ = rotOffsetZ.getAsFloat();
        }
    }

    public float getPosOffsetX() {
        return posOffsetX;
    }

    public void setPosOffsetX(JsonElement posOffsetX) {
        if(posOffsetX != null) {
            this.posOffsetX = posOffsetX.getAsFloat();
        }
    }

    public float getPosOffsetY() {
        return posOffsetY;
    }

    public void setPosOffsetY(JsonElement posOffsetY) {
        if(posOffsetY != null) {
            this.posOffsetY = posOffsetY.getAsFloat();
        }
    }

    public float getPosOffsetZ() {
        return posOffsetZ;
    }

    public void setPosOffsetZ(JsonElement posOffsetZ) {
        if(posOffsetZ != null) {
            this.posOffsetZ = posOffsetZ.getAsFloat();
        }
    }
}
