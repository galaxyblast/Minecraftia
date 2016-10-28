package com.galaxyblast.minecraftia.world.gen.tree;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TreeTypeBush implements TreeType
{
	@Override
	public boolean generate(World w, Random rng, int x, int y, int z)
	{
		if(w.getBlock(x, y, z) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants)
		{
			w.setBlock(x, y, z, Blocks.log, 0, 2);
			for(int i = -1; i < 2; i++)
			{
				for(int j = -1; j < 2; j++)
				{
					if(w.getBlock(x + i, y, z + j) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants)
						w.setBlock(x + i, y, z + j, Blocks.leaves, 0, 2);
				}
			}

			if(w.getBlock(x, y + 1, z) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants)
				w.setBlock(x, y + 1, z, Blocks.leaves, 0, 2);
			if(w.getBlock(x - 1, y + 1, z) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants)
				w.setBlock(x - 1, y + 1, z, Blocks.leaves, 0, 2);
			if(w.getBlock(x + 1, y + 1, z) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants)
				w.setBlock(x + 1, y + 1, z, Blocks.leaves, 0, 2);
			if(w.getBlock(x, y + 1, z - 1) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants)
				w.setBlock(x, y + 1, z - 1, Blocks.leaves, 0, 2);
			if(w.getBlock(x, y + 1, z + 1) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants)
				w.setBlock(x, y + 1, z + 1, Blocks.leaves, 0, 2);
			
			for(int i = -1; i < 2; i++)
			{
				for(int j = -1; j < 2; j++)
				{
					if((w.getBlock(x + i, y + 1, z + j) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants) && rng.nextBoolean())
						w.setBlock(x + i, y + 1, z + j, Blocks.leaves, 0, 2);
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public int getWeight()
	{
		return 5;
	}
}
