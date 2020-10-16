package com.github.immortalmice.foodpower.specialclass;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraftforge.fml.network.NetworkHooks;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.baseclass.ItemBase;
import com.github.immortalmice.foodpower.container.recipescroll.RecipeScrollContainer;
import com.github.immortalmice.foodpower.cooking.CookingPattern;
import com.github.immortalmice.foodpower.cooking.CookingRecipe;
import com.github.immortalmice.foodpower.lists.OtherItems.Items;
import com.github.immortalmice.foodpower.util.TooltipUtil;

public class RecipeScroll extends ItemBase{
    private static final IItemPropertyGetter RARITY_GETTER = (stack, world, entity) -> {
        int rarity = CookingRecipe.getRarity(RecipeScroll.getRecipeTag(stack));
        switch(rarity){
            case 0:
                return 0.0f; // Rarity WOOD
            case 1:
                return 0.1f; // Rarity IRON
            case 2:
                return 0.2f; // Rarity GOLD
            case 3:
                return 0.3f; // Rarity DIAMOND
            default:
                return 0.0f; // Rarity WOOD
        }
    };
    public static final String NBT_KEY_RECIPE = "recipe";

	public RecipeScroll(){
		super(new Item.Properties().maxStackSize(1));

        this.addPropertyOverride(new ResourceLocation(FoodPower.MODID, "scroll_property"), RecipeScroll.RARITY_GETTER);
	}

	/* create a ItemStack and set NBTTags with given information */
	public static ItemStack create(CookingPattern patternIn, List<ItemStack> listIn, String nameIn){
		ItemStack result = new ItemStack(Items.RECIPE_SCROLL);
        RecipeScroll.writeCookingRecipe(result, new CookingRecipe(patternIn, listIn, nameIn));

		return result;
	}

    /* Set rarity and calculate ingredient amount in need */
    public static void initStack(ItemStack stack, int rarity, float rand){
        CookingRecipe recipe = RecipeScroll.readCookingRecipe(stack);
        if(recipe != null){
            recipe.initialize(rarity, rand);
            RecipeScroll.writeCookingRecipe(stack, recipe);
        }
    }

    /* dif may be negative */
    public static int addOutputAmount(ItemStack scroll, int dif){
        CookingRecipe recipe = RecipeScroll.readCookingRecipe(scroll);
        if(recipe != null){
            int amount = recipe.getOutputAmount() + dif;
            if(amount <= 0) amount += 64;
            if(amount > 64) amount -= 64;

            recipe.setOutputAmount(amount);
            RecipeScroll.writeCookingRecipe(scroll, recipe);

            return amount;
        }
        return 1;
    }

    @Nullable
    public static CookingPattern getPattern(ItemStack scroll){
        return CookingRecipe.getPattern(RecipeScroll.getRecipeTag(scroll));
    }

    public static CompoundNBT getRecipeTag(ItemStack stack){
        if(stack.hasTag() && stack.getTag().contains(RecipeScroll.NBT_KEY_RECIPE)){
            return (CompoundNBT) stack.getTag().get(RecipeScroll.NBT_KEY_RECIPE);
        }
        return new CompoundNBT();
    }

    @Nullable
    public static CookingRecipe readCookingRecipe(ItemStack stack){
        return CookingRecipe.read(RecipeScroll.getRecipeTag(stack));
    }

    public static void writeCookingRecipe(ItemStack stack, CookingRecipe recipe){
        CompoundNBT nbt = new CompoundNBT();
        if(stack.hasTag()){
            nbt = stack.getTag();
        }
        nbt.put(NBT_KEY_RECIPE, recipe.write());
        stack.setTag(nbt);
    }

	/* Add information about pattern and ingrdient to tooltip */
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    	super.addInformation(stack, worldIn, tooltip, flagIn);

        CookingRecipe recipe = RecipeScroll.readCookingRecipe(stack);
        if(recipe == null) return;

        TooltipUtil tooltipHelper = new TooltipUtil(tooltip);

        if(recipe.isInitialized())
        	tooltipHelper.add("(" + recipe.getID() + ")", new Style().setItalic(true).setColor(TextFormatting.GRAY));
        
    	String patternStr = TooltipUtil.translate("general.foodpower.cooking_pattern") + " : ";
    	patternStr += TooltipUtil.translate("pattern.foodpower." + recipe.getPattern().getName());
        tooltipHelper.newBlankRow();
    	tooltipHelper.add(patternStr);

        tooltipHelper.addTranslate("message.foodpower.output_amount", recipe.getOutputAmount());

        /* Whether player press down shift key or not */
        boolean moreInfo = Screen.hasShiftDown();
        if(!moreInfo){
            tooltipHelper.newBlankRow();
            tooltipHelper.addTranslate("message.foodpower.tooltip_more_info"
                , (new Style()).setItalic(true).setColor(TextFormatting.GRAY));
            return;
        }

    	recipe.appendIngredientInfo(tooltipHelper);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack){
    	return CookingRecipe.getDisplayName(RecipeScroll.getRecipeTag(stack));
    }

    /* Open gui on right click, transfer nbt tag to construct container and screen */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn){
        ItemStack stack = playerIn.getHeldEquipment().iterator().next();

        /* ActionResultType.SUCCESS */
        if(worldIn.isRemote) return ActionResult.resultSuccess(stack);
        NetworkHooks.openGui((ServerPlayerEntity)playerIn, new INamedContainerProvider(){
            @Override
            public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player){
                return new RecipeScrollContainer(windowId, playerInventory, RecipeScroll.getRecipeTag(stack));
            }
            @Override
            public ITextComponent getDisplayName(){
                return new StringTextComponent("");
            }
        }, extraData -> {
            extraData.writeCompoundTag(RecipeScroll.getRecipeTag(stack));
        });
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}