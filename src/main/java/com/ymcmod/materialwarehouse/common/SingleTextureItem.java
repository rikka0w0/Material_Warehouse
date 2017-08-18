package com.ymcmod.materialwarehouse.common;

import java.util.LinkedList;

import com.ymcmod.materialwarehouse.client.ISESimpleTextureItem;

public final class SingleTextureItem extends GenericItem implements ISESimpleTextureItem{	
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

	@Override
	public void beforeRegister() {

	}
	
    public String[] getSubItemUnlocalizedNames(){
    	return subNames;
    }

	@Override
	public String getIconName(int damage) {
		return itemName + "_" + subNames[damage];
	}
}
