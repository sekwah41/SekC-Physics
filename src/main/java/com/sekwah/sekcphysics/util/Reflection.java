package com.sekwah.sekcphysics.util;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Reflection {

    private Class renderClass;

    private Method getResource;

    public Reflection() {
        renderClass = Render.class;

        getResource = findMethod(renderClass, ResourceLocation.class, Entity.class);
        if (getResource != null) {
            getResource.setAccessible(true);
        }
    }

    // Be careful of use, doesnt work well if if there are multiple methods with similar uses.
    private Method findMethod(Class searchClass, Class returnType, Class... parameterTypes) {
        for(Method method : searchClass.getDeclaredMethods()) {
            //System.out.println(method.getName());
            if(method.getReturnType() == returnType && Arrays.equals(method.getParameterTypes(), parameterTypes)) {
                return method;
            }
        }
        return null;
    }

    public ResourceLocation getResource(Render render, Entity entity) {
        try {
            return (ResourceLocation) getResource.invoke(render, entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return TextureManager.RESOURCE_LOCATION_EMPTY;
    }

}
