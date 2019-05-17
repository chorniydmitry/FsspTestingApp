package ru.fssprus.r82.swing.main;

import java.awt.Component;

import javax.swing.JOptionPane;

import ru.fssprus.r82.swing.main.settingsDialog.SettingsDialog;

public class MessageBox extends JOptionPane {
	private static final long serialVersionUID = -2426219006577522299L;
	
	private static final String READY = "Готово!";
	private static final String ERROR_FILE_NOT_LOAD = "Ошибка при открытии файла!";
	private static final String ERROR_WRONG_SPEC_SPECIFIED = "Не верно указано название специализации!";
	
	public static void showReadyDialog(Component component) {
		MessageBox.showMessageDialog(component, READY, null, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showFileNotLoadedErrorDialog(Component component) {
		MessageBox.showMessageDialog(component, ERROR_FILE_NOT_LOAD, null, JOptionPane.ERROR_MESSAGE);
		
	}

	public static void showWrongSpecSpecifiedErrorDialog(SettingsDialog component) {
		MessageBox.showMessageDialog(component, ERROR_WRONG_SPEC_SPECIFIED, null, JOptionPane.ERROR_MESSAGE);
	}
}
