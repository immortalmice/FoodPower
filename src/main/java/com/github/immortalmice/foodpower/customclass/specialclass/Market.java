package com.github.immortalmice.foodpower.customclass.specialclass;

import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import com.github.immortalmice.foodpower.baseclass.BlockBase;
import com.github.immortalmice.foodpower.customclass.container.classes.market.MarketContainer;

public class Market extends BlockBase{
	public Market(){
		super("market", Material.ROCK);
	}
	/* Open the GUI */
	@Override
	public ActionResultType func_225533_a_(BlockState stateIn, World worldIn
		, BlockPos posIn, PlayerEntity playerIn
		, Hand handIn, BlockRayTraceResult resultIn){
		
		if(worldIn.isRemote) return ActionResultType.SUCCESS;

		if(!playerIn.func_225608_bj_()){
			NetworkHooks.openGui((ServerPlayerEntity)playerIn, new INamedContainerProvider(){
				@Override
				public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player){
					return new MarketContainer(windowId, playerInventory);
				}
				@Override
				public ITextComponent getDisplayName(){
					return new TranslationTextComponent("block.foodpower.market");
				}
			}, extraData -> {});
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}
}