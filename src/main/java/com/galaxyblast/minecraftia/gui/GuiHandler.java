package com.galaxyblast.minecraftia.gui;

import org.lwjgl.opengl.GL11;

import com.galaxyblast.minecraftia.Minecraftia;
import com.galaxyblast.minecraftia.entities.player.PlayerData;
import com.galaxyblast.minecraftia.entities.player.PlayerUtils;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiHandler
{
	@SubscribeEvent
	public void showStats(GuiScreenEvent.DrawScreenEvent.Post e)
	{
		if(e.gui instanceof GuiInventory)
		{
			GuiInventory gui = (GuiInventory)e.gui;
			PlayerData playerData = PlayerData.forWorld(null);
			NBTTagCompound nbt = playerData.getData();
			EntityPlayer player = e.gui.mc.thePlayer;
			int[] stats;
			int weight = PlayerUtils.calculateWeight(player);
			int offset = 0;
			
			if(!player.getActivePotionEffects().isEmpty())
			{
				offset = 60;
			}
			
			if(nbt.hasKey("player_" + player.getDisplayName()))
			{
				stats = nbt.getIntArray("player_" + player.getDisplayName());
				String lvl = "Lv" + stats[0];
				String xpNeeded = Integer.toString(PlayerUtils.getNextLevel(stats[0])) + "xp";
				
				gui.mc.fontRenderer.drawString("Rank: " + PlayerUtils.getTitle(stats[0]), (gui.width / 2) + 90 + offset, (gui.height / 2) - 105, 16777215, false);
				
				if(PlayerUtils.getNextLevel(stats[0]) == -1)
					xpNeeded = "Infinite";
				
				gui.mc.fontRenderer.drawString(Integer.toString(weight) + " / " + Integer.toString(stats[6]), (gui.width / 2), (gui.height / 2) - 10, 4210752, false);
				gui.mc.fontRenderer.drawString(Integer.toString(stats[1]) + "xp / " + xpNeeded, (gui.width / 2) - 88 + offset, (gui.height / 2) - 93, 16777215, false);
				gui.mc.fontRenderer.drawString(lvl, (gui.width / 2) + 85 - gui.mc.fontRenderer.getStringWidth(lvl), (gui.height / 2) - 79, 4210752, false);
				
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				gui.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/widgets.png"));
				for(int i = 0; i < 7; i++)
					gui.drawTexturedModalRect((gui.width / 2) + 90 + offset, (gui.height / 2) - 77 + (i * 22), 1, 1, 20, 20);
				
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				gui.mc.getTextureManager().bindTexture(new ResourceLocation(Minecraftia.MODID + ":textures/gui/icons.png"));
				for(int i = 0; i < 7; i++)
					gui.drawTexturedModalRect((gui.width / 2) + 92 + offset, (gui.height / 2) - 75 + (i * 22), 0 + (i * 16), 0, 16, 16);

				gui.mc.fontRenderer.drawString("Points remaining: " + Integer.toString(stats[9]), (gui.width / 2) + 90 + offset, (gui.height / 2) - 93, 16777215, false);
				gui.mc.fontRenderer.drawString(Integer.toString(stats[3]) + " / " + Integer.toString(stats[2]) + " HP", (gui.width / 2) + 115 + offset, (gui.height / 2) - 72, 16777215, false);
				gui.mc.fontRenderer.drawString(Integer.toString(stats[10]) + " Regen Speed", (gui.width / 2) + 115 + offset, (gui.height / 2) - 50, 16777215, false);
				gui.mc.fontRenderer.drawString(Integer.toString(stats[4]) + " Attack", (gui.width / 2) + 115 + offset, (gui.height / 2) - 28, 16777215, false);
				gui.mc.fontRenderer.drawString(Integer.toString(stats[5]) + " Defense", (gui.width / 2) + 115 + offset, (gui.height / 2) - 6, 16777215, false);
				gui.mc.fontRenderer.drawString(Integer.toString(stats[6]) + " Weight", (gui.width / 2) + 115 + offset, (gui.height / 2) + 12, 16777215, false);
				gui.mc.fontRenderer.drawString(Integer.toString(stats[11]) + " Stamina", (gui.width / 2) + 115 + offset, (gui.height / 2) + 34, 16777215, false);
				gui.mc.fontRenderer.drawString(Integer.toString(stats[8]) + " Mana", (gui.width / 2) + 115 + offset, (gui.height / 2) + 56, 16777215, false);
			}
		}
	}
	
	@SubscribeEvent
	public void addStatButtons(GuiScreenEvent.InitGuiEvent.Post e)
	{
		if(e.gui instanceof GuiInventory)
		{
			for(int i = 0; i < 7; i++)
				e.buttonList.add(new GuiStatButton(120 + i, (e.gui.width / 2) + 90, (e.gui.height / 2) - 78 + (i * 22)));
		}
	}
	
	@SubscribeEvent
	public void statButtonPressed(GuiScreenEvent.ActionPerformedEvent.Post e)
	{
		if(e.gui instanceof GuiInventory)
		{
			PlayerData playerData = PlayerData.forWorld(null);
			NBTTagCompound nbt = playerData.getData();
			EntityPlayer player = e.gui.mc.thePlayer;
			int[] stats;
			
			if(nbt.hasKey("player_" + player.getDisplayName()))
			{
				stats = nbt.getIntArray("player_" + player.getDisplayName());
				if(stats[9] > 0)
				{
					if(e.button.id == 120)
					{
						stats[9]--;
						stats[2] += 10;
						nbt.setIntArray("player_" + player.getDisplayName(), stats);
					}
					else if(e.button.id == 121)
					{
						stats[9]--;
						stats[10] += 1;
						nbt.setIntArray("player_" + player.getDisplayName(), stats);
					}
					else if(e.button.id == 122)
					{
						stats[9]--;
						stats[4] += 1;
						nbt.setIntArray("player_" + player.getDisplayName(), stats);
					}
					else if(e.button.id == 123)
					{
						stats[9]--;
						stats[5] += 1;
						nbt.setIntArray("player_" + player.getDisplayName(), stats);
					}
					else if(e.button.id == 124)
					{
						stats[9]--;
						stats[6] += 25;
						nbt.setIntArray("player_" + player.getDisplayName(), stats);
					}
					else if(e.button.id == 125)
					{
						stats[9]--;
						stats[7] += 25;
						nbt.setIntArray("player_" + player.getDisplayName(), stats);
					}
					else if(e.button.id == 126)
					{
						stats[9]--;
						stats[8] += 5;
						nbt.setIntArray("player_" + player.getDisplayName(), stats);
					}
				}
			}
		}
	}
}
