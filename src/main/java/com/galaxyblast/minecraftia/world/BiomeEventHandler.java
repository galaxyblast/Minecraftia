package com.galaxyblast.minecraftia.world;

import com.galaxyblast.minecraftia.blocks.MinecraftiaBlocks;
import com.galaxyblast.minecraftia.world.gen.ClayGen;
import com.galaxyblast.minecraftia.world.gen.SandGen;
import com.galaxyblast.minecraftia.world.gen.biome.MinecraftiaBiomeDecorator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

public class BiomeEventHandler
{
	@SubscribeEvent
	public void BiomeGen(WorldTypeEvent.BiomeSize e)
	{
		e.newSize = 5;
	}
	
	@SubscribeEvent
	public void transformBiomes(BiomeEvent.CreateDecorator e)
	{
		e.newBiomeDecorator = new MinecraftiaBiomeDecorator();
		
		e.biome.theBiomeDecorator = e.newBiomeDecorator;
		
		if(e.biome == BiomeGenBase.forest || e.biome == BiomeGenBase.forestHills || e.biome == BiomeGenBase.birchForest
				|| e.biome == BiomeGenBase.birchForestHills || e.biome == BiomeGenBase.taiga
				|| e.biome == BiomeGenBase.taigaHills || e.biome == BiomeGenBase.roofedForest)
			((MinecraftiaBiomeDecorator)e.biome.theBiomeDecorator).genForest = true;
	}
}
