package com.ymcmod.materialwarehouse.machine;

import java.util.ArrayList;

import com.ymcmod.materialwarehouse.MaterialWarehouse;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;
import rikka.librikka.block.MetaBlock;
import rikka.librikka.item.ISimpleTexture;
import rikka.librikka.item.ItemBlockBase;

public class BlockMachine extends MetaBlock implements ISimpleTexture{
	public final static String[] subNames = new String[] {"metal_melter"};
	
	public BlockMachine() {
        super("ymcmw_machine", subNames, Material.ROCK, ItemBlockBase.class);
        setCreativeTab(MaterialWarehouse.creativeTab);
        
        propertyType = this.getPropertyMeta();
	}
	
	@Override
	public String getIconName(int damage) {
		return subNames[damage];
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState meta) {
		return null;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player){
	    return new ItemStack(itemBlock, 1, this.getMetaFromState(world.getBlockState(pos)));
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
		
		int heading = MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
        switch (heading) {
        case 0:
        	state = state.withProperty(propertyFacing, EnumFacing.NORTH);
        	break;
        case 1:
        	state = state.withProperty(propertyFacing, EnumFacing.EAST);
        	break;
        case 2:
        	state = state.withProperty(propertyFacing, EnumFacing.SOUTH);
        	break;
        case 3:
        	state = state.withProperty(propertyFacing, EnumFacing.WEST);
        	break;
        }
		
		return state;
	}
	
    ///////////////////////////
    /// BlockState
    ///////////////////////////
    public static IProperty<Integer> propertyType;
    public final static IProperty<Boolean> propertyWorking = PropertyBool.create("working");
    public final static IProperty<EnumFacing> propertyFacing = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
	@Override
	protected void createProperties(ArrayList<IProperty> properties, ArrayList<IUnlistedProperty> unlisted) {
		super.createProperties(properties, unlisted);
		properties.add(propertyFacing);
		properties.add(propertyWorking);
	}
	
	@Override
    public final IBlockState getStateFromMeta(int meta){
		int type = meta & 3;
		int facing = ((meta>>2) & 3) + 2;
        return super.getDefaultState()
        		.withProperty(getPropertyMeta(), type)
        		.withProperty(propertyFacing, EnumFacing.getFront(facing));
    }
	
	@Override
    public final int getMetaFromState(IBlockState state){
		int type = state.getValue(getPropertyMeta());
		int facing = state.getValue(propertyFacing).ordinal() - 2;
		int meta = (facing<<2)&12 | (type&3);
		return meta;
    }
	
	@Override
	public final int damageDropped(IBlockState state) {
	    return state.getValue(propertyType);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(propertyWorking, false);
	}
}
