package com.github.immortalmice.foodpower.customclass.specialclass;

import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.lists.other.OtherItems;

public class DirtyFood extends ItemBase{
	public DirtyFood(){
		super("dirty_food");

		OtherItems.list.add(this);
	}
}