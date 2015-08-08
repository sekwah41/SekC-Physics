package com.sekwah.sekcphysics.ragdoll;

import com.sekwah.sekcphysics.ragdoll.parts.Skeleton;
import net.minecraft.client.model.ModelBase;

/**
 * Created by sekawh on 8/4/2015.
 */
public class BaseRagdoll {

    // Current skeleton position and shape
    public Skeleton skeleton;

    // Will be used once the physics is sorted, then can render all the stuff to the correct positions
    public ModelBase entityModel;

    // offset from the bottom of the desired entity to the main point of the ragdoll
    public double centerHeightOffset;

    public BaseRagdoll(){
    }

    public BaseRagdoll(float centerHeightOffset){
        this.centerHeightOffset = centerHeightOffset;
    }


    private void updateRagdoll() {

    }

    public void rotateRagdoll(float rotYaw) {
        // add some matrix code to calaulate positions for all of the new points to be alligned with the rotation of
        // the entity
    }
}
