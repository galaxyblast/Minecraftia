package com.galaxyblast.minecraftia.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class WorldTypeMinecraftia extends WorldType
{

	public WorldTypeMinecraftia(String name)
	{
		super(name);
		this.adjustBiomeSettings();
	}

	@Override
	public float getCloudHeight()
    {
        return 192.0F;
    }
	
	@Override
	public IChunkProvider getChunkGenerator(World world, String generatorOptions)
    {
		return new ChunkProviderMinecraftia(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
    }
	
	@Override
	public WorldChunkManager getChunkManager(World world)
    {
		return new MinecraftiaChunkManager(world);
    }
	
	//Look into this
	@Override
	public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer)
    {
        GenLayer ret = new GenLayerBiome(200L, parentLayer, this);
        ret = GenLayerZoom.magnify(1000L, ret, 4);
        ret = new GenLayerBiomeEdge(1000L, ret);
        return ret;
    }
	
	private void adjustBiomeSettings()
	{
		BiomeGenBase.extremeHills.rootHeight = 1.2F;
		BiomeGenBase.extremeHills.heightVariation = 0.8F;
		BiomeGenBase.extremeHillsPlus.rootHeight = 0.8F;
		BiomeGenBase.extremeHillsPlus.heightVariation = 0.9F;
		BiomeGenBase.swampland.rootHeight = -0.25F;
		BiomeGenBase.swampland.heightVariation = -0.08F;
		BiomeGenBase.forest.rootHeight += 0.2F;
		BiomeGenBase.forest.heightVariation += 0.3F;
		BiomeGenBase.roofedForest.rootHeight += 0.2F;
	}
}
