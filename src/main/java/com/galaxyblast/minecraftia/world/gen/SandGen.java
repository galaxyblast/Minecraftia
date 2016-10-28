package com.galaxyblast.minecraftia.world.gen;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class SandGen extends WorldGenerator
{
	public boolean generate(World world, Random rnd, int x, int y, int z)
	{
		if(world.checkChunksExist(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1))
		{
			if(world.getBlock(x, y, z).getMaterial() == Material.water || world.getBlock(x, y + 1, z).getMaterial() == Material.water)
			{
				for(int i = -1; i < 2; i++)
				{
					for(int j = -1; j < 2; j++)
					{
						for(int k = -1; k < 2; k++)
						{
							if(world.getBlock(x + i, y + k, z + j) == Blocks.dirt)
								world.setBlock(x + i, y + k, z + j, Blocks.sand, 0, 2);
						}
					}
				}
			}
			else
			{
				if(world.getBlock(x + 1, y, z).getMaterial() == Material.water || world.getBlock(x - 1, y, z).getMaterial() == Material.water || world.getBlock(x, y, z + 1).getMaterial() == Material.water
						|| world.getBlock(x, y, z - 1).getMaterial() == Material.water)
				{
					for(int i = -1; i < 2; i++)
					{
						for(int j = -1; j < 2; j++)
						{
							for(int k = -1; k < 2; k++)
							{
								if(world.getBlock(x + i, y + k, z + j) == Blocks.grass)
									world.setBlock(x + i, y + k, z + j, Blocks.sand, 0, 2);
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}
}
