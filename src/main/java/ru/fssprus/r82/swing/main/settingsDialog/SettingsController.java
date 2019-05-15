package ru.fssprus.r82.swing.main.settingsDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.swing.main.MessageBox;
import ru.fssprus.r82.utils.ApplicationConfiguration;
import ru.fssprus.r82.utils.TestFromODSLoader;

public class SettingsController implements ActionListener, DocumentListener {
	private SettingsDialog settingsDialog;
	
	public SettingsController(SettingsDialog settingsDialog) {
		this.settingsDialog = settingsDialog;
		setListeners();
	}
	
	private void setListeners() {
		settingsDialog.getTfsList().forEach((n) -> n.getDocument().addDocumentListener(this));
		
		settingsDialog.getBtnLoadQuestionsSet().addActionListener(this);
		settingsDialog.getBtnSave().addActionListener(this);
	}
	private void enableButton(boolean action) {
		settingsDialog.getBtnSave().setEnabled(action);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		enableButton(true);
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		enableButton(true);
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		enableButton(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == settingsDialog.getBtnLoadQuestionsSet())
			doLoadQuestionSet();
		if(e.getSource() == settingsDialog.getBtnSave())
			doSave();
	}

	private void doSave() {
		if(validateFields()) {
			settingsDialog.getTfsList().forEach((n) -> ApplicationConfiguration.saveItem(n.getName(), n.getText()));
			enableButton(false);
			clearFields();
		}
	}
	
	private boolean validateFields() {
		boolean fieldsValidated = true;
		for (JTextField tf : settingsDialog.getTfsList()) {
			String text = tf.getText();
			if(!isNumeric(text)) {
				tf.setBackground(Color.RED);
				fieldsValidated = false;
			}
		}
		return fieldsValidated;
	}
	
	private void clearFields() {
		settingsDialog.getTfsList().forEach((n) -> n.setBackground(Color.WHITE));
	}
	
	public static boolean isNumeric(String strNum) {
	    try {
	        Integer.parseInt(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}

	private void doLoadQuestionSet() {
		settingsDialog.getBtnLoadQuestionsSet().setEnabled(false);
		new TestFromODSLoader((QuestionLevel) settingsDialog.getCbQuestLevel().getSelectedItem(), settingsDialog.getAccbSpecName().getSelectedItem().toString()).doOpenODS();
		settingsDialog.getBtnLoadQuestionsSet().setEnabled(true);
		MessageBox.showReadyDialog(settingsDialog);
		
	}
	

}
