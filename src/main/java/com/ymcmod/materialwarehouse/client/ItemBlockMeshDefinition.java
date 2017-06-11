package com.ymcmod.materialwarehouse.client;

import java.util.LinkedList;

import com.ymcmod.materialwarehouse.common.GenericBlock;
import com.ymcmod.materialwarehouse.common.GenericItem;
import com.ymcmod.materialwarehouse.common.GenericItemBlock;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class is used to register variants for block and items, 
 * item and block classes instantiate this class during pre-init phase,
 * and the registerRenders() method should be called in init phase
 */
@SideOnly(Side.CLIENT)
public class ItemBlockMeshDefinition implements ItemMeshDefinition{
	private boolean hasSubTypes;
	private ModelResourceLocation[] resources;
	
	public ItemBlockMeshDefinition(GenericBlock block){
		GenericItemBlock itemBlock = block.getItemBlock();
		this.hasSubTypes = itemBlock.getHasSubtypes();
		
    	if (hasSubTypes){
    		//The item contains subItems
    		String[] subItemUnlocalizedNames = block.getSubBlockUnlocalizedNames();
    		this.resources = new ModelResourceLocation[subItemUnlocalizedNames.length];
    		
    		//Create ModelResourceLocation for each of them 
    		for (int i = 0; i < subItemUnlocalizedNames.length; i++) {
    			//this.resources[i] = new ModelResourceLocation(
    					//block.getRegistryName() + "_" + subItemUnlocalizedNames[i], "inventory");
    			this.resources[i] = new ModelResourceLocation(block.getRegistryName()+"_inventory", "meta="+String.valueOf(i));
    			//And let MineCraft know the existence of all subItems(Variants)
    			ModelBakery.registerItemVariants(itemBlock, this.resources[i]);
    		}
    	}else{
    		//The item doesn't contain any subItem, use single texture instead
    		this.resources = new ModelResourceLocation[]{new ModelResourceLocation(block.getRegistryName(), "inventory")};
    	}
    	
    	ModelLoader.setCustomMeshDefinition(itemBlock, this);
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		if (this.hasSubTypes)
    		return this.resources[stack.getItemDamage()];
    	else
			return this.resources[0];
	}
}

