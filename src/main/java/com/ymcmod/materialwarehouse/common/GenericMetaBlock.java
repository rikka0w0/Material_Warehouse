package com.ymcmod.materialwarehouse.common;

import java.util.LinkedList;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Must have at least 2 sub blocks, otherwise the game will crash QAQ!!!
 * @author Rikka0_0
 */
public abstract class GenericMetaBlock extends GenericBlock{
	public IProperty<Integer> pIntMeta = null;
	
	protected GenericMetaBlock(String unlocalizedName, String[] subNames, Class<? extends GenericItemBlock> itemBlockClass, Material material) {
		super(unlocalizedName, subNames, itemBlockClass, material);
		
		pIntMeta = (IProperty<Integer>) this.getBlockState().getProperty("meta");
	}
	
	protected abstract int getNumberOfSubBlocksDuringInit();
	
    //Block states
	@Override
	public void registerBlockState(LinkedList<IProperty> properties){
		int metaMax = getNumberOfSubBlocksDuringInit() - 1;
		
		if (metaMax < 1)
			return;
		
		pIntMeta = PropertyInteger.create("meta", 0 , metaMax);
		properties.add(pIntMeta);
	}
	
	@Override
	public IBlockState setDefaultBlockState(IBlockState baseState){
		if (pIntMeta == null)
			return super.setDefaultBlockState(baseState);
		
		return super.setDefaultBlockState(baseState)
				.withProperty(pIntMeta, 0);	
	}
	
	@Override
    public final IBlockState getStateFromMeta(int meta){
		if (pIntMeta == null)
        	return super.getDefaultState();
		
        return super.getDefaultState().withProperty(pIntMeta, meta & 15);
    }
	
	@Override
    public final int getMetaFromState(IBlockState state){
		if (pIntMeta == null)
			return 0;

		int meta = state.getValue(pIntMeta);
		meta = meta & 15;
		return meta;
    }
	
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
		if (pIntMeta == null)
			return state;
		
		int meta = this.getMetaFromState(state);
		return state.withProperty(pIntMeta, meta);
    }
}
