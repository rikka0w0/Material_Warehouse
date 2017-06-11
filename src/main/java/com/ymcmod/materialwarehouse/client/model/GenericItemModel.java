package com.ymcmod.materialwarehouse.client.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.ymcmod.materialwarehouse.common.GenericItem;

@SideOnly(Side.CLIENT)
public class GenericItemModel implements IPerspectiveAwareModel {
	private IBakedModel parentModel;
	private ItemOverrideList overrideList;
	
	public static ImmutableList<BakedQuad> getQuadsForItemStack(ItemStack itemStack){
		GenericItem item = (GenericItem) itemStack.getItem();
		ImmutableList<BakedQuad>[] bakedQuads = (ImmutableList<BakedQuad>[]) item.bakedQuadsCache;
		return bakedQuads[itemStack.getItemDamage()];
	}
	
	public GenericItemModel(IBakedModel parentModel){
		this.parentModel = parentModel;
		overrideList = new GenericItemOverrideList(Collections.EMPTY_LIST);
		
        Map<TransformType, TRSRTransformation> tMap = Maps.newHashMap();
        tMap.putAll(IPerspectiveAwareModel.MapWrapper.getTransforms(parentModel.getItemCameraTransforms()));
        IModelState state = new SimpleModelState(ImmutableMap.copyOf(tMap));
		Optional<TRSRTransformation> transform = state.apply(Optional.<IModelPart>absent());
		
		for (GenericItem item: GenericItem.registeredItems){
			String[] textures = item.getTexturePaths();
			ImmutableList<BakedQuad>[] bakedQuads = new ImmutableList[textures.length];
			item.bakedQuadsCache = bakedQuads;
			
			for (int i=0; i<textures.length; i++){
				TextureAtlasSprite texture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(textures[i]);
				bakedQuads[i] = ItemLayerModel.getQuadsForSprite(0, texture, DefaultVertexFormats.ITEM, transform);
			}
		}
	}
	
	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState blockState,
			@Nullable EnumFacing side, long rand) {
		return parentModel.getQuads(blockState, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return parentModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return parentModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return parentModel.getParticleTexture();
	}

	@Override
	@Deprecated
	public ItemCameraTransforms getItemCameraTransforms() {
		return parentModel.getItemCameraTransforms();  
	}

	@Override
	public ItemOverrideList getOverrides() {
		return overrideList;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		if (parentModel instanceof IPerspectiveAwareModel) {
			Matrix4f matrix4f = ((IPerspectiveAwareModel)parentModel).handlePerspective(cameraTransformType).getRight();
			return Pair.of(this, matrix4f);
	    } else {
	    	// If the base model isn't an IPerspectiveAware, we'll need to generate the correct matrix ourselves using the
	    	//  ItemCameraTransforms.

	    	ItemCameraTransforms itemCameraTransforms = parentModel.getItemCameraTransforms();
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
}
