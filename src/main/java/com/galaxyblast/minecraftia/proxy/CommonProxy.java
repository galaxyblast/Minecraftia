package com.galaxyblast.minecraftia.proxy;

import com.galaxyblast.minecraftia.blocks.MinecraftiaBlocks;
import com.galaxyblast.minecraftia.engine.BucketHandler;
import com.galaxyblast.minecraftia.engine.PhysicsEventHandler;
import com.galaxyblast.minecraftia.engine.WorldTickHandler;
import com.galaxyblast.minecraftia.entities.player.EntityEventHandler;
import com.galaxyblast.minecraftia.entities.player.PlayerEventHandler;
import com.galaxyblast.minecraftia.gui.GuiHandler;
import com.galaxyblast.minecraftia.items.MinecraftiaItems;
import com.galaxyblast.minecraftia.world.BiomeEventHandler;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public static BucketHandler bucketHandler = new BucketHandler();
	
	public void preInit(FMLPreInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    	MinecraftForge.EVENT_BUS.register(new GuiHandler());
    	MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
    	MinecraftForge.EVENT_BUS.register(new PhysicsEventHandler());
    	MinecraftForge.TERRAIN_GEN_BUS.register(new BiomeEventHandler());
    	FMLCommonHandler.instance().bus().register(new WorldTickHandler());
    	MinecraftForge.EVENT_BUS.register(bucketHandler);
    	
    	MinecraftiaBlocks.init();
    	MinecraftiaItems.init();
    }
    
    public void init(FMLInitializationEvent event)
    {
		
    }
    
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }
}
