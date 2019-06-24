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
	
	// Размеры окон
	public static final int DIALOG_CONFIG_WIDTH = 800;
	public static final int DIALOG_CONFIG_HEIGHT = 600;
	
	public static final int DIALOG_LOADING_QUESTION_SET_WIDTH = 600;
	public static final int DIALOG_LOADING_QUESTION_SET_HEIGHT = 200;
	
	public static final int DIALOG_QUESTUIN_EDIT_WIDTH = 1024;
	public static final int DIALOG_QUESTUIN_EDIT_HEIGHT = 768;
	
	public static final int DIALOG_MANAGE_PASSWORDS_WIDTH = 600;
	public static final int DIALOG_MANAGE_PASSWORDS_HEIGHT = 300;
	
	public static final int DIALOG_TEST_WIDTH = 1000;
	public static final int DIALOG_TEST_HEIGHT = 800;
	
	// Прочие константы
	public static final int TABLE_ROWS_LIMIT = 25;
	public static final int MAX_ANSWERS_AMOUNT = 5;
	public static final int MIN_ANSWERS_AMOUNT = 2;
	public static final int QUESTION_TEXT_MIN_LENGTH = 5;
	public static final int MINIMUM_QUESTIONS_TO_INIT_TEST = 30;
	public static final Color TABLE_SELECTION_COLOR = new Color(0, 0, 255);
	public static final int NO_ROW_SELECTED = -1;
	public static final int NO_SQL_LIMIT_START_SPECIFIED = -1;
	public static final int NO_SQL_LIMIT_MAX_SPECIFIED = -1;
	
	public static final String VALIDATION_ANSWER_NOT_EMPTY = "Не заполнен ответ!";
	public static final String VALIDATION_ANSWER_WRONG_SIZE = 
			"Не верная длина ответа (должна быть от 2 до 2048 символов)";
	
	public static final String VALIDATION_QUESTION_TITLE_EMPTY = "Не заполнена Формулировка вопроса!";
	public static final String VALIDATION_QUESTION_TITLE_SIZE = 
			"Не верная длина вопроса (должна быть от 2 до 2048 символов)";
	
	public static final String VALIDATION_SPECIFICATION_TITLE_EMPTY = "Не заполнено название специализации!";
	public static final String VALIDATION_SPECIFICATION_TITLE_SIZE = 
			"Не верная длина названия специализации (должна быть от 2 до 2048 символов)";

	public static final String DIALOG_LOADING_QUEST_SET_ABOUT_INFO = "<html><p width = 580>Данная форма предназначена"
			+ "для загрузки в базу данных вопросов, выгруженных из базы данных "
			+ "приложения ФССП тест в фотматах XLSX или ODS</p><html>";
}
