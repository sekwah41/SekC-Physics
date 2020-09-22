package com.sekwah.sekcphysics.client;

import com.sekwah.sekcphysics.SekCPhysics;
import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class EventHook {

    @SubscribeEvent
    public static void playerDeath(LivingEvent.LivingUpdateEvent event) {
        Entity entity = event.getEntity();
        if(entity instanceof EntityPlayer) {
            if(((EntityLivingBase) entity).deathTime > 0 && RagdollConfig.maxRagdolls != 0) {
                entityDied((EntityLivingBase) entity);
            }
        }
    }

    /**
     * Because for some stupid reason this doesnt run on players (except server side despite the rest all work fine)
     * @param event
     */
    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event) {

        if(FMLCommonHandler.instance().getEffectiveSide().isClient() && RagdollConfig.maxRagdolls != 0) {
            entityDied(event.getEntityLiving());
        }
    }

    public static void entityDied(LivingEntity deadEntity) {

        if(deadEntity.isChild()) {
            return;
        }

        BaseRagdoll ragdoll = SekCPhysics.RAGDOLLS.createRagdoll(deadEntity);
        if(ragdoll != null) {

            EntityRagdoll entityRagdoll = new EntityRagdoll(deadEntity.world, ragdoll);

            entityRagdoll.ragdoll.setStanceToEntity(deadEntity);

            entityRagdoll.setSpawnPosition(deadEntity.posX, deadEntity.posY, deadEntity.posZ);

            SekCPhysics.RAGDOLLS.spawnRagdoll(entityRagdoll);

            entityRagdoll.ragdoll.rotateRagdoll(deadEntity.rotationYaw);

            entityRagdoll.ragdoll.skeleton.verifyPoints(entityRagdoll);

            entityRagdoll.ragdoll.update(entityRagdoll);

            entityRagdoll.ragdoll.skeleton.updateLastLocations(entityRagdoll);

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

    /*@SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event) {
        RagdollRenderer.hasRendered = false;
    }*/

    @SubscribeEvent
    public static void worldTicks(TickEvent.ClientTickEvent event) {
        if(event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.END) {
            SekCPhysics.RAGDOLLS.updateRagdolls();
        }
    }

    /*@SubscribeEvent
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
    }*/


}
