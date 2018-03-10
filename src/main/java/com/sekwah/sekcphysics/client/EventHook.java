package com.sekwah.sekcphysics.client;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.Ragdolls;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import com.sekwah.sekcphysics.ragdoll.ragdolls.vanilla.BipedRagdoll;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Client side event hook
 *
 * Created by sekwah on 8/1/2015.
 */
public class EventHook {

    @SubscribeEvent
    public void deathEvent(LivingDeathEvent event) {

        if(FMLCommonHandler.instance().getEffectiveSide().isClient() && !event.getEntityLiving().isChild()) {
            //SekCPhysics.logger.info("Entity Died.");

            // add checks for the ragolls and everything.

            EntityLivingBase deadEntity = event.getEntityLiving();

            BaseRagdoll ragdoll = SekCPhysics.ragdolls.createRagdoll(deadEntity);
            if(ragdoll != null) {

                EntityRagdoll entityRagdoll = new EntityRagdoll(deadEntity.world, ragdoll);

                entityRagdoll.ragdoll.setStanceToEntity(deadEntity);

                entityRagdoll.setSpawnPosition(deadEntity.posX, deadEntity.posY, deadEntity.posZ);

                deadEntity.world.spawnEntity(entityRagdoll);

                entityRagdoll.ragdoll.rotateRagdoll(deadEntity.rotationYaw);

                entityRagdoll.ragdoll.skeleton.verifyPoints(entityRagdoll);

                entityRagdoll.ragdoll.update(entityRagdoll);

                for(EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
                    entityRagdoll.setItemStackToSlot(slot, deadEntity.getItemStackFromSlot(slot));
                }

                deadEntity.setDead();

                //entityRagdoll.ragdoll.skeleton.setVelocity(deadEntity.posX - deadEntity.lastTickPosX, deadEntity.posY - deadEntity.lastTickPosY, deadEntity.posZ - deadEntity.lastTickPosZ);

                // Doesn't seem possible with client side only through just this event.
                /*if(event.source.getEntity() != null) {
                    Entity attackingEntity = event.source.getSourceOfDamage();
                    SekCPhysics.logger.info(attackingEntity);
                    if(attackingEntity instanceof EntityPlayer) {
                        EntityPlayer attackingPlayer = (EntityPlayer) attackingEntity;
                        if(attackingPlayer.getCurrentEquippedItem() != null) {
                            // Try to add some other way such as finding the damage event and storing the knockback speed wanted.
                            ItemStack playerItem = attackingPlayer.getCurrentEquippedItem();
                            int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, playerItem);
                            SekCPhysics.logger.info("Test:" + knockback);
                        }
                    }
                }*/
            }
        }
    }

    @SubscribeEvent
    public void playerInteract(PlayerInteractEvent.RightClickItem event) {
        //SekCPhysics.logger.info("Player Interact");

        if(event.getWorld().isRemote) {
            if(event.getEntityPlayer().capabilities.isCreativeMode && event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND) != null && event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.NETHER_STAR) {

                BaseRagdoll ragdoll = new BipedRagdoll();

                EntityRagdoll entityRagdoll = new EntityRagdoll(event.getEntityPlayer().world, ragdoll);

                //BaseRagdoll ragdoll = new WreckingBallRagdoll();

                //BaseRagdoll ragdoll = new ClothRagdoll();
                //BaseRagdoll ragdoll = new CurtainRagdoll();

                entityRagdoll.ragdoll = ragdoll;

                Vec3d lookVec = event.getEntityPlayer().getLookVec();

                entityRagdoll.setSpawnPosition(event.getEntityPlayer().posX + lookVec.x, event.getEntityPlayer().posY + lookVec.y - 0.5f, event.getEntityPlayer().posZ + lookVec.z);

                event.getEntityPlayer().world.spawnEntity(entityRagdoll);

                entityRagdoll.ragdoll.skeleton.verifyPoints(entityRagdoll);

                entityRagdoll.ragdoll.skeleton.setVelocity(lookVec.x, lookVec.y, lookVec.z);
            }

        }
    }


}
