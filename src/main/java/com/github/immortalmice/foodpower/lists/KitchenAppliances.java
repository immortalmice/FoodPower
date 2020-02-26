package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.KitchenAppliance;

/* All the ingredient need to be registed will list below. */
public class KitchenAppliances{
	public static final DeferredRegister<Block> REGISTER = new DeferredRegister<Block>(ForgeRegistries.BLOCKS, FoodPower.MODID);

	public static final List<KitchenAppliance> list = new ArrayList<KitchenAppliance>();

	/* Constructor: name, bounding block */
	public static final KitchenAppliance OVEN = new KitchenAppliance("oven", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance JUICER = new KitchenAppliance("juicer", new AxisAlignedBB(3.0D/16, 2.0D/16, 3.0D/16, 13.0D/16, 1.0D, 13.0D/16));
	public static final KitchenAppliance REVOLVING_CAKE_STAND = new KitchenAppliance("revolving_cake_stand", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance REFRIGERATOR = new KitchenAppliance("refrigerator", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance ELECTRIC_POT = new KitchenAppliance("electric_pot", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance STOCKPOT = new KitchenAppliance("stockpot", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance FRYING_PAN = new KitchenAppliance("frying_pan", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance ELECRIC_STOVE = new KitchenAppliance("electric_stove", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance SHAKER = new KitchenAppliance("shaker", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance CHOPPING_BOARD = new KitchenAppliance("chopping_board", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
	public static final KitchenAppliance UNIVERSAL_STATION = new KitchenAppliance("universal_stand", new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
}