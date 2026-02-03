package de.niclasl.herobrines_world.procedures;

import de.niclasl.herobrines_world.network.ModVariables;
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
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class Herobrine {

	private static final int SOUL_PER_LEVEL = 100;

    @SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (!(event.getSource().getEntity() instanceof LivingEntity)) return;
        execute(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource().getEntity());
    }

	private static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (!(sourceentity instanceof LivingEntity _livEnt)) return;

		ModVariables.PlayerVariables playerVars = sourceentity.getData(ModVariables.PLAYER_VARIABLES);

        ItemStack mainHand = _livEnt.getMainHandItem();
		int enchLevel = mainHand.getEnchantmentLevel(
				world.registryAccess()
						.lookupOrThrow(Registries.ENCHANTMENT)
						.getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("herobrines_world:herobrine")))
		);

		if (enchLevel == 0) return;

		RandomSource random = RandomSource.create();

		int min, max;
		switch (enchLevel) {
			case 4 -> { min = 3; max = 4; }
			case 3 -> { min = 2; max = 3; }
			case 2 -> { min = 1; max = 2; }
			default -> { min = 0; max = 1; }
		}

		int bolts = Mth.nextInt(random, min, max);

		if (bolts == 0) return;

		int soulCost = bolts * 5;

		if (soulCost > 0) {
			if (!tryConsumeSoul(playerVars)) {
				return;
			}
		}

		for (int i = 0; i < bolts; i++) {
			double posX = x + random.nextInt(3) * (random.nextBoolean() ? 1 : -1);
			double posY = y + random.nextInt(3) * (random.nextBoolean() ? 1 : -1);
			double posZ = z + random.nextInt(3) * (random.nextBoolean() ? 1 : -1);

			if (world instanceof ServerLevel _level) {
				EntityType.LIGHTNING_BOLT.spawn(_level, BlockPos.containing(posX, posY, posZ), EntitySpawnReason.MOB_SUMMONED);
			}
		}
	}

	private static boolean tryConsumeSoul(ModVariables.PlayerVariables vars) {
		int totalSoul =
				vars.Soul_Current +
						vars.Soul_Level * SOUL_PER_LEVEL;

		if (totalSoul < 5) {
			return false;
		}

		totalSoul -= 5;

		vars.Soul_Level = totalSoul / SOUL_PER_LEVEL;
		vars.Soul_Current = totalSoul % SOUL_PER_LEVEL;

		vars.markSyncDirty();
		return true;
	}
}
