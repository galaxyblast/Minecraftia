package com.galaxyblast.minecraftia.world.noise;

import java.util.Random;

public class MinecraftiaNoiseGen
{
    private final double[] baseNoise = new double[] {1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
    private double xCoord, zCoord;
    private int permutations[];
    
    public MinecraftiaNoiseGen(Random r)
    {
    	this.xCoord = r.nextDouble() * 256.0D;
    	this.zCoord = r.nextDouble() * 256.0D;
    	
    	this.permutations = new int[512];
    	
    	for (int i = 0; i < 256; this.permutations[i] = i++)
        {
            ;
        }

        for (int i = 0; i < 256; ++i)
        {
            int j = r.nextInt(256 - i) + i;
            int k = this.permutations[i];
            this.permutations[i] = this.permutations[j];
            this.permutations[j] = k;
            this.permutations[i + 256] = this.permutations[i];
        }
    }
    
	public double[][] genNoiseMap(double in[][], double x, double z)
	{
		double amplitude = 1.0D;
		double scale = 4.0D;
		double tmpX, tmpZ, outX, outZ;
		double xFloor, zFloor;
		double tX, tZ, tXSmooth, tZSmooth, fSmooth;
		int xMin, xMax, zMin, zMax;
		
		for(int x1 = 0; x1 < 16; x1++)
		{
			for(int z1 = 0; z1 < 16; z1++)
			{
				/*tmpX = (x1 + x) * scale;
				tmpZ = (z1 + z) * scale;
				xFloor = Math.floor(tmpX);
				zFloor = Math.floor(tmpZ);
				tX = tmpX - xFloor;
				tZ = tmpZ - zFloor;
				tXSmooth = tX * tX * (3 - 2 * tX);
				tZSmooth = tZ * tZ * (3 - 2 * tZ);
				fSmooth = permutations[((int)tXSmooth * (int)tZSmooth) & 511];
				
				xMin = (int)xFloor & 15;
				xMax = (xMin + 1) & 15;
				zMin = (int)zFloor & 15;
				zMax = (zMin + 1) & 15;

				outX = lerp(baseNoise[xMin], baseNoise[xMax], tXSmooth);
				outZ = lerp(baseNoise[zMin], baseNoise[zMax], tZSmooth);
				
				in[x1][z1] = range(lerp(outX, outZ, fSmooth));*/

				tmpX = x + x1 * scale + this.xCoord;
				tmpZ = z + z1 * scale + this.zCoord;
				in[x1][z1] = range(tmpX * tmpZ);
			}
		}
		
		return in;
	}
	
	public double range(double a)
	{
		return (1.0D / 58046.0D) * (a);
	}
	
	public double lerp(double a, double b, double w)
	{
		return a * (1 - w) + b * w;
	}
}
