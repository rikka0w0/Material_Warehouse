package com.ymcmod.materialwarehouse.common;

import com.ymcmod.materialwarehouse.MaterialWarehouse;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GenericItemBlock extends ItemBlock{
	public GenericItemBlock(Block block, boolean hasSubBlocks) {
        super(block);
        
        if (!(block instanceof GenericBlock))
        	throw new RuntimeException("GenericItemBlock should be used with GenericBlock!");
        
        setHasSubtypes(hasSubBlocks);
        
		if (hasSubBlocks)
			this.setMaxDamage(0);	//The item can not be damaged
	}

	
    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
    	if (this.getHasSubtypes()){
    		GenericBlock block = (GenericBlock)this.block;
        	String[] subBlockUnlocalizedNames = block.getSubBlockUnlocalizedNames();
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
