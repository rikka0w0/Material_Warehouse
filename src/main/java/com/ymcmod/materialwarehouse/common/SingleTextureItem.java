package com.ymcmod.materialwarehouse.common;

import java.util.Iterator;
import java.util.LinkedList;

import com.ymcmod.materialwarehouse.MaterialWarehouse;
import com.ymcmod.materialwarehouse.client.CustomModelLoader;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class SingleTextureItem extends GenericItem{	
	private static final LinkedList<SingleTextureItem> registeredSTI = new LinkedList();
	/**
	 * Total number of registered STI
	 */
	private static int count = 0;
	
	private final String itemName;
	private final int index;
	
	public SingleTextureItem(String name, String[] subNames) {
		super(name, subNames);
		
		this.itemName = name;
		this.index = count;
		this.count++;
		registeredSTI.add(this);
	}
	
	public static Iterator<SingleTextureItem> iterator(){
		return registeredSTI.iterator();
	}
	
	@SideOnly(Side.CLIENT)
	public String getFakeInventoryModelName(int i){
		return MaterialWarehouse.modID + ":" + CustomModelLoader.invSTI + "_" + String.valueOf(this.index) + "_" + String.valueOf(i);
	}
	
	@SideOnly(Side.CLIENT)
	public static String[][] texturePaths;
	
	@SideOnly(Side.CLIENT)
	public void updateTexturePaths(){
		texturePaths[index] = new String[subNames.length];
		for (int i=0; i<subNames.length; i++)
			texturePaths[index][i] = this.itemName + "_" + this.subNames[i];
	}
	
	@SideOnly(Side.CLIENT)
	public static void initTexturePathArray(){
		texturePaths = new String[count][];
	}
}
