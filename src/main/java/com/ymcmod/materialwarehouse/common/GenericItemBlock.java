package com.ymcmod.materialwarehouse.common;

import com.ymcmod.materialwarehouse.MaterialWarehouse;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GenericItemBlock extends ItemBlock{

	public GenericItemBlock(Block block) {
		this(block, block instanceof ISubBlock);
	}
	
	public GenericItemBlock(Block block, boolean hasSubItems) {
		super(block);
		
		this.setHasSubtypes(hasSubItems);
		
		if (hasSubItems)
			this.setMaxDamage(0);	//The item can not be damaged
	}
	
    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
    	if (this.getHasSubtypes()){
        	String[] subBlockUnlocalizedNames = ((ISubBlock)this.block).getSubBlockUnlocalizedNames();
            return super.getUnlocalizedName() + "." + subBlockUnlocalizedNames[itemstack.getItemDamage()];
    	}
    	else{
    		return super.getUnlocalizedName();
    	}
    }
    
    /**
     * Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is
     * placed as a Block (mostly used with ItemBlocks).
     */
    @Override
    public final int getMetadata(int damage)
    {
    	if (this.getHasSubtypes()){
    		return damage;
    	}else{
    		return 0;
    	}
    }
    
	@Override
	public String getUnlocalizedNameInefficiently(ItemStack stack){
		String prevName = super.getUnlocalizedNameInefficiently(stack);
		return "tile." + MaterialWarehouse.modID + ":" + prevName.substring(5);
	}
}
