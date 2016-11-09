package com.galaxyblast.minecraftia.world.gen.tree;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TreeTypeNormal implements TreeType
{
	private int height;
	
	@Override
	public boolean generate(World w, Random r, int x, int y, int z)
	{
		this.height = r.nextInt(12) + 10;
		int leafStart = r.nextInt(3) + 3;
		int meta = r.nextInt(3);
		int rad = 5;
		int tmp = 0;
		int growUp = r.nextInt(4) + 1;
		
		if(isClear(w, x, y + leafStart, z) || !isTooClose(w, x, y, z))
		{
			for(int i = 0; i <= this.height; i++)
			{
				w.setBlock(x, y + i, z, Blocks.log, meta, 2);
			}
			
			for(int i = leafStart; i < height + 3; i++)
			{
				tmp++;
				if(tmp == (int)Math.ceil((double)(height - leafStart) / 3.0D) && rad > 0)
				{
					tmp = 0;
					rad--;
				}
				
				if(i % (r.nextInt(5) + 1) == 0 && i < this.height)
				{
					genBranch(w, r, x, y + i, z, meta, r.nextInt(4));
				}
				
				if(growUp >= 0)
				{
					growUp--;
					for(int j = 0; j < 16; j++)
						growLeaves(w, r, x, y + i, z, meta, j % 8, 0, rad);
				}
				else
					growUp = r.nextInt(4) + 1;
			}
			
			for(int i = height; i < height + 6; i++)
			{
				if(w.getBlock(x, y + i, z) == Blocks.air)
					w.setBlock(x, y + i, z, Blocks.leaves, meta + 12, 2);
				
				if(i <= height + 3)
				{
					for(int j = -1; j <= 1; j++)
					{
						for(int k = -1; k <= 1; k++)
						{
							if(w.getBlock(x + j, y + i, z + k) == Blocks.air)
								w.setBlock(x + j, y + i, z + k, Blocks.leaves, meta + 12, 2);
						}
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	private void growLeaves(World w, Random r, int x, int y, int z, int meta, int dir, int steps, int size)
	{
		int xStep, zStep;
		
		switch(dir)
		{
			case 0:
				xStep = 1;
				zStep = -1;
				break;
			case 1:
				xStep = 1;
				zStep = 0;
				break;
			case 2:
				xStep = 1;
				zStep = 1;
				break;
			case 3:
				xStep = 0;
				zStep = 1;
				break;
			case 4:
				xStep = -1;
				zStep = 1;
				break;
			case 5:
				xStep = -1;
				zStep = 0;
				break;
			case 6:
				xStep = -1;
				zStep = -1;
				break;
			case 7:
			default:
				xStep = 0;
				zStep = -1;
				break;
		}
		
		Block b = w.getBlock(x + xStep, y, z + zStep);
		if((b == Blocks.air || b.getMaterial() != Material.plants || b.getMaterial() != Material.leaves) && steps < size)
		{
			w.setBlock(x + xStep, y, z + zStep, Blocks.leaves, meta + 12, 2);
			if(xStep == 0)
				growLeaves(w, r, x + r.nextInt(3) - 1, y, z + zStep, meta, dir, steps + 1, size);
			else
				growLeaves(w, r, x + xStep, y, z + r.nextInt(3) - 1, meta, dir, steps + 1, size);
		}
	}
	
	private void genBranch(World w, Random r, int x, int y, int z, int meta, int dir)
	{
		int xStep, zStep, ch, chX = 0, chZ = 0;
		int size = r.nextInt(3) + 1;
		
		switch(dir)
		{
			case 0:
				xStep = 1;
				zStep = 0;
				break;
			case 1:
				xStep = -1;
				zStep = 0;
				break;
			case 2:
				xStep = 0;
				zStep = 1;
				break;
			case 3:
			default:
				xStep = 0;
				zStep = -1;
				break;
		}
		
		ch = r.nextInt(3) - 1;
		if(xStep == 0)
			chX = ch;
		else
			chZ = ch;
		
		int p = (int)Math.ceil((double)size / 2.0D);
		for(int i = 0; i <= size; i++)
		{
			if(i == p)
			{
				x += chX;
				z += chZ;
			}
			
			w.setBlock(x + (xStep * i), y, z + (zStep * i), Blocks.log, meta + 16, 2);
			for(int x1 = -1; x1 <= 1; x1++)
			{
				for(int z1 = -1; z1 <= 1; z1++)
				{
					for(int y1 = 0; y1 <= 1; y1++)
					{
						if(w.getBlock(x + (xStep * i) + x1, y + y1, z + (zStep * i) + z1) == Blocks.air)
						{
							w.setBlock(x + (xStep * i) + x1, y + y1, z + (zStep * i) + z1, Blocks.leaves, meta + 12, 2);
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
		int ySize = this.height;
		
		for(int i = -xSize; i <= xSize; i++)
		{
			for(int j = -xSize; j <= xSize; j++)
			{
				for(int k = 0; k <= ySize; k++)
				{
					Block b = w.getBlock(x + i, y + k, z + j);
					
					if(b != Blocks.air && (b.getMaterial() != Material.plants || b.getMaterial() != Material.leaves || b.getMaterial() != Material.wood || !b.isFoliage(w, x + i, y + k, z + j)))
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
		return 12;
	}

}
