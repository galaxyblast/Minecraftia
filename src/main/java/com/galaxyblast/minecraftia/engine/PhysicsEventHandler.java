package com.galaxyblast.minecraftia.engine;

import com.galaxyblast.minecraftia.Minecraftia;
import com.galaxyblast.minecraftia.engine.tasks.TaskFallingTree;

import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public class PhysicsEventHandler
{	
	@SubscribeEvent
	public void blockBreak(BlockEvent.HarvestDropsEvent e)
	{
		if(e.block == Blocks.log || e.block == Blocks.log2)
		{
			TaskFallingTree treeTask = new TaskFallingTree();
			treeTask.setBase(e.block, e.world);
			treeTask.setCoords(e.x, e.y, e.z);
			Minecraftia.instance.getPhysicsThread().queueTask(treeTask);
		}
	}
	
	@SubscribeEvent
	public void unloadWorld(WorldEvent.Unload e)
	{
		System.out.println("Clearing physics thread.");
		Minecraftia.instance.getPhysicsThread().setRunning(false);
		Minecraftia.instance.getPhysicsThread().clearQueue();
	}
}
