package com.galaxyblast.minecraftia.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.galaxyblast.minecraftia.blocks.MinecraftiaBlocks;
import com.galaxyblast.minecraftia.engine.BucketHandler;
import com.galaxyblast.minecraftia.proxy.CommonProxy;

import cpw.mods.fml.common.registry.GameRegistry;

public class MinecraftiaItems
{
	public static final ItemMinecraftiaBucket bucket = (ItemMinecraftiaBucket) new ItemMinecraftiaBucket(MinecraftiaBlocks.water).setUnlocalizedName("waterBucket").setCreativeTab(CreativeTabs.tabMisc).setTextureName("minecraft:bucket_water");
	
	public static void init()
	{
		GameRegistry.registerItem(bucket, "waterBucket");
		
		CommonProxy.bucketHandler.registerBucket(MinecraftiaBlocks.water, bucket);
		
	}
}
