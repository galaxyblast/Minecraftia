package com.galaxyblast.minecraftia.engine.tasks;

import java.util.Random;

import net.minecraft.world.World;

import com.galaxyblast.minecraftia.fluids.BlockFluidWater;

public class TaskFluid extends TaskBlockUpdate
{
	public TaskFluid(BlockFluidWater b, World w, int x, int y, int z, Random r)
	{
		this.blockForUpdate = b;
		this.worldObj = w;
		this.x = x;
		this.y = y;
		this.z = z;
		this.rng = r;
	}
	
	@Override
	public void execute()
	{
		try
		{
			BlockFluidWater b = (BlockFluidWater)this.blockForUpdate;
			b.runUpdate(this.worldObj, this.x, this.y, this.z, this.rng);
		}
		catch(ClassCastException e)
		{
			System.out.println("ERROR: TaskFluid was givin an invalid block!");
		}
	}
}
