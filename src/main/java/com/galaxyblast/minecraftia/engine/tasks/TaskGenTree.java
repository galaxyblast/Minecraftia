package com.galaxyblast.minecraftia.engine.tasks;

import java.util.Random;

import net.minecraft.world.World;

import com.galaxyblast.minecraftia.world.gen.tree.TreeType;

public class TaskGenTree implements Task
{
	private TreeType tree;
	private World worldObj;
	private Random rng;
	private int x, y, z;
	
	public TaskGenTree(TreeType t, World w, Random r, int x, int y, int z)
	{
		this.tree = t;
		this.worldObj = w;
		this.rng = r;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void execute()
	{
		this.tree.generate(this.worldObj, this.rng, this.x, this.y, this.z);
	}
}
