package com.ymcmod.materialwarehouse;


import com.ymcmod.materialwarehouse.common.SingleTextureItem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;


public class ItemRegistry {	
	/**
	 * Register items and add them to oreDict
	 */
	public static void preInit(){
		for (int i=0; i<MaterialWarehouse.itemPrefixes.length; i++){
			String prefix = MaterialWarehouse.itemPrefixes[i];
			SingleTextureItem item = new SingleTextureItem(prefix, MaterialWarehouse.suffixes);
			
			item.setCreativeTab(CreativeTabs.TRANSPORTATION);
			
			//Register oreDict
			for (int j=0; j<MaterialWarehouse.suffixes.length; j++){
				String suffix = MaterialWarehouse.suffixes[j];
				//Capitalize the first letter
				suffix = suffix.substring(0, 1).toUpperCase() + suffix.substring(1);
				String name = prefix + suffix;
				OreDictionary.registerOre(name, new ItemStack(item,1,j));
			}
		}
	}
}
