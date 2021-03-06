package com.github.immortalmice.foodpower.container.tutorialbook;

import java.util.ArrayList;
import java.util.List;

import com.github.immortalmice.foodpower.baseclass.ContainerBase;
import com.github.immortalmice.foodpower.container.tutorialbook.page.IPage;
import com.github.immortalmice.foodpower.container.tutorialbook.page.IngredientPage;
import com.github.immortalmice.foodpower.container.tutorialbook.page.IngredientsPage;
import com.github.immortalmice.foodpower.container.tutorialbook.page.MainPage;
import com.github.immortalmice.foodpower.lists.Containers.ContainerTypes;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.specialclass.TutorialBook;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntReferenceHolder;

public class TutorialBookContainer extends ContainerBase {
	public final Navigator navigator;
	public static final List<IPage> PAGES = new ArrayList<>();

	static {
		TutorialBookContainer.PAGES.add(new MainPage());
		Navigator.HOME_PAGE = TutorialBookContainer.PAGES.size() - 1;
		
		Navigator.TUTORIAL_PAGE = TutorialBookContainer.PAGES.size() - 1;
		
		TutorialBookContainer.PAGES.add(new IngredientsPage());
		Navigator.INGREDIENTS_PAGE = TutorialBookContainer.PAGES.size() - 1;
		Ingredients.list.forEach(ingredient -> {
			TutorialBookContainer.PAGES.add(new IngredientPage(ingredient));
		});
		
		Navigator.PATTERNS_PAGE = TutorialBookContainer.PAGES.size() - 1;
		
		Navigator.BOSSES_PAGE = TutorialBookContainer.PAGES.size() - 1;
	}

	public TutorialBookContainer(int windowIdIn, PlayerInventory playerInventory, PacketBuffer extraData){
		this(windowIdIn, playerInventory, extraData.readInt());
	}
	
	public TutorialBookContainer(int windowIdIn, PlayerInventory inventoryIn, int pageIndexIn) {
		super(ContainerTypes.TUTORIAL_BOOK, windowIdIn, new int[] { -1, -1 }, inventoryIn);
		
		this.navigator = new Navigator(pageIndexIn);
		
		this.trackInt(new IntReferenceHolder() {	
			@Override
			public void set(int index) {
				TutorialBookContainer.this.navigator.setPage(index);
			}
			@Override
			public int get() {
				return TutorialBookContainer.this.navigator.getPage();
			}
		});
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		
		ItemStack stack = playerIn.inventory.getCurrentItem();
		if(stack.getItem() instanceof TutorialBook && stack.hasTag()) {
			stack.getTag().putInt("page", this.navigator.getPage());
		}
	}
	
	public static class Navigator {
		private int pageIndex;
		private static int HOME_PAGE, TUTORIAL_PAGE, INGREDIENTS_PAGE, PATTERNS_PAGE, BOSSES_PAGE;
		
		private Navigator() {
			this(0);
		}
		
		private Navigator(int pageIn) {
			this.pageIndex = pageIn;
		}

		public void setPage(int index) {
			this.pageIndex = index;
		}
		
		public void nextPage() {
			this.setPage(Math.min(this.pageIndex + 1, TutorialBookContainer.PAGES.size() - 1));
		}
		
		public void previousPage() {
			this.setPage(Math.max(this.pageIndex - 1, 0));
		}
		
		public void homePage() {
			this.setPage(Navigator.HOME_PAGE);
		}
		
		public void tutorialPage() {
			this.tutorialPage(0);
		}
		
		public void tutorialPage(int offset) {
			this.setPage(Navigator.TUTORIAL_PAGE + offset);
		}
		
		public void ingredientsPage() {
			this.ingredientsPage(0);
		}
		
		public void ingredientsPage(int offset) {
			this.setPage(Navigator.INGREDIENTS_PAGE + offset);
		}
		
		public void patternsPage() {
			this.patternsPage(0);
		}
		
		public void patternsPage(int offset) {
			this.setPage(Navigator.PATTERNS_PAGE + offset);
		}
		
		public void bossesPage() {
			this.bossesPage(0);
		}
		
		public void bossesPage(int offset) {
			this.setPage(Navigator.BOSSES_PAGE + offset);
		}
		
		public int getPage() {
			return this.pageIndex;
		}
	}
}
