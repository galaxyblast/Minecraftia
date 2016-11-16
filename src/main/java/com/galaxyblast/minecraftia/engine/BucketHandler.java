package com.galaxyblast.minecraftia.engine;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class BucketHandler
{
	public static BucketHandler instance = new BucketHandler();
	private static Map<Block, ItemBucket> buckets = new HashMap<Block, ItemBucket>();
	
	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event)
	{
	    ItemStack b = fillBucket(event.world, event.target);
    	
	    if (b == null)
	    {
	            return;
	    }
	    
	    event.result = b;
	    event.setResult(Result.ALLOW);
	}
	
	private ItemStack fillBucket(World world, MovingObjectPosition pos)
	{
	    Block b = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
	
	    ItemBucket bucket = buckets.get(b);
	    if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0)
	    {
	    	world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
	    	return new ItemStack(bucket);
	    }
	    
	    return null;
	}
	
	public static void registerBucket(Block b, ItemBucket i)
	{
		buckets.put(b, i);
	}
}
