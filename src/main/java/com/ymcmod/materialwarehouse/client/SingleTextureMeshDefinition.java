package com.ymcmod.materialwarehouse.client;

import java.util.Iterator;

import com.ymcmod.materialwarehouse.common.GenericItemBlock;
import com.ymcmod.materialwarehouse.common.SingleTextureBlock;
import com.ymcmod.materialwarehouse.common.SingleTextureItem;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SingleTextureMeshDefinition implements ItemMeshDefinition{	
	private final ModelResourceLocation[] resources;
	
	private SingleTextureMeshDefinition(SingleTextureBlock block){
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
	
	public static void registerForItemBlocks(){
		SingleTextureBlock.initTexturePathArray();
		
		Iterator<SingleTextureBlock.Set> iterator = SingleTextureBlock.Set.iterator();
		while(iterator.hasNext()){
			SingleTextureBlock.Set currentSet = iterator.next();
			Iterator<SingleTextureBlock> blockIterator = currentSet.blockIterator();
			while(blockIterator.hasNext()){
				SingleTextureBlock block = blockIterator.next();
				block.updateTexturePaths();
				new SingleTextureMeshDefinition(block);
				ModelLoader.setCustomStateMapper(block, 
						new SimpleTextureBlockStateMapper(block.getTextureIndex()));
			}
		}
	}
	
	public static void registerForItems(){
		SingleTextureItem.initTexturePathArray();
		
		Iterator<SingleTextureItem> iterator = SingleTextureItem.iterator();
		while(iterator.hasNext()){
			SingleTextureItem item = iterator.next();
			item.updateTexturePaths();
			//new SimpleTextureItemMeshDefinition(item);
			for (int i = 0; i < item.getSubItemUnlocalizedNames().length; i++)
				ModelLoader.setCustomModelResourceLocation(item, i, 
						new ModelResourceLocation(item.getFakeInventoryModelName(i), "inventory"));
		}
	}
}
