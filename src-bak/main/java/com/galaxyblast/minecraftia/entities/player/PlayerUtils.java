package com.galaxyblast.minecraftia.entities.player;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PlayerUtils
{
	public static int calculateWeight(EntityPlayer player)
	{
		int total = 0;
		ItemStack[] inv;
		inv = player.inventory.mainInventory;
		
		for(int i = 0; i < 36; i++)
		{
			if(inv[i] != null && Block.getBlockFromItem(inv[i].getItem()) != null)
				total += getItemWeight(Block.getBlockFromItem(inv[i].getItem()), inv[i].stackSize);
		}
		
		return total;
	}
	
	private static int getItemWeight(Block block, int size) //TODO: Add item ids to this
	{
		float truWeight = 0.0F;
		int id = Block.getIdFromBlock(block);
		
		switch(id)
		{
			case 0:
				truWeight = 0.0F;
				break;
			case 2:
			case 3:
			case 12:
			case 19:
			case 69:
			case 83:
			case 110:
			case 121:
			case 131:
			case 132:
				truWeight = 0.2F;
				break;
			case 5:
			case 20:
			case 53:
			case 87:
			case 95:
				truWeight = 0.25F;
				break;
			case 6:
			case 31:
			case 32:
			case 37:
			case 38:
			case 39:
			case 40:
			case 106:
			case 111:
			case 115:
			case 127:
			case 153:
			case 171:
			case 175:
				truWeight = 0.02F;
				break;
			case 7:
			case 49:
			case 82:
			case 108:
			case 116:
			case 138:
			case 174:
				truWeight = 4.0F;
				break;
			case 13:
			case 88:
				truWeight = 0.22F;
				break;
			case 14:
				truWeight = 1.5F;
				break;
			case 15:
			case 21:
				truWeight = 1.1F;
				break;
			case 18:
			case 30:
			case 160:
			case 161:
				truWeight = 0.05F;
				break;
			case 22:
			case 42:
			case 54:
			case 146:
				truWeight = 1.6F;
				break;
			case 23:
			case 79:
				truWeight = 2.5F;
				break;
			case 24:
			case 93:
			case 94:
				truWeight = 0.8F;
				break;
			case 35:
			case 63:
			case 65:
			case 92:
			case 102:
			case 140:
				truWeight = 0.4F;
				break;
			case 41:
				truWeight = 4.5F;
				break;
			case 44:
			case 81:
				truWeight = 0.5F;
				break;
			case 45:
				truWeight = 2.0F;
				break;
			case 50:
			case 76:
			case 77:
			case 143:
				truWeight = 0.1F;
				break;
			case 61:
			case 145:
				truWeight = 6.0F;
				break;
			case 96:
				truWeight = 0.6F;
				break;
			case 122:
				truWeight = 0.7F;
				break;
			case 126:
				truWeight = 0.12F;
				break;
			default:
				truWeight = 1.0F;
				break;
		}
		
		truWeight *= (float)size;
		
		return Math.round(truWeight);
	}
	
	public static int getNextLevel(int level)
	{
		if(level < 3150)
			return (int)(500.0D * Math.pow(((double)level / 7.0D), 2.5D)) + 50;
		else
			return -1;
	}
	
	public static String getTitle(int level)
	{
		String lvl = "Newb";
		
		if(level > 5 && level < 10)
			lvl = "Beginner";
		else if(level >= 10 && level < 20)
			lvl = "Novice";
		else if(level >= 20 && level < 40)
			lvl = "Apprentice";
		else if(level >= 40 && level < 65)
			lvl = "Amatuer";
		else if(level >= 65 && level < 100)
			lvl = "Adept";
		else if(level >= 100 && level < 150)
			lvl = "§eEpic";
		else if(level >= 150 && level < 250)
			lvl = "§6Elite";
		else if(level >= 250 && level < 500)
			lvl = "§aMaster";
		else if(level >= 500 && level < 1000)
			lvl = "§bChampion";
		else if(level >= 1000 && level < 2000)
			lvl = "§cMythic";
		else if(level >= 2000 && level < 3000)
			lvl = "§dLegendary";
		else if(level >= 3000)
			lvl = "§d§lLegendary+";
		
		return lvl;
	}
}
