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

public class WorldTypeMinecraftia extends WorldType
{

	public WorldTypeMinecraftia(String name)
	{
		super(name);
		BiomeGenBase.extremeHills.rootHeight = 0.8F;
		BiomeGenBase.extremeHills.heightVariation = 0.9F;
		BiomeGenBase.extremeHillsPlus.rootHeight = 0.8F;
		BiomeGenBase.extremeHillsPlus.heightVariation = 0.9F;
		BiomeGenBase.swampland.rootHeight = 0.1F;
		BiomeGenBase.swampland.heightVariation = 0.1F;
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
	
	//Look into this
	@Override
	public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer)
    {
        GenLayer ret = new GenLayerBiome(200L, parentLayer, this);
        ret = GenLayerZoom.magnify(1000L, ret, 2);
        ret = new GenLayerBiomeEdge(1000L, ret);
        return ret;
    }
}
