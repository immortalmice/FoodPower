package com.github.immortalmice.foodpower.bus;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.function.Consumer;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.capability.implement.FPFlavorExpCapability;
import com.github.immortalmice.foodpower.capability.implement.FPPatternExpCapability;
import com.github.immortalmice.foodpower.food.Ingredient;
import com.github.immortalmice.foodpower.food.Meal;
import com.github.immortalmice.foodpower.handlers.CapabilityHandler;
import com.github.immortalmice.foodpower.handlers.CommandHandler;
import com.github.immortalmice.foodpower.handlers.LootTableHandler;
import com.github.immortalmice.foodpower.lists.Capabilities;
import com.github.immortalmice.foodpower.lists.Effects.FoodEffects;
import com.github.immortalmice.foodpower.lists.Ingredients;
import com.github.immortalmice.foodpower.lists.OtherBlocks;

public class ForgeEventHandlers{
	private static final IEventBus BUS = MinecraftForge.EVENT_BUS;

	public static void registAllEvent(){
		ForgeEventHandlers.BUS.register(ForgeEventHandlers.class);
		ForgeEventHandlers.BUS.register(new ForgeEventHandlers());
	}

	@SubscribeEvent
	public static void onServerLoad(FMLServerStartingEvent event){
		CommandHandler.registAllCommand(event.getCommandDispatcher());
	}

