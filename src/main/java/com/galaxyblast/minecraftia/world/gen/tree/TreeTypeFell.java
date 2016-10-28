package com.galaxyblast.minecraftia.world.gen.tree;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TreeTypeFell implements TreeType
{

	@Override
	public boolean generate(World w, Random r, int x, int y, int z)
	{
		int size = r.nextInt(5) + 3;
		int xOff, zOff = 0;
		int meta = 4 + r.nextInt(3);
		
		xOff = r.nextInt(2);
		if(xOff == 0)
		{
			zOff = 1;
			meta = 8 + r.nextInt(3);
		}
		
		if(isClear(w, x, y, z, size, xOff))
		{
			for(int i = 0; i <= size; i++)
			{
				w.setBlock(x + (xOff * i), y, z + (zOff * i), Blocks.log, meta, 2);
			}
		}
		
		return false;
	}
	
	private boolean isClear(World w, int x, int y, int z, int size, int dir)
	{
		boolean clear = true;
		
		if(dir == 1)
		{
			for(int i = 0; i <= size; i++)
			{
				Block b = w.getBlock(x + i, y, z);
				if(b != Blocks.air && (b.getMaterial() != Material.plants || b.getMaterial() != Material.leaves || b.getMaterial() != Material.wood))
					clear = false;
				else if(i == size && (w.getBlock(x + i, y - 1, z) == Blocks.air || w.getBlock(x + i, y - 1, z).getMaterial() == Material.water || w.getBlock(x + i, y - 1, z).getMaterial() == Material.lava))
					clear = false;
			}
		}
		else
		{
			for(int i = 0; i <= size; i++)
			{
				Block b = w.getBlock(x, y, z + i);
				if(b != Blocks.air && (b.getMaterial() != Material.plants || b.getMaterial() != Material.leaves || b.getMaterial() != Material.wood))
					clear = false;
				else if(i == size && (w.getBlock(x, y - 1, z + i) == Blocks.air || w.getBlock(x, y - 1, z + i).getMaterial() == Material.water || w.getBlock(x, y - 1, z + i).getMaterial() == Material.lava))
					clear = false;
			}
		}
		
		return clear;
	}

	@Override
	public int getWeight()
	{
		return 1;
	}

}
