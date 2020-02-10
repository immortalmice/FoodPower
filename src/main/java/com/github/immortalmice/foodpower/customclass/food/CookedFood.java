package com.github.immortalmice.foodpower.customclass.food;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;

import com.github.immortalmice.foodpower.customclass.food.Ingredient;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class CookedFood extends Ingredient{
	private int scrollId;

	public CookedFood(String nameIn, int id){
		super(nameIn);
		this.scrollId = id;
		/* To Do */
	}
	/* For a empty CookedFood */
	public CookedFood(String name){
		this(name, -1);
		
		/** Add to ingredient list, and regist it later */
		Ingredients.cookedFoodList.add(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    	return;
    }
}