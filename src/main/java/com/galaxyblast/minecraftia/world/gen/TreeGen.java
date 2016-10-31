package com.galaxyblast.minecraftia.world.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.galaxyblast.minecraftia.engine.BiomeDecoThread;
import com.galaxyblast.minecraftia.engine.tasks.TaskGenTree;
import com.galaxyblast.minecraftia.world.gen.tree.*;

public class TreeGen extends WorldGenerator
{
	private WeightedRandom treeList;
	private BiomeDecoThread decoThread = new BiomeDecoThread();
	
	public TreeGen(Random rng, BiomeGenBase b)
	{
		this.treeList = new WeightedRandom(rng);
		
		TreeTypeBush bush = new TreeTypeBush();
		this.treeList.add(bush, bush.getWeight());
		TreeTypeSmall small = new TreeTypeSmall();
		this.treeList.add(small, small.getWeight());
		TreeTypeMed med = new TreeTypeMed();
		this.treeList.add(med, med.getWeight());
		TreeTypeFell fell = new TreeTypeFell();
		this.treeList.add(fell, fell.getWeight());
		TreeTypeTiny tiny = new TreeTypeTiny();
		this.treeList.add(tiny, tiny.getWeight());
	}
	
	public boolean generate(World world, Random rng, int x, int y, int z)
	{
		if(world.checkChunksExist(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1))
		{
			if(world.getBlock(x, y, z) == Blocks.grass)
			{
				TreeType tree = this.treeList.get();
				
				if(tree != null)
					decoThread.queueTask(new TaskGenTree(tree, world, rng, x, y, z));
				//return tree.generate(world, rng, x, y + 1, z);
			}
			else if(world.getBlock(x, y - 1, z) == Blocks.grass)
			{
				TreeType tree = this.treeList.get();
				
				if(tree != null)
					decoThread.queueTask(new TaskGenTree(tree, world, rng, x, y, z));
				//return tree.generate(world, rng, x, y, z);
			}
			return true;
		}
		return false;
	}
	
	private static class WeightedRandom
	{
		private int totalWeight;
		List<TreeType> tr = new ArrayList<TreeType>();
		private Random rng;
		
		public WeightedRandom(Random rng)
		{
			this.totalWeight = 0;
			this.rng = rng;
		}
		
		private void add(TreeType t, int w)
		{
			this.tr.add(t);
			this.totalWeight += w;
		}
		
		private TreeType get()
		{
			int r = this.rng.nextInt(totalWeight);
			for(int i = 0; i < tr.size(); i++)
			{
				if(r < tr.get(i).getWeight())
					return tr.get(i);
				
				//System.out.print(tr.get(i).getWeight() + " | " + r + "\n");
				
				r -= tr.get(i).getWeight();
			}
			return null;
		}
	}
}
