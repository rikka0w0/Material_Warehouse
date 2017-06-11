package com.ymcmod.materialwarehouse.common;

import com.ymcmod.materialwarehouse.MaterialWarehouse;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class SimpleTextureItemBlock extends GenericItemBlock{
	public SimpleTextureItemBlock(Block block, boolean useless) {
		super(block, true);

        if (!(block instanceof SimpleTextureBlock))
        	throw new RuntimeException("SimpleTextureItemBlock should be used with SimpleTextureBlock!");
	}

    @Override
    public final String getUnlocalizedName(ItemStack itemstack) {
    	SimpleTextureBlock block = (SimpleTextureBlock)this.block;
    	return "tile." + block.getBlockName()+ "_" + block.getSubBlockUnlocalizedNames()[itemstack.getItemDamage()];
    }
}
