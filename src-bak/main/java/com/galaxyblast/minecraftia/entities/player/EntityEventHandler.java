package com.galaxyblast.minecraftia.entities.player;

import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler
{
	private Random rng = new Random();
	
	@SubscribeEvent
	public void entitySpawn(EntityJoinWorldEvent e)
	{
		int lvl;
		
		if(e.entity instanceof EntityPig)
		{
			EntityPig mob = (EntityPig)e.entity;
			lvl = this.rng.nextInt(10) + 1;
			mob.setCustomNameTag(MobUtils.getTitle(lvl) + " - Lv" + Integer.toString(lvl));
			mob.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D + (double)lvl * 2.5D);
			mob.setHealth(10.0F + (float)lvl * 2.5F);
		}
		else if(e.entity instanceof EntityCow)
		{
			EntityCow mob = (EntityCow)e.entity;
			lvl = this.rng.nextInt(25) + 1;
			mob.setCustomNameTag(MobUtils.getTitle(lvl) + " - Lv" + Integer.toString(lvl));
			mob.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D + (double)lvl * 3.0D);
			mob.setHealth(10.0F + (float)lvl * 3.0F);
		}
		else if(e.entity instanceof EntitySheep)
		{
			EntitySheep mob = (EntitySheep)e.entity;
			lvl = this.rng.nextInt(15) + 1;
			mob.setCustomNameTag(MobUtils.getTitle(lvl) + " - Lv" + Integer.toString(lvl));
			mob.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D + (double)lvl * 2.0D);
			mob.setHealth(8.0F + (float)lvl * 2.0F);
		}
		else if(e.entity instanceof EntityChicken)
		{
			EntityChicken mob = (EntityChicken)e.entity;
			lvl = this.rng.nextInt(5) + 1;
			mob.setCustomNameTag(MobUtils.getTitle(lvl) + " - Lv" + Integer.toString(lvl));
			mob.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D + (double)lvl * 1.5D);
			mob.setHealth(4.0F + (float)lvl * 1.5F);
		}
		else if(e.entity instanceof EntityMob)
		{
			EntityMob mob = (EntityMob)e.entity;
			int highestLvl = 1;
			double dmg;
			
			World world = mob.worldObj;
			List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(mob.posX - 64.0D, mob.posY - 64.0D, mob.posZ - 64.0D, mob.posX + 64.0D, mob.posY + 64.0D, mob.posZ + 64.0D));
			
			for(EntityPlayer p : players)
			{
				int[] stats;
				PlayerData playerData = PlayerData.forWorld(null);
				NBTTagCompound nbt = playerData.getData();
				stats = nbt.getIntArray("player_" + p.getDisplayName());
				if(stats[0] > highestLvl)
					highestLvl = stats[0];
			}
			
			lvl = this.rng.nextInt(highestLvl) + (highestLvl / 2) + 1;
			mob.setCustomNameTag(MobUtils.getTitle(lvl) + " - Lv" + Integer.toString(lvl));
			
			double hp = 20.0D + (double)lvl * 6.0D;
			
			if(mob instanceof EntitySkeleton)
			{
				hp = 20.0D + (double)lvl * 2.5D;
				int power = lvl / 5;
				ItemStack item = new ItemStack(Items.bow);
				item.addEnchantment(Enchantment.power, power);
				
				if(mob.getHeldItem() != null && mob.getHeldItem().getItem() == Items.bow)
			        mob.setCurrentItemOrArmor(0, item);
			}
			else if(mob instanceof EntitySpider)
				hp = 10.0D + (double)lvl * 3.5D;
			else if(mob instanceof EntityEnderman)
				hp = 40.0D + (double)lvl * 4.0D;
			
			mob.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(hp);
			mob.setHealth((float)hp);
			
			dmg = ((double)lvl / 2) + 1.0D;
			
			mob.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(dmg);
		}
	}
	
	@SubscribeEvent
	public void entityDie(LivingDeathEvent e)
	{
		int lvl;
		String name;
		String[] parts;
		int xp = 0;
		
		if(!(e.entityLiving instanceof EntityPlayer))
		{
			EntityLiving mob = (EntityLiving)e.entityLiving;
			if(mob.hasCustomNameTag())
			{
				name = mob.getCustomNameTag();
				if(name.contains(" - Lv"))
				{
					parts = name.split("Lv");
					lvl = Integer.parseInt(parts[1]);
					
					if(mob instanceof EntityPig)
						xp = 2 * lvl;
					else if(mob instanceof EntityCow)
						xp = 2 * lvl;
					else if(mob instanceof EntitySheep)
						xp = 2 * lvl;
					else if(mob instanceof EntityChicken)
						xp = lvl;
					else if(mob instanceof EntityZombie)
						xp = lvl * 12;
					else if(mob instanceof EntitySkeleton)
						xp = lvl * 10;
					else if(mob instanceof EntitySpider)
						xp = lvl * 9;
					else if(mob instanceof EntityEnderman)
						xp = lvl * 16;
				}
			}
			
			if(e.source.getEntity() != null && e.source.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)e.source.getEntity();
				if(xp > 0)
					PlayerEventHandler.addXp(player, xp);
			}
		}
	}
}
