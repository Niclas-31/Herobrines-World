package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

public class HerobrineWithRedEyesOnEntityTickUpdate {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof Level _lvl0 && _lvl0.isBrightOutside() && world.canSeeSkyFromBelowWater(BlockPos.containing(x, y, z))) {
			entity.igniteForSeconds(5);
		}
	}
}