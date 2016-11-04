package com.galaxyblast.minecraftia.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockThinLog extends Block
{

	protected BlockThinLog(String un, Material m)
	{
		super(m);
		this.setBlockName(un);
		this.setBlockBounds(0.35F, 0.0F, 0.35F, 0.7F, 1.0F, 0.7F);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public IIcon getIcon(int side, int meta)
    {
    	return Blocks.log.getIcon(2, meta);
    }
    
    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }
    
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < 3; i ++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
