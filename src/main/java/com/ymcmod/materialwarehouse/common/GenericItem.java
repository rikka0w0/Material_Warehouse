package com.ymcmod.materialwarehouse.common;

import java.util.LinkedList;
import java.util.List;

import com.ymcmod.materialwarehouse.MaterialWarehouse;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class GenericItem extends Item{
	@Deprecated
	protected String registryName;	//1.11.2 compatibility
	
	public static final LinkedList<GenericItem> registeredItems = new LinkedList();
	
	/**
	 * 
	 * @param name Naming rules: lower case English letters and numbers only, words are separated by '_', e.g. "cooked_beef"
	 * @param hasSubItems
	 */
    public GenericItem(String name, boolean hasSubItems) {
		this.setUnlocalizedName(name);	//UnlocalizedName = "item." + name
		//this.setRegistryName(name);
    	this.registryName = name;
		this.setHasSubtypes(hasSubItems);
		
		if (hasSubItems)
			this.setMaxDamage(0);	//The item can not be damaged
		
		this.beforeRegister();
		
		GameRegistry.registerItem(this, name);
		registeredItems.add(this);
    }
    
    @Override
    public final String getUnlocalizedName(ItemStack itemstack) {
    	if (this.getHasSubtypes()){
            return super.getUnlocalizedName() + "_" + getSubItemUnlocalizedNames()[itemstack.getItemDamage()];
    	}
    	else{
    		return super.getUnlocalizedName();
    	}
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public final void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
    {
    	if (this.getHasSubtypes()){
            for (int ix = 0; ix < getSubItemUnlocalizedNames().length; ix++) 
                subItems.add(new ItemStack(this, 1, ix));
    	}else{
    		subItems.add(new ItemStack(itemIn));
    	}
    }
    
	@Override
	public final String getUnlocalizedNameInefficiently(ItemStack stack){
		String prevName = super.getUnlocalizedNameInefficiently(stack);
		return "item." + MaterialWarehouse.modID + ":" + prevName.substring(5);
	}
    
	public abstract void beforeRegister();
	
    /**
     * Only use for subItems
     * 
     * @return an array of unlocalized names
     */
    public String[] getSubItemUnlocalizedNames(){
    	return null;
    }
}
