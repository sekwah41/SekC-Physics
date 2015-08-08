package com.sekwah.sekcphysics.client;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.BaseRagdoll;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

/**
 * Created by sekwah on 8/1/2015.
 */
public class EventHook {

    @SubscribeEvent
    public void onJoinWorld(LivingDeathEvent event) {
        // TODO check entities for if they are in a list of registered mobs for ragdolls,
        //  and also check if the died is when the body is removed after death animation or if its
        //  as soon as it hits 0
        if(FMLCommonHandler.instance().getEffectiveSide().isClient()){
            SekCPhysics.LOGGER.info("Entity Died.");

            // add checks for the ragolls and everything.

            EntityLivingBase deadEntity = event.entityLiving;

            BaseRagdoll ragdoll = SekCPhysics.ragdolls.createRagdoll(deadEntity);
            if(ragdoll != null) {

                EntityRagdoll entityRagdoll = new EntityRagdoll(deadEntity.worldObj);

                entityRagdoll.ragdoll = ragdoll;

                entityRagdoll.setSpawnPosition(deadEntity.posX, deadEntity.posY, deadEntity.posZ);

                deadEntity.worldObj.spawnEntityInWorld(entityRagdoll);

                deadEntity.setDead();
            }

            // Look at the events and see when the world update and other ticks are, if you cant do it as a client entity
            //  you may need to trigger it each time.

            // This will only fire once, even in single player. Gather data such as rotation and spawn the ragdoll.
            //  also if its in the list of ragdolls remove the death animation of rotating and being red.
            //  add a class type for ragdolls to set properties, such as for horses and players get the skin.
            //  Also for ageable entities add the isbaby check and create baby ragdolls.

            // Challange, create base zombie and try ender dragon for hard challange then make a video and see how many
            //  people say its fake, dont give a download till most vanilla mobs are done and stuff is set up.
            //  possibly just do a test of concept video with the zombie and player to see how people act.
            //  ender dragon may be very hard... not sure if its rendered all as one or how the model is.
        }
    }


}
