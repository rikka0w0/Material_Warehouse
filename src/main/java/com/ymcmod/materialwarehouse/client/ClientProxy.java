package com.ymcmod.materialwarehouse.client;

import com.ymcmod.materialwarehouse.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy{
	@Override
	public World getClientWorld(){
		return Minecraft.getMinecraft().theWorld;
	}
	
	@Override
	public void registerRenders() {		

	}
}
