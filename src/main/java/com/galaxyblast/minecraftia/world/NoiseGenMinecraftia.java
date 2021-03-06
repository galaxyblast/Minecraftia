package com.galaxyblast.minecraftia.world;

import java.util.Random;

import com.galaxyblast.minecraftia.world.noise.MinecraftiaNoiseGenImproved;

import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.NoiseGeneratorImproved;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class NoiseGenMinecraftia extends NoiseGeneratorOctaves
{
    private MinecraftiaNoiseGenImproved[] generatorCollection;
    private int octaves;
    
	public NoiseGenMinecraftia(Random rng, int oct)
	{
		super(rng, oct);
		this.octaves = oct;
        this.generatorCollection = new MinecraftiaNoiseGenImproved[oct];

        for (int i = 0; i < oct; ++i)
        {
            this.generatorCollection[i] = new MinecraftiaNoiseGenImproved(rng);
        }
	}

	@Override
	public double[] generateNoiseOctaves(double[] par1, int noiseOff1, int noiseOff2, int noiseOff3, int xArrSize, int yArrSize, int zArrSize, double xNoiseScale, double yNoiseScale, double zNoiseScale)
    {
        if (par1 == null)
        {
            par1 = new double[xArrSize * yArrSize * zArrSize];
        }
        else
        {
            for (int k1 = 0; k1 < par1.length; ++k1)
            {
                par1[k1] = 0.0D;
            }
        }

        double dy = 0.8D;
        double dxz = 0.3D;

        for (int l1 = 0; l1 < this.octaves; ++l1)
        {
            double d3 = (double)noiseOff1 * dxz * xNoiseScale;
            double d4 = (double)noiseOff2 * dy * yNoiseScale;
            double d5 = (double)noiseOff3 * dxz * zNoiseScale;
            long i2 = MathHelper.floor_double_long(d3);
            long j2 = MathHelper.floor_double_long(d5);
            d3 -= (double)i2;
            d5 -= (double)j2;
            i2 %= 16777216L;
            j2 %= 16777216L;
            d3 += (double)i2;
            d5 += (double)j2;
            this.generatorCollection[l1].populateNoiseArray(par1, d3, d4, d5, xArrSize, yArrSize, zArrSize, xNoiseScale * dxz, yNoiseScale * dy, zNoiseScale * dxz, dy);
            dy /= 2.0D;
            dxz /= 2.0D;
        }

        return par1;
    }
}
