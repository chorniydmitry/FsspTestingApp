package ru.fssprus.r82.swing.dialogs.settingsDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.utils.ApplicationConfiguration;
import ru.fssprus.r82.utils.Utils;

public class ConfigController extends CommonController<ConfigDialog> implements ActionListener, DocumentListener {
	

	public ConfigController(ConfigDialog dialog) {
		super(dialog);
	}
	
	@Override
	protected void setListeners() {
		dialog.getBtnSave().addActionListener(this);
		dialog.getTfsList().forEach((n) -> n.getDocument().addDocumentListener(this));
	}

	private void enableBtnSave(boolean action) {
		dialog.getBtnSave().setEnabled(action);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		enableBtnSave(true);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		enableBtnSave(true);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		enableBtnSave(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dialog.getBtnSave()) {
			doSave();
			dialog.fillTfsList();
			dialog.revalidate();
			dialog.repaint();
		}
	}

	private void doSave() {
		if (validateFields()) {
			dialog.getTfsList().forEach((n) -> ApplicationConfiguration.saveItem(n.getName(), n.getText()));
			enableBtnSave(false);
			uncolorFields();
		}
	}

	private boolean validateFields() {
		boolean fieldsValidated = true;
		for (JTextField tf : dialog.getTfsList()) {
			String text = tf.getText();
			if (!Utils.isNumeric(text)) {
				tf.setBackground(Color.RED);
				fieldsValidated = false;
			}
		}
		return fieldsValidated;
	}

	private void uncolorFields() {
		dialog.getTfsList().forEach((n) -> n.setBackground(Color.WHITE));
	}

}