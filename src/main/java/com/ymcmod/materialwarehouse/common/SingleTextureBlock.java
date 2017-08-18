package com.ymcmod.materialwarehouse.common;

import java.util.LinkedList;

import com.ymcmod.materialwarehouse.client.ISESimpleTextureItem;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SingleTextureBlock extends Block implements ISubBlock, ISESimpleTextureItem{
	public static final class Set{
		public static final LinkedList<SingleTextureBlock.Set> registeredSTBSets = new LinkedList();
		
		public final String name;	//ore, chunk
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
	protected final String[] subNames;
	protected final ItemBlock itemBlock;
	
	private SingleTextureBlock(SingleTextureBlock.Set parent, String name, int blockIndex, String[] subNames, Material material) {
		super(material);
		this.blockName = name;
		name = name + "_" + String.valueOf(blockIndex);
        this.subNames = subNames;
        this.setUnlocalizedName(name);
        this.setRegistryName(name);				//Key!
        this.setCreativeTab(parent.tab);
        
        GameRegistry.register(this);
        
        this.itemBlock = new GenericItemBlock(this, subNames.length > 1);
        GameRegistry.register(this.itemBlock, this.getRegistryName());
        
        this.setDefaultState(this.blockState.getBaseState().withProperty(getPropertyMeta(), 0));
	}
	
	@Override
	public String[] getSubBlockUnlocalizedNames() {
		return subNames;
	}
	
	@Override
    public String getUnlocalizedName() {
        return "tile." + blockName;
    }

    @Override
    @SideOnly(Side.CLIENT)
	public String getIconName(int damage) {
		return blockName + "_" + subNames[damage];
	}

    @Override
    @SideOnly(Side.CLIENT)
    public final void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems){
    	if (itemBlock.getHasSubtypes()){
            for (int ix = 0; ix < getSubBlockUnlocalizedNames().length; ix++)
                subItems.add(new ItemStack(this, 1, ix));
    	}else{
    		super.getSubBlocks(itemIn, tab, subItems);
    	}
    }
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state){
	    return new ItemStack(itemBlock, 1, this.getMetaFromState(state));
	}
    
    public final ItemBlock getItemBlock(){
    	return this.itemBlock;
    }
    

    ///////////////////////////
    /// BlockState
    ///////////////////////////
    public IProperty<Integer> propertyMeta = null;
    
	@Override
	protected final BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] {new PropertyMeta(subNamesLengthCache)});
	}
	
	/**
	 * Before the initialization is done, propertyMeta is null,
	 * @return @NonNullable propertyMeta
	 */
	public final IProperty<Integer> getPropertyMeta(){
		if (propertyMeta == null)
			propertyMeta = (IProperty<Integer>) this.blockState.getProperty("meta");
		return propertyMeta;
	}
	
	@Override
    public final IBlockState getStateFromMeta(int meta){
        return super.getDefaultState().withProperty(getPropertyMeta(), meta & 15);
    }
	
	@Override
    public final int getMetaFromState(IBlockState state){
		int meta = state.getValue(getPropertyMeta());
		meta = meta & 15;
		return meta;
    }
	
	@Override
	public final int damageDropped(IBlockState state) {
	    return getMetaFromState(state);
	}
}
