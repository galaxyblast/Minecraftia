package com.galaxyblast.minecraftia.world.gen.tree;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TreeTypeMed implements TreeType
{

	@Override
	public boolean generate(World w, Random r, int x, int y, int z)
	{
		int meta = r.nextInt(3);
		
		if(w.getBlock(x, y, z) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants)
		{
			if(isClear(w, x, y + 1, z))
			{
				int yOff = 4;
				
				//Base
				for(int y1 = 0; y1 <= 8; y1++)
				{
					w.setBlock(x, y + y1, z, Blocks.log, meta, 2);
					w.setBlock(x + 1, y + y1, z, Blocks.log, meta, 2);
					w.setBlock(x + 1, y + y1, z + 1, Blocks.log, meta, 2);
					w.setBlock(x, y + y1, z + 1, Blocks.log, meta, 2);
				}
				
				//Branches
				for(int i = 0; i < 4; i++)
				{
					if(r.nextInt(3) != 0)
					{
						yOff = r.nextInt(4) + 3;
						switch(r.nextInt(4))
						{
							case 0:
								if(w.getBlock(x + 2, y + yOff, z) != Blocks.log)
								{
									w.setBlock(x + 2, y + yOff, z, Blocks.log, meta, 2);
									w.setBlock(x + 2, y + yOff, z + 1, Blocks.log, meta, 2);
									w.setBlock(x + 2, y + yOff + 1, z, Blocks.log, meta, 2);
									w.setBlock(x + 2, y + yOff + 1, z + 1, Blocks.log, meta, 2);
									genBranch(w, r, x + 3, y + yOff, z + r.nextInt(3) - 1, 1, 0, meta);
								}
								break;
							case 1:
								if(w.getBlock(x - 1, y + yOff, z) != Blocks.log)
								{
									w.setBlock(x - 1, y + yOff, z, Blocks.log, meta, 2);
									w.setBlock(x - 1, y + yOff, z + 1, Blocks.log, meta, 2);
									w.setBlock(x - 1, y + yOff + 1, z, Blocks.log, meta, 2);
									w.setBlock(x - 1, y + yOff + 1, z + 1, Blocks.log, meta, 2);
									genBranch(w, r, x - 2, y + yOff, z + r.nextInt(3) - 1, 0, 0, meta);
								}
								break;
							case 2:
								if(w.getBlock(x, y + yOff, z - 1) != Blocks.log)
								{
									w.setBlock(x, y + yOff, z - 1, Blocks.log, meta, 2);
									w.setBlock(x + 1, y + yOff, z - 1, Blocks.log, meta, 2);
									w.setBlock(x, y + yOff + 1, z - 1, Blocks.log, meta, 2);
									w.setBlock(x + 1, y + yOff + 1, z - 1, Blocks.log, meta, 2);
									genBranch(w, r, x + r.nextInt(3) - 1, y + yOff, z - 2, 3, 0, meta);
								}
								break;
							case 3:
							default:
								if(w.getBlock(x, y + yOff, z + 2) != Blocks.log)
								{
									w.setBlock(x, y + yOff, z + 2, Blocks.log, meta, 2);
									w.setBlock(x + 1, y + yOff, z + 2, Blocks.log, meta, 2);
									w.setBlock(x, y + yOff + 1, z + 2, Blocks.log, meta, 2);
									w.setBlock(x + 1, y + yOff + 1, z + 2, Blocks.log, meta, 2);
									genBranch(w, r, x + r.nextInt(3) - 1, y + yOff, z + 3, 3, 0, meta);
								}
								break;
								
						}
					}
				}
				
				//Base leaves
				int sizeMod = r.nextInt(3) - 1;
				for(int i = -4 + sizeMod; i < 5 + sizeMod; i++)
				{
					for(int j = -4 + sizeMod; j < 5 + sizeMod; j++)
					{
						for(int k = -3; k < 3; k++)
						{
							if((int)Math.sqrt((i * i) + (k * k) + (j * j)) <= 5)
							{
								if(w.getBlock(x + i, y + k + 7, z + j) != Blocks.log)
									w.setBlock(x + i, y + k + 7, z + j, Blocks.leaves, meta + 12, 2);
							}
						}
					}
				}
				
				//Leaf noise
				for(int i = -5 + sizeMod; i < 6 + sizeMod; i++)
				{
					for(int j = -5 + sizeMod; j < 6 + sizeMod; j++)
					{
						for(int k = -5; k < 5; k++)
						{
							if((int)Math.sqrt((i * i) + (k * k) + (j * j)) <= 5 && r.nextBoolean())
							{
								if(w.getBlock(x + i, y + k + 7, z + j) != Blocks.log)
									w.setBlock(x + i, y + k + 7, z + j, Blocks.leaves, meta + 12, 2);
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
	private void genBranch(World w, Random r, int x, int y, int z, int mode, int steps, int meta)
	{
		if(steps < 4)
		{
			w.setBlock(x, y, z, Blocks.log, meta, 2);
			for(int i = -1; i < 2; i++)
			{
				for(int j = -1; j < 2; j++)
				{
					for(int k = -1; k < 2; k++)
					{
						Block b = w.getBlock(x + i, y + k, z + j);
						if(b == Blocks.air)
						{
							w.setBlock(x + i, y + k, z + j, Blocks.leaves, meta + 12, 2);
						}
					}
				}
			}
			
			int x1, z1;
			int y1 = r.nextInt(2);
			if(mode == 0)
			{
				x1 = x - 1;
				z1 = z;
				if(r.nextBoolean())
					genBranch(w, r, x - 1, y + y1, z + r.nextInt(3) - 1, mode, steps + 1, meta);
			}
			else if(mode == 1)
			{
				x1 = x + 1;
				z1 = z;
				if(r.nextBoolean())
					genBranch(w, r, x + 1, y + y1, z + r.nextInt(3) - 1, mode, steps + 1, meta);
			}
			else if(mode == 2)
			{
				x1 = x;
				z1 = z + 1;
				if(r.nextBoolean())
					genBranch(w, r, x + r.nextInt(3) - 1, y + y1, z + 1, mode, steps + 1, meta);
			}
			else
			{
				x1 = x;
				z1 = z - 1;
				if(r.nextBoolean())
					genBranch(w, r, x + r.nextInt(3) - 1, y + y1, z - 1, mode, steps + 1, meta);
			}
			
			w.setBlock(x1, y + y1, z1, Blocks.log, meta, 2);
			for(int i = -1; i < 2; i++)
			{
				for(int j = -1; j < 2; j++)
				{
					for(int k = -1; k < 2; k++)
					{
						Block b = w.getBlock(x1 + i, y + y1 + k, z1 + j);
						if(b == Blocks.air)
						{
							w.setBlock(x1 + i, y + y1 + k, z1 + j, Blocks.leaves, meta + 12, 2);
						}
					}
				}
			}
		}
	}
	
	private boolean isClear(World w, int x, int y, int z)
	{
		boolean clear = true;
		int xSize = 5;
		int ySize = 8;
		
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

	@Override
	public int getWeight()
	{
		return 8;
	}

}
