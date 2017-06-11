package com.ymcmod.materialwarehouse.common;

import java.util.Iterator;
import java.util.LinkedList;

import com.ymcmod.materialwarehouse.MaterialWarehouse;
import com.ymcmod.materialwarehouse.client.CustomModelLoader;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A set of blocks (up to 16), each block has their own texture, and all six sides display the same texture
 * <p/>
 * Texture file: blockName_subName.png, ModelResourceLocation: simple_texture_block_setName_blockIndex_meta
 * @author Rikka0_0
 */
public final class SimpleTextureBlock extends GenericMetaBlock{
	public static final class Set{
		private static final LinkedList<SimpleTextureBlock.Set> registeredSTBSets = new LinkedList();
		
		public static final Iterator<SimpleTextureBlock.Set> iterator(){
			return registeredSTBSets.iterator();
		}
		
		private final String name;	//ore, chunk
		private final LinkedList<SimpleTextureBlock> blocks;
		private final CreativeTabs tab;
		
		public Set(String name, String[] subNames, Material material, CreativeTabs tab){
			this.name = name;
			this.tab = tab;
			this.blocks = new LinkedList();
			
			int maxBlockIndex = (subNames.length - 1) / 16;
			for (int blockIndex = 0; blockIndex <= maxBlockIndex; blockIndex++){
				int sectionLength = 16;
				if (blockIndex == maxBlockIndex)
					sectionLength = subNames.length - maxBlockIndex*16;	//Last block
					
				String[] subNameSection = new String[sectionLength];
				for (int i=0; i<sectionLength; i++)
					subNameSection[i] = subNames[blockIndex*16 + i];
				
				//block state json = name_blockIndex
				SimpleTextureBlock stb= 
						SimpleTextureBlock.create(this, name, blockIndex, subNameSection, material);
				blocks.add(stb);
			}
			
			registeredSTBSets.add(this);
		}
		
		public Iterator<SimpleTextureBlock> blockIterator(){
			return this.blocks.iterator();
		}
		
		public String getName(){
			return this.name;
		}
	}
	
	/**
	 * Total number of registered STB
	 */
	private static int count = 0;
	/**
	 * Used for initialization only!
	 */
	private static int subNamesLengthCache;
	
	private final SimpleTextureBlock.Set parent;
	private final String blockName;
	private final int index;
	
	private static SimpleTextureBlock create(SimpleTextureBlock.Set parent, String name, int blockIndex, String[] subNames, Material material){
		subNamesLengthCache = subNames.length;
		SimpleTextureBlock instance = new SimpleTextureBlock(parent, name, blockIndex, subNames, material);
		return instance;
	}
	
	private SimpleTextureBlock(SimpleTextureBlock.Set parent, String name, int blockIndex, String[] subNames, Material material) {
		super(name + "_" + String.valueOf(blockIndex), subNames, SimpleTextureItemBlock.class, material);
		this.setCreativeTab(parent.tab);
		this.blockName = name;
		this.parent = parent;
		index = count;
		count++;
	}

	@Override
	protected int getNumberOfSubBlocksDuringInit() {
		return subNamesLengthCache;
	}
	
	public int getTextureIndex(){
		return this.index;
	}
	
	public String getBlockName(){
		return blockName;
	}
	
	/**
	 * 
	 * @param i
	 * @return "material_warehouse:fakeinv_stb_index_i"
	 */
	@SideOnly(Side.CLIENT)
	public String getFakeInventoryModelName(int i){
		return MaterialWarehouse.modID + ":" + CustomModelLoader.invSTBBlockState + "_" + String.valueOf(this.index) + "_" + String.valueOf(i);
	}
	
	/**
	 * texturePath = [index][meta]
	 */
	@SideOnly(Side.CLIENT)
	public static String[][] texturePaths;
	
	@SideOnly(Side.CLIENT)
	public void updateTexturePaths(){
		texturePaths[index] = new String[subNames.length];
		for (int i=0; i<subNames.length; i++)
			texturePaths[index][i] = MaterialWarehouse.modID + ":blocks/" + this.blockName + "_" + this.subNames[i];
	}
	
	@SideOnly(Side.CLIENT)
	public static void initTexturePathArray(){
		texturePaths = new String[count][];
	}
}
