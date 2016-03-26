package com.sekwah.sekcphysics.client;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.BaseRagdoll;
import com.sekwah.sekcphysics.ragdoll.vanilla.ZombieRagdoll;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by sekwah on 8/1/2015.
 */
public class EventHook {

    @SubscribeEvent
    public void onJoinWorld(LivingDeathEvent event) {
        SekCPhysics.logger.info("Entity Death");
        // TODO check entities for if they are in a list of registered mobs for ragdolls,
        //  and also check if the died is when the body is removed after death animation or if its
        //  as soon as it hits 0
        //SekCPhysics.logger.info("Entity Died.");
        // TODO Either add recalculation for the children or the option for a different model.
        if(FMLCommonHandler.instance().getEffectiveSide().isClient() && !event.getEntityLiving().isChild()){
            //SekCPhysics.logger.info("Entity Died.");

            // add checks for the ragolls and everything.

            EntityLivingBase deadEntity = event.getEntityLiving();

            BaseRagdoll ragdoll = SekCPhysics.ragdolls.createRagdoll(deadEntity);
            if(ragdoll != null) {

                EntityRagdoll entityRagdoll = new EntityRagdoll(deadEntity.worldObj);

                entityRagdoll.ragdoll = ragdoll;

                entityRagdoll.ragdoll.setStanceToEntity(deadEntity);

                entityRagdoll.setSpawnPosition(deadEntity.posX, deadEntity.posY, deadEntity.posZ);

                deadEntity.worldObj.spawnEntityInWorld(entityRagdoll);

                entityRagdoll.ragdoll.rotateRagdoll(deadEntity.rotationYaw);

                entityRagdoll.ragdoll.skeleton.verifyPoints(entityRagdoll);

                entityRagdoll.ragdoll.skeleton.setVelocity(deadEntity.posX - deadEntity.lastTickPosX, deadEntity.posY - deadEntity.lastTickPosY, deadEntity.posZ - deadEntity.lastTickPosZ);

                // Doesn't seem possible with client side only through just this event.
                /*if(event.source.getEntity() != null){
                    Entity attackingEntity = event.source.getSourceOfDamage();
                    SekCPhysics.logger.info(attackingEntity);
                    if(attackingEntity instanceof EntityPlayer){
                        EntityPlayer attackingPlayer = (EntityPlayer) attackingEntity;
                        if(attackingPlayer.getCurrentEquippedItem() != null){
                            // Try to add some other way such as finding the damage event and storing the knockback speed wanted.
                            ItemStack playerItem = attackingPlayer.getCurrentEquippedItem();
                            int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, playerItem);
                            SekCPhysics.logger.info("Test:" + knockback);
                        }
                    }
                }*/

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

    // TODO still being readded atm for forge, looks like it should be done but they need to recode for 1.9 changes.
    @SubscribeEvent
    public void playerInteract(PlayerInteractEvent event){
        SekCPhysics.logger.info("Player Interact");
        if(FMLCommonHandler.instance().getEffectiveSide().isClient() && event.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_AIR){
            if(event.getEntityPlayer().capabilities.isCreativeMode && event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND) != null && event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.nether_star){
                EntityRagdoll entityRagdoll = new EntityRagdoll(event.getEntityPlayer().worldObj);

                BaseRagdoll ragdoll = new ZombieRagdoll();

                //BaseRagdoll ragdoll = new WreckingBallRagdoll();

                entityRagdoll.ragdoll = ragdoll;

                Vec3d lookVec = event.getEntityPlayer().getLookVec();

                entityRagdoll.setSpawnPosition(event.getEntityPlayer().posX + lookVec.xCoord, event.getEntityPlayer().posY + lookVec.yCoord - 0.5f, event.getEntityPlayer().posZ + lookVec.zCoord);

                event.getEntityPlayer().worldObj.spawnEntityInWorld(entityRagdoll);

                entityRagdoll.ragdoll.skeleton.verifyPoints(entityRagdoll);

                entityRagdoll.ragdoll.skeleton.setVelocity(lookVec.xCoord, lookVec.yCoord, lookVec.zCoord);
            }
        }
    }


}
