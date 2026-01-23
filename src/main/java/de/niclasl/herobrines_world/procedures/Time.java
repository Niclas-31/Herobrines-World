package de.niclasl.herobrines_world.procedures;

import java.util.Calendar;

public class Time {
	public static String execute() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);
	}
}