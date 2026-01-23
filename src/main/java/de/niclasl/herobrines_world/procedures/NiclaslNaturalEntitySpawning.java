package de.niclasl.herobrines_world.procedures;

import java.util.Calendar;

public class NiclaslNaturalEntitySpawning {
	public static boolean execute() {
		if (Calendar.getInstance().get(Calendar.MONTH) <= 11) {
			return true;
		} else if (Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER && (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 24 || Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 25 || Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 26)) {
			return false;
		}
		return false;
	}
}