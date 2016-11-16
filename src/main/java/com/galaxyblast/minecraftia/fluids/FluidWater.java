package com.galaxyblast.minecraftia.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidWater extends Fluid
{

	public FluidWater(String fluidName)
	{
		super(fluidName);
		this.setDensity(FluidRegistry.WATER.getDensity());
		this.setViscosity(FluidRegistry.WATER.getViscosity());
	}

}
