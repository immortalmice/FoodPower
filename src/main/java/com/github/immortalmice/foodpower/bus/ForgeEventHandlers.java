package com.github.immortalmice.foodpower.bus;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.github.immortalmice.foodpower.FoodPower;
import com.github.immortalmice.foodpower.capability.implement.FPFlavorExpCapability;
import com.github.immortalmice.foodpower.capability.implement.FPPatternExpCapability;
import com.github.immortalmice.foodpower.food.Ingredient;
import com.github.immortalmice.foodpower.food.Meal;
import com.github.immortalmice.foodpower.handlers.CapabilityHandler;
import com.github.immortalmice.foodpower.handlers.CommandHandler;
import com.github.immortalmice.foodpower.handlers.ConfigHandler;
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
				Boolean isDisabled = ConfigHandler.SERVER.disables.get(ingredient.asItem().getRegistryName().getPath());
	        	if(isDisabled == null || !isDisabled) {
	        		ingredient.applyInteractEffect(event, Meal.getIngredientLevel(nbt, ingredient));
	        	}
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		PlayerEntity player = event.getPlayer();
		if(!player.world.isRemote && player.isPotionActive(FoodEffects.HONEY_BOTTLE_POWER)) {
			BlockPos pos = event.getPos();
			BlockState state = player.world.getBlockState(pos);
			int level = player.getActivePotionEffect(FoodEffects.HONEY_BOTTLE_POWER).getAmplifier();
			if(state.getProperties().contains(BlockStateProperties.HONEY_LEVEL) && state.get(BlockStateProperties.HONEY_LEVEL) == 5) {
				ItemEntity itemEntity = null;
				if(event.getItemStack().getItem() == Items.SHEARS) {
					itemEntity = new ItemEntity(player.world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.HONEYCOMB, level + 1));
				}else if(event.getItemStack().getItem() == Items.GLASS_BOTTLE) {
					itemEntity = new ItemEntity(player.world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.HONEY_BOTTLE, level));
				}
				if(itemEntity != null)
					player.world.addEntity(itemEntity);
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
			}else if(player.isPotionActive(FoodEffects.OIL_POWER)) {
				int level = player.getActivePotionEffect(FoodEffects.OIL_POWER).getAmplifier();
				float probability = (new float[]{0.05f, 0.15f, 0.35f})[Math.min(level, 2)];
				if(player.world.canBlockSeeSky(player.getPosition())
					&& player.world.getWorldInfo().isRaining()
					&& player.getHeldItemMainhand().getItem() instanceof GlassBottleItem
					&& player.world.rand.nextFloat() <= probability) {
					
					player.getHeldItemMainhand().shrink(1);
					player.addItemStackToInventory(new ItemStack(com.github.immortalmice.foodpower.lists.Ingredients.Items.OIL));
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
		if(!player.world.isRemote && player.isPotionActive(FoodEffects.COCOA_BEANS_POWER)){
			int level = player.getActivePotionEffect(FoodEffects.COCOA_BEANS_POWER).getAmplifier();
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
		if(!target.world.isRemote){
			if(target.isPotionActive(FoodEffects.RICE_POWER)
				&& sourceEntity instanceof LivingEntity
				&& target instanceof PlayerEntity){
				
				LivingEntity source = (LivingEntity) sourceEntity;
				int level = target.getActivePotionEffect(FoodEffects.RICE_POWER).getAmplifier();
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
			}else if(
				sourceEntity instanceof PlayerEntity
				&& ((PlayerEntity) sourceEntity).isPotionActive(FoodEffects.PORKCHOP_POWER)
				&& target instanceof ZombieEntity){
				
				int level = ((PlayerEntity) sourceEntity).getActivePotionEffect(FoodEffects.PORKCHOP_POWER).getAmplifier();
				float probability = (new float[]{ 0.05f, 0.15f, 0.35f })[Math.min(level, 2)];
				
				if(target.world.rand.nextFloat() <= probability){
					BlockPos pos = target.getPosition();
		            World world = target.world;
		            
		            PigEntity pig = new PigEntity(EntityType.PIG, world);
		            pig.setPosition(pos.getX(), pos.getY(), pos.getZ());
		            target.remove(false);
		            world.addEntity(pig);
				}
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
		Entity trueSourceEntity = event.getSource().getTrueSource();
		Entity immediateSourceEntity = event.getSource().getImmediateSource();
		LivingEntity targetEntity = event.getEntityLiving();
		
		if(!targetEntity.world.isRemote) {
			if(trueSourceEntity instanceof PlayerEntity && ((PlayerEntity) trueSourceEntity).isPotionActive(FoodEffects.CHILI_POWER)) {
				int level = ((PlayerEntity) trueSourceEntity).getActivePotionEffect(FoodEffects.CHILI_POWER).getAmplifier();
				boolean isApproved = false;
				if(level >= 2 || trueSourceEntity == immediateSourceEntity){
					isApproved = true;
				}
				
				if(isApproved) event.setAmount(event.getAmount() + (level + 1) * 2);
			}else if(targetEntity.isPotionActive(FoodEffects.SWEET_BERRIES_POWER)) {
				int level = targetEntity.getActivePotionEffect(FoodEffects.SWEET_BERRIES_POWER).getAmplifier();
				if(level == 0) {
					if(immediateSourceEntity instanceof LivingEntity && targetEntity.world.rand.nextFloat() <= 0.15f) {
						((LivingEntity) immediateSourceEntity).setHealth(((LivingEntity) immediateSourceEntity).getHealth() - 1);
					}
				}else if(level == 1) {
					if(immediateSourceEntity instanceof LivingEntity && targetEntity.world.rand.nextFloat() <= 0.35f) {
						((LivingEntity) immediateSourceEntity).setHealth(((LivingEntity) immediateSourceEntity).getHealth() - 2);
					}
				}else if(level >= 2) {
					if(trueSourceEntity instanceof LivingEntity && targetEntity.world.rand.nextFloat() <= 0.7f) {
						((LivingEntity) trueSourceEntity).setHealth(((LivingEntity) trueSourceEntity).getHealth() - 3);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onContainerOpen(PlayerContainerEvent.Open event) {
		if(event.getContainer() instanceof MerchantContainer && event.getPlayer().isPotionActive(FoodEffects.BEETROOT_POWER)) {
			MerchantContainer container = (MerchantContainer) event.getContainer();
			int level = event.getPlayer().getActivePotionEffect(FoodEffects.BEETROOT_POWER).getAmplifier();
			float discount = (new float[]{0.1f, 0.25f, 0.5f})[Math.min(level, 2)];
			try{
				IMerchant merchant = ObfuscationReflectionHelper.getPrivateValue(MerchantContainer.class, container, "field_75178_e");
				if(merchant instanceof VillagerEntity && ((VillagerEntity) merchant).getVillagerData().getProfession() == VillagerProfession.FARMER) {
					MerchantOffers offers = container.getOffers();
					offers.forEach(offer -> {
						if(offer.getSellingStack().getItem() == Items.EMERALD) {
							ItemStack buy = !offer.getBuyingStackFirst().isEmpty() ? offer.getBuyingStackFirst() : offer.getBuyingStackSecond();
							int price = !buy.isEmpty() ? buy.getCount() : 0;
							int specialPrice = (int) (price * discount);
							offer.setSpecialPrice(offer.getSpecialPrice() - specialPrice);
						}
					});
				}
			}catch(Exception e) {
				System.out.print(e);
			}
		}
	}
	
	@SubscribeEvent
	public static void onProjectileImpact(ProjectileImpactEvent event) {
		if(!event.getEntity().world.isRemote && event.getEntity() instanceof EggEntity){
			LivingEntity player = ((EggEntity) event.getEntity()).getThrower();
			if(player instanceof PlayerEntity && player.isPotionActive(FoodEffects.EGG_POWER)) {
				int level = player.getActivePotionEffect(FoodEffects.EGG_POWER).getAmplifier();
				float probability = (new float[]{0.2f, 0.5f, 1.0f})[Math.min(level, 2)];
				if(player.world.rand.nextFloat() <= probability) {
					ChickenEntity chickenentity = EntityType.CHICKEN.create(event.getEntity().world);
					chickenentity.setGrowingAge(-24000);
					chickenentity.setLocationAndAngles(event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), event.getEntity().rotationYaw, 0.0F);
					event.getEntity().world.addEntity(chickenentity);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onArrowImpact(ProjectileImpactEvent.Arrow event) {
		if(event.getRayTraceResult() instanceof EntityRayTraceResult &&
			((EntityRayTraceResult) event.getRayTraceResult()).getEntity() instanceof PlayerEntity &&
			((PlayerEntity) ((EntityRayTraceResult) event.getRayTraceResult()).getEntity()).isPotionActive(FoodEffects.CHORUS_FRUIT_POWER)) {
			
			PlayerEntity player = (PlayerEntity) ((EntityRayTraceResult) event.getRayTraceResult()).getEntity();
			int level = player.getActivePotionEffect(FoodEffects.CHORUS_FRUIT_POWER).getAmplifier();
			float probability = (new float[]{0.3f, 0.6f, 0.9f})[Math.min(level, 2)];
			
			if(player.world.rand.nextFloat() <= probability) {
				event.setCanceled(true);
				
				if(level <= 1) {
					Items.CHORUS_FRUIT.onItemUseFinish(ItemStack.EMPTY, player.world, player);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onPotionApplicable(PotionApplicableEvent event) {
		if(!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(FoodEffects.MILK_BUCKET_POWER)){
			int level = event.getEntityLiving().getActivePotionEffect(FoodEffects.MILK_BUCKET_POWER).getAmplifier();
			Effect effect = event.getPotionEffect().getPotion();
			if((level == 0
				&& (effect == Effects.NAUSEA
					|| effect == Effects.WEAKNESS
					|| effect == Effects.SLOWNESS))
			|| (level == 1
				&& (effect == Effects.NAUSEA
					|| effect == Effects.WEAKNESS
					|| effect == Effects.SLOWNESS
					|| effect == Effects.MINING_FATIGUE
					|| effect == Effects.BLINDNESS)
			|| (level >= 2 && effect.getEffectType() == EffectType.HARMFUL))) {
				
				event.setResult(Result.DENY);
			}
		}
	}
	
	@SubscribeEvent
	public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
		World world = event.getWorld();
		if(!world.isRemote) {
			Optional<PlayerEntity> player = event.getAffectedEntities().stream()
				.filter(entity -> {
					return entity instanceof PlayerEntity && ((PlayerEntity) entity).isPotionActive(FoodEffects.CHICKEN_POWER);
				})
				.map(entity -> (PlayerEntity) entity)
				.findFirst();
			
			if(player.isPresent() && event.getExplosion().getExplosivePlacedBy() instanceof CreeperEntity) {
				int level = player.get().getActivePotionEffect(FoodEffects.CHICKEN_POWER).getAmplifier();
				float probability = (new float[]{0.05f, 0.15f, 0.35f})[Math.min(level, 2)];
				
				if(world.rand.nextFloat() <= probability) {
					event.getAffectedBlocks().clear();
					event.getAffectedEntities().clear();
					Explosion explosion = event.getExplosion();
					for(int i = 1; i <= 3; i ++) {
						ChickenEntity chicken = new ChickenEntity(EntityType.CHICKEN, world);
						chicken.setPosition(explosion.getPosition().x + world.rand.nextDouble() - 0.5, explosion.getPosition().y + 0.5, explosion.getPosition().z + world.rand.nextDouble() - 0.5);
						world.addEntity(chicken);
					}
				}
				
			}
		}
	}
	
	@SubscribeEvent

	public static void onXPPickUp(PlayerXpEvent.XpChange event) {
		PlayerEntity player = event.getPlayer();
		if(!player.world.isRemote && player.isPotionActive(FoodEffects.EXPERIENCE_BOTTLE_POWER)) {
			int level = player.getActivePotionEffect(FoodEffects.EXPERIENCE_BOTTLE_POWER).getAmplifier();
			float factor = (new float[]{1.2f, 2f, 3f})[Math.min(level, 2)];
			event.setAmount((int)(event.getAmount() * factor));
		}
	}
}