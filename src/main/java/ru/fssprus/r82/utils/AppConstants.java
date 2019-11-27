package ru.fssprus.r82.utils;

/**
 * @author Chernyj Dmitry
 *
 */
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppConstants {
	// Константы разделов приложения
	public static final int SECTIONS_AMOUNT = 7;
	
	public static final String QUESTION_LOAD_ICON = "/questionload.png";
	public static final String ADMIN_ICON = "/admin.png";
	public static final String CONFIG_ICON = "/config.png";
	public static final String NEW_TEST_ICON = "/newtest.png";
	public static final String MANAGE_PASSWORDS_ICON = "/password.png";
	public static final String QUESTION_EDIT_ICON = "/questionedit.png";
	public static final String RESULTING_ICON = "/result.png";
	public static final String STATISTICS_ICON = "/statistics.png";
	public static final String TEST_ICON = "/quizz.png";
	public static final String WRONG_ANSWERS_ICON = "/wronganswers.png";
	public static final String EXIT_ICON = "/exit.png";
	
	public static final String QUESTION_LOAD_DIALOG = "LoadingQuestionSetDialog";
	public static final String ADMIN_DIALOG = "AdminDialog";
	public static final String CONFIG_DIALOG = "ConfigDialog";
	public static final String NEW_TEST_DIALOG = "NewTestDialog";
	public static final String MANAGE_PASSWORDS_DIALOG = "PasswordManageDialog";
	public static final String QUESTION_EDIT_DIALOG = "QuestionListDialog";
	public static final String RESULTING_DIALOG = "ResultingDialog";
	public static final String STATISTICS_DIALOG = "StatisticsDialog";
	public static final String TEST_DIALOG = "TestDialog";
	public static final String WRONG_ANSWERS_DIALOG = "WrongAnswersDialog";
	
	public static final String[] DIALOG_WITH_PASSWORDS_ARR = { NEW_TEST_DIALOG, STATISTICS_DIALOG, 
			ADMIN_DIALOG, CONFIG_DIALOG, QUESTION_EDIT_DIALOG, QUESTION_LOAD_DIALOG, 
			MANAGE_PASSWORDS_DIALOG };

	public static final String QUESTION_LOAD_TEXT = "Загрузка вопросов";
	public static final String ADMIN_TEXT = "Администрирование";
	public static final String CONFIG_TEXT = "Конфигурация";
	public static final String NEW_TEST_TEXT = "Новый тест";
	public static final String MANAGE_PASSWORDS_TEXT = "Редактор паролей";
	public static final String QUESTION_EDIT_TEXT = "Редактор вопросов";
	public static final String RESULTING_TEXT = "Результаты тестирования";
	public static final String STATISTICS_TEXT = "Статистика";
	public static final String TEST_TEXT = "Тестирование";
	public static final String WRONG_ANSWERS_TEXT = "Не правильные ответы";
	

	public static final String[] DIALOG_WITH_PASSWORDS_TEXT_ARR = { TEST_TEXT, STATISTICS_TEXT, 
			ADMIN_TEXT, CONFIG_TEXT,QUESTION_EDIT_TEXT, QUESTION_LOAD_TEXT, 
			MANAGE_PASSWORDS_TEXT };
	
	// Размеры окон
	public static final int DIALOG_CONFIG_WIDTH = 800;
	public static final int DIALOG_CONFIG_HEIGHT = 600;
	
	public static final int DIALOG_LOADING_QUESTION_SET_WIDTH = 600;
	public static final int DIALOG_LOADING_QUESTION_SET_HEIGHT = 300;
	
	public static final int DIALOG_QUESTUIN_EDIT_WIDTH = 1000;
	public static final int DIALOG_QUESTUIN_EDIT_HEIGHT = 700;
	
	public static final int DIALOG_MANAGE_PASSWORDS_WIDTH = 600;
	public static final int DIALOG_MANAGE_PASSWORDS_HEIGHT = 400;
	
	public static final int DIALOG_TEST_WIDTH = 1000;
	public static final int DIALOG_TEST_HEIGHT = 700;
	
	public static final int DIALOG_WRONG_ANSWERS_WIDTH = 800;
	public static final int DIALOG_WRONG_ANSWERS_HEIGHT = 700;
	
	public static final int DIALOG_NEW_TEST_WIDTH = 600;
	public static final int DIALOG_NEW_TEST_HEIGHT = 300;
	
	public static final int DIALOG_ADMIN_WIDTH = 500;
	public static final int DIALOG_ADMIN_HEIGHT = 300;
	
	public static final int DIALOG_STATISTICS_WIDTH = 1000;
	public static final int DIALOG_STATISTICS_HEIGHT = 600;

	public static final int DIALOG_RESULTING_WIDTH = 600;
	public static final int DIALOG_RESULTING_HEIGHT = 300;
	
	// Константы шрифтов
	public static final Font RESULTDIALOG_TEXT_FONT = new Font("Courier New", Font.BOLD, 18);
	public static final Font TESTDIALOG_HEADER_FONT = new Font("Courier New", Font.PLAIN, 20);
	public static final Font TESTDIALOG_QUESTION_FONT = new Font("Courier New", Font.PLAIN, 20);
	public static final Font TESTDIALOG_ITEMS_FONT = new Font("Courier New", Font.PLAIN, 16);
	public static final Font TOP_PANELS_TEXT_FONT = new Font("Courier New", Font.PLAIN, 22);
	
//	public static final Font RESULTDIALOG_TEXT_FONT = new Font("Times New Roman", Font.BOLD, 18);
//	public static final Font TESTDIALOG_HEADER_FONT = new Font("Tahoma", Font.PLAIN, 20);
//	public static final Font TESTDIALOG_QUESTION_FONT = new Font("Times New Roman", Font.PLAIN, 20);
//	public static final Font TESTDIALOG_ITEMS_FONT = new Font("Times New Roman", Font.PLAIN, 16);
//	public static final Font TOP_PANELS_TEXT_FONT = new Font("Tahoma", Font.PLAIN, 22);
	
	// Размеры компонентов
	public static final int MAINFRAME_BTN_WIDTHS = 250;
	public static final int MAINFRAME_BTN_HEIGHTS = 60;
	
	public static final int TESTDIALOG_LBL_QUESTION_INFO_HEIGHT = 30;
	public static final int TESTDIALOG_PNLQNA_RIGID_AREA_WIDTH = 0;
	public static final int TESTDIALOG_PNLQNA_RIGID_AREA_HEIGHT = 30;
	public static final int WADIALOG_TAWRONGS_HEIGHT_PADDING = 60;
	
	public static final int TOP_PANEL_HEIGHT = 60;
	
	
	// QuestionListDialog константы
	public static final int QLDIALOG_TF_SIZE = 25;
	
	public static final int[] QLDIALOG_TABLE_COL_WIDTHS_ARR = {40, 550, 190, 190};
	public static final String[] QLDIALOG_TABLE_COL_CAPTIONS_ARR = {"id", "Формулировка", "Уровни", "Спецификация" };
	
	public static final String ACCP_SPEC_NAMES_PROTOTYPE_DISPLAY_VALUE = "XXXXXXXXXXXXXXXXXXXX";
	
	public static final String QLDIALOG_PNL_QEDIT_BORDER_TITLE_RU = "Просмотр и редактирование вопроса";
	
	// StatisticsDialog константы
	
	public static final int[] STATDIALOG_TABLE_COL_WIDTHS_ARR = { 20, 250, 125, 75, 125, 50, 50, 50, 125};
	public static final String[] STATDIALOG_TABLE_COL_CAPTIONS_ARR = {"#", "ФИО пользователя", "Специализация", 
			"Уровень", "Дата теста", "Время теста", 
			"Верных ответов", "%", "Результат"};
	public static final SimpleDateFormat STATDIALOG_TABLE_DATE_FORMAT = new SimpleDateFormat("dd MMMMM yyyy HH:mm", new Locale("ru", "RU"));
	
	// Прочие константы
	public static final String CONTENT_TYPE_HTML = "text/html";
	public static final String PASSWORD_IS_SET_DEFAULT_MASK = "******";
	public static final int TABLE_ROWS_LIMIT = 20;
	public static final int MAX_ANSWERS_AMOUNT = 5;
	public static final int MIN_ANSWERS_AMOUNT = 2;
	public static final int QUESTION_TEXT_MIN_LENGTH = 5;
	public static final int MINIMUM_QUESTIONS_TO_INIT_TEST = 30;
	public static final Color TABLE_SELECTION_COLOR = new Color(0x03a06c);
			//new Color(0x0000ff);
	public static final Color FSSP_COLOR = new Color(0x03a06c);
	public static final Color TOP_PANELS_TEXT_FONT_COLOR = new Color(0xffffff);
	public static final int NO_ROW_SELECTED = -1;
	public static final int NO_INDEX_SELECTED = -1;
	
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
			+ "приложения ФССП Тест в фотматах XLSX или ODS</p><html>";
	
	public static final String DIALOG_PASSOWRD_MANAGE_ABOUT_INFO = "<html><b>Назначение паролей для следующих разделов программы</b><br/>"
			+ "<i>оставьте пустым, если пароль для доступа к разделу не требуется:</i></html>";
}
