package com.sekwah.sekcphysics.cliententity.render.renderfactory;

import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.cliententity.render.RenderRagdoll;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by on 25/03/2016.
 *
 * I see absolutely no reason for this thing to exist at the moment... Either im using it wrong(havent looked up what
 * its meant for yet) or it is a useless pile of s**t. Could literally store it the old way and not need the factory.
 * Need to find if they store changelogs and stuff anywhere so you arn't blindly trying to find whats changed. Also
 * make 100% sure what T is, I have an idea but want to make sure where it can be used and how.
 * @author sekwah41
 */
public class RenderFac<T extends EntityRagdoll> implements IRenderFactory<T> {

    private Constructor<Render<T>> constructor;

    public RenderFac(Class<Render<T>> renderClass){
        try {
            this.constructor = renderClass.getConstructor(RenderManager.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Render<T> createRenderFor(RenderManager manager) {
        try {
            this.constructor.newInstance(manager);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        manager.entityRenderMap.put(EntityRagdoll.class, new RenderRagdoll(manager));
        return new RenderRagdoll(manager);
    }
}
