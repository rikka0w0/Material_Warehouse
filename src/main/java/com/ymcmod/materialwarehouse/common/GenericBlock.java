package com.ymcmod.materialwarehouse.common;

import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class GenericBlock extends Block{
	@Deprecated
	protected String registryName;	//1.11.2 compatibility
	
	public static final LinkedList<GenericBlock> registeredBlocks = new LinkedList();
	
	protected GenericItemBlock itemBlock;
		
    protected GenericBlock(String unlocalizedName, Class<? extends GenericItemBlock> itemBlockClass, Material material) {
        super(material);
        this.setBlockName(unlocalizedName);
        //this.setUnlocalizedName(unlocalizedName);
        registryName = unlocalizedName;
        //this.setRegistryName(unlocalizedName);				//Key!
        
        //this.setDefaultState(setDefaultBlockState(this.blockState.getBaseState()));
        this.beforeRegister();
                
        GameRegistry.registerBlock(this, itemBlockClass, unlocalizedName);
        itemBlock = (GenericItemBlock) Item.getItemFromBlock(this);
        registeredBlocks.add(this);
    }
  
    
    @Override
    @SideOnly(Side.CLIENT)
    public final void getSubBlocks(Item itemIn, CreativeTabs tab, List subItems){
    	if (itemBlock.getHasSubtypes()){
            for (int ix = 0; ix < ((ISubBlock)this).getSubBlockUnlocalizedNames().length; ix++)
                subItems.add(new ItemStack(this, 1, ix));
    	}else{
    		super.getSubBlocks(itemIn, tab, subItems);
    	}
    }
    
	@Override
	public int damageDropped(int meta) {
	    return meta;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player){
	//public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player){
	    //return new ItemStack(itemBlock, 1, this.getMetaFromState(world.getBlockState(pos)));
		return itemBlock.getHasSubtypes() ? new ItemStack(itemBlock, 1, world.getBlockMetadata(x, y, z))
											: super.getPickBlock(target, world, x, y, z, player);
	}
   

    public final GenericItemBlock getItemBlock(){
    	return this.itemBlock;
    }
    
    public void beforeRegister(){}
}