	@SubscribeEvent
	public static void onLootLoad(LootTableLoadEvent event){
		LootTableHandler.modify(event);
	}

	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent event){
		ItemStack stack = event.getPlayer().getHeldItem(event.getHand());
		if(stack.getItem() instanceof Meal){
			ListNBT nbt = Meal.getIngredientsListNBT(stack);
			List<Ingredient> ingrdients = Meal.getIngredients(nbt);
			for(Ingredient ingredient : ingrdients){
				ingredient.applyInteractEffect(event, Meal.getIngredientLevel(nbt, ingredient));
			}
		}
	}
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onItemTooltip(ItemTooltipEvent event){
		Item item = event.getItemStack().getItem();
		Ingredient ingredient = Ingredients.getIngredientByItem(item);

		if(ingredient != null){
			Ingredient.addFoodAndFlavorTooltip(ingredient, event.getToolTip());
		}
	}

	@SubscribeEvent
	public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event){
		if(event.getObject() instanceof PlayerEntity){
			event.addCapability(new ResourceLocation(FoodPower.MODID, "pattern_exp_capability"), new FPPatternExpCapability.Provider());
			event.addCapability(new ResourceLocation(FoodPower.MODID, "flavor_exp_capability"), new FPFlavorExpCapability.Provider());
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event){
		/* Copy capability data to new player */
		for(Capability<?> cap : Capabilities.list){
			CapabilityHandler.copyCapabilityData(event.getOriginal(), event.getPlayer(), cap);
		}
	}
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event){
		PlayerEntity player = event.player;
		if(!player.world.isRemote){
			if(player.isPotionActive(FoodEffects.MINT_POWER)) {
				int level = player.getActivePotionEffect(FoodEffects.MINT_POWER).getAmplifier();
				FrostWalkerEnchantment.freezeNearby(player, player.world, player.getPosition(), level);
			}else if(player.isPotionActive(FoodEffects.SPINACH_POWER)) {
				int level = player.getActivePotionEffect(FoodEffects.SPINACH_POWER).getAmplifier();
				int range = (int) Math.pow(2, level);
				BlockPos pos = player.getPosition();
				Float probability = (new Float[]{0.05f, 0.1f, 0.2f})[Math.min(level, 2)];
				for(int y = pos.getY() - 1; y <= pos.getY() + 1; y ++) {
					for(int x = pos.getX() - range; x <= pos.getX() + range; x ++) {
						for(int z = pos.getZ() - range; z <= pos.getZ() + range; z ++) {
							BlockState state = player.world.getBlockState(new BlockPos(x, y, z));
							Block block = state.getBlock();
							if(block instanceof CropsBlock && player.world.rand.nextFloat() <= probability) {
								block.tick(state, (ServerWorld) player.world, new BlockPos(x, y, z), player.world.rand);
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onEnderTeleport(EnderTeleportEvent event){
		LivingEntity living = event.getEntityLiving();
		if(!living.world.isRemote && living instanceof PlayerEntity && living.isPotionActive(FoodEffects.FERMENTED_ENDEREYE_POWER)){
			int level = living.getActivePotionEffect(FoodEffects.FERMENTED_ENDEREYE_POWER).getAmplifier();
			
			float damage = event.getAttackDamage() / 2;
			if(level >= 2) damage = 0;
			
			event.setAttackDamage(damage);
		}
	}
	
	@SubscribeEvent
	public static void onAttackEntity(AttackEntityEvent event){
		PlayerEntity player = event.getPlayer();
		if(!player.world.isRemote){
			if(player.isPotionActive(FoodEffects.KETCHUP_POWER) && event.getTarget() instanceof MobEntity){
				int level = player.getActivePotionEffect(FoodEffects.KETCHUP_POWER).getAmplifier();
				MobEntity entity = (MobEntity) event.getTarget();
				float probability = player.world.rand.nextFloat();
				if((level == 0 && probability <= 0.2)
					|| (level == 1 && probability <= 0.5)
					|| (level >= 2)){
					entity.setAttackTarget(null);
				}
			}
			if(player.isPotionActive(FoodEffects.CORN_POWER) && event.getTarget() instanceof ZombieVillagerEntity){
				int level = player.getActivePotionEffect(FoodEffects.CORN_POWER).getAmplifier();
				ZombieVillagerEntity entity = (ZombieVillagerEntity) event.getTarget();
				float probability = player.world.rand.nextFloat();
				
				if((level == 0 && probability <= 0.15)
					|| (level == 1 && probability <= 0.45)
					|| (level >= 2 && probability <= 0.8)){
					
					CompoundNBT nbt = new CompoundNBT();
					nbt.putInt("ConversionTime", 200);
					nbt.putUniqueId("ConversionPlayer", PlayerEntity.getUUID(player.getGameProfile()));
					
					entity.readAdditional(nbt);
				}
			}
			if(player.isPotionActive(FoodEffects.POTATO_POWER) && event.getTarget() instanceof MobEntity){
				int level = (int) Math.pow(2, player.getActivePotionEffect(FoodEffects.POTATO_POWER).getAmplifier());
				((MobEntity) event.getTarget()).addPotionEffect(new EffectInstance(Effects.POISON, 100, level - 1));
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onItemCrafted(ItemCraftedEvent event){
		PlayerEntity player = event.getPlayer();
		if(!player.world.isRemote && player.isPotionActive(FoodEffects.SAUCE_POWER)){
			int level = player.getActivePotionEffect(FoodEffects.SAUCE_POWER).getAmplifier();
			float probability = player.world.rand.nextFloat();
			IInventory inventory = event.getInventory();
			for(int i = 0; i <= inventory.getSizeInventory()-1; i ++){
				ItemStack stack = inventory.getStackInSlot(i);
				if(stack.getItem() == Items.BROWN_DYE){
					if((level == 0 && probability <= 0.1)
						|| (level == 1 && probability <= 0.3)
						|| (level >= 2 && probability <= 0.6)){

						player.inventory.placeItemBackInInventory(player.world, new ItemStack(Items.BROWN_DYE));
						player.openContainer.detectAndSendChanges();
						break;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event){
		Entity sourceEntity = event.getSource().getTrueSource();
		LivingEntity target = event.getEntityLiving();
		if(target instanceof PlayerEntity
			&& sourceEntity instanceof LivingEntity
			&& !target.world.isRemote
			&& ((PlayerEntity) target).isPotionActive(FoodEffects.RICE_POWER)){
			
			LivingEntity source = (LivingEntity) sourceEntity;
			int level = ((PlayerEntity) target).getActivePotionEffect(FoodEffects.RICE_POWER).getAmplifier();
			ItemStack selectedArmor = ItemStack.EMPTY;
			EquipmentSlotType selectedType = null;
			int damage = 0;
			float probability = 0;
			switch(level){
				case 2:
					if(source.hasItemInSlot(EquipmentSlotType.CHEST)){
						selectedType = EquipmentSlotType.CHEST;
					}else if(source.hasItemInSlot(EquipmentSlotType.HEAD)){
						selectedType = EquipmentSlotType.HEAD;
					}
					damage = 80;
					probability = 1f;
				case 1:
					if(source.hasItemInSlot(EquipmentSlotType.LEGS) && selectedArmor.isEmpty()){
						selectedType = EquipmentSlotType.LEGS;
					}
					damage = damage != 0 ? damage : 40;
					probability = probability != 0 ? probability : 1f;
				case 0:
					if(source.hasItemInSlot(EquipmentSlotType.FEET) && selectedArmor.isEmpty()){
						selectedType = EquipmentSlotType.FEET;
					}
					damage = damage != 0 ? damage :  20;
					probability = probability != 0 ? probability : 1f;
			}
			selectedArmor = selectedType == null ? selectedArmor : source.getItemStackFromSlot(selectedType);
			final EquipmentSlotType type = selectedType;
			if(!selectedArmor.isEmpty() && source.world.rand.nextFloat() <= probability){
				selectedArmor.damageItem(damage, (LivingEntity) source, entity -> {
					entity.sendBreakAnimation(type);;
				});
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event){
		LivingEntity entity = event.getEntityLiving();
		LivingEntity target = event.getTarget();
		if(target != null && !target.world.isRemote){
			if(target.isPotionActive(FoodEffects.CHEESE_POWER) && entity instanceof CreeperEntity) {
				CompoundNBT tag = target.getPersistentData();
				int cooldown = tag.contains("cheese_cobweb_cooldown") ? tag.getInt("cheese_cobweb_cooldown") : 0;
				if(cooldown > 0){
					cooldown --;
					tag.putInt("cheese_cobweb_cooldown", cooldown);
					return;
				}
				
				int level = target.getActivePotionEffect(FoodEffects.CHEESE_POWER).getAmplifier();
				World world = entity.world;
				Consumer<BlockPos> trySetCobweb = pos -> {
					if(world.getBlockState(pos).isAir()){
						world.setBlockState(pos, OtherBlocks.Blocks.CHEESE_COBWEB.getDefaultState());
					}
				};
				switch(level){
					case 0:
						if(world.rand.nextFloat() <= 0.2f){
							trySetCobweb.accept(entity.getPosition());
						}
						break;
					case 1:
						if(world.rand.nextFloat() <= 0.5f){
							trySetCobweb.accept(entity.getPosition());
						}
						break;
					case 2:
						int i = -1, j = -1;
						while(i <= 1){
							BlockPos pos = entity.getPosition().add(i, 0, j);
							trySetCobweb.accept(pos);
							j ++;
							if(j > 1){
								i ++;
								j = -1;
							}
						}
				}
				tag.putInt("cheese_cobweb_cooldown", 200);
			}else if(target.isPotionActive(FoodEffects.PUMPKIN_POWER) && entity instanceof EndermanEntity){
				int level = target.getActivePotionEffect(FoodEffects.PUMPKIN_POWER).getAmplifier();
				float probability = (new float[]{ 0.2f, 0.6f, 1.0f })[Math.min(level, 2)];
				if(target.world.rand.nextFloat() <= probability) {
					((EndermanEntity) entity).setAttackTarget(null);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingDamaged(LivingDamageEvent event){
		Entity sourceEntity = event.getSource().getTrueSource();
		
		if(sourceEntity instanceof PlayerEntity
			&& !sourceEntity.world.isRemote
			&& ((PlayerEntity) sourceEntity).isPotionActive(FoodEffects.CHILI_POWER)){
			
			int level = ((PlayerEntity) sourceEntity).getActivePotionEffect(FoodEffects.CHILI_POWER).getAmplifier();
			boolean isApproved = false;
			if(level >= 2 || sourceEntity == event.getSource().getImmediateSource()){
				isApproved = true;
			}
			
			if(isApproved) event.setAmount(event.getAmount() + (level + 1) * 2);
		}
	}
}