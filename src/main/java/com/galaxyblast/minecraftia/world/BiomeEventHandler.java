package com.galaxyblast.minecraftia.world;

import com.galaxyblast.minecraftia.world.gen.ClayGen;
import com.galaxyblast.minecraftia.world.gen.SandGen;
import com.galaxyblast.minecraftia.world.gen.biome.MinecraftiaBiomeDecorator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.terraingen.BiomeEvent;
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
		/*if(e.biome == BiomeGenBase.plains || e.biome == BiomeGenBase.forest || e.biome == BiomeGenBase.forestHills || e.biome == BiomeGenBase.birchForest || e.biome == BiomeGenBase.birchForestHills
				|| e.biome == BiomeGenBase.taiga || e.biome == BiomeGenBase.taigaHills || e.biome == BiomeGenBase.roofedForest)
		{
			e.originalBiomeDecorator.grassPerChunk = 16;
			e.originalBiomeDecorator.flowersPerChunk = 3;
		}
		e.originalBiomeDecorator.clayPerChunk = 1;
		e.originalBiomeDecorator.sandPerChunk = 24;
		e.originalBiomeDecorator.sandPerChunk2 = 24;
		e.originalBiomeDecorator.gravelAsSandGen = new SandGen();
		e.originalBiomeDecorator.clayGen = new ClayGen();
		e.originalBiomeDecorator.sandGen = new SandGen();*/
		e.newBiomeDecorator = new MinecraftiaBiomeDecorator();
		
		e.biome.theBiomeDecorator = e.newBiomeDecorator;
		
		if(e.biome == BiomeGenBase.forest)
			((MinecraftiaBiomeDecorator)e.biome.theBiomeDecorator).genForest = true;
	}
}
