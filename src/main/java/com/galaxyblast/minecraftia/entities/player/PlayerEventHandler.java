package com.galaxyblast.minecraftia.entities.player;

import com.galaxyblast.minecraftia.Minecraftia;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class PlayerEventHandler
{
	@SubscribeEvent
	public void playerJoin(EntityJoinWorldEvent e)
	{
		if(e.world.getGameRules().getGameRuleBooleanValue("naturalRegeneration"))
			e.world.getGameRules().setOrCreateGameRule("naturalRegeneration", "false");
		
		EntityPlayer player;
		if(e.entity instanceof EntityPlayer)
		{
			player = (EntityPlayer) e.entity;
			PlayerData playerData = PlayerData.forWorld(null);
			NBTTagCompound playerNBT = playerData.getData();
			
			int[] stats = {1, 0, 100, 100, 1, 0, 100, 100, 0, 0, 1, 100}; //Level 0, xp 1, max hp 2, hp 3, attack 4, defense 5, weight 6, stamina 7, mana 8, points 9, regen 10, stamina left 11
			int[] vars = {3, 0, 0, 3}; //Regen cooldown, isDead, Stamina Regen, stamina drain
			
			if(!playerNBT.hasKey("player_" + player.getDisplayName()))
			{
				playerNBT.setIntArray("player_" + player.getDisplayName(), stats);
				playerNBT.setIntArray("player_" + player.getDisplayName() + "_vars", vars);
				playerData.markDirty();
				System.out.print("Player Data Created: player_" + player.getDisplayName() + "\n");
			}
		}
	}
	
	@SubscribeEvent
	public void playerTick(LivingEvent.LivingUpdateEvent e)
	{
		if(e.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)e.entityLiving;
			int[] stats;
			int[] vars;
			PlayerData playerData = PlayerData.forWorld(null);
			NBTTagCompound nbt = playerData.getData();
			if(nbt.hasKey("player_" + player.getDisplayName()) && !player.capabilities.isCreativeMode)
			{
				stats = nbt.getIntArray("player_" + player.getDisplayName());
				vars = nbt.getIntArray("player_" + player.getDisplayName() + "_vars");
				if(player.motionX != 0.0F || player.motionZ != 0.0F)
				{
					int weight = PlayerUtils.calculateWeight(player);
					
					float mult = (float)weight / (float)stats[6];
					if(mult > 1.0F)
						mult = 1.0F;
					
					if(mult > 0.8F)
					{
						player.motionX -= player.motionX * mult;
						player.motionZ -= player.motionZ * mult;
						if(player.isSprinting())
							player.setSprinting(false);
					}
					
					if(stats[11] <= 0)
					{
						player.motionX /= 2.0D;
						player.motionZ /= 2.0D;
					}
					
					if(player.isSprinting() && stats[11] > 0)
					{
						if(vars[3] <= 0)
						{
							stats[11]--;
							vars[3] = 3;
						}
						else
							vars[3]--;
					}
					
					if(stats[11] <= 0 && player.isSprinting())
						player.setSprinting(false);
				}
				
				if(player.isSprinting())
					vars[2] = 30;
				
				if(!player.isSprinting() && stats[11] < stats[7])
				{
					if(vars[2] <= 0)
						stats[11]++;
					else
						vars[2]--;
				}
				
				float health;
				if(stats[3] < stats[2] && player.hurtResistantTime <= 0 && player.getFoodStats().getFoodLevel() > 0)
				{
					if(vars[0] <= 0)
					{
						if(vars[1] == 1)
						{
							stats[3] = stats[2];
							vars[1] = 0;
						}
						else
							stats[3] += stats[10];
						
						vars[0] = 3;
					}
					else
						vars[0]--;
				}
				
				if(stats[3] > stats[2]) stats[3] = stats[2];
				
				//DEBUG ADDING XP
				if(player.isSneaking() && Minecraftia.debugMode && player.getHeldItem() != null)
				{
					if(player.getHeldItem().getItem() == Items.writable_book)
					{
						//addXp(player, 100);
						stats[1] = Integer.MAX_VALUE;
						//stats[0] = 3150;
					}
					
					else if(player.getHeldItem().getItem() == Items.emerald)
					{
						addXp(player, 1);
					}
				}
				
				health = ((float)stats[3] / (float)stats[2]) * 20.0F;
				if(health > 0.0F && vars[1] == 0)
					player.setHealth(health);
				
				nbt.setIntArray("player_" + player.getDisplayName(), stats);
				nbt.setIntArray("player_" + player.getDisplayName() + "_vars", vars);
				playerData.markDirty();
			}
		}
	}
	
	@SubscribeEvent
	public void playerRespawn(PlayerEvent.PlayerRespawnEvent e)
	{
		EntityPlayer player = e.player;
		int[] stats;
		PlayerData playerData = PlayerData.forWorld(null);
		NBTTagCompound nbt = playerData.getData();
		if(nbt.hasKey("player_" + player.getDisplayName()))
		{
			stats = nbt.getIntArray("player_" + player.getDisplayName());
			stats[3] = stats[2];
			nbt.setIntArray("player_" + player.getDisplayName(), stats);
			playerData.markDirty();
		}
	}
	
	@SubscribeEvent
	public void damagePlayer(LivingHurtEvent e)
	{
		if(e.entityLiving instanceof EntityPlayer)
		{
			//e.ammount *= 5.0F;
			EntityPlayer player = (EntityPlayer)e.entityLiving;
			int[] stats;
			int[] vars;
			PlayerData playerData = PlayerData.forWorld(null);
			NBTTagCompound nbt = playerData.getData();
			float actualHealth;
			
			if(nbt.hasKey("player_" + player.getDisplayName()))
			{
				stats = nbt.getIntArray("player_" + player.getDisplayName());
				vars = nbt.getIntArray("player_" + player.getDisplayName() + "_vars");
				
				e.ammount -= (float)stats[5] / 2.5F;
				
				stats[3] -= Math.round(e.ammount);
				if(stats[3] <= 0)
				{
					vars[1] = 1;
				}
				nbt.setIntArray("player_" + player.getDisplayName(), stats);
				actualHealth = ((float)stats[3] / (float)stats[2]) * 20.0F;
				
				if(actualHealth < 0.5F && actualHealth > 0.0F)
					actualHealth = 1.0F;
				
				player.setHealth(actualHealth);
			}
			e.ammount = 0.0F;
		}
	}
	
	@SubscribeEvent
	public void breakBlockXp(BlockEvent.BreakEvent e)
	{
		EntityPlayer player = e.getPlayer();
		int[] stats;
		PlayerData playerData = PlayerData.forWorld(null);
		NBTTagCompound nbt = playerData.getData();
		
		if(nbt.hasKey("player_" + player.getDisplayName()) && (e.block.getBlockHardness(e.world, e.x, e.y, e.z) > 0.0F || Minecraftia.debugMode))
		{
			stats = nbt.getIntArray("player_" + player.getDisplayName());
			addXp(player, e.getExpToDrop() + 1);
		}
	}
	
	@SubscribeEvent
	public void playerAttack(AttackEntityEvent e)
	{
		
			EntityPlayer player = e.entityPlayer;
			int[] stats;
			PlayerData playerData = PlayerData.forWorld(null);
			NBTTagCompound nbt = playerData.getData();
			stats = nbt.getIntArray("player_" + player.getDisplayName());
			if(e.target instanceof EntityLiving)
			{
				EntityLiving entity = (EntityLiving)e.target;
				float dmg = 0.0F;
				
				if(player.getHeldItem() != null)
				{
					Item item = player.getHeldItem().getItem();
					if(item instanceof ItemTool)
						dmg = ((ItemTool) item).func_150913_i().getDamageVsEntity();
					else if(item instanceof ItemSword)
							dmg = ((ItemSword) item).func_150931_i() + 4.0F;
				}
				
				dmg += (float)stats[4] - 1.0F;
				
				entity.attackEntityFrom(DamageSource.causePlayerDamage(player), dmg);
			}
	}
	
	public static void addXp(EntityPlayer player, int amt)
	{
		PlayerData playerData = PlayerData.forWorld(null);
		NBTTagCompound nbt = playerData.getData();
		int[] stats = nbt.getIntArray("player_" + player.getDisplayName());
		
		long tmp = (long)stats[1] + (long)amt;
		if(tmp >= (long)Integer.MAX_VALUE)
			stats[1] = Integer.MAX_VALUE;
		else
			stats[1] += amt;
		
		if(stats[1] >= PlayerUtils.getNextLevel(stats[0]) && stats[0] <= 3150)
		{
			stats[0]++;
			stats[9]++;
		}
		
		nbt.setIntArray("player_" + player.getDisplayName(), stats);
		playerData.markDirty();
	}
}
