package com.ymcmod.materialwarehouse.client;

import java.util.List;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.ymcmod.materialwarehouse.machine.BlockMachine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MachineModel implements IPerspectiveAwareModel {
	private final IBakedModel[] firstState, secondState;
	private final boolean hasSecondState;
	public MachineModel(IBakedModel[] firstState, IBakedModel[] secondState){
		this.firstState = firstState;
		this.secondState = secondState;
		this.hasSecondState = secondState != null;
	}
	
	@Override
	public boolean isAmbientOcclusion() {
		return firstState[2].isAmbientOcclusion();
	}
	@Override
	public boolean isGui3d() {
		return firstState[2].isGui3d();
	}
	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}
	@Override
	public TextureAtlasSprite getParticleTexture() {
		return firstState[2].getParticleTexture();
	}
	@Override
	@Deprecated
	public ItemCameraTransforms getItemCameraTransforms() {
		return firstState[2].getItemCameraTransforms();
	}
	
	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;	//I'm not sure what this thing does QAQ, only know this prevents crashing 233
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		if (firstState[2] instanceof IPerspectiveAwareModel) {
	      Matrix4f matrix4f = ((IPerspectiveAwareModel)firstState[2]).handlePerspective(cameraTransformType).getRight();
	      return Pair.of(this, matrix4f);
	    } else {
	      // If the parent model isn't an IPerspectiveAware, we'll need to generate the correct matrix ourselves using the
	      //  ItemCameraTransforms.

	      ItemCameraTransforms itemCameraTransforms = firstState[2].getItemCameraTransforms();
	      ItemTransformVec3f itemTransformVec3f = itemCameraTransforms.getTransform(cameraTransformType);
	      TRSRTransformation tr = new TRSRTransformation(itemTransformVec3f);
	      Matrix4f mat = null;
	      if (tr != null) { // && tr != TRSRTransformation.identity()) {
	        mat = tr.getMatrix();
	      }
	      // The TRSRTransformation for vanilla items have blockCenterToCorner() applied, however handlePerspective
	      //  reverses it back again with blockCornerToCenter().  So we don't need to apply it here.

	      return Pair.of(this, mat);
	    }
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState blockState, @Nullable EnumFacing side, long rand) {	
		if (blockState == null)
			return firstState[2].getQuads(blockState, side, rand);
		
	    EnumFacing facing = blockState.getValue(BlockMachine.propertyFacing);
	    boolean is2State = blockState.getValue(BlockMachine.propertyWorking);
	    
		List<BakedQuad> selectedModel = is2State ? 
				secondState[facing.ordinal()].getQuads(blockState, side, rand) :
				firstState[facing.ordinal()].getQuads(blockState, side, rand);
        
		return selectedModel;
	}
}
