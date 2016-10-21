package com.galaxyblast.minecraftia.world;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent;

public class BiomeEvent
{
	@SubscribeEvent
	public void BiomeGen(WorldTypeEvent.BiomeSize e)
	{
		e.newSize = 6;
	}
}
