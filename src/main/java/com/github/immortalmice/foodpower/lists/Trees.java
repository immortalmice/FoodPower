package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.customclass.tree.FPTree;
import com.github.immortalmice.foodpower.customclass.tree.TreeSaplingBush;
import com.github.immortalmice.foodpower.customclass.tree.TreeLeave;

public class Trees{
	public static final DeferredRegister<Block> REGISTER = new DeferredRegister<Block>(ForgeRegistries.BLOCKS, FoodPower.MODID);

	public static final List<FPTree> list = new ArrayList<FPTree>();
	public static final List<TreeSaplingBush> saplingBushList = new ArrayList<TreeSaplingBush>();
	public static final List<TreeLeave> leaveList = new ArrayList<TreeLeave>();

	public static final FPTree ORANGE = new FPTree("orange");
	public static final FPTree KIWI = new FPTree("kiwi");
	public static final FPTree PAPAYA = new FPTree("papaya");
	public static final FPTree MANGO = new FPTree("mango");
	public static final FPTree LEMON = new FPTree("lemon");
}