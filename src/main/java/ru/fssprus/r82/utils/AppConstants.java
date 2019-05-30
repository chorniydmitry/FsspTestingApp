package ru.fssprus.r82.utils;

public class AppConstants {
	// Константы конфигурации приложения
	public static final int BASE_QUESTS = Integer
			.parseInt(ApplicationConfiguration.getItem("base.num"));
	public static final int STANDART_QUESTS = Integer
			.parseInt(ApplicationConfiguration.getItem("standart.num"));
	public static final int ADVANCED_QUESTS = Integer
			.parseInt(ApplicationConfiguration.getItem("advanced.num"));
	public static final int RESERVE_QUESTS = Integer
			.parseInt(ApplicationConfiguration.getItem("reserve.num"));

	public static final int BASE_TIME = Integer
			.parseInt(ApplicationConfiguration.getItem("base.time"));
	public static final int STANDART_TIME = Integer
			.parseInt(ApplicationConfiguration.getItem("standart.time"));
	public static final int ADVANCED_TIME = Integer
			.parseInt(ApplicationConfiguration.getItem("advanced.time"));
	public static final int RESERVE_TIME = Integer
			.parseInt(ApplicationConfiguration.getItem("reserve.time"));

	public static final int BASE_COMMONS = Integer
			.parseInt(ApplicationConfiguration.getItem("base.common.percent"));
	public static final int STANDART_COMMONS = Integer
			.parseInt(ApplicationConfiguration.getItem("standart.common.percent"));
	public static final int ADVANCED_COMMONS = Integer
			.parseInt(ApplicationConfiguration.getItem("advanced.common.percent"));
	public static final int RESERVE_COMMONS = Integer
			.parseInt(ApplicationConfiguration.getItem("reserve.common.percent"));

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

}
