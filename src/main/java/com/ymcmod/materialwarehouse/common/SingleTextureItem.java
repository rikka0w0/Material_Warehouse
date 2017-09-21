package com.ymcmod.materialwarehouse.common;

import java.util.LinkedList;

import rikka.librikka.item.ISimpleTexture;
import rikka.librikka.item.ItemBase;

public final class SingleTextureItem extends ItemBase implements ISimpleTexture{	
	public static final LinkedList<SingleTextureItem> registeredSTI = new LinkedList();
	/**
	 * Total number of registered STI
	 */
	private static int count = 0;
	
	private final String itemName;
	private final int index;
	
	private final String[] subNames;
	
	public SingleTextureItem(String name, String[] subNames) {
		super(name, true);
		
		this.itemName = name;
		this.subNames = subNames;
		this.index = count;
		this.count++;
		registeredSTI.add(this);
	}
	
    public String[] getSubItemUnlocalizedNames(){
    	return subNames;
    }

	@Override
	public String getIconName(int damage) {
		return itemName + "_" + subNames[damage];
	}
	
	public String getOreDictName(int damage) {
		String suffix = subNames[damage];
		suffix = suffix.substring(0, 1).toUpperCase() + suffix.substring(1);
		return itemName.toLowerCase() + suffix;
	}
}
