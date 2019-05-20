package ru.fssprus.r82.utils;

public class TimeConverter {

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
	
}
