package com.ymcmod.materialwarehouse;

import com.ymcmod.materialwarehouse.common.SingleTextureBlock;
import com.ymcmod.materialwarehouse.machine.BlockMachine;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BlockRegistry {
	public static BlockMachine blockMachine;
	
	/**
	 * Register blocks and add them to oreDict
	 */
	public static void preInit(){
		for (int i=0; i<MaterialWarehouse.blockPrefixes.length; i++){
			String prefix = MaterialWarehouse.blockPrefixes[i];
			
			SingleTextureBlock.Set set = new SingleTextureBlock.Set(prefix, MaterialWarehouse.suffixes,
					 Material.ROCK, MaterialWarehouse.creativeTab);
			
			//Register oreDict
			for (int j=0; j<MaterialWarehouse.suffixes.length; j++){
				String suffix = MaterialWarehouse.suffixes[j];
				//Capitalize the first letter
				suffix = suffix.substring(0, 1).toUpperCase() + suffix.substring(1);
				String name = prefix + suffix;
				
				OreDictionary.registerOre(name, 
						new ItemStack(set.getBlock(j), 1, set.getMeta(j)));
			}
		}
		
		blockMachine = new BlockMachine();
	}
	
	/**
	 * Register TileEntities
	 */
	public static void init(){
		
	}
}
