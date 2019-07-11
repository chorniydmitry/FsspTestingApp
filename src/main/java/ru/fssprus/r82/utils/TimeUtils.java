package ru.fssprus.r82.utils;

import ru.fssprus.r82.entity.QuestionLevel;

public class TimeUtils {

	public static int[] splitToComponentTimes(int seconds) {
		int minutes = (int) seconds / 60;
		int remainder = (int) seconds - minutes * 60;

		int[] ints = { minutes, remainder };
		return ints;
	}

	public static String stringTimes(int seconds) {
		int[] intTimes = splitToComponentTimes(seconds);
		String min = String.valueOf(intTimes[0]);
		String sec = String.valueOf(intTimes[1]);
		if (intTimes[1] < 10)
			sec = "0" + sec;
		return min + ":" + sec;
	}

	public static int getQuizzTimeSecByLevel(QuestionLevel level) {
		int timeSeconds = 0;
		switch (level) {
		case Базовый:
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("base.time"));
			break;
		case Стандартный:
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("standart.time"));
			break;
		case Продвинутый:
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("advanced.time"));
			break;
		case Резерв:
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("reserve.time"));
			break;
		}
		return timeSeconds;
	}

	public static int getQuizzTimeSecByLevel(int index) {
		return getQuizzTimeSecByLevel(QuestionLevel.values()[index]);
	}

}
