package com.galaxyblast.minecraftia.world.gen.tree;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TreeTypePine implements TreeType
{
	private int yOff;
	private int xPos, yPos, zPos;
	
	@Override
	public boolean generate(World w, Random r, int x, int y, int z)
	{
		this.xPos = x;
		this.yPos = y;
		this.zPos = z;
		
		int meta = r.nextInt(2);
		this.yOff = r.nextInt(3) + 2;
		int ySize = r.nextInt(4) + 8;
		int rad = 4;
		int rand;
		
		if(w.getBlock(x, y, z) == Blocks.air || w.getBlock(x, y, z).getMaterial() == Material.plants)
		{
			if(isClear(w, x, y, z))
			{
				//Base
				for(int i = 0; i <= ySize + this.yOff; i++)
				{
					w.setBlock(x, y + i, z, Blocks.log, meta, 2);
				}
				
				//Base Leaves
				for(int i = -2; i <= 2; i++)
				{
					for(int j = -2; j <= 2; j++)
					{
						if(w.getBlock(x + i, y + this.yOff, z + j) == Blocks.air && (int)Math.sqrt((double)(i * i) + 1.0D + (double)(j * j)) <= 2)
							w.setBlock(x + i, y + this.yOff, z + j, Blocks.leaves, meta + 12, 2);
					}
				}
				for(int i = -3; i <= 3; i++)
				{
					for(int j = -3; j <= 3; j++)
					{
						if(w.getBlock(x + i, y + this.yOff + 1, z + j) == Blocks.air && (int)Math.sqrt((double)(i * i) + 1.0D + (double)(j * j)) <= 3)
							w.setBlock(x + i, y + this.yOff + 1, z + j, Blocks.leaves, meta + 12, 2);
					}
				}
				
				int tmp = 0;
				//Main branches
				for(int i = this.yOff + 2; i <= ySize + this.yOff + 2; i++)
				{
					tmp++;
					if(tmp == (int)Math.ceil(((double)ySize + 2.0D) / 3.0D) && rad > 0)
					{
						tmp = 0;
						rad--;
					}
					
					for(int j = 0; j < 16; j++)
					{
						growLeaves(w, r, x, y + i, z, meta + 12, j % 8, 0, rad);
					}
				}
				
				int tmp2 = r.nextInt(2) + 4;
				
				//Top leaves
				for(int i = ySize + this.yOff; i <= ySize + this.yOff + tmp2; i++)
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
				for(int i = ySize + this.yOff + tmp2; i <= ySize + this.yOff + tmp2 + 3; i++)
				{
					if(w.getBlock(x, y + i, z) == Blocks.air)
						w.setBlock(x, y + i, z, Blocks.leaves, meta + 12, 2);
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	private void growLeaves(World w, Random r, int x, int y, int z, int meta, int mode, int steps, int dist)
	{
		int xStep, zStep;
		int xDiff, zDiff;
		
		switch(mode)
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
				xStep = 0;
				zStep = -1;
				break;
			case 4:
				xStep = 1;
				zStep = -1;
				break;
			case 5:
				xStep = -1;
				zStep = -1;
				break;
			case 6:
				xStep = 1;
				zStep = 1;
				break;
			case 7:
			default:
				xStep = -1;
				zStep = 1;
				break;
		}
		
		if(steps <= dist)
		{
			xDiff = this.xPos - x + xStep;
			zDiff = this.zPos - z + zStep;
			
			if(w.getBlock(x + xStep, y, z + zStep) == Blocks.air || w.getBlock(x + xStep, y, z + zStep).getMaterial() == Material.leaves)
			{
				if((int)Math.sqrt((xDiff * xDiff) + (zDiff * zDiff)) <= dist)
				{
					w.setBlock(x + xStep, y, z + zStep, Blocks.leaves, meta, 2);
					if(xStep == 0)
						growLeaves(w, r, x + r.nextInt(3) - 1, y, z + zStep, meta, mode, steps + 1, dist);
					else
						growLeaves(w, r, x + xStep, y, z + r.nextInt(3) - 1, meta, mode, steps + 1, dist);
				}
			}
		}
	}
	
	private int pick(Random r, int a, int b)
	{
		if(r.nextBoolean())
			return a;
		else
			return b;
	}
	
	private boolean isClear(World w, int x, int y, int z)
	{
		boolean clear = true;
		int xSize = 3;
		int ySize = 10 + this.yOff;
		
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
		return 10;
	}

}
