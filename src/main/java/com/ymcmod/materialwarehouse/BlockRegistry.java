package com.ymcmod.materialwarehouse;

import com.ymcmod.materialwarehouse.common.SimpleTextureBlock;
import com.ymcmod.materialwarehouse.common.GenericMetaBlock;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockRegistry {
	/**
	 * Register Blocks
	 */
	public static void preInit(){
		new SimpleTextureBlock.Set("ore", new String[]{"coal", "diamond","gold","iron","redstone"}, Material.ROCK, CreativeTabs.TRANSPORTATION);
		new SimpleTextureBlock.Set("block", new String[]{"coal", "diamond","gold","iron","redstone"}, Material.ROCK, CreativeTabs.TRANSPORTATION);
	}
	
	/**
	 * Register TileEntities
	 */
	public static void init(){
		
	}
}
