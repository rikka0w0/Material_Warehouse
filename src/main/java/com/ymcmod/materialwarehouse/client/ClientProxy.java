package com.ymcmod.materialwarehouse.client;

import com.ymcmod.materialwarehouse.CommonProxy;
import com.ymcmod.materialwarehouse.common.GenericBlock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{
	@Override
	public World getClientWorld(){
		return Minecraft.getMinecraft().world;
	}
	
	@Override
	public IThreadListener getClientThread() {
		return Minecraft.getMinecraft();
	}
	
	@Override
	public void registerRenders() {		
		ModelLoaderRegistry.registerLoader(CustomModelLoader.instance);
		
		SingleTextureMeshDefinition.registerForItemBlocks();
		SingleTextureMeshDefinition.registerForItems();
	}
}
