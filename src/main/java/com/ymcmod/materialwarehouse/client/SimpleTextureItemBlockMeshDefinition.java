package com.ymcmod.materialwarehouse.client;

import java.util.Iterator;

import com.ymcmod.materialwarehouse.common.GenericItemBlock;
import com.ymcmod.materialwarehouse.common.SimpleTextureBlock;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SimpleTextureItemBlockMeshDefinition implements ItemMeshDefinition{
	private final ModelResourceLocation[] resources;
	
	public static void regsterItemMeshDefinitionForSets(){
		SimpleTextureBlock.initTexturePathArray();
		
		Iterator<SimpleTextureBlock.Set> iterator = SimpleTextureBlock.Set.iterator();
		while(iterator.hasNext()){
			SimpleTextureBlock.Set currentSet = iterator.next();
			Iterator<SimpleTextureBlock> blockIterator = currentSet.blockIterator();
			while(blockIterator.hasNext()){
				SimpleTextureBlock block = blockIterator.next();
				block.updateTexturePaths();
				new SimpleTextureItemBlockMeshDefinition(block);
				ModelLoader.setCustomStateMapper(block, 
						new SimpleTextureBlockStateMapper(block.getTextureIndex()));
			}
		}
	}
	
	private SimpleTextureItemBlockMeshDefinition(SimpleTextureBlock block){
		GenericItemBlock itemBlock = block.getItemBlock();
		String[] subItemUnlocalizedNames = block.getSubBlockUnlocalizedNames();
		this.resources = new ModelResourceLocation[subItemUnlocalizedNames.length];
		
		//Create ModelResourceLocation for each of them 
		for (int i = 0; i < subItemUnlocalizedNames.length; i++) {
			this.resources[i] = new ModelResourceLocation(
					block.getFakeInventoryModelName(i)
					, "inventory");
			
			//And let MineCraft know the existence of all subItems(Variants)
			ModelBakery.registerItemVariants(itemBlock, this.resources[i]);
		}
		
		ModelLoader.setCustomMeshDefinition(itemBlock, this);
	}
	
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		return this.resources[stack.getItemDamage()];
	}
}
