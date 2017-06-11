package com.ymcmod.materialwarehouse.client;

import com.ymcmod.materialwarehouse.common.GenericItem;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GenericItemMeshDefinition implements ItemMeshDefinition{
	public static ModelResourceLocation defaultModel = new ModelResourceLocation("material_warehouse:generic", "inventory");
	private ModelResourceLocation modelLocation;
	
	public GenericItemMeshDefinition(GenericItem item){		
    	if (item.getHasSubtypes()){
    		String[] subItemUnlocalizedNames = item.getSubItemUnlocalizedNames();
    		for (int i = 0; i < subItemUnlocalizedNames.length; i++) 
    			ModelBakery.registerItemVariants(item, defaultModel);
    		
    		this.modelLocation = null;
		}else{
			//The item doesn't contain any subItem, use single texture instead
			this.modelLocation = new ModelResourceLocation(item.getRegistryName(), "inventory");
		}
	
    	ModelLoader.setCustomMeshDefinition(item, this);
	}
	
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		return this.modelLocation == null ? defaultModel : modelLocation;
	}

}
