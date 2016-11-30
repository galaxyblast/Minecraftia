package com.galaxyblast.minecraftia.fluids;

import java.util.Random;

import com.galaxyblast.minecraftia.Minecraftia;
import com.galaxyblast.minecraftia.blocks.MinecraftiaBlocks;
import com.galaxyblast.minecraftia.engine.tasks.TaskFluid;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
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
	
	@Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        Minecraftia.instance.getPhysicsThread().queueTask(new TaskFluid(this, world, x, y, z, rand));
    }
	
	//Vanilla update
	public synchronized void runUpdate(World world, int x, int y, int z, Random rand)
	{
		if(!world.isRemote && world.checkChunksExist(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1))
		{
			int quantaRemaining = quantaPerBlock - world.getBlockMetadata(x, y, z);
	        int expQuanta = -101;
	
	        // check adjacent block levels if non-source
	        if (quantaRemaining < quantaPerBlock)
	        {
	            int y2 = y - densityDir;
	
	            if (world.getBlock(x,     y2, z    ) == this ||
	                world.getBlock(x - 1, y2, z    ) == this ||
	                world.getBlock(x + 1, y2, z    ) == this ||
	                world.getBlock(x,     y2, z - 1) == this ||
	                world.getBlock(x,     y2, z + 1) == this)
	            {
	                expQuanta = quantaPerBlock - 1;
	            }
	            else
	            {
	                int maxQuanta = -100;
	                maxQuanta = getLargerQuanta(world, x - 1, y, z,     maxQuanta);
	                maxQuanta = getLargerQuanta(world, x + 1, y, z,     maxQuanta);
	                maxQuanta = getLargerQuanta(world, x,     y, z - 1, maxQuanta);
	                maxQuanta = getLargerQuanta(world, x,     y, z + 1, maxQuanta);
	
	                expQuanta = maxQuanta - 1;
	            }
	
	            // decay calculation
	            if (expQuanta != quantaRemaining)
	            {
	                quantaRemaining = expQuanta;
	
	                if (expQuanta <= 0)
	                {
	                    world.setBlock(x, y, z, Blocks.air, 0, 3);
	                }
	                else
	                {
	                    world.setBlockMetadataWithNotify(x, y, z, quantaPerBlock - expQuanta, 3);
	                    world.scheduleBlockUpdate(x, y, z, this, tickRate);
	                    world.notifyBlocksOfNeighborChange(x, y, z, this);
	                }
	            }
	        }
	        // This is a "source" block, set meta to zero, and send a server only update
	        else if (quantaRemaining >= quantaPerBlock)
	        {
	            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
	        }
	
	        // Flow vertically if possible
	        if (canDisplace(world, x, y + densityDir, z))
	        {
	            flowIntoBlock(world, x, y + densityDir, z, 1);
	            return;
	        }
	
	        // Flow outward if possible
	        int flowMeta = quantaPerBlock - quantaRemaining + 1;
	        if (flowMeta >= quantaPerBlock)
	        {
	            return;
	        }
	
	        if (isSourceBlock(world, x, y, z) || !isFlowingVertically(world, x, y, z))
	        {
	            if (world.getBlock(x, y - densityDir, z) == this)
	            {
	                flowMeta = 1;
	            }
	            boolean flowTo[] = getOptimalFlowDirections(world, x, y, z);
	
	            if (flowTo[0]) flowIntoBlock(world, x - 1, y, z,     flowMeta);
	            if (flowTo[1]) flowIntoBlock(world, x + 1, y, z,     flowMeta);
	            if (flowTo[2]) flowIntoBlock(world, x,     y, z - 1, flowMeta);
	            if (flowTo[3]) flowIntoBlock(world, x,     y, z + 1, flowMeta);
	        }
		}
	}
	
	//Finite water, EXTREMELY BUGGY
	public void runUpdateExperimental(World world, int x, int y, int z, Random rand)
	{
		int quantaRemaining = quantaPerBlock - world.getBlockMetadata(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		boolean shouldUpdate = true;
		
		//If surrounded by water of equal amounts, don't update
		if(world.getBlock(x - 1, y, z) == MinecraftiaBlocks.water && world.getBlockMetadata(x - 1, y, z) == meta &&
				world.getBlock(x, y, z - 1) == MinecraftiaBlocks.water && world.getBlockMetadata(x, y, z - 1) == meta &&
				world.getBlock(x + 1, y, z) == MinecraftiaBlocks.water && world.getBlockMetadata(x + 1, y, z) == meta &&
				world.getBlock(x, y, z + 1) == MinecraftiaBlocks.water && world.getBlockMetadata(x, y, z + 1) == meta &&
				world.getBlock(x, y - 1, z) != Blocks.air)
		{
			shouldUpdate = false;
		}
		
		if(shouldUpdate)
		{
			//Try to flow vertically first
			if(world.getBlock(x, y - 1, z) == Blocks.air || world.getBlock(x, y - 1, z).getMaterial() == Material.plants || world.getBlock(x, y - 1, z).getMaterial() == Material.fire
					 || world.getBlock(x, y - 1, z).isBlockSolid(world, x, y, z, 0) && world.getBlock(x, y - 1, z) != MinecraftiaBlocks.water)
			{
				world.setBlock(x, y - 1, z, this);
				world.setBlockMetadataWithNotify(x, y - 1, z, meta, 2);
				world.setBlockToAir(x, y, z);
			}
			else if(world.getBlock(x, y - 1, z) == MinecraftiaBlocks.water)
			{
				int targetMeta = world.getBlockMetadata(x, y - 1, z);
				int myMeta = world.getBlockMetadata(x, y, z);
				
				while(targetMeta > 0 && myMeta < 7)
				{
					world.setBlockMetadataWithNotify(x, y - 1, z, targetMeta - 1, 2);
					targetMeta++;
					world.setBlockMetadataWithNotify(x, y, z, myMeta + 1, 2);
					myMeta--;
				}
				if(myMeta >= 8)
					world.setBlockToAir(x, y, z);
			}
			
			//Try to flow outward
			if(world.getBlock(x - 1, y, z) == Blocks.air && meta < 7)
			{
				world.setBlock(x - 1, y, z, this, 7, 3);
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				meta++;
			}
			if(world.getBlock(x, y, z - 1) == Blocks.air && meta < 7)
			{
				world.setBlock(x, y, z - 1, this, 7, 3);
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				meta++;
			}
			if(world.getBlock(x + 1, y, z) == Blocks.air && meta < 7)
			{
				world.setBlock(x + 1, y, z, this, 7, 3);
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				meta++;
			}
			if(world.getBlock(x, y, z + 1) == Blocks.air && meta < 7)
			{
				world.setBlock(x, y, z + 1, this, 7, 3);
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				meta++;
			}
			
			//equalize
			int tmpMeta = world.getBlockMetadata(x - 1, y, z);
			if(world.getBlock(x - 1, y, z) == MinecraftiaBlocks.water && meta < 7 && tmpMeta > meta)
			{
				world.setBlockMetadataWithNotify(x - 1, y, z, tmpMeta - 1, 3);
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				meta++;
			}
			tmpMeta = world.getBlockMetadata(x, y, z - 1);
			if(world.getBlock(x, y, z - 1) == MinecraftiaBlocks.water && meta < 7 && tmpMeta > meta)
			{
				world.setBlockMetadataWithNotify(x, y, z - 1, tmpMeta - 1, 3);
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				meta++;
			}
			tmpMeta = world.getBlockMetadata(x + 1, y, z);
			if(world.getBlock(x + 1, y, z) == MinecraftiaBlocks.water && meta < 7 && tmpMeta > meta)
			{
				world.setBlockMetadataWithNotify(x + 1, y, z, tmpMeta - 1, 3);
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				meta++;
			}
			tmpMeta = world.getBlockMetadata(x, y, z + 1);
			if(world.getBlock(x, y, z + 1) == MinecraftiaBlocks.water && meta < 7 && tmpMeta > meta)
			{
				world.setBlockMetadataWithNotify(x, y, z + 1, tmpMeta - 1, 3);
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				meta++;
			}
		}
	}
}
