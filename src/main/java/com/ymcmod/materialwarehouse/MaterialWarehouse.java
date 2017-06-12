package com.ymcmod.materialwarehouse;

import net.minecraft.creativetab.CreativeTabs;
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
	
	/*This will register items and blocks: 
	 * Block/Item name: ingot_copper, block0
	 * oreDict name: ingotCopper, blockCopper
	 * texture: items/ingot_copper.png, blocks/blockCopper
	 * language string:
	 * item.material_warehouse:ingot_copper.name=My Copper Ingot
	 * tile.material_warehouse:block_iron.name=My Iron Block
	*/
	public static String[] itemPrefixes = new String[]{"ingot", "plate", "dust", "dustSmall", "dustTiny", "nugget", "crushed"};
	public static String[] blockPrefixes = new String[]{};
	public static String[] suffixes = new String[]{"copper", "tin", "silver", "nickel", "tungsten", "platinum", "titanium", "magnesium", "aluminum", "uranium", "iridium"};
	
    @Instance(MaterialWarehouse.modID)
    public static MaterialWarehouse instance;
    
    @SidedProxy(clientSide="com.ymcmod.materialwarehouse.client.ClientProxy", serverSide="com.ymcmod.materialwarehouse.CommonProxy") 
    public static CommonProxy proxy;
    
    public static CreativeTabs creativeTab;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	creativeTab = new CreativeTab();
    	
    	ItemRegistry.preInit();
    	BlockRegistry.preInit();
    	
    	proxy.registerRenders();
    }
}
