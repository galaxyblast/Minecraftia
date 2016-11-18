package com.galaxyblast.minecraftia.world;

import java.util.Random;

import com.galaxyblast.minecraftia.blocks.MinecraftiaBlocks;
import com.galaxyblast.minecraftia.world.gen.BiomeGen;
import com.galaxyblast.minecraftia.world.gen.biome.BiomeUtils;
import com.galaxyblast.minecraftia.world.noise.MinecraftiaNoiseGen;

import cpw.mods.fml.common.eventhandler.Event.Result;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerDeepOcean;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRareBiome;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

public class ChunkProviderMinecraftia extends ChunkProviderGenerate
{
	private Random rng;
    private NoiseGenMinecraftia noiseGen1;
    private NoiseGenMinecraftia noiseGen2;
    private NoiseGenMinecraftia noiseGen3;
    private NoiseGeneratorPerlin noiseGen4;
    public NoiseGenMinecraftia noiseGen5;
    public NoiseGenMinecraftia noiseGen6;
    private BiomeGenBase[] biomesForGeneration;
    double[] field_147427_d;
    double[] field_147428_e;
    double[] field_147425_f;
    double[] field_147426_g;
    private double[] stoneNoise = new double[256];
    private WorldType field_147435_p;
    private final double[] noiseMap;
    private double[][] newNoiseMap;
    private final float[] parabolicField;
    private final float[] parabolicField2;
    private World worldObj;
    private MapGenBase caveGenerator = new MinecraftiaGenCave();
    private MapGenBase ravineGenerator = new MapGenRavine();
    private final boolean mapFeaturesEnabled;
    private MapGenStronghold strongholdGenerator = new MapGenStronghold();
    private MapGenVillage villageGenerator = new MapGenVillage();
    private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
    private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
    
    private MinecraftiaNoiseGen newNoiseGen;
    
    private double xCoord, zCoord;
	
