package ru.fssprus.r82.utils;

public class TimeUtils {

	public static int[] splitToComponentTimes(int seconds) {
		int minutes = (int) seconds / 60;
		int remainder = (int) seconds - minutes * 60;

		int[] ints = { minutes, remainder };
		return ints;
	}
	
	public static String stringTimes(int seconds) {
		int[] intTimes = splitToComponentTimes(seconds);
		return intTimes[0] + ":" + intTimes[1];
	}
	
	public static int getQuizzTimeSecByLevel(int level) {
		int timeSeconds = 0;
		switch (level) {
		case 0:
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("base.time"));
			break;
		case 1:
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("standart.time"));
			break;
		case 2:
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("advanced.time"));
			break;
		case 3:
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("reserve.time"));
			break;
		}
		return timeSeconds;
	}
	
}
