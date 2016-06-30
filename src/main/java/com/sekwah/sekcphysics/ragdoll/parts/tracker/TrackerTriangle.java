package com.sekwah.sekcphysics.ragdoll.parts.tracker;

import net.minecraft.client.model.ModelRenderer;

import javax.sound.midi.Track;

/**
 * Created by on 30/06/2016.
 *
 * @author sekwah41
 */
public class TrackerTriangle extends Tracker {

    @Override
    public void render(ModelRenderer modelPart) {
        modelPart.render(0.0625f);
    }

    @Override
    public void calcRotation(){


    }

}
