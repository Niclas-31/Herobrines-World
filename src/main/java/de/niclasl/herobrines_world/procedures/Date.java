package de.niclasl.herobrines_world.procedures;

import java.util.Calendar;

public class Date {
	public static String execute() {
		return new java.text.SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
	}
}