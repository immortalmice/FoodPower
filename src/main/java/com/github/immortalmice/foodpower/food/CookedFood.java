package com.github.immortalmice.foodpower.food;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

import com.github.immortalmice.foodpower.baseclass.ItemFoodBase;
import com.github.immortalmice.foodpower.boss.entities.BitterBoss;
import com.github.immortalmice.foodpower.boss.entities.EnderBoss;
import com.github.immortalmice.foodpower.boss.entities.NetherBoss;
import com.github.immortalmice.foodpower.boss.entities.SaltyBoss;
import com.github.immortalmice.foodpower.boss.entities.SourBoss;
import com.github.immortalmice.foodpower.boss.entities.SweetBoss;
import com.github.immortalmice.foodpower.cooking.ICookingElement;
import com.github.immortalmice.foodpower.cooking.CookingRecipe.StepRequest;
import com.github.immortalmice.foodpower.lists.Bosses;
import com.github.immortalmice.foodpower.lists.FlavorTypes;
import com.github.immortalmice.foodpower.lists.OtherItems.Items;
import com.github.immortalmice.foodpower.types.FlavorType;
import com.github.immortalmice.foodpower.util.TooltipUtil;
import com.github.immortalmice.foodpower.world.FPWorldSavedData;

public class CookedFood extends ItemFoodBase implements ICookingElement{
    public CookedFood(){
        super(0, 0.0f, false);
    }

	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
		CompoundNBT nbt = stack.getTag();
		if(nbt != null){
			TooltipUtil tooltipHelper = new TooltipUtil(tooltip);
			
			if(nbt.contains("id")){
				tooltipHelper.add("(" + nbt.getString("id") + ")", new Style().setItalic(true).setColor(TextFormatting.GRAY));
			}
			
			if(nbt.contains("output_amount")){
				tooltipHelper.newBlankRow();
				tooltipHelper.addTranslate("message.foodpower.output_amount", nbt.getInt("output_amount"));
			}
		}
    	return;
    }

    @Override
    public int getItemStackLimit(ItemStack stack){
    	return 1;
    }

    @Override
    public ICookingElement.ElementType getElementType(){
        return ICookingElement.ElementType.COOKED_FOOD;
    }

    /* 3 second rule! If you don't pickup in 3 seconds.....Eww */
    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem){
    	if(entityItem.world instanceof ServerWorld) {
    		ServerWorld world = (ServerWorld) entityItem.world;
    		if(this.getRegistryName().toString() != "foodpower:dirty_food"){
                CompoundNBT compound = new CompoundNBT();
                entityItem.writeAdditional(compound);
                if(compound.getShort("Age") >= 20 * 3){
                    entityItem.setItem(new ItemStack(Items.DIRTY_FOOD, entityItem.getItem().getCount()));
                    if(entityItem.getThrowerId() != null) {
                        FPWorldSavedData data = world.getSavedData().getOrCreate(FPWorldSavedData::new, "foodpower");
                        FlavorType type = data.triggerWaste(entityItem.getThrowerId(), entityItem.world.getGameTime(), stack);
                        MobEntity bossEntity = null;
                        if(type == FlavorTypes.SWEET) {
                        	bossEntity = new SweetBoss(Bosses.EntityTypes.SWEET_BOSS, world);
                        }else if(type == FlavorTypes.BITTER){
                        	bossEntity = new BitterBoss(Bosses.EntityTypes.BITTER_BOSS, world);
                        }else if(type == FlavorTypes.SOUR) {
                        	bossEntity = new SourBoss(Bosses.EntityTypes.SOUR_BOSS, world);
                        }else if(type == FlavorTypes.SALTY) {
                        	bossEntity = new SaltyBoss(Bosses.EntityTypes.SALTY_BOSS, world);
                        }else if(type == FlavorTypes.NETHER) {
                        	bossEntity = new NetherBoss(Bosses.EntityTypes.NETHER_BOSS, world);
                        }else if(type == FlavorTypes.ENDER) {
                        	bossEntity = new EnderBoss(Bosses.EntityTypes.ENDER_BOSS, world);
                        }
                        if(bossEntity != null) {
                        	bossEntity.setPosition(entityItem.getPosX(), entityItem.getPosY(), entityItem.getPosZ());
                        	world.addEntity(bossEntity);
                        }
                    }
                }
            }
    	}
        return false;
    }

	public static boolean isMatchedWith(ItemStack provide, String id, int outputAmount){
		if(provide.hasTag()){
			CompoundNBT nbt = provide.getTag();
			if(nbt.contains("id") && nbt.contains("output_amount")
				&& id.equals(nbt.getString("id"))
				&& outputAmount == nbt.getInt("output_amount")){
				
				return true;
			}
		}
		return false;
	}

	public static ItemStack create(StepRequest stepRequest){
		ItemStack result = new ItemStack(stepRequest.getResult());
		
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("output_amount", stepRequest.getOutputAmount());
		nbt.putString("id", stepRequest.getRequestID());
		result.setTag(nbt);
		
		return result;
	}
}