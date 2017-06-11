package com.ymcmod.materialwarehouse.client.model;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ISmartVariant;
import net.minecraftforge.client.model.ModelProcessingHelper;
import net.minecraftforge.client.model.MultiModel;
import net.minecraftforge.client.model.BlockStateLoader.SubModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

/**
 * A block variant has the same texture for all 6 sides
 * @author Rikka0_0
 */
public class SimpleBlockVariant extends Variant implements ISmartVariant{
	private static final ResourceLocation cube_all = new ResourceLocation("minecraft:block/cube_all");
	
    private final ImmutableMap<String, String> textures;
    private final ImmutableMap<String, String> customData;
    private final IModelState state;
    
    public SimpleBlockVariant(String texture){
    	this(TRSRTransformation.identity(), texture);
    }
    
    private SimpleBlockVariant(IModelState state, String texture) {
		super(cube_all
			, state instanceof ModelRotation ? (ModelRotation)state : ModelRotation.X0_Y0
			, false, 1);	//uvLock = false, weight always 1
		
		ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
		builder.put("all", texture);
		this.textures = builder.build();
		this.customData = ImmutableMap.copyOf(new HashMap<String, String>());
        this.state = state;
	}

    @Override
    public IModelState getState()
    {
        return state;
    }
    
    private IModel runModelHooks(IModel base, boolean smooth, boolean gui3d, boolean uvlock, ImmutableMap<String, String> textureMap, ImmutableMap<String, String> customData)
    {
        base = ModelProcessingHelper.customData(base, customData);
        base = ModelProcessingHelper.retexture(base, textureMap);
        base = ModelProcessingHelper.smoothLighting(base, smooth);
        base = ModelProcessingHelper.gui3d(base, gui3d);
        base = ModelProcessingHelper.uvlock(base, uvlock);
        return base;
    }
    
    @Override
    public IModel process(IModel base){
    	//base must be cube_all
    	//texture string,string {all=minecraft:blocks/diamond_block}
    	//ImmutableMap<String, String> customData
    	
    	//						smooth=gui3d=true
    	return runModelHooks(base, true, true, this.isUvLock(), textures, customData);
    }
}
