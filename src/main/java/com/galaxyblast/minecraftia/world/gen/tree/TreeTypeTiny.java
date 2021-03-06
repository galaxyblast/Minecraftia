package com.galaxyblast.minecraftia.world.gen.tree;

import java.util.Random;

import com.galaxyblast.minecraftia.blocks.MinecraftiaBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TreeTypeTiny implements TreeType
{

	@Override
	public boolean generate(World w, Random r, int x, int y, int z)
	{
		int meta = r.nextInt(3);
		if(w.getBlock(x, y, z) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants)
		{
			if(isClear(w, x, y + 1, z) || !isTooClose(w, x, y, z))
			{
				//Base
				for(int i = 0; i <= 3; i++)
					w.setBlock(x, y + i, z, MinecraftiaBlocks.thinLog, meta, 2);
				
				//Leaves
				for(int x1 = -1; x1 < 2; x1++)
				{
					for(int z1 = -1; z1 < 2; z1++)
					{
						for(int y1 = 1; y1 <= 3; y1++)
						{
							if(r.nextInt(3) != 0 && w.getBlock(x + x1, y + y1, z + z1).getMaterial() != Material.wood)
								w.setBlock(x + x1, y + y1, z + z1, Blocks.leaves, meta + 12, 2);
						}
					}
				}
				
				//Top leaves
				w.setBlock(x, y + 4, z, Blocks.leaves, meta + 12, 2);
				
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isClear(World w, int x, int y, int z)
	{
		boolean clear = true;
		int xSize = 1;
		int ySize = 3;
		
		for(int i = -xSize; i <= xSize; i++)
		{
			for(int j = -xSize; j <= xSize; j++)
			{
				for(int k = 1; k <= ySize; k++)
				{
					Block b = w.getBlock(x + i, y + k, z + j);
					
					if(b != Blocks.air && (b.getMaterial() != Material.plants || b.getMaterial() != Material.leaves || b.getMaterial() != Material.wood))
					{
						clear = false;
					}
				}
			}
		}
		
		return clear;
	}
	
	private boolean isTooClose(World w, int x, int y, int z)
	{
		for(int i = -2; i <= 2; i++)
		{
			for(int j = -2; j <= 2; j++)
			{
				for(int k = -2; k <= 3; k++)
				{
					Block b = w.getBlock(x + i, y + k, z + j);
					
					if(b == Blocks.log)
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public int getWeight()
	{
		return 4;
	}

}
