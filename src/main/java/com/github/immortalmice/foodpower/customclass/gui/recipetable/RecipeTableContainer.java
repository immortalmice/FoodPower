package com.github.immortalmice.foodpower.customclass.gui.recipetable;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.item.ItemStack;

import com.github.immortalmice.foodpower.customclass.gui.ModContainer;
import com.github.immortalmice.foodpower.lists.other.OtherItems;

public class RecipeTableContainer extends ModContainer{

	protected World world; 
	protected BlockPos pos;

	public RecipeTableContainer(EntityPlayer playerIn, World worldIn, BlockPos posIn){
		super(playerIn, 138);
		
		world = worldIn;
		pos = posIn;

		items = new ItemStackHandler(1);

		this.addSlotToContainer(new SlotItemHandler(items, 0, 151, 115){
			/** Only Recipe Scroll Here */
			@Override
			public boolean isItemValid(ItemStack stack){
				return stack != null 
					&& stack.getItem() == OtherItems.RECIPE_SCROLL 
					&& super.isItemValid(stack);
			}
		});
	}
}