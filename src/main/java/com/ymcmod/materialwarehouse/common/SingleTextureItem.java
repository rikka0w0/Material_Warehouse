package com.ymcmod.materialwarehouse.common;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class SingleTextureItem extends GenericItem{		
	private final String[] subNames;
	private final IIcon[] iconCache;
	
	public SingleTextureItem(String name, String[] subNames) {
		super(name, true);
		
		this.subNames = subNames;
		this.iconCache = new IIcon[subNames.length];
	}
	
	@Override
    public String[] getSubItemUnlocalizedNames(){
    	return subNames;
    }

	@Override
	public void beforeRegister() {

	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister r)	{
		for (int i=0; i<subNames.length; i++)
			iconCache[i] = r.registerIcon("material_warehouse:" + this.registryName + "_" + subNames[i]);
    }
	
    /**
     * Gets an icon index based on an item's damage value
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int dmg)
    {
        return iconCache[dmg];
    }
}
