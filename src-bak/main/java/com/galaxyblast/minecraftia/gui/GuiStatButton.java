package com.galaxyblast.minecraftia.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiStatButton extends GuiButton
{
	private int x, y;
	private int id;
	
	public GuiStatButton(int t, int x, int y)
	{
		super(t, x, y, 20, 20, "");
		this.id = t;
	}
	
	@Override
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {}
}
