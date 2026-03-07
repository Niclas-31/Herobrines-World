package de.niclasl.herobrines_world.entity;

import de.niclasl.herobrines_world.entity.custom.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.HerobrinesWorld;

import java.util.function.Supplier;

@EventBusSubscriber
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
			DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, HerobrinesWorld.MODID);

	public static ResourceKey<EntityType<?>> HEROBRINE_BOSS_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.withDefaultNamespace("herobrine_boss"));
	public static ResourceKey<EntityType<?>> NICLASL_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.withDefaultNamespace("niclasl"));
	public static ResourceKey<EntityType<?>> ENTITY_303_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.withDefaultNamespace("entity_303"));
	public static ResourceKey<EntityType<?>> STATUE_ENTITY_303_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.withDefaultNamespace("statue_entity_303"));
	public static ResourceKey<EntityType<?>> GOOD_HEROBRINE_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.withDefaultNamespace("good_herobrine"));
	public static ResourceKey<EntityType<?>> BAD_HEROBRINE_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.withDefaultNamespace("bad_herobrine"));
	public static ResourceKey<EntityType<?>> CHRISTMAS_NICLASL_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.withDefaultNamespace("christmas_niclasl"));

	public static final Supplier<EntityType<HerobrineBoss>> HEROBRINE_BOSS =
			ENTITY_TYPES.register("herobrine_boss", () -> EntityType.Builder.of(HerobrineBoss::new, MobCategory.MONSTER)
					.sized(0.6f, 1.8f).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().build(HEROBRINE_BOSS_KEY));

	public static final Supplier<EntityType<Niclasl>> NICLASL =
			ENTITY_TYPES.register("niclasl", () -> EntityType.Builder.of(Niclasl::new, MobCategory.MONSTER)
					.sized(0.6f, 1.8f).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).build(NICLASL_KEY));

	public static final Supplier<EntityType<Entity303>> ENTITY_303 =
			ENTITY_TYPES.register("entity_303", () -> EntityType.Builder.of(Entity303::new, MobCategory.MONSTER)
					.sized(0.6f, 1.6f).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().build(ENTITY_303_KEY));

	public static final Supplier<EntityType<StatueEntity303>> STATUE_ENTITY_303 =
			ENTITY_TYPES.register("statue_entity_303", () -> EntityType.Builder.of(StatueEntity303::new, MobCategory.MONSTER)
					.sized(0.6f, 1.8f).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().build(STATUE_ENTITY_303_KEY));

	public static final Supplier<EntityType<GoodHerobrine>> GOOD_HEROBRINE =
			ENTITY_TYPES.register("good_herobrine", () -> EntityType.Builder.of(GoodHerobrine::new, MobCategory.MONSTER)
					.sized(0.6f, 1.8f).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).build(GOOD_HEROBRINE_KEY));

	public static final Supplier<EntityType<BadHerobrine>> BAD_HEROBRINE =
			ENTITY_TYPES.register("bad_herobrine", () -> EntityType.Builder.of(BadHerobrine::new, MobCategory.MONSTER)
					.sized(0.6f, 1.8f).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).build(BAD_HEROBRINE_KEY));

	public static final Supplier<EntityType<ChristmasNiclasl>> CHRISTMAS_NICLASL =
			ENTITY_TYPES.register("christmas_niclasl", () -> EntityType.Builder.of(ChristmasNiclasl::new, MobCategory.MONSTER)
					.sized(0.6f, 1.8f).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).build(CHRISTMAS_NICLASL_KEY));

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent event) {
		Niclasl.init(event);
		GoodHerobrine.init(event);
		BadHerobrine.init(event);
		ChristmasNiclasl.init(event);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(HEROBRINE_BOSS.get(), HerobrineBoss.createAttributes().build());
		event.put(NICLASL.get(), Niclasl.createAttributes().build());
		event.put(ENTITY_303.get(), Entity303.createAttributes().build());
		event.put(STATUE_ENTITY_303.get(), StatueEntity303.createAttributes().build());
		event.put(GOOD_HEROBRINE.get(), GoodHerobrine.createAttributes().build());
		event.put(BAD_HEROBRINE.get(), BadHerobrine.createAttributes().build());
		event.put(CHRISTMAS_NICLASL.get(), ChristmasNiclasl.createAttributes().build());
	}

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}