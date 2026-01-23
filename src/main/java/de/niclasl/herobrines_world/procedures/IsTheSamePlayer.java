package de.niclasl.herobrines_world.procedures;

import net.minecraft.world.entity.Entity;

public class IsTheSamePlayer {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!entity.isInvisible()) {
			entity.setInvisible(true);
		} else if (entity.isInvisible()) {
			entity.setInvisible(false);
		}
	}
}