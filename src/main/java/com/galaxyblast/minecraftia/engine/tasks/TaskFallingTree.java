package com.galaxyblast.minecraftia.engine.tasks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TaskFallingTree implements Task
{
	private Block base;
	private Random rng = new Random();
	private World worldObj;
	private boolean hasBlockAbove = true;
	int xPos, yPos, zPos;
	
	@Override
	public void execute()
	{
		if(this.base != null)
		{
			int y = 1;
			Block tmp;
			
			//Fall
			while(this.hasBlockAbove)
			{
				if(this.worldObj.getBlock(this.xPos, this.yPos + y, this.zPos) == Blocks.air)
					this.hasBlockAbove = false;
				else
					y++;
			}
			
			int i = 1;
			while(i < y)
			{
				for(int x = -5; x <= 5; x++)
				{
					for(int z = -5; z <= 5; z++)
					{
						tmp = this.worldObj.getBlock(this.xPos + x, this.yPos + i, this.zPos + z);
						if(tmp.getMaterial() == Material.wood || tmp.getMaterial() == Material.leaves)
						{
							if(!this.worldObj.isRemote)
							{
								EntityFallingBlock entityBlock = new EntityFallingBlock(this.worldObj, (double)(this.xPos + x + 0.5D), (double)(this.yPos + i + 0.5D), (double)(this.zPos + z + 0.5D), tmp, this.worldObj.getBlockMetadata(this.xPos + x, this.yPos + i, this.zPos + z));
								this.worldObj.spawnEntityInWorld(entityBlock);
							}
						}
					}
				}
				i++;
			}
		}
	}
	
	public void setBase(Block b, World w)
	{
		this.base = b;
		this.worldObj = w;
	}
	
	public void setCoords(int x, int y, int z)
	{
		this.xPos = x;
		this.yPos = y;
		this.zPos = z;
	}
}
