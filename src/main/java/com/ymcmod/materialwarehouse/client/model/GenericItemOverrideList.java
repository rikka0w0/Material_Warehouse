package com.ymcmod.materialwarehouse.client.model;

import java.util.List;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GenericItemOverrideList extends ItemOverrideList{
	public GenericItemOverrideList(List<ItemOverride> overridesIn) {
		super(overridesIn);
	}

	@Override
	public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity){
		return new GenericItemFinalizedModel(originalModel, GenericItemModel.getQuadsForItemStack(stack));
	}
}
