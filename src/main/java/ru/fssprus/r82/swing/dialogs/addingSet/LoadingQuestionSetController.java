package ru.fssprus.r82.swing.dialogs.addingSet;

/**
 * @author Chernyj Dmitry
 *
 */
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.utils.MessageBox;
import ru.fssprus.r82.utils.spreadsheet.SpreadSheetParser;
import ru.fssprus.r82.utils.spreadsheet.SpreadsheetFileChooser;

public class LoadingQuestionSetController extends CommonController<LoadingQuestionSetDialog> {
	private File testFile;

	public LoadingQuestionSetController(LoadingQuestionSetDialog dialog) {
		super(dialog);
	}

	@Override
	protected void setListeners() {
		dialog.getBtnLoadQuestionsSet().addActionListener(listener -> doLoadQuestionSet());
		dialog.getBtnOpenTextFile().addActionListener(listener -> doOpenTestFile());
	}


	private void doLoadQuestionSet() {
		if(!validate())
			return;

		dialog.getBtnLoadQuestionsSet().setEnabled(false);

		saveQuestionSetToDB(getQuestionsParsed());

		dialog.getBtnLoadQuestionsSet().setEnabled(true);
		
		MessageBox.showReadyDialog(dialog);
	}

	private boolean validate() {
		if (!validateFile()) {
			MessageBox.showFileNotLoadedErrorDialog(dialog);
			dialog.getTfFilePath().requestFocus();
			return false;
		}

		if (!validateSpecTf()) {
			MessageBox.showWrongSpecSpecifiedErrorDialog(dialog);
			dialog.getAccbSpecName().requestFocus();
			return false;
		}
		
		return true;
	}
	
	//TODO
	private boolean validateFile() {
		if (testFile == null || dialog.getTfFilePath().getText().isEmpty()
				|| ((!dialog.getTfFilePath().getText().toUpperCase().endsWith(".ODS"))
						&& (!dialog.getTfFilePath().getText().toUpperCase().endsWith(".XLSX")))) {
			return false;

		}
		return true;
	}
	//TODO
	private boolean validateSpecTf() {
		if (dialog.getAccbSpecName().getSelectedItem().toString().isEmpty())
			return false;
		return true;
	}

	/**
	 * @return
	 */
	private HashSet<Question> getQuestionsParsed() {
		SpreadSheetParser parser = new SpreadSheetParser();
		
		HashSet<Question> questions = parser.parse(testFile, configureLevelsSet(), getSpecification());
		return questions;
	}

	/**
	 * @return
	 */
	private Specification getSpecification() {
		Specification spec = new Specification();
		spec.setName(dialog.getAccbSpecName().getSelectedItem().toString());
		return spec;
	}

	private void doOpenTestFile() {
		SpreadsheetFileChooser chooser = new SpreadsheetFileChooser();
		testFile = chooser.selectSpreadSheetFileToOpen();
		if (testFile != null)
			try {
				dialog.getTfFilePath().setText(testFile.getCanonicalFile().getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
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
