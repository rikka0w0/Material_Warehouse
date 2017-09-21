package com.ymcmod.materialwarehouse;

import com.ymcmod.materialwarehouse.common.SingleTextureBlock;
import com.ymcmod.materialwarehouse.machine.BlockMachine;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.oredict.OreDictionary;
import rikka.librikka.block.BlockBase;

public class BlockRegistry {
	public static BlockMachine blockMachine;
	
	/**
	 * Register blocks and add them to oreDict
	 */
	public static void initBlocks(){
		for (int i=0; i<MaterialWarehouse.blockPrefixes.length; i++){
			String prefix = MaterialWarehouse.blockPrefixes[i];
			
			new SingleTextureBlock.Set(prefix, MaterialWarehouse.suffixes,
					 Material.ROCK, MaterialWarehouse.creativeTab);
		}
		
		blockMachine = new BlockMachine();
	}
	
	public static void registerOreDict() {
		for (SingleTextureBlock.Set set: SingleTextureBlock.Set.registeredSTBSets){
			//Register oreDict
			for (int j=0; j<MaterialWarehouse.suffixes.length; j++){
				String suffix = MaterialWarehouse.suffixes[j];
				//Capitalize the first letter
				suffix = suffix.substring(0, 1).toUpperCase() + suffix.substring(1);
				String name = set.name + suffix;
				
				OreDictionary.registerOre(name, 
						new ItemStack(set.getBlock(j).itemBlock, 1, set.getMeta(j)));
			}
		}
	}
	
	public static void registerBlocks(IForgeRegistry registry, boolean isItemBlock) {
		for (SingleTextureBlock.Set set: SingleTextureBlock.Set.registeredSTBSets){
			if (isItemBlock) {
				for (SingleTextureBlock stb: set.blocks)
					registry.register(stb.itemBlock);
			} else {
				for (SingleTextureBlock stb: set.blocks)
					registry.register(stb);
			}
			
		}
		
		registerBlocks(registry, isItemBlock,
				blockMachine
				);
	}
	
	/**
	 * Register TileEntities
	 */
	public static void registerTileEntities(){
		
	}
	
    private static void registerBlocks(IForgeRegistry registry, boolean isItemBlock, BlockBase... blocks) {
    	if (isItemBlock) {
        	for (BlockBase block: blocks)
        		registry.register(block.itemBlock);
    	} else {
    		registry.registerAll(blocks);
    	}
    }
	
    private static void registerTile(Class<? extends TileEntity> teClass) {
        String registryName = teClass.getName();
        registryName = registryName.substring(registryName.lastIndexOf(".") + 1);
        registryName = MaterialWarehouse.MODID + ":" + registryName;
        GameRegistry.registerTileEntity(teClass, registryName);
    }
}
