package com.ymcmod.materialwarehouse.common;

import java.util.LinkedList;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rikka.librikka.block.MetaBlock;
import rikka.librikka.item.ISimpleTexture;
import rikka.librikka.item.ItemBlockBase;

public class SingleTextureBlock extends MetaBlock implements ISimpleTexture{
	public static final class Set{
		public static final LinkedList<SingleTextureBlock.Set> registeredSTBSets = new LinkedList();
		
		public final String name;	//ore, block, ...
		public final LinkedList<SingleTextureBlock> blocks;
		public final CreativeTabs tab;
		
		public Set(String name, String[] subNames, Material material, CreativeTabs tab){
			this.name = name;
			this.tab = tab;
			this.blocks = new LinkedList();
			
			int maxBlockIndex = (subNames.length - 1) >> 4;
			for (int blockIndex = 0; blockIndex <= maxBlockIndex; blockIndex++){
				int sectionLength = 16;
				if (blockIndex == maxBlockIndex)
					sectionLength = subNames.length - maxBlockIndex*16;	//Last block
					
				String[] subNameSection = new String[sectionLength];
				for (int i=0; i<sectionLength; i++)
					subNameSection[i] = subNames[blockIndex*16 + i];
				
				//block state json = name_blockIndex
				SingleTextureBlock stb= 
						SingleTextureBlock.create(this, name, blockIndex, subNameSection, material);
				blocks.add(stb);
			}
			
			registeredSTBSets.add(this);
		}
		
		public SingleTextureBlock getBlock(int i){
			int counter = 0;
			SingleTextureBlock ret = null;
			
			for (SingleTextureBlock block: blocks){
				ret = block;
				if (counter >= i>>4)
					break;
			}
			
			return ret;
		}
		
		/**
		 * Meta < 16, max 15 (0x0F)
		 */
		public int getMeta(int i){
			return i&15;
		}
	}
	
	private static SingleTextureBlock create(SingleTextureBlock.Set parent, String name, int blockIndex, String[] subNames, Material material){
		subNamesLengthCache = subNames.length;
		SingleTextureBlock instance = new SingleTextureBlock(parent, name, blockIndex, subNames, material);
		return instance;
	}
	
	/**
	 * Used for initialization only!
	 */
	private static int subNamesLengthCache;
	
	private final String blockName;
	
	private SingleTextureBlock(SingleTextureBlock.Set parent, String name, int blockIndex, String[] subNames, Material material) {
		super(name + "_" + String.valueOf(blockIndex), subNames, material, ItemBlockBase.class);
		this.blockName = name;
		name = name + "_" + String.valueOf(blockIndex);
        this.setCreativeTab(parent.tab);
	}

    @Override
    @SideOnly(Side.CLIENT)
	public String getIconName(int damage) {
		return blockName + "_" + getSubBlockUnlocalizedNames()[damage];
	}
}
