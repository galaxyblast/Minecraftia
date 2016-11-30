package com.galaxyblast.minecraftia.engine;

import com.galaxyblast.minecraftia.Minecraftia;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public class WorldTickHandler
{
	@SubscribeEvent
	public void runPhysicThread(TickEvent.WorldTickEvent e)
	{
		if(e.phase == Phase.END)
		{
			synchronized(Minecraftia.instance.getPhysicsThread())
			{
				if(!Minecraftia.instance.getPhysicsThread().getRunning())
					Minecraftia.instance.getPhysicsThread().setRunning(true);
				
				Minecraftia.instance.getPhysicsThread().notifyAll();
			}
		}
	}
}
