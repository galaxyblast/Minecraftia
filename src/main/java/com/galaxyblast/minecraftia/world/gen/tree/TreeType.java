package com.galaxyblast.minecraftia.world.gen.tree;

import java.util.Random;

import net.minecraft.world.World;

public interface TreeType
{
	public boolean generate(World w, Random r, int x, int y, int z);
	public int getWeight();
}
