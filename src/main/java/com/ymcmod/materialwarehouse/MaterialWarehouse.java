package com.ymcmod.materialwarehouse;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

@Mod(modid = MaterialWarehouse.MODID, name = MaterialWarehouse.modName, version = MaterialWarehouse.version)
public class MaterialWarehouse {
	public static final String MODID = "material_warehouse";
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
	public static String[] blockPrefixes = new String[]{"ore", "block"};
	public static String[] suffixes = new String[]{"copper", "tin", "silver", "nickel", "tungsten", "platinum", "titanium", "magnesium", "aluminum", "uranium", "iridium"};
	
    @Instance(MaterialWarehouse.MODID)
    public static MaterialWarehouse instance;
    
    @SidedProxy(clientSide="com.ymcmod.materialwarehouse.ClientProxy", serverSide="com.ymcmod.materialwarehouse.CommonProxy") 
    public static CommonProxy proxy;
    
    public static CreativeTabs creativeTab;
    
    @Mod.EventBusSubscriber(modid = MaterialWarehouse.MODID)
    public static class RegistrationHandler {
    	@SubscribeEvent
    	public static void newRegistry(RegistryEvent.NewRegistry event) {
    		creativeTab = new CreativeTab();
    	}
    
    	@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
    		IForgeRegistry registry = event.getRegistry();
    		BlockRegistry.initBlocks();
    		BlockRegistry.registerBlocks(registry, false);
    	}
    	
    	@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
    		IForgeRegistry registry = event.getRegistry();
    		ItemRegistry.initItems();
    		BlockRegistry.registerBlocks(registry, true);
    		ItemRegistry.registerItems(registry);
    		
    		BlockRegistry.registerOreDict();
    		ItemRegistry.registerOreDict();
    	}
    }
    
/*    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	
    	
    	ItemRegistry.preInit();
    	BlockRegistry.preInit();
    	
    	proxy.registerRenders();
    }*/
}
