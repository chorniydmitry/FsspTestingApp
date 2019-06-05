package ru.fssprus.r82.swing.ulils;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.utils.AppConstants;

public class MessageBox extends JOptionPane {
	private static final long serialVersionUID = -2426219006577522299L;

	private static final String READY = "Готово!";
	private static final String ERROR_FILE_NOT_LOAD = "Ошибка при открытии файла!";
	private static final String ERROR_WRONG_SPEC_SPECIFIED = "Не верно указано название специализации!";
	private static final String ERROR_WRONG_LEVEL_SPECIFIED = "Не верно указан уровень сложности\n"
			+ "Допустимые значения: ";
	private static final String PASSWORD_INPUT_TITLE = "Ввод пароля";
	private static final String PASSWORD_INPUT_MESSAGE = "Введите пароль:";
	private static final String CONFIRM_QUESTION_DELETE = "Будет удален выбранный вопрос. Продолжить?";
	private static final String ERROR_WRONG_QUESTION_SPECIFIED = 
			"Не верно заполнен вопрос для добавления в базу данных!\n\n"
			+ "ТЕКСТ ВОПРОСА: "
			+ "Длина текста вопроса должна быть больше " + AppConstants.QUESTION_TEXT_MIN_LENGTH + " символов.\n\n"
			+ "СПИСОК ОТВЕТОВ:\n"
			+ " - длина текста ответа должна быть больше " + AppConstants.ANSWER_TEXT_MIN_LENGTH + " cимволов;\n"
			+ " - минимальное количество ответов должно быть больше или равно " + AppConstants.MIN_ANSWERS_AMOUNT + ";\n"
			+ " - как минимум один из ответов должен быть помечен как верный; \n"
			+ " - не заполненный вопрос не должен быть помечен как верный.\n\n"
			+ "СЛОЖНОСТЬ ВОПРОСА: "
			+ "Для вопроса должна быть выбрана как минимум одна сложность.\n\n"
			+ "СПЕЦИАЛИЗАЦИЯ ВОПРОСА: "
			+ "Специализация должна быть выбрана из списка или заполнена.\n\n" 
			+ "---------------------------------------\n"
			+ "Отредактируйте введенные данные, чтобы они удовлетворяли условиям, описанным выше"
			+ " и повторите попытку!";

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
		String[] options = new String[] { "OK", "Cancel" };
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
}
