package com.ymcmod.materialwarehouse.common;

import java.util.Iterator;
import java.util.LinkedList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

/**
 * A set of blocks (up to 16), each block has their own texture, and all six sides display the same texture
 * <p/>
 * Texture file: blockName_subName.png, ModelResourceLocation: simple_texture_block_setName_blockIndex_meta
 * @author Rikka0_0
 */
public final class SingleTextureBlock extends GenericBlock implements ISubBlock{
	public static final class Set{
		private static final LinkedList<SingleTextureBlock.Set> registeredSTBSets = new LinkedList();
		
		public static final Iterator<SingleTextureBlock.Set> iterator(){
			return registeredSTBSets.iterator();
		}
		
		private final String name;	//ore, chunk
		private final LinkedList<SingleTextureBlock> blocks;
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
				SingleTextureBlock stb= 
						SingleTextureBlock.create(this, name, blockIndex, subNameSection, material);
				blocks.add(stb);
			}
			
			registeredSTBSets.add(this);
		}
		
		public Iterator<SingleTextureBlock> blockIterator(){
			return this.blocks.iterator();
		}
		
		public String getName(){
			return this.name;
		}
		
		/**
		 * Meta < 16, max 15 (0x0F)
		 */
		public int getMeta(int i){
			return i&15;
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
	}
	
	/**
	 * Total number of registered STB
	 */
	private static int count = 0;
	/**
	 * Used for initialization only!
	 */
	private static int subNamesLengthCache;
	
	private final SingleTextureBlock.Set parent;
	private final String blockName;
	private final int index;
	private final String[] subNames;
	
	private static SingleTextureBlock create(SingleTextureBlock.Set parent, String name, int blockIndex, String[] subNames, Material material){
		subNamesLengthCache = subNames.length;
		SingleTextureBlock instance = new SingleTextureBlock(parent, name, blockIndex, subNames, material);
		instance.setCreativeTab(parent.tab);
		return instance;
	}
	
	private SingleTextureBlock(SingleTextureBlock.Set parent, String name, int blockIndex, String[] subNames, Material material) {
		super(name + "_" + String.valueOf(blockIndex), SingleTextureItemBlock.class, material);
		this.subNames = subNames;
		
		this.blockName = name;
		this.parent = parent;
		this.index = count;
		this.count++;
		
		iconBuffer = new IIcon[subNames.length];
	}
	
	public int getTextureIndex(){
		return this.index;
	}
	
	public String getBlockName(){
		return this.blockName;
	}

	@Override
	public String[] getSubBlockUnlocalizedNames() {
		return subNames;
	}
	
	@Override
	public void beforeRegister(){
		
	}
	
	//[meta][side]
	protected final IIcon[] iconBuffer;
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
		for (int i=0; i<subNames.length; i++){
			iconBuffer[i] = iconRegister.registerIcon("material_warehouse:" + parent.name + "_" + subNames[i]);
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
    	return iconBuffer[meta];
    }
}
