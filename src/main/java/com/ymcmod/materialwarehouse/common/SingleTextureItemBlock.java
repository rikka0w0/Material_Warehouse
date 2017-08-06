package com.ymcmod.materialwarehouse.common;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public final class SingleTextureItemBlock extends GenericItemBlock{
	public SingleTextureItemBlock(Block block) {
		super(block);

        if (!(block instanceof SingleTextureBlock))
        	throw new RuntimeException("SingleTextureItemBlock should be used with SingleTextureBlock!");
	}

    @Override
    public final String getUnlocalizedName(ItemStack itemstack) {
    	SingleTextureBlock block = (SingleTextureBlock)this.field_150939_a;
    	return "tile." + block.getBlockName()+ "_" + block.getSubBlockUnlocalizedNames()[itemstack.getItemDamage()];
    }
}
