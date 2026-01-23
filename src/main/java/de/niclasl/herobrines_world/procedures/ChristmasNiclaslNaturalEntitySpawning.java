package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import java.util.Calendar;

public class ChristmasNiclaslNaturalEntitySpawning {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if (world.getBiome(BlockPos.containing(x, y, z)).is(ResourceLocation.parse("herobrines_world:frozen_forest"))) {
			return true;
		}
        return world.getBiome(BlockPos.containing(x, y, z)).is(ResourceLocation.parse("herobrines_world:fire_land")) && Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER
                && (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 24 || Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 25 || Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 26);
    }

}