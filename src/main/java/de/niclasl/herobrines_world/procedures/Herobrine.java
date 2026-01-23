package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.*;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber
public class Herobrine {
	@SubscribeEvent
	public static void onEntityDeath(LivingDamageEvent.Pre event) {
        execute(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource().getEntity());
    }

	private static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double posX;
		double posY;
		double posZ;
		double random;
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)
				.getEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("herobrines_world:herobrine")))) != 0) {
			if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)
					.getEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("herobrines_world:herobrine")))) == 4) {
				for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 3, 4); index0++) {
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posX = x + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posX = x - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posY = y + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posY = y - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posZ = z + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posZ = z - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					if (world instanceof ServerLevel _level) {
                        EntityType.LIGHTNING_BOLT.spawn(_level, BlockPos.containing(posX, posY, posZ), EntitySpawnReason.MOB_SUMMONED);
                    }
				}
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)
					.getEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("herobrines_world:herobrine")))) == 3) {
				for (int index1 = 0; index1 < Mth.nextInt(RandomSource.create(), 2, 3); index1++) {
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posX = x + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posX = x - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posY = y + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posY = y - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posZ = z + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posZ = z - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					if (world instanceof ServerLevel _level) {
                        EntityType.LIGHTNING_BOLT.spawn(_level, BlockPos.containing(posX, posY, posZ), EntitySpawnReason.MOB_SUMMONED);
                    }
				}
			} else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)
					.getEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("herobrines_world:herobrine")))) == 2) {
				for (int index2 = 0; index2 < Mth.nextInt(RandomSource.create(), 1, 2); index2++) {
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posX = x + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posX = x - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posY = y + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posY = y - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posZ = z + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posZ = z - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					if (world instanceof ServerLevel _level) {
                        EntityType.LIGHTNING_BOLT.spawn(_level, BlockPos.containing(posX, posY, posZ), EntitySpawnReason.MOB_SUMMONED);
                    }
				}
			} else {
				for (int index3 = 0; index3 < Mth.nextInt(RandomSource.create(), 0, 1); index3++) {
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posX = x + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posX = x - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posY = y + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posY = y - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					random = Mth.nextInt(RandomSource.create(), 0, 1);
					if (random == 1) {
						posZ = z + Mth.nextInt(RandomSource.create(), 0, 2);
					} else {
						posZ = z - Mth.nextInt(RandomSource.create(), 0, 2);
					}
					if (world instanceof ServerLevel _level) {
                        EntityType.LIGHTNING_BOLT.spawn(_level, BlockPos.containing(posX, posY, posZ), EntitySpawnReason.MOB_SUMMONED);
                    }
				}
			}
		}
	}
}