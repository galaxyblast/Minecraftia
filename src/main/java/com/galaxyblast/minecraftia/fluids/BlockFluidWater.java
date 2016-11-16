package com.galaxyblast.minecraftia.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidWater extends BlockFluidClassic
{
	public BlockFluidWater(Fluid f)
	{
		super(f, Material.water);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
    public IIcon getIcon(int side, int meta)
    {
    	return Blocks.water.getIcon(side, meta);
    }
}
