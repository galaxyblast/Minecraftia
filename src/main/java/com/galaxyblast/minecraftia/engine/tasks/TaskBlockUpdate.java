package com.galaxyblast.minecraftia.engine.tasks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import com.galaxyblast.minecraftia.fluids.BlockFluidWater;

public abstract class TaskBlockUpdate implements Task
{
	protected Block blockForUpdate;
	protected World worldObj;
	protected int x, y, z;
	protected Random rng;
}
