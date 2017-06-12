package com.ymcmod.materialwarehouse;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs{
	public static ItemStack icon;
	
	public CreativeTab() {
		super("material_warehouse");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return icon;
	}

	public static boolean isIcon(String s){
		return s.equals("ingotCopper");	//OreDict format
	}
}
