package com.ymcmod.materialwarehouse.client;

import java.util.Iterator;

import com.ymcmod.materialwarehouse.MaterialWarehouse;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

/**
 * 
 * IBlockState to ModelResourceLocation mapping
 *
 */
public class SimpleTextureBlockStateMapper extends StateMapperBase{
	private final int textureIndex;
	public SimpleTextureBlockStateMapper(int textureIndex){
		this.textureIndex = textureIndex;
	}
	
	/**
	 * for all possible block states
	 */
	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		int meta = 0;
		Iterator<IProperty<?>> properties = state.getPropertyKeys().iterator();
		while(properties.hasNext()){
			IProperty<?> property = properties.next();
			if (property.getName() == "meta")
				meta = (Integer) state.getValue(property);
		}
		return new ModelResourceLocation(MaterialWarehouse.modID + ":" + CustomModelLoader.STBBlockState,
				"meta=" + String.valueOf(meta) + ",tindex=" + String.valueOf(textureIndex));
	}
}
