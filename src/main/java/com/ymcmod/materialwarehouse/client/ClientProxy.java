package com.ymcmod.materialwarehouse.client;

import com.ymcmod.materialwarehouse.BlockRegistry;
import com.ymcmod.materialwarehouse.CommonProxy;
import com.ymcmod.materialwarehouse.MaterialWarehouse;
import com.ymcmod.materialwarehouse.common.SingleTextureBlock;
import com.ymcmod.materialwarehouse.common.SingleTextureItem;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;

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
		CustomModelLoader mdlLoader = new CustomModelLoader(MaterialWarehouse.modID);
		
		for (SingleTextureItem item: SingleTextureItem.registeredSTI) {
			mdlLoader.registerInventoryIcon(item);
		}
		
		SimpleTextureBlockStateMapper stbStateMapper = new SimpleTextureBlockStateMapper(MaterialWarehouse.modID);
		for (SingleTextureBlock.Set set: SingleTextureBlock.Set.registeredSTBSets) {
			for (SingleTextureBlock block: set.blocks) {
				stbStateMapper.registerSingleTextureBlock(block);
			}
		}
		
		MachineBlockStateMapper mbStateMapper = new MachineBlockStateMapper(MaterialWarehouse.modID);
		mbStateMapper.register(BlockRegistry.blockMachine);
	}
}
