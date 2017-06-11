package com.ymcmod.materialwarehouse.client;

import com.ymcmod.materialwarehouse.MaterialWarehouse;
import com.ymcmod.materialwarehouse.client.model.SingleTextureModel;
import com.ymcmod.materialwarehouse.common.SingleTextureBlock;
import com.ymcmod.materialwarehouse.common.SingleTextureItem;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class CustomModelLoader implements ICustomModelLoader{	
	public static final String STBBlockState = "stb";
	public static final String invSTBBlockState = "fakeinv_stb";
	public static final String invSTI = "fakeinv_sti";
	public static final CustomModelLoader instance = new CustomModelLoader();
	
	
	private CustomModelLoader(){}

	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		// TODO Handles nothing?
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {		
		if (!modelLocation.getResourceDomain().equals(MaterialWarehouse.modID))
			return false;
		
		String resPath = modelLocation.getResourcePath();
		if (modelLocation.getResourcePath().equals(STBBlockState)){
			ModelResourceLocation loc = (ModelResourceLocation)modelLocation;
			String[] vars = loc.getVariant().split(",");
			return vars.length == 2;
		}else{
			if (resPath.startsWith("models/item/"))
				resPath = resPath.substring(12);
			
			if (resPath.startsWith(invSTBBlockState))
				return true;
			
			if (resPath.startsWith(invSTI))
				return true;
		}
			

		return false;
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		String resPath = modelLocation.getResourcePath();
		if (resPath.equals(STBBlockState)){
			ModelResourceLocation loc = (ModelResourceLocation)modelLocation;
			String[] vars = loc.getVariant().split(",");
			
			int meta = Integer.parseInt(vars[0].substring(5));
			int tindex= Integer.parseInt(vars[1].substring(7));
			
			return new SingleTextureModel(SingleTextureBlock.texturePaths[tindex][meta], true);
		}else{
			if (resPath.startsWith("models/item/"))
				resPath = resPath.substring(12);
			
			if (resPath.startsWith(invSTBBlockState)){
				resPath = resPath.substring(invSTBBlockState.length()+1);
				String[] vars = resPath.split("_");
				int tindex = Integer.parseInt(vars[0]);
				int meta = Integer.parseInt(vars[1]);
				return new SingleTextureModel(SingleTextureBlock.texturePaths[tindex][meta], true);
			}
			
			if (resPath.startsWith(invSTI)){
				resPath = resPath.substring(invSTI.length()+1);
				String[] vars = resPath.split("_");
				int index = Integer.parseInt(vars[0]);
				int meta = Integer.parseInt(vars[1]);
				return new SingleTextureModel(SingleTextureItem.texturePaths[index][meta], false);
			}
		}
		
		return null;	//This should not happen!
	}

}
