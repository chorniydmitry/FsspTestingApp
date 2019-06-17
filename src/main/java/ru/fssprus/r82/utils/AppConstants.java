package ru.fssprus.r82.utils;

import java.awt.Color;

public class AppConstants {
	// Константы разделов приложения
	public static final int SECTIONS_AMOUNT = 7;
	public static final String ADMIN_SECTION = "ADMIN";
	public static final String CONFIG_SECTION = "CONFIG";
	public static final String SETTINGS_SECTION = "SETTINGS";
	public static final String QUESTION_EDIT_SECTION = "QEDIT";
	public static final String QUESTION_LOAD_SECTION = "QLOAD";
	public static final String MANAGE_PASSWORDS_SECTION = "PASSWORDS";
	public static final String TEST_SECTION = "TEST";
	public static final String STATISTICS_SECTION = "STATISTICS";

	public static final String[] SECTIONS_NAMES_ARR = { TEST_SECTION, STATISTICS_SECTION, 
			ADMIN_SECTION, CONFIG_SECTION, QUESTION_EDIT_SECTION, QUESTION_LOAD_SECTION, 
			MANAGE_PASSWORDS_SECTION };

	public static final String ADMIN_TEXT = "Администрирование";
	public static final String CONFIG_TEXT = "Конфигурация";
	public static final String SETTINGS_TEXT = "Настройки";
	public static final String QUESTION_EDIT_TEXT = "Редактор вопросов";
	public static final String QUESTION_LOAD_TEXT = "Загрузка вопросов";
	public static final String MANAGE_PASSWORDS_TEXT = "Редактор паролей";
	public static final String TEST_TEXT = "Тестирование";
	public static final String STATISTICS_TEXT = "Статистика";

	public static final String[] SECTIONS_TEXT_ARR = { TEST_TEXT, STATISTICS_TEXT, 
			ADMIN_TEXT, CONFIG_TEXT,QUESTION_EDIT_TEXT, QUESTION_LOAD_TEXT, 
			MANAGE_PASSWORDS_TEXT };
	
	//прочие константы
	public static final int MAX_ANSWERS_AMOUNT = 5;
	public static final int MIN_ANSWERS_AMOUNT = 2;
	public static final int QUESTION_TEXT_MIN_LENGTH = 5;
	public static final int MINIMUM_QUESTIONS_TO_INIT_TEST = 30;
	public static final Color TABLE_SELECTION_COLOR = new Color(200, 255, 200);
}
