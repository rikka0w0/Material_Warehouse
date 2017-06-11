package com.ymcmod.materialwarehouse;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MaterialWarehouse.modID, name = MaterialWarehouse.modName, version = MaterialWarehouse.version)
public class MaterialWarehouse {
	public static final String modID = "material_warehouse";
	public static final String modName = "Material Warehouse";
	public static final String version = "1.0";
	
    @Instance(MaterialWarehouse.modID)
    public static MaterialWarehouse instance;
    
    @SidedProxy(clientSide="com.ymcmod.materialwarehouse.client.ClientProxy", serverSide="com.ymcmod.materialwarehouse.CommonProxy") 
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	ItemRegistry.preInit();
    	BlockRegistry.preInit();
    	
    	proxy.registerRenders();
    }
}
