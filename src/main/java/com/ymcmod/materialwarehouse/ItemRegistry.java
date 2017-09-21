package com.ymcmod.materialwarehouse;


import com.ymcmod.materialwarehouse.common.SingleTextureItem;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.oredict.OreDictionary;


public class ItemRegistry {	
	/**
	 * Register items and add them to oreDict
	 */
	public static void initItems(){
		for (int i=0; i<MaterialWarehouse.itemPrefixes.length; i++){
			String prefix = MaterialWarehouse.itemPrefixes[i];
			SingleTextureItem item = new SingleTextureItem(prefix, MaterialWarehouse.suffixes);
			
			item.setCreativeTab(MaterialWarehouse.creativeTab);
		}
	}
	
	public static void registerOreDict() {
		for (SingleTextureItem sti: SingleTextureItem.registeredSTI) {
			for (int i=0; i<sti.getSubItemUnlocalizedNames().length; i++) {
				String name = sti.getOreDictName(i);
				ItemStack stack = new ItemStack(sti, 1, i);
				OreDictionary.registerOre(name, stack);
				
				if (CreativeTab.isIcon(name))
					CreativeTab.icon = stack;
			}
			
		}
	}
	
	public static void registerItems(IForgeRegistry registry) {
		for (SingleTextureItem sti: SingleTextureItem.registeredSTI)
			registry.register(sti);
		
		
	}
}
