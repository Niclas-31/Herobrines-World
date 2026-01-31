package de.niclasl.herobrines_world.entity;

import de.niclasl.herobrines_world.entity.custom.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import de.niclasl.herobrines_world.HerobrinesWorld;

@EventBusSubscriber
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, HerobrinesWorld.MODID);

	public static final DeferredHolder<EntityType<?>, EntityType<HerobrineBoss>> HEROBRINE_BOSS = register("herobrine_boss",
			EntityType.Builder.of(HerobrineBoss::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().ridingOffset(-0.6f).sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<Niclasl>> NICLASL = register("niclasl",
			EntityType.Builder.of(Niclasl::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().ridingOffset(-0.6f).sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<Entity303>> ENTITY_303 = register("entity_303",
			EntityType.Builder.of(Entity303::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().ridingOffset(-0.6f).sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<StatueEntity303>> STATUE_ENTITY_303 = register("statue_entity_303",
			EntityType.Builder.of(StatueEntity303::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().ridingOffset(-0.6f).sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<GoodHerobrine>> GOOD_HEROBRINE = register("good_herobrine",
			EntityType.Builder.of(GoodHerobrine::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().ridingOffset(-0.6f).sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<BadHerobrine>> BAD_HEROBRINE = register("bad_herobrine",
			EntityType.Builder.of(BadHerobrine::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).ridingOffset(-0.6f).sized(0.6f, 1.8f));

	public static final DeferredHolder<EntityType<?>, EntityType<ChristmasNiclasl>> CHRISTMAS_NICLASL = register("christmas_niclasl",
			EntityType.Builder.of(ChristmasNiclasl::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().ridingOffset(-0.6f).sized(0.6f, 1.8f));

	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryName, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryName, () -> entityTypeBuilder.build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(HerobrinesWorld.MODID, registryName))));
	}

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent event) {
		Niclasl.init(event);
		StatueEntity303.init(event);
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
}
