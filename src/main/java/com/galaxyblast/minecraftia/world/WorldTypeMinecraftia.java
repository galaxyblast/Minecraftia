package com.galaxyblast.minecraftia.world;

import com.galaxyblast.minecraftia.world.gen.biome.BiomeUtils;

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
		this.registerVanillaBiomes();
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
	
	private void registerVanillaBiomes()
	{
		BiomeUtils.registerBiomeValues(BiomeGenBase.beach, 0);
		BiomeUtils.registerBiomeValues(BiomeGenBase.birchForest, 64);
		BiomeUtils.registerBiomeValues(BiomeGenBase.birchForestHills, 64);
		BiomeUtils.registerBiomeValues(BiomeGenBase.coldBeach, 0);
		BiomeUtils.registerBiomeValues(BiomeGenBase.coldTaiga, 48);
		BiomeUtils.registerBiomeValues(BiomeGenBase.coldTaigaHills, 48);
		BiomeUtils.registerBiomeValues(BiomeGenBase.deepOcean, 80);
		BiomeUtils.registerBiomeValues(BiomeGenBase.desert, 48);
		BiomeUtils.registerBiomeValues(BiomeGenBase.desertHills, 80);
		BiomeUtils.registerBiomeValues(BiomeGenBase.extremeHills, 80);
		BiomeUtils.registerBiomeValues(BiomeGenBase.extremeHillsEdge, 32);
		BiomeUtils.registerBiomeValues(BiomeGenBase.extremeHillsPlus, 112);
		BiomeUtils.registerBiomeValues(BiomeGenBase.forest, 64);
		BiomeUtils.registerBiomeValues(BiomeGenBase.forestHills, 64);
		BiomeUtils.registerBiomeValues(BiomeGenBase.frozenOcean, 64);
		BiomeUtils.registerBiomeValues(BiomeGenBase.frozenRiver, 16);
		BiomeUtils.registerBiomeValues(BiomeGenBase.hell, 80);
		BiomeUtils.registerBiomeValues(BiomeGenBase.iceMountains, 80);
		BiomeUtils.registerBiomeValues(BiomeGenBase.icePlains, 32);
		BiomeUtils.registerBiomeValues(BiomeGenBase.jungle, 40);
		BiomeUtils.registerBiomeValues(BiomeGenBase.jungleEdge, 40);
		BiomeUtils.registerBiomeValues(BiomeGenBase.jungleHills, 72);
		BiomeUtils.registerBiomeValues(BiomeGenBase.megaTaiga, 64);
		BiomeUtils.registerBiomeValues(BiomeGenBase.megaTaigaHills, 80);
		BiomeUtils.registerBiomeValues(BiomeGenBase.mesa, 30);
		BiomeUtils.registerBiomeValues(BiomeGenBase.mesaPlateau, 38);
		BiomeUtils.registerBiomeValues(BiomeGenBase.mesaPlateau_F, 12);
		BiomeUtils.registerBiomeValues(BiomeGenBase.mushroomIsland, 72);
		BiomeUtils.registerBiomeValues(BiomeGenBase.mushroomIslandShore, 0);
		BiomeUtils.registerBiomeValues(BiomeGenBase.ocean, 64);
		BiomeUtils.registerBiomeValues(BiomeGenBase.plains, 24);
		BiomeUtils.registerBiomeValues(BiomeGenBase.river, 16);
		BiomeUtils.registerBiomeValues(BiomeGenBase.roofedForest, 64);
		BiomeUtils.registerBiomeValues(BiomeGenBase.savanna, 40);
		BiomeUtils.registerBiomeValues(BiomeGenBase.savannaPlateau, 32);
		BiomeUtils.registerBiomeValues(BiomeGenBase.stoneBeach, 12);
		BiomeUtils.registerBiomeValues(BiomeGenBase.swampland, 16);
		BiomeUtils.registerBiomeValues(BiomeGenBase.taiga, 48);
		BiomeUtils.registerBiomeValues(BiomeGenBase.taigaHills, 80);
	}
}
