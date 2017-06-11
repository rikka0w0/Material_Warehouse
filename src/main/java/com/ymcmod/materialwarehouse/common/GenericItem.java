package com.ymcmod.materialwarehouse.common;

import java.util.LinkedList;

import com.ymcmod.materialwarehouse.MaterialWarehouse;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GenericItem extends Item{
	public static final LinkedList<GenericItem> registeredItems = new LinkedList();
	
	@SideOnly(Side.CLIENT)
	public Object bakedQuadsCache;
	protected String[] subNames;
	
	public GenericItem(String name) {
		this(name, null);
	}
	
	/**
	 * 
	 * @param name Naming rules: lower case English letters and numbers only, words are separated by '_', e.g. "cooked_beef"
	 * @param hasSubItems
	 */
    public GenericItem(String name, String[] subNames) {
    	this.subNames = subNames;
		this.setUnlocalizedName(name);	//UnlocalizedName = "item." + name
		this.setRegistryName(name);
		this.setHasSubtypes(subNames != null);
		
		if (subNames != null)
			this.setMaxDamage(0);	//The item can not be damaged
		
		GameRegistry.register(this);
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
    public final void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
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
    
    /**
     * Only use for subItems
     * 
     * @return an array of unlocalized names
     */
    public String[] getSubItemUnlocalizedNames(){
    	return subNames;
    }
    
    /**
     * Only valid for subItems
     * 
     * @return an array of texture paths
     */
    @SideOnly(Side.CLIENT)
    public String[] getTexturePaths(){
    	String[] ret = new String[subNames.length];
    	ResourceLocation res = this.getRegistryName();
    	String domain = res.getResourceDomain();
    	String path = res.getResourcePath();
    	for (int i = 0; i<subNames.length; i++){
    		ret[i] = domain + ":items/" + path + "_" + subNames[i];;
    	}
    	return ret;
    }
}
