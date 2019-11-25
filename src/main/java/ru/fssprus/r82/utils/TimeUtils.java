package ru.fssprus.r82.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.fssprus.r82.entity.QuestionLevel;

/**
 * @author Chernyj Dmitry
 *
 */
public class TimeUtils {
	private static final int DATE_DAY_END_POSITION = 2;
	private static final int DATE_MONTH_END_POSITION = 5;

	public static String convertToStandart(String dateString) {
		String convertedDate = dateString.replaceAll("[^\\d]", "");
		
		if(convertedDate.length() >= 7) {
		convertedDate = convertedDate.substring(0, DATE_DAY_END_POSITION) + "."
				+ convertedDate.substring(DATE_DAY_END_POSITION, convertedDate.length());
		convertedDate = convertedDate.substring(0, DATE_MONTH_END_POSITION) + "."
				+ convertedDate.substring(DATE_MONTH_END_POSITION, convertedDate.length());
		} else convertedDate = null;

		return convertedDate;
	}
	
	public static Date getDate(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		dateFormat.setLenient(false);
		
		Date dateFound = null;
		try {
			dateFound = dateFormat.parse(dateString.trim());
		} catch (ParseException pe) {
			System.err.println("НЕ ВЕРНАЯ СТРОКА ВРЕМЕНИ!!!");
			return null;
		}
		
		return dateFound;
	}

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
