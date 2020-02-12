package com.github.immortalmice.foodpower.customclass;

import net.minecraft.util.BlockRenderLayer;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.immortalmice.foodpower.baseclass.BlockRotatableBase;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;

public class KitchenAppliance extends BlockRotatableBase{
	public KitchenAppliance(String name){
		super(name, Material.IRON);

		/** How dare you use stone pickaxe break this holy things */
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(1.5f);
		this.setSoundType(SoundType.METAL);

        /** Add to kitchen appliances list, and regist it later */
        KitchenAppliances.list.add(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
    public BlockRenderLayer getRenderLayer(){
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}