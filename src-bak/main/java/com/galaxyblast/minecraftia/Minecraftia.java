package com.galaxyblast.minecraftia;

import com.galaxyblast.minecraftia.proxy.CommonProxy;
import com.galaxyblast.minecraftia.world.WorldTypeMinecraftia;

import net.minecraft.init.Blocks;
import net.minecraft.world.WorldType;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Minecraftia.MODID, name = Minecraftia.NAME, version = Minecraftia.VERSION)
public class Minecraftia
{
    public static final String MODID = "Minecraftia";
    public static final String VERSION = "1.0A-0"; //Major, minor, [build, [revision [alpha/beta]]] - release number
    public static final String NAME = "Minecraftia";
    
    public static final boolean debugMode = true;
    
    public static final WorldType MWORLD = new WorldTypeMinecraftia("minecraftiaworld");
    
    @SidedProxy(clientSide = "com.galaxyblast.minecraftia.proxy.ClientProxy", serverSide = "com.galaxyblast.minecraftia.proxy.ServerProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.postInit(event);
    }
}
