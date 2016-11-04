package com.galaxyblast.minecraftia.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class MinecraftiaBlocks
{
	public static final BlockThinLog thinLog = new BlockThinLog("thinLog", Material.wood);
	
	public static void init()
	{
		GameRegistry.registerBlock(thinLog, ItemBlockThinLog.class, "thinLog");
	}
}
