package com.galaxyblast.minecraftia.entities.player;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.DimensionManager;

public class PlayerData extends WorldSavedData
{
	final static String key = "minecraftia";
	

	public static PlayerData forWorld(World world)
	{
		MapStorage storage = DimensionManager.getWorld(0).perWorldStorage;
		PlayerData result = (PlayerData) storage.loadData(PlayerData.class, key);
		if (result == null)
		{
			result = new PlayerData(key);
			storage.setData(key, result);
		}
		return result;
	}

	private NBTTagCompound data = new NBTTagCompound();

	public PlayerData(String tagName)
	{
		super(tagName);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		this.data = compound.getCompoundTag(key);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		compound.setTag(key, this.data);
	}

	public NBTTagCompound getData()
	{
		return this.data;
	}

}
