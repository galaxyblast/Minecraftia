package com.galaxyblast.minecraftia.world.gen.biome;

import java.util.Map;
import java.util.HashMap;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeUtils
{
	private static Map<Integer, Integer> roughnessMap = new HashMap<Integer, Integer>();
	
	/**
	 * How spurratic different biomes are, 0 being totally flat and 255+ being complete chaos
	 * @param BiomeGenBase b
	 * @param Roughness r
	 */
	public static void registerBiomeValues(BiomeGenBase b, int r)
	{
		if(!roughnessMap.containsKey(b.biomeID))
		{
			if(r < 0)
			{
				System.out.println("Warning: roughness values must be > 0!");
				r = 0;
			}
			roughnessMap.put(b.biomeID, r);
		}
		else
			System.out.println("Error: Biome (" + b.toString() + ") already registered!");
	}
	
	public static int getRoughness(BiomeGenBase b)
	{
		if(roughnessMap.get(b.biomeID) != null)
			return roughnessMap.get(b.biomeID);
		else
			return -1;
	}
	
	public static void printRegisteredBiomes()
	{
		for (Map.Entry<Integer, Integer> entry : roughnessMap.entrySet())
		{
		    String key = entry.getKey().toString();
		    Integer value = entry.getValue();
		    System.out.println(key + ", " + value);
		}
	}
}
