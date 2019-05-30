package ru.fssprus.r82.swing.dialogs.addingSetDialog;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import g.cope.swing.autocomplete.jcombobox.AutocompleteJComboBox;
import g.cope.swing.autocomplete.jcombobox.StringSearchable;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.ulils.MessageBox;
import ru.fssprus.r82.utils.SpreadSheetParser;
import ru.fssprus.r82.utils.TestFileChooser;

public class AddingQuestionSetController extends CommonController<AddingQuestionSetDialog> {
	private File testFile;
	
	
	public AddingQuestionSetController(AddingQuestionSetDialog dialog) {
		super(dialog);
		setKeyWords();
	}
	
	@Override
	protected void setListeners() {
		dialog.getBtnLoadQuestionsSet().addActionListener(this);
		dialog.getBtnOpenTextFile().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dialog.getBtnLoadQuestionsSet())
			doLoadQuestionSet();
		if (e.getSource() == dialog.getBtnOpenTextFile())
			doOpenTestFile();
		
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

		HashSet<Question> questions = parser.parse(testFile, configureLevelsSet(), configureSpecsSet());
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

	private HashSet<Specification> configureSpecsSet() {
		String specName = dialog.getAccbSpecName().getSelectedItem().toString();
		
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
	
	private void saveQuestionSetToDB(HashSet<Question> questions) {
		QuestionService qService = new QuestionService();
		qService.updateSet(questions);
	}
	
}
