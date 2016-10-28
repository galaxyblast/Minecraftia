package com.galaxyblast.minecraftia.proxy;

import com.galaxyblast.minecraftia.engine.PhysicsEventHandler;
import com.galaxyblast.minecraftia.entities.player.EntityEventHandler;
import com.galaxyblast.minecraftia.entities.player.PlayerEventHandler;
import com.galaxyblast.minecraftia.gui.GuiHandler;
import com.galaxyblast.minecraftia.world.BiomeEventHandler;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    	MinecraftForge.EVENT_BUS.register(new GuiHandler());
    	MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
    	MinecraftForge.EVENT_BUS.register(new PhysicsEventHandler());
    	MinecraftForge.TERRAIN_GEN_BUS.register(new BiomeEventHandler());
    }
    
    public void init(FMLInitializationEvent event)
    {
		
    }
    
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }
}
