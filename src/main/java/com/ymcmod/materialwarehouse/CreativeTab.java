package com.ymcmod.materialwarehouse;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs{
	public static ItemStack icon;
	
	public CreativeTab() {
		super("material_warehouse");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() {
		return icon;
	}

	public static boolean isIcon(String s){
		return s.equals("ingotCopper");	//OreDict format
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return icon.getItem();
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public int func_151243_f()
    {
        return icon.getItemDamage();
    }
}
