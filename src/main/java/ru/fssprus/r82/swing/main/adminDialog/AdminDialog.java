package ru.fssprus.r82.swing.main.adminDialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;

public class AdminDialog extends JDialog{
	private static final long serialVersionUID = -2557630253920656451L;
	
	private JButton btnSettings = new JButton("Конфигурация");
	private JButton btnQuestionLoad = new JButton("Выгрузка наборов вопросов");
	private JButton btnQuestionEdit = new JButton("Редактор вопросов");
	private JButton btnPasswords = new JButton("Установка паролей");
	
	public AdminDialog(int width, int height) {
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		
		setVisible(true);
	}
	
	public void init() {
		layoutDialog();
	}
	
	private void layoutDialog() {
		setLayout(new GridBagLayout());
		
		add(btnSettings, new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 10, 10));
		
		add(btnQuestionLoad, new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 10, 10));
		
		add(btnQuestionEdit, new GridBagConstraints(0, 2, GridBagConstraints.REMAINDER, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 10, 10));
		
		add(btnPasswords, new GridBagConstraints(0, 3, GridBagConstraints.REMAINDER, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 10, 10));
		
	}

	public JButton getBtnSettings() {
		return btnSettings;
	}

	public void setBtnSettings(JButton btnSettings) {
		this.btnSettings = btnSettings;
	}

	public JButton getBtnQuestionLoad() {
		return btnQuestionLoad;
	}

	public void setBtnQuestionLoad(JButton btnQuestionLoad) {
		this.btnQuestionLoad = btnQuestionLoad;
	}

	public JButton getBtnQuestionEdit() {
		return btnQuestionEdit;
	}

	public void setBtnQuestionEdit(JButton btnQuestionEdit) {
		this.btnQuestionEdit = btnQuestionEdit;
	}

	public JButton getBtnPasswords() {
		return btnPasswords;
	}

	public void setBtnPasswords(JButton btnPasswords) {
		this.btnPasswords = btnPasswords;
	}

}
