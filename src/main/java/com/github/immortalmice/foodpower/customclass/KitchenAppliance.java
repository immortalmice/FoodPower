package com.github.immortalmice.foodpower.customclass;

import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import com.github.immortalmice.foodpower.baseclass.BlockBase;
import com.github.immortalmice.foodpower.lists.KitchenAppliances;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class KitchenAppliance extends BlockBase{
	private VoxelShape blockAABB;
	private Item item;

	public KitchenAppliance(String name, AxisAlignedBB blockAABBIn){
		super(name, Block.Properties.create(Material.IRON)
			.harvestLevel(2)
			.harvestTool(ToolType.PICKAXE)
			.sound(SoundType.METAL)
			.hardnessAndResistance(1.5f));

		this.blockAABB = VoxelShapes.create(blockAABBIn);

		item = new BlockItem(this, new Item.Properties().group(FPCreativeTabs.BLOCK_TAB));

        /* Add to kitchen appliances list*/
        KitchenAppliances.list.add(this);

        /* Regist it to game using DeferredRegister */
        KitchenAppliances.REGISTER.register(this.getFPName(), () -> this);
	}

	
	/* How dare you use stone pickaxe break this holy things */
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    	return this.blockAABB;
    }

    @Override
    public Item asItem(){
    	return this.item;
    }
}