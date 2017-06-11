package com.ymcmod.materialwarehouse;

import java.util.HashMap;
import java.util.LinkedList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.ymcmod.materialwarehouse.common.GenericItem;

public class ItemRegistry {
	/*This will register items such as: 
	 * ingot_copper
	 * oreDict name: ingotCopper
	 * texture:ingot_copper.png
	 * language string: item.material_warehouse:ingot_copper.name=Copper Ingot
	*/
	public static String[] prefixes = new String[]{"ingot", "nugget", "dust"};
	public static String[] suffixes = new String[]{"copper", "aluminum", "lead"};
	
	public static HashMap<String, Item> map = new HashMap<String, Item>();
	
	public static void preInit(){
		//registerItems();
	}
	
	public static void registerItems(){
		for (int i=0; i<prefixes.length; i++){
			String prefix = prefixes[i];
			GenericItem item = new GenericItem(prefix, suffixes);
			
			item.setCreativeTab(CreativeTabs.TRANSPORTATION);
			for (int j=0; j<suffixes.length; j++){
				String suffix = suffixes[i];
				//Capitalize the first letter
				suffix = suffix.substring(0, 1).toUpperCase() + suffix.substring(1);
				String name = prefix + suffix;
				OreDictionary.registerOre(name, new ItemStack(item,1,j));
			}
		}
	}
}
