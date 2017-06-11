package com.ymcmod.materialwarehouse.client;

import com.ymcmod.materialwarehouse.client.model.GenericItemModel;
import com.ymcmod.materialwarehouse.common.GenericItem;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {
	private boolean onModelBakeEventFirstFired = true;
	
	@SubscribeEvent 
	public void onModelBakeEvent(ModelBakeEvent event) {
		if (onModelBakeEventFirstFired){
			onModelBakeEventFirstFired = false;
			return;
		}
		
		//IBakedModel existingModel =  event.getModelRegistry().getObject(GenericItemMeshDefinition.defaultModel);
		//IBakedModel customModel = new GenericItemModel(existingModel); 
		//event.getModelRegistry().putObject(GenericItemMeshDefinition.defaultModel, customModel); 
	}
	
	/*
	@SubscribeEvent 
	public void stitcherEventPre(TextureStitchEvent.Pre event) {
		for (GenericItem item: GenericItem.registeredItems){
			String[] textures = item.getTexturePaths();
			for (int i=0; i<textures.length; i++){
				ResourceLocation res = new ResourceLocation(textures[i]);
				event.getMap().registerSprite(res);
			}
		}

	}*/
}
