package ru.fssprus.r82.swing.utils;

/**
 * @author Chernyj Dmitry
 *
 */
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.Utils;

public class MessageBox extends JOptionPane {
	private static final long serialVersionUID = -2426219006577522299L;

	private static final String READY = "Готово!";
	private static final String ERROR_FILE_NOT_LOAD = "Ошибка при открытии файла!";
	private static final String ERROR_WRONG_SPEC_SPECIFIED = "Не верно указано название специализации!";
	private static final String ERROR_WRONG_LEVEL_SPECIFIED = "Не верно указан уровень сложности\n"
			+ "Допустимые значения: ";
	private static final String PASSWORD_INPUT_TITLE = "Ввод пароля";
	private static final String PASSWORD_INPUT_MESSAGE = "Введите пароль:";
	private static final String CONFIRM_QUESTION_DELETE = "Будет удалена выбранная запись из базы данных. Продолжить?";
	private static final String ERROR_WRONG_QUESTION_SPECIFIED = "Не верно заполнен вопрос для добавления в базу данных!\n\n"
			+ "ТЕКСТ ВОПРОСА: " + "Длина текста вопроса должна быть больше " + AppConstants.QUESTION_TEXT_MIN_LENGTH
			+ " символов.\n\n" + "СПИСОК ОТВЕТОВ:\n"
			+ " - минимальное количество ответов должно быть больше или равно " + AppConstants.MIN_ANSWERS_AMOUNT
			+ ";\n" + " - как минимум один из ответов должен быть помечен как верный; \n"
			+ " - не заполненный вопрос не должен быть помечен как верный.\n\n" + "СЛОЖНОСТЬ ВОПРОСА: "
			+ "Для вопроса должна быть выбрана как минимум одна сложность.\n\n" + "СПЕЦИАЛИЗАЦИЯ ВОПРОСА: "
			+ "Специализация должна быть выбрана из списка или заполнена.\n\n"
			+ "---------------------------------------\n"
			+ "Отредактируйте введенные данные, чтобы они удовлетворяли условиям, описанным выше"
			+ " и повторите попытку!";

	private static final String ERROR_NOT_ENOUGH_COMMONS = "Не существует вопросов со специализацией \"Общие\", "
			+ "или недостаточно общих вопросов\nдля выбраного уровня сложности(при текущей конфигурации приложения):\n";

	private static final Object ERROR_APPCONFIG_FILE_PROBLEMS = "ОПЕРАЦИЯ ПРЕРВАНА!\nФайл application.configuration "
			+ "не найден, поврежден или имеет не правильный формат. Для корректной работы приложения "
			+ "проверьте файл application.configurationь и приведите его к корректному формату или "
			+ "загрузите стандартный файл application.configuration с настройками\"по-умолчанию\" "
			+ "в корневую папку приложения, заменив существующий";
	
	private static final String ERROR_WRONG_QUESTION = "Не правильный вопрос для ответа!";
	
	private static final String ERROR_WRONG_ANSWERS_SELECTED = "Выбраны не правильные ответы или ответы, не принадлежащие этому вопросу!";

	public static void showReadyDialog(Component component) {
		MessageBox.showMessageDialog(component, READY, null, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showFileNotLoadedErrorDialog(Component component) {
		MessageBox.showMessageDialog(component, ERROR_FILE_NOT_LOAD, null, JOptionPane.ERROR_MESSAGE);

	}

	public static void showWrongSpecSpecifiedErrorDialog(Component component) {
		MessageBox.showMessageDialog(component, ERROR_WRONG_SPEC_SPECIFIED, null, JOptionPane.ERROR_MESSAGE);
	}

	public static void showWrongLevelSpecifiedErrorDialog(Component component) {
		String msg = ERROR_WRONG_LEVEL_SPECIFIED;
		for (QuestionLevel level : QuestionLevel.values()) {
			msg += "[" + level.toString() + "] ";
		}
		MessageBox.showMessageDialog(component, msg, null, JOptionPane.ERROR_MESSAGE);
	}

	public static String showInputPasswordDialog(Component component) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(PASSWORD_INPUT_MESSAGE);
		JPasswordField pass = new JPasswordField(20);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[] { "OK", "Отменить" };
		System.out.println(component.getClass());
		
		int option = JOptionPane.showOptionDialog(component, panel, PASSWORD_INPUT_TITLE, JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (option == 0) {
			char[] password = pass.getPassword();
			return new String(password);
		}
		return "";
	}

	public static boolean showConfirmQuestionDelete(Component component) {
		int result = JOptionPane.showOptionDialog(component, CONFIRM_QUESTION_DELETE, null, JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Да", "Нет" }, "Да");

		if (result == JOptionPane.YES_OPTION)
			return true;
		return false;

	}

	public static void showWrongQuestionSpecifiedErrorDialog(Component component) {
		MessageBox.showMessageDialog(component, ERROR_WRONG_QUESTION_SPECIFIED, null, JOptionPane.ERROR_MESSAGE);

	}

	public static void showNotEnoughCommonQuestionError(Component component) {
		int baseMin = Utils.countMinimumCommonQuestionsForLevel("base");

		int standartMin = Utils.countMinimumCommonQuestionsForLevel("standart");
		
		int advancedMin = Utils.countMinimumCommonQuestionsForLevel("advanced");
		
		int reserveMin = Utils.countMinimumCommonQuestionsForLevel("reserve");

		String message = ERROR_NOT_ENOUGH_COMMONS
				+ "\n Для базовых(минимум): " + baseMin
		+ "\n Для стандартных(минимум): " + standartMin
		+ "\n Для продвинутых(минимум): " + advancedMin
		+ "\n Для резерва(минимум): " + reserveMin;

		MessageBox.showMessageDialog(component, message, null, JOptionPane.ERROR_MESSAGE);

	}

	public static void showAppConfigFileNotFoundOrCorrupted(Component component) {
		MessageBox.showMessageDialog(component, ERROR_APPCONFIG_FILE_PROBLEMS, null, JOptionPane.ERROR_MESSAGE);

	}

	public static void showWrongQuestionToAnswerErrorMessage(Component component) {
		MessageBox.showMessageDialog(component, ERROR_WRONG_QUESTION, null, JOptionPane.ERROR_MESSAGE);
		
		
	}

	public static void showWrongAnswerListForQuestionErrorMessage(Component component) {
		MessageBox.showMessageDialog(component, ERROR_WRONG_ANSWERS_SELECTED, null, JOptionPane.ERROR_MESSAGE);
		
		
	}
}
