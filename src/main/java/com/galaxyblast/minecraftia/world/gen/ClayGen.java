package com.galaxyblast.minecraftia.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class ClayGen extends WorldGenerator
{
	public boolean generate(World world, Random rng, int x, int y, int z)
	{
		if(world.getBlock(x, y + 1, z).getMaterial() == Material.water && rng.nextInt(16) == 0)
		{
			spreadClay(world, rng, x, y, z, 0);
			return true;
		}
		else
			return false;
	}
	
	private void spreadClay(World world, Random rng, int x, int y, int z, int size)
	{
		if(world.checkChunksExist(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1) && size <= 16)
		{
			world.setBlock(x, y, z, Blocks.clay, 0, 2);
			for(int i = -1; i < 2; i++)
			{
				for(int j = -1; j < 2; j++)
				{
					for(int k = -1; k < 2; k++)
					{
						if(world.getBlock(x + i, y + k, z + j) == Blocks.dirt && rng.nextInt(5) != 0)
						{
							spreadClay(world, rng, x + i, y + k, z + j, size + 1);
						}
					}
				}
			}
		}
	}
	
	/*public boolean generate(World world, Random rng, int x, int y, int z)
    {
        if (world.getBlock(x, y, z).getMaterial() != Material.water)
        {
            return false;
        }
        else if(rng.nextInt(16) != 0)
        {
        	return false;
        }
        else
        {
        	Block block = world.getBlock(x, y, z);
            int xOff = 0, yOff = 0, zOff = 0;
        	while(block != Blocks.dirt && yOff > -10)
        	{
        		yOff--;
        		block = world.getBlock(x, y + yOff, z);
        	}
        	
            while(rng.nextInt(36) != 1)
            {
	            for (int i1 = x + xOff - 1; i1 <= x + xOff + 1; ++i1)
	            {
	                for (int j1 = z + zOff - 1; j1 <= z + zOff + 1; ++j1)
	                {
	                    int k1 = i1 - x;
	                    int l1 = j1 - z;
	
	                    if (k1 * k1 + l1 * l1 <= 256)
	                    {
	                        for (int i2 = y + yOff - 1; i2 <= y + yOff + 1; ++i2)
	                        {
	                            block = world.getBlock(i1, i2, j1);
	
	                            if (block == Blocks.dirt || block == Blocks.grass)
	                            {
	                                world.setBlock(i1, i2, j1, Blocks.clay, 0, 2);
	                            }
	                        }
	                    }
	                }
	            }
	            xOff += rng.nextInt(3) - 1;
	            yOff += rng.nextInt(3) - 1;
	            zOff += rng.nextInt(3) - 1;
            }

            return true;
        }
    }*/
}
