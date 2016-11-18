package com.galaxyblast.minecraftia.world.gen;

import java.util.Random;

import com.galaxyblast.minecraftia.blocks.MinecraftiaBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGen
{
	public static void genBiomeBlocks(World world, Random rng, Block[] blockArray, byte[] p_150560_4_, int p_150560_5_, int p_150560_6_, double p_150560_7_, BiomeGenBase b)
	{
        boolean flag = true;
        Block block = b.topBlock;
        byte b0 = (byte)(b.field_150604_aj & 255);
        Block block1 = b.fillerBlock;
        int k = -1;
        int l = (int)(p_150560_7_ / 3.0D + 3.0D + rng.nextDouble() * 0.25D);
        int i1 = p_150560_5_ & 15;
        int j1 = p_150560_6_ & 15;
        int k1 = blockArray.length / 256;

        for (int l1 = 255; l1 >= 0; --l1)
        {
            int i2 = (j1 * 16 + i1) * k1 + l1;

            if (l1 <= 0 + rng.nextInt(5))
            {
                blockArray[i2] = Blocks.bedrock;
            }
            else
            {
                Block block2 = blockArray[i2];

                if (block2 != null && block2.getMaterial() != Material.air)
                {
                    if (block2 == Blocks.stone)
                    {
                        if (k == -1)
                        {
                            if (l <= 0)
                            {
                                block = null;
                                b0 = 0;
                                block1 = Blocks.stone;
                            }
                            else if (l1 >= 59 && l1 <= 64)
                            {
                                block = b.topBlock;
                                b0 = (byte)(b.field_150604_aj & 255);
                                block1 = b.fillerBlock;
                            }

                            if (l1 < 63 && (block == null || block.getMaterial() == Material.air))
                            {
                                if (b.getFloatTemperature(p_150560_5_, l1, p_150560_6_) < 0.15F)
                                {
                                    block = Blocks.ice;
                                    b0 = 0;
                                }
                                else
                                {
                                    block = MinecraftiaBlocks.water;
                                    b0 = 0;
                                }
                            }

                            k = l;

                            if (l1 >= 62)
                            {
                                blockArray[i2] = block;
                                p_150560_4_[i2] = b0;
                            }
                            else if (l1 < 56 - l)
                            {
                                block = null;
                                block1 = Blocks.stone;
                                blockArray[i2] = Blocks.gravel;
                            }
                            else
                            {
                                blockArray[i2] = block1;
                            }
                        }
                        else if (k > 0)
                        {
                            --k;
                            blockArray[i2] = block1;

                            if (k == 0 && block1 == Blocks.sand)
                            {
                                k = rng.nextInt(4) + Math.max(0, l1 - 63);
                                block1 = Blocks.sandstone;
                            }
                        }
                    }
                }
                else
                {
                    k = -1;
                }
            }
        }
	}
}
