package ru.fssprus.r82.swing.main.settingsDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.main.MessageBox;
import ru.fssprus.r82.utils.ApplicationConfiguration;
import ru.fssprus.r82.utils.ODSFileChooser;
import ru.fssprus.r82.utils.SpreadSheetParser;

public class SettingsController implements ActionListener, DocumentListener {
	private SettingsDialog settingsDialog;
	private File testFile;

	public SettingsController(SettingsDialog settingsDialog) {
		this.settingsDialog = settingsDialog;
		setListeners();
	}

	private void setListeners() {
		settingsDialog.getTfsList().forEach((n) -> n.getDocument().addDocumentListener(this));

		settingsDialog.getBtnLoadQuestionsSet().addActionListener(this);
		settingsDialog.getBtnOpenTextFile().addActionListener(this);
		settingsDialog.getBtnSave().addActionListener(this);
	}

	private void enableBtnSave(boolean action) {
		settingsDialog.getBtnSave().setEnabled(action);
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
		if (e.getSource() == settingsDialog.getBtnLoadQuestionsSet())
			doLoadQuestionSet();
		if (e.getSource() == settingsDialog.getBtnSave())
			doSave();
		if (e.getSource() == settingsDialog.getBtnOpenTextFile())
			doOpenTestFile();
	}

	private void doSave() {
		if (validateFields()) {
			settingsDialog.getTfsList().forEach((n) -> ApplicationConfiguration.saveItem(n.getName(), n.getText()));
			enableBtnSave(false);
			clearFields();
		}
	}

	private void doOpenTestFile() {
		ODSFileChooser chooser = new ODSFileChooser();
		testFile = chooser.selectODSFileToOpen();
		if (testFile != null)
			try {
				settingsDialog.getTfFilePath().setText(testFile.getCanonicalFile().getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private boolean validateFields() {
		boolean fieldsValidated = true;
		for (JTextField tf : settingsDialog.getTfsList()) {
			String text = tf.getText();
			if (!isNumeric(text)) {
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
		if (!validateFile()) {
			MessageBox.showFileNotLoadedErrorDialog(settingsDialog);
			settingsDialog.getTfFilePath().requestFocus();
			return;
		}

		if (!validateSpecTf()) {
			MessageBox.showWrongSpecSpecifiedErrorDialog(settingsDialog);
			settingsDialog.getAccbSpecName().requestFocus();
			return;
		}

		settingsDialog.getBtnLoadQuestionsSet().setEnabled(false);

		SpreadSheetParser parser = new SpreadSheetParser();

		HashSet<Question> questions = parser.parse(testFile, configureLevelsSet(), configureSpecsSet());
		saveQuestionSetToDB(questions);

		settingsDialog.getBtnLoadQuestionsSet().setEnabled(true);
		MessageBox.showReadyDialog(settingsDialog);
	}
	
	private void saveQuestionSetToDB(HashSet<Question> questions) {
		QuestionService qService = new QuestionService();
		qService.updateSet(questions);
	}
	
	private Set<QuestionLevel> configureLevelsSet() {
		QuestionLevel level = (QuestionLevel) settingsDialog.getCbQuestLevel().getSelectedItem();
		Set<QuestionLevel> lvls = new HashSet<QuestionLevel>();
		lvls.add(level);
		return lvls;
	}

	private HashSet<Specification> configureSpecsSet() {
		String specName = settingsDialog.getAccbSpecName().getSelectedItem().toString();
		
		SpecificationService specService = new SpecificationService();

		List<Specification> specsList = specService.getByName(specName);
		if (specsList.size() == 0) {
			Specification specification = new Specification();
			specification.setName(specName);
			specsList.add(specification);

			specService.save(specification);
		}
		HashSet<Specification> specs = new HashSet<Specification>(specService.getByName(specName));
		return specs;
	}

	private boolean validateFile() {
		if (testFile == null || settingsDialog.getTfFilePath().getText().isEmpty()
				|| ((!settingsDialog.getTfFilePath().getText().toUpperCase().endsWith(".ODS"))
						&& (!settingsDialog.getTfFilePath().getText().toUpperCase().endsWith(".XLSX")))) {
			return false;

		}
		return true;
	}

	private boolean validateSpecTf() {
		if (settingsDialog.getAccbSpecName().getSelectedItem().toString().isEmpty())
			return false;
		return true;
	}

}