	public ChunkProviderMinecraftia(World world, long seed, boolean features)
	{
		super(world, seed, features);
        this.field_147435_p = world.getWorldInfo().getTerrainType();
        this.rng = new Random(seed);
		this.noiseGen1 = new NoiseGenMinecraftia(this.rng, 16);
		this.noiseGen2 = new NoiseGenMinecraftia(this.rng, 16);
		this.noiseGen3 = new NoiseGenMinecraftia(this.rng, 8);
        this.noiseGen4 = new NoiseGeneratorPerlin(this.rng, 4);
		this.noiseGen5 = new NoiseGenMinecraftia(this.rng, 10);
		this.noiseGen6 = new NoiseGenMinecraftia(this.rng, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rng, 8);
        this.noiseMap = new double[825];
        this.newNoiseMap = new double[16][16];
        this.parabolicField = new float[25];
        this.parabolicField2 = new float[25];
        this.worldObj = world;
        this.mapFeaturesEnabled = features;
        
        this.newNoiseGen = new MinecraftiaNoiseGen(this.rng);
        
        for (int j = -2; j <= 2; ++j)
        {
            for (int k = -2; k <= 2; ++k)
            {
                float f = 10.0F / MathHelper.sqrt_float((float)(j * j + k * k) + 10.0F);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }
        
        for (int j = 0; j < 25; ++j)
        {
                float f = 10.0F / MathHelper.sqrt_float((float)Math.pow(((double)j - 12.5D), 2) + 6.0F);
                this.parabolicField2[j] = f;
        }
        
        NoiseGenerator[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6, mobSpawnerNoise};
        noiseGens = TerrainGen.getModdedNoiseGenerators(world, this.rng, noiseGens);
        this.noiseGen1 = (NoiseGenMinecraftia)noiseGens[0];
        this.noiseGen2 = (NoiseGenMinecraftia)noiseGens[1];
        this.noiseGen3 = (NoiseGenMinecraftia)noiseGens[2];
        this.noiseGen4 = (NoiseGeneratorPerlin)noiseGens[3];
        this.noiseGen5 = (NoiseGenMinecraftia)noiseGens[4];
        this.noiseGen6 = (NoiseGenMinecraftia)noiseGens[5];
        this.mobSpawnerNoise = (NoiseGeneratorOctaves)noiseGens[6];
	}
	
	@Override
	public void replaceBlocksForBiome(int p_147422_1_, int p_147422_2_, Block[] p_147422_3_, byte[] p_147422_4_, BiomeGenBase[] p_147422_5_)
    {
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, p_147422_1_, p_147422_2_, p_147422_3_, p_147422_4_, p_147422_5_, this.worldObj);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return;

        double d0 = 0.03125D;
        this.stoneNoise = this.noiseGen4.func_151599_a(this.stoneNoise, (double)(p_147422_1_ * 16), (double)(p_147422_2_ * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int k = 0; k < 16; ++k)
        {
            for (int l = 0; l < 16; ++l)
            {
                BiomeGenBase biomegenbase = p_147422_5_[l + k * 16];
                biomegenbase.genTerrainBlocks(this.worldObj, this.rng, p_147422_3_, p_147422_4_, p_147422_1_ * 16 + k, p_147422_2_ * 16 + l, this.stoneNoise[l + k * 16]);
            }
        }
    }
	
	@Override
	public Chunk provideChunk(int x, int y)
    {
        this.rng.setSeed((long)x * 341873128712L + (long)y * 132897987541L);
        Block[] ablock = new Block[65536];
        byte[] abyte = new byte[65536];
        this.genTerrain(x, y, ablock);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, y * 16, 16, 16);
        this.replaceBlocksForBiome(x, y, ablock, abyte, this.biomesForGeneration);
        this.caveGenerator.func_151539_a(this, this.worldObj, x, y, ablock);
        this.ravineGenerator.func_151539_a(this, this.worldObj, x, y, ablock);

        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, x, y, ablock);
            this.villageGenerator.func_151539_a(this, this.worldObj, x, y, ablock);
            this.strongholdGenerator.func_151539_a(this, this.worldObj, x, y, ablock);
            this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, x, y, ablock);
        }

        Chunk chunk = new Chunk(this.worldObj, ablock, abyte, x, y);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
        {
            abyte1[k] = (byte)this.biomesForGeneration[k].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }
	
	public void genTerrain(int p_147424_1_, int p_147424_2_, Block[] p_147424_3_)
    {
        byte b0 = 63;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, p_147424_1_ * 4 - 2, p_147424_2_ * 4 - 2, 10, 10);
        this.createNoiseFields(p_147424_1_ * 4, 0, p_147424_2_ * 4);

        for (int k = 0; k < 4; ++k)
        {
            int l = k * 5;
            int i1 = (k + 1) * 5;

            for (int j1 = 0; j1 < 4; ++j1)
            {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;

                for (int k2 = 0; k2 < 32; ++k2)
                {
                    double d0 = 0.14D;
                    double d1 = this.noiseMap[k1 + k2];
                    double d2 = this.noiseMap[l1 + k2];
                    double d3 = this.noiseMap[i2 + k2];
                    double d4 = this.noiseMap[j2 + k2];
                    double d5 = (this.noiseMap[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.noiseMap[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.noiseMap[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.noiseMap[j2 + k2 + 1] - d4) * d0;

                    for (int l2 = 0; l2 < 8; ++l2)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i3 = 0; i3 < 4; ++i3)
                        {
                            int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
                            short short1 = 256;
                            j3 -= short1;
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int k3 = 0; k3 < 4; ++k3)
                            {
                                if ((d15 += d16) > 0.0D)
                                {
                                    p_147424_3_[j3 += short1] = Blocks.stone;
                                }
                                else if (k2 * 8 + l2 < b0)
                                {
                                    p_147424_3_[j3 += short1] = MinecraftiaBlocks.water;
                                }
                                else
                                {
                                    p_147424_3_[j3 += short1] = null;
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

	//Use world.getBiomeGen... in the noise octaves for biome roughness
	private void createNoiseFields(int noiseOff1, int noiseOff2, int noiseOff3)
    {
        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, noiseOff1, noiseOff3, 5, 5, 0.0D, 200.0D, 0.0D);
        this.field_147427_d = this.noiseGen3.generateNoiseOctaves(this.field_147427_d, noiseOff1, noiseOff2, noiseOff3, 5, 33, 5, 0.0D, 4.277575000000001D, 0.0D);
//        this.field_147428_e = this.noiseGen1.generateNoiseOctaves(this.field_147428_e, noiseOff1, noiseOff2, noiseOff3, 5, 33, 5, 0.0D, 684.412D, 0.0D);
//        this.field_147425_f = this.noiseGen2.generateNoiseOctaves(this.field_147425_f, noiseOff1, noiseOff2, noiseOff3, 5, 33, 5, 0.0D, 684.412D, 0.0D);
//        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, noiseOff1, noiseOff3, 5, 5, 200.0D, 200.0D, 0.5D);
//        this.field_147427_d = this.noiseGen3.generateNoiseOctaves(this.field_147427_d, noiseOff1, noiseOff2, noiseOff3, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
        this.field_147428_e = this.noiseGen1.generateNoiseOctaves(this.field_147428_e, noiseOff1, noiseOff2, noiseOff3, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.field_147425_f = this.noiseGen2.generateNoiseOctaves(this.field_147425_f, noiseOff1, noiseOff2, noiseOff3, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        int l = 0;
        int i1 = 0;
        int rough;

        for (int j1 = 0; j1 < 5; ++j1)
        {
            for (int k1 = 0; k1 < 5; ++k1)
            {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

                for (int l1 = -b0; l1 <= b0; ++l1)
                {
                    for (int i2 = -b0; i2 <= b0; ++i2)
                    {
                    	BiomeGenBase biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f3 = biomegenbase1.rootHeight;
                        float f4 = biomegenbase1.heightVariation;

                        float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

                        if (biomegenbase1.rootHeight > biomegenbase.rootHeight)
                        {
                            f5 /= 2.0F;
                        }

                        f += f4 * f5;
                        f1 += f3 * f5;
                        f2 += f5;
                    }
                }
                
                rough = BiomeUtils.getRoughness(biomegenbase);
                
                if(rough <= -1)
                {
                	rough = 0;
                	System.out.println("Error: Biome ID " + biomegenbase.biomeID + " roughness not registered! Defaulting to 0.");
                }

                f /= f2;
                f1 /= f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;
                double d12 = this.field_147426_g[i1] / 8000.0D;

                if (d12 < 0.0D)
                {
                    d12 = -d12 * 0.3D;
                }

                d12 = d12 * 3.0D - 2.0D;

                if (d12 < 0.0D)
                {
                    d12 /= 2.0D;

                    if (d12 < -1.0D)
                    {
                        d12 = -1.0D;
                    }

                    d12 /= 1.4D;
                    d12 /= 2.0D;
                }
                else
                {
                    if (d12 > 1.0D)
                    {
                        d12 = 1.0D;
                    }

                    d12 /= 8.0D;
                }

                ++i1;
                double d13 = (double)f1;
                double d14 = (double)f;
                d13 += d12 * 0.2D;
                d13 = d13 * 8.5D / 8.0D;
                double d5 = 8.5D + d13 * 4.0D;

                for (int j2 = 0; j2 < 33; ++j2)
                {
                    double d6 = ((double)j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

                    if (d6 < 0.0D)
                    {
                        d6 *= 4.0D;
                    }

                    double d7 = this.field_147428_e[l] / 512.0D;
                    double d8 = this.field_147425_f[l] / 512.0D;
                    double d9 = (this.field_147427_d[l] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

                    if (j2 > 29)
                    {
                        double d11 = (double)((float)(j2 - 29) / 3.0F);
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    this.noiseMap[l] = d10;
                    ++l;
                }
            }
        }
    }
	
	/*private void createNoiseFields(int p_147423_1_, int p_147423_2_, int p_147423_3_) Vanilla noise field gen for reference
    {
        double d0 = 684.412D;
        double d1 = 684.412D;
        double d2 = 512.0D;
        double d3 = 512.0D;
        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, p_147423_1_, p_147423_3_, 5, 5, 200.0D, 200.0D, 0.5D);
        this.field_147427_d = this.noiseGen3.generateNoiseOctaves(this.field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
        this.field_147428_e = this.noiseGen1.generateNoiseOctaves(this.field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.field_147425_f = this.noiseGen2.generateNoiseOctaves(this.field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        boolean flag1 = false;
        boolean flag = false;
        int l = 0;
        int i1 = 0;
        double d4 = 8.5D;

        for (int j1 = 0; j1 < 5; ++j1)
        {
            for (int k1 = 0; k1 < 5; ++k1)
            {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

                for (int l1 = -b0; l1 <= b0; ++l1)
                {
                    for (int i2 = -b0; i2 <= b0; ++i2)
                    {
                        BiomeGenBase biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f3 = biomegenbase1.rootHeight;
                        float f4 = biomegenbase1.heightVariation;

                        float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

                        if (biomegenbase1.rootHeight > biomegenbase.rootHeight)
                        {
                            f5 /= 2.0F;
                        }

                        f += f4 * f5;
                        f1 += f3 * f5;
                        f2 += f5;
                    }
                }

                f /= f2;
                f1 /= f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;
                double d12 = this.field_147426_g[i1] / 8000.0D;

                if (d12 < 0.0D)
                {
                    d12 = -d12 * 0.3D;
                }

                d12 = d12 * 3.0D - 2.0D;

                if (d12 < 0.0D)
                {
                    d12 /= 2.0D;

                    if (d12 < -1.0D)
                    {
                        d12 = -1.0D;
                    }

                    d12 /= 1.4D;
                    d12 /= 2.0D;
                }
                else
                {
                    if (d12 > 1.0D)
                    {
                        d12 = 1.0D;
                    }

                    d12 /= 8.0D;
                }

                ++i1;
                double d13 = (double)f1;
                double d14 = (double)f;
                d13 += d12 * 0.2D;
                d13 = d13 * 8.5D / 8.0D;
                double d5 = 8.5D + d13 * 4.0D;

                for (int j2 = 0; j2 < 33; ++j2)
                {
                    double d6 = ((double)j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

                    if (d6 < 0.0D)
                    {
                        d6 *= 4.0D;
                    }

                    double d7 = this.field_147428_e[l] / 512.0D;
                    double d8 = this.field_147425_f[l] / 512.0D;
                    double d9 = (this.field_147427_d[l] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

                    if (j2 > 29)
                    {
                        double d11 = (double)((float)(j2 - 29) / 3.0F);
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    this.noiseMap[l] = d10;
                    ++l;
                }
            }
        }
    }*/
}
