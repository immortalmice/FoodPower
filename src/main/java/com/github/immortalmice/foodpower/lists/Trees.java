package com.github.immortalmice.foodpower.lists;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.customclass.tree.TreeSaplingBush;
import com.github.immortalmice.foodpower.customclass.tree.TreeLeave;
import com.github.immortalmice.foodpower.lists.Ingredients;

public class Trees{
	public static final List<TreeSaplingBush> saplingBushList = new ArrayList<TreeSaplingBush>();
	public static final List<TreeLeave> leaveList = new ArrayList<TreeLeave>();

	public static final TreeSaplingBush ORANGE = new TreeSaplingBush("orange", Ingredients.ORANGE);
	public static final TreeSaplingBush KIWI = new TreeSaplingBush("kiwi", Ingredients.KIWI);
	public static final TreeSaplingBush PAPAYA = new TreeSaplingBush("papaya", Ingredients.PAPAYA);
	public static final TreeSaplingBush MANGO = new TreeSaplingBush("mango", Ingredients.MANGO);
	public static final TreeSaplingBush LEMON = new TreeSaplingBush("lemon", Ingredients.LEMON);
}