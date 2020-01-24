package com.github.immortalmice.foodpower.customclass.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.NoClassDefFoundError;
import javax.annotation.Nonnull;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;

import com.github.immortalmice.foodpower.customclass.tree.TreeSaplingBush;
import com.github.immortalmice.foodpower.lists.Trees;
import com.github.immortalmice.foodpower.lists.FPCreativeTabs;

public class TreeLeave extends BlockLeaves{
	private TreeSaplingBush sapling;
	private Item dropItem;
	public TreeLeave(String nameIn, TreeSaplingBush saplingIn, Item dropItemIn){
		this.setTranslationKey(nameIn.concat("_leave"));
        this.setRegistryName(nameIn.concat("_leave"));

        this.setCreativeTab(FPCreativeTabs.BLOCK_TAB);

        IBlockState ib = this.blockState.getBaseState();
		this.setDefaultState(ib.withProperty(CHECK_DECAY, Boolean.valueOf(true)));
		this.setDefaultState(ib.withProperty(DECAYABLE, Boolean.valueOf(true)));

		this.sapling = saplingIn;
		this.dropItem = dropItemIn;

        try{
            this.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics);
        }catch(NoClassDefFoundError e){
            /** Function not exist, maybe in server, skip */
        }

		Trees.leaveList.add(this);
	}
/*
    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }
*/
	@Override
	public BlockPlanks.EnumType getWoodType(int meta){
		return BlockPlanks.EnumType.OAK;
	}

	@Override
	public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune){
		ArrayList<ItemStack> al = new ArrayList<ItemStack>();
		al.add(new ItemStack(this));
		return al;
	}

	@Override
    public IBlockState getStateFromMeta(int meta){
        return getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }

    @Override
    public int getMetaFromState(IBlockState state){
        int i = 0;
        if (!state.getValue(DECAYABLE).booleanValue()){
            i |= 4;
        }

        if (state.getValue(CHECK_DECAY).booleanValue()){
            i |= 8;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {CHECK_DECAY, DECAYABLE});
    }

    /** Drop Ingreient, Not Apple :D */
    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance){
        if (worldIn.rand.nextInt(16) == 0){
        	EntityPlayer closestPlayer = worldIn.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 30, false);
        	if(closestPlayer != null){
        		if(closestPlayer.inventory.getFirstEmptyStack() != -1){
        			closestPlayer.inventory.addItemStackToInventory(new ItemStack(dropItem));
        		}else{
        			spawnAsEntity(worldIn, pos, new ItemStack(dropItem));
        		}
        		
        	}
            
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
        return Item.getItemFromBlock(sapling);
    }
}