package com.ymcmod.materialwarehouse;

import com.ymcmod.materialwarehouse.client.MachineBlockStateMapper;
import com.ymcmod.materialwarehouse.client.SimpleTextureBlockStateMapper;
import com.ymcmod.materialwarehouse.common.SingleTextureBlock;
import com.ymcmod.materialwarehouse.common.SingleTextureItem;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import rikka.librikka.model.loader.AdvancedModelLoader;

@Mod.EventBusSubscriber(modid = MaterialWarehouse.MODID, value = Side.CLIENT)
public class ClientRegistrationHandler {
	@SubscribeEvent
	public static void registerModel(ModelRegistryEvent event) {
		AdvancedModelLoader mdlLoader = new AdvancedModelLoader(MaterialWarehouse.MODID);
		
		for (SingleTextureItem item: SingleTextureItem.registeredSTI) {
			mdlLoader.registerInventoryIcon(item);
		}
		
		SimpleTextureBlockStateMapper stbStateMapper = new SimpleTextureBlockStateMapper(MaterialWarehouse.MODID);
		mdlLoader.registerModelLoader(stbStateMapper);
		for (SingleTextureBlock.Set set: SingleTextureBlock.Set.registeredSTBSets) {
			for (SingleTextureBlock block: set.blocks) {
				stbStateMapper.registerSingleTextureBlock(block);
			}
		}
		
		MachineBlockStateMapper mbStateMapper = new MachineBlockStateMapper(MaterialWarehouse.MODID);
		mdlLoader.registerModelLoader(mbStateMapper);
		mbStateMapper.register(BlockRegistry.blockMachine);
	}
}
