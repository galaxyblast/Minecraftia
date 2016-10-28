package com.galaxyblast.minecraftia.world.gen.tree;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TreeTypeSmall implements TreeType
{
	public boolean generate(World w, Random r, int x, int y, int z)
	{	
		int meta = r.nextInt(3);
		if(w.getBlock(x, y, z) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants)
		{
			if(isClear(w, x, y + 1, z))
			{
				int xOff, yOff, zOff;
				
				//Generate base
				for(int y1 = 0; y1 <= 5; y1++)
				{
					w.setBlock(x, y + y1, z, Blocks.log, meta, 2);
				}
				
				//Random branches
				for(int i = 0; i < 4; i++)
				{
					if(r.nextBoolean())
					{
						xOff = r.nextInt(3) - 1;
						yOff = r.nextInt(3) + 2; //[2, Max Height - 2]
						zOff = r.nextInt(3) - 1;
						w.setBlock(x + xOff, y + yOff, z + zOff, Blocks.log, meta, 2);
						for(int x1 = (x + xOff) - 1; x1 <= (x + xOff) + 1; x1++)
						{
							for(int z1 = (z + zOff) - 1; z1 <= (z + zOff) + 1; z1++)
							{
								for(int y1 = (y + yOff) - 1; y1 <= (y + yOff) + 1; y1++)
								{
									if(r.nextInt(3) != 0 && w.getBlock(x1, y1, z1) != Blocks.log)
										w.setBlock(x1, y1, z1, Blocks.leaves, meta, 2);
								}
							}
						}
					}
				}
				
				//Top leaves
				for(int x1 = x - 1; x1 <= x + 1; x1++)
				{
					for(int z1 = z - 1; z1 <= z + 1; z1++)
					{
						for(int y1 = (y + 5) - 2; y1 <= (y + 5) + 1; y1++)
						{
							if(w.getBlock(x1, y1, z1) != Blocks.log)
								w.setBlock(x1, y1, z1, Blocks.leaves, meta, 2);
						}
					}
				}
				
				//Leaf noise
				for(int x1 = x - 2; x1 <= x + 2; x1++)
				{
					for(int z1 = z - 2; z1 <= z + 2; z1++)
					{
						for(int y1 = (y + 5) - 2; y1 <= (y + 5); y1++)
						{
							if(w.getBlock(x1, y1, z1) != Blocks.log && r.nextBoolean())
								w.setBlock(x1, y1, z1, Blocks.leaves, meta, 2);
						}
					}
				}
				return true;
			}
			//System.out.print(isClear(w, x, y + 1, z) + "\n0");
		}
		
		return false;
	}
	
	private boolean isClear(World w, int x, int y, int z)
	{
		boolean clear = true;
		int xSize = 2;
		int ySize = 5;
		
		for(int i = -xSize; i <= xSize; i++)
		{
			for(int j = -xSize; j <= xSize; j++)
			{
				for(int k = 0; k <= ySize; k++)
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
	
	public int getWeight()
	{
		return 8;
	}
}
