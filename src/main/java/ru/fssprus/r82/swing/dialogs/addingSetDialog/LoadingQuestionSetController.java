package ru.fssprus.r82.swing.dialogs.addingSetDialog;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.ulils.MessageBox;
import ru.fssprus.r82.utils.SpreadSheetParser;
import ru.fssprus.r82.utils.TestFileChooser;

public class LoadingQuestionSetController extends CommonController<LoadingQuestionSetDialog> {
	private File testFile;

	public LoadingQuestionSetController(LoadingQuestionSetDialog dialog) {
		super(dialog);
		setKeyWords();
	}

	@Override
	protected void setListeners() {
		dialog.getBtnLoadQuestionsSet().addActionListener(listener -> doLoadQuestionSet());
		dialog.getBtnOpenTextFile().addActionListener(listener -> doOpenTestFile());
	}

	private void setKeyWords() {

	}

	private void doLoadQuestionSet() {
		if (!validateFile()) {
			MessageBox.showFileNotLoadedErrorDialog(dialog);
			dialog.getTfFilePath().requestFocus();
			return;
		}

		if (!validateSpecTf()) {
			MessageBox.showWrongSpecSpecifiedErrorDialog(dialog);
			dialog.getAccbSpecName().requestFocus();
			return;
		}

		dialog.getBtnLoadQuestionsSet().setEnabled(false);

		SpreadSheetParser parser = new SpreadSheetParser();
		
		Specification spec = new Specification();
		spec.setName(dialog.getAccbSpecName().getSelectedItem().toString());

		HashSet<Question> questions = parser.parse(testFile, configureLevelsSet(), spec);

		saveQuestionSetToDB(questions);

		dialog.getBtnLoadQuestionsSet().setEnabled(true);
		MessageBox.showReadyDialog(dialog);
	}

	private void doOpenTestFile() {
		TestFileChooser chooser = new TestFileChooser();
		testFile = chooser.selectSpreadSheetFileToOpen();
		if (testFile != null)
			try {
				dialog.getTfFilePath().setText(testFile.getCanonicalFile().getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private boolean validateFile() {
		if (testFile == null || dialog.getTfFilePath().getText().isEmpty()
				|| ((!dialog.getTfFilePath().getText().toUpperCase().endsWith(".ODS"))
						&& (!dialog.getTfFilePath().getText().toUpperCase().endsWith(".XLSX")))) {
			return false;

		}
		return true;
	}

	private boolean validateSpecTf() {
		if (dialog.getAccbSpecName().getSelectedItem().toString().isEmpty())
			return false;
		return true;
	}

	private Set<QuestionLevel> configureLevelsSet() {
		QuestionLevel level = (QuestionLevel) dialog.getCbQuestLevel().getSelectedItem();
		Set<QuestionLevel> lvls = new HashSet<QuestionLevel>();
		lvls.add(level);
		return lvls;
	}

	private void saveQuestionSetToDB(HashSet<Question> questions) {
		QuestionService qService = new QuestionService();
		qService.addFilteringExistant(questions);
	}

}
