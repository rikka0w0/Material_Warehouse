package com.ymcmod.materialwarehouse.client;

import com.ymcmod.materialwarehouse.common.SingleTextureBlock;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rikka.librikka.model.SingleTextureModel;
import rikka.librikka.model.loader.IModelLoader;

@SideOnly(Side.CLIENT)
public class SimpleTextureBlockStateMapper extends StateMapperBase implements IModelLoader {
	public final static String VPATH = "virtual/blockstates/stb";
	public final String domain;
	
	public SimpleTextureBlockStateMapper(String domain){
		this.domain = domain;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		Block block = state.getBlock();
		
		if (block instanceof SingleTextureBlock) {
			int meta = block.getMetaFromState(state);
			String textureName = ((SingleTextureBlock) block).getIconName(meta);
			
			ModelResourceLocation res = new ModelResourceLocation(
					this.domain + ":" + VPATH,
					textureName
					);
			return res;
		}
		return null;
	}

	@Override
	public boolean accepts(String resPath){
		return resPath.startsWith(VPATH);
	}
	
	@Override
	public IModel loadModel(String domain, String resPath, String variantStr) throws Exception {
		return new SingleTextureModel(domain, variantStr, true);	//SimpleTextureItem
	}
	
	public void registerSingleTextureBlock(SingleTextureBlock block) {
		ModelLoader.setCustomStateMapper(block, this);
		
		ItemBlock itemBlock = block.itemBlock;
		for (int meta: block.propertyMeta.getAllowedValues()){
			IBlockState blockState = block.getStateFromMeta(meta);
			ModelResourceLocation res = this.getModelResourceLocation(blockState);
			//Also register inventory variants here
			ModelLoader.setCustomModelResourceLocation(itemBlock, meta, res);
		}
	}
}
