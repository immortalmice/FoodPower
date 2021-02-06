package com.github.immortalmice.foodpower.specialclass;

import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.container.tutorialbook.TutorialBookContainer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class TutorialBook extends ItemBase {
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn){
        ItemStack stack = playerIn.getHeldEquipment().iterator().next();
        int page = stack.hasTag() ? stack.getTag().getInt("page") : 0;

        /* ActionResultType.SUCCESS */
        if(worldIn.isRemote) return ActionResult.resultSuccess(stack);
        NetworkHooks.openGui((ServerPlayerEntity)playerIn, new INamedContainerProvider(){
            @Override
            public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player){
                return new TutorialBookContainer(windowId, playerInventory, page);
            }
            @Override
            public ITextComponent getDisplayName(){
                return new StringTextComponent("");
            }
        }, extraData -> {
            extraData.writeInt(page);
        });
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
