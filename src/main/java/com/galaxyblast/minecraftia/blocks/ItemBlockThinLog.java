package com.galaxyblast.minecraftia.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockThinLog extends ItemBlockWithMetadata
{

	public ItemBlockThinLog(Block b)
	{
		super(b, b);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
	    return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}
}
