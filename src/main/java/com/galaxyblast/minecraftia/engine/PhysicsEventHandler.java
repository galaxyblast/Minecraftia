package com.galaxyblast.minecraftia.engine;

import com.galaxyblast.minecraftia.engine.tasks.TaskFallingTree;

import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PhysicsEventHandler
{
	public BlockPhysicsThread bPhysics = new BlockPhysicsThread();
	
	@SubscribeEvent
	public void blockBreak(BlockEvent.HarvestDropsEvent e)
	{
		if(e.block == Blocks.log || e.block == Blocks.log2)
		{
			TaskFallingTree treeTask = new TaskFallingTree();
			treeTask.setBase(e.block, e.world);
			treeTask.setCoords(e.x, e.y, e.z);
			this.bPhysics.queueTask(treeTask);
		}
	}
}
