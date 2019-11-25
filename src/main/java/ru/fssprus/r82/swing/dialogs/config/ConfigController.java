package ru.fssprus.r82.swing.dialogs.config;

/**
 * @author Chernyj Dmitry
 *
 */
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.utils.ApplicationConfiguration;
import ru.fssprus.r82.utils.Utils;

public class ConfigController extends CommonController<ConfigDialog> implements DocumentListener {

	public ConfigController(ConfigDialog dialog) {
		super(dialog);
	}

	@Override
	protected void setListeners() {
		dialog.getBtnSave().addActionListener(listener -> doSaveAction());
		dialog.getTfsList().forEach((n) -> n.getDocument().addDocumentListener(this));
	}

	private void doSaveAction() {
		doSave();
		dialog.fillTfsList();
		dialog.revalidate();
		dialog.repaint();
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

	private void doSave() {
		if (!validateFields())
			return;
		dialog.getTfsList().forEach((n) -> ApplicationConfiguration.saveItem(n.getName(), n.getText()));
		enableBtnSave(false);
		uncolorFields();

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