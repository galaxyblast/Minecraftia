package com.galaxyblast.minecraftia.blocks;

import com.galaxyblast.minecraftia.fluids.BlockFluidWater;
import com.galaxyblast.minecraftia.fluids.FluidWater;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class MinecraftiaBlocks
{
	public static final Fluid fluidWater = new FluidWater("newWater");
	
	public static final BlockThinLog thinLog = new BlockThinLog("thinLog", Material.wood);
	public static BlockFluidWater water;
	
	public static void init()
	{
		GameRegistry.registerBlock(thinLog, ItemBlockThinLog.class, "thinLog");
		FluidRegistry.registerFluid(fluidWater);
		water = (BlockFluidWater) new BlockFluidWater(fluidWater).setBlockName("newWater");
		
		GameRegistry.registerBlock(water, "newWaterBlock");
	}
}
