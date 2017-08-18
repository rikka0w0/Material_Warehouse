package com.ymcmod.materialwarehouse.client;

import com.ymcmod.materialwarehouse.machine.BlockMachine;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MachineBlockStateMapper extends StateMapperBase{
	public final static String VPATH = "virtual/blockstates/mb";
	public final String domain;
	
	public MachineBlockStateMapper(String domain){
		this.domain = domain;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		Block block = state.getBlock();
		
		if (block instanceof BlockMachine){
			BlockMachine machine = (BlockMachine) block;
			
			String modelName = machine.getIconName(state.getValue(BlockMachine.propertyType));
			boolean has2State = true;
			
			String varStr = (has2State ? "2" : "1") + modelName;
			
			ModelResourceLocation res = new ModelResourceLocation(
					this.domain + ":" + VPATH,
					varStr
					);
			return res;
		}
		
		return null;
	}

	public static boolean accepts(String resPath){
		return resPath.startsWith(VPATH);
	}
	
	public static IModel loadModel(String domain, String resPath, String variantStr) throws Exception {
		boolean has2State = variantStr.startsWith("2");
		variantStr = variantStr.substring(1);
		IModel model = new MachineRawModel(domain, variantStr, has2State);
		return model;
	}
	
	public void register(BlockMachine block){
		ModelLoader.setCustomStateMapper(block, this);
		
		ItemBlock itemBlock = block.itemBlock;
		for (int meta: block.propertyType.getAllowedValues()){
			IBlockState blockState = block.getStateFromMeta(meta);
			ModelResourceLocation res = this.getModelResourceLocation(blockState);
			//Also register inventory variants here
			ModelLoader.setCustomModelResourceLocation(itemBlock, meta, res);
		}
	}
}
