package com.sekwah.sekcphysics.ragdoll.layers;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;

public class FakeLayerBipedArmor extends LayerBipedArmor {
    public FakeLayerBipedArmor(RenderLivingBase<?> rendererIn) {
        super(rendererIn);
    }

    @Override
    protected void initArmor()
    {
        //this.modelLeggings = new ModelBiped(0.5F);
        //this.modelArmor = new ModelBiped(1.0F);
    }

}
