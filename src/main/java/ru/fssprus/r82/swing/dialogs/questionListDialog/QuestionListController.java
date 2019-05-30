package ru.fssprus.r82.swing.dialogs.questionListDialog;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import g.cope.swing.autocomplete.jcombobox.AutocompleteJComboBox;
import g.cope.swing.autocomplete.jcombobox.StringSearchable;
import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.ulils.MessageBox;

public class QuestionListController extends CommonController<QuestionListDialog>{
	private static final int ENTRIES_FOR_PAGE = 25;

	private int currentPage = 0;
	private int totalPages;
	private int totalQuestions;
	private List<Question> questionsOnScreenList = new ArrayList<Question>();
	private boolean isQuestionEditFieldsBlocked = true;
	private Question currentQuestion = null;


	public QuestionListController(QuestionListDialog dialog) {
		super(dialog);
		initTfSpec();
		blockQuestionEditFields(true);
		setListeners();

		doFilterAction();
		showQuestions();
	}

	private void initTfSpec() {
		ArrayList<String> keywords = getSpecsNames();
		StringSearchable searchable = new StringSearchable(keywords);

		dialog.setAccbSpecNames(new AutocompleteJComboBox(searchable));
		dialog.getAccbSpecNames().addItem(null);
		keywords.forEach((n) -> dialog.getAccbSpecNames().addItem(n));
		dialog.getAccbSpecNames().setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
	}

	private void blockQuestionEditFields(boolean block) {
		dialog.getTaQuestion().setEditable(!block);

		for (int i = 0; i < dialog.getCbLevelsList().size(); i++) {
			dialog.getCbLevelsList().get(i).setEnabled(!block);
		}
		dialog.getAccbSpecNames().setEditable(!block);

		for (int i = 0; i < QuestionListDialog.MAX_ANSWERS; i++) {
			dialog.getTfAnsList().get(i).setEditable(!block);

			dialog.getCbAnsList().get(i).setEnabled(!block);
		}
	}

	private ArrayList<String> getSpecsNames() {
		ArrayList<String> specNames = new ArrayList<String>();
		loadSpecsFromDB().forEach((n) -> specNames.add(n.getName()));

		return specNames;
	}

	private List<Specification> loadSpecsFromDB() {
		SpecificationService specService = new SpecificationService();
		return specService.getAll();
	}

	@Override
	protected void setListeners() {
		dialog.getBtnFilter().addActionListener(this);
		dialog.getBtnNextPage().addActionListener(this);
		dialog.getBtnPreviousPage().addActionListener(this);
		dialog.getBtnDiscardQuestionEditChanges().addActionListener(this);
		dialog.getBtnEditQuestion().addActionListener(this);
		dialog.getBtnClearFilters().addActionListener(this);
		dialog.getBtnSaveQuestion().addActionListener(this);
		setTableOnSelectionListener();

	}

	private void setTableOnSelectionListener() {
		ListSelectionModel cellSelectionModel = dialog.getTabQuestList().getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (dialog.getTabQuestList().getSelectedRows().length > 0) {
					clearQuestionEditPanelContents();
					int[] selectedRow = dialog.getTabQuestList().getSelectedRows();
					currentQuestion = questionsOnScreenList.get(selectedRow[0]);
					List<Answer> answers = new ArrayList<Answer>(currentQuestion.getAnswers());

					dialog.getTaQuestion().setText(currentQuestion.getTitle());

					for (int i = 0; i < answers.size(); i++) {
						dialog.getTfAnsList().get(i).setText(answers.get(i).getTitle());
						dialog.getCbAnsList().get(i).setSelected(answers.get(i).getIsCorrect());
					}

					List<QuestionLevel> levelsList = new ArrayList<QuestionLevel>(currentQuestion.getLevels());
					QuestionLevel[] levels = QuestionLevel.values();
					for (int i = 0; i < levelsList.size(); i++) {
						for (int j = 0; j < levels.length; j++) {
							if (levelsList.get(i) == levels[j]) {
								dialog.getCbLevelsList().get(j).setSelected(true);
							}
						}
					}
					String spec = currentQuestion.getSpecifications().iterator().next().getName();
					dialog.getAccbSpecNames().setSelectedItem(spec);

				}
			}
		});

	}

	private void clearQuestionEditPanelContents() {
		dialog.getTaQuestion().setText(null);
		for (int i = 0; i < QuestionListDialog.MAX_ANSWERS; i++) {
			dialog.getTfAnsList().get(i).setText(null);
			dialog.getCbAnsList().get(i).setSelected(false);
		}

		for (int i = 0; i < dialog.getCbLevelsList().size(); i++) {
			dialog.getCbLevelsList().get(i).setSelected(false);
		}

		dialog.getAccbSpecNames().setSelectedIndex(0);

	}

	private int countTotalPages(int amountOfQuestions) {
		this.totalPages = amountOfQuestions / ENTRIES_FOR_PAGE + 1;
		return totalPages;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dialog.getBtnEditQuestion()) {
			doEditAction();
			return;
		}
		if (e.getSource() == dialog.getBtnSaveQuestion())
			doSaveQuestionAction();

		doUnselectTableLines();
		if (e.getSource() == dialog.getBtnFilter())
			doFilterAction();
		if (e.getSource() == dialog.getBtnNextPage())
			doNextPageAction();
		if (e.getSource() == dialog.getBtnPreviousPage())
			doPreviousPageAction();
		if (e.getSource() == dialog.getBtnClearFilters())
			doClearFiltersAction();

	}

	private void doSaveQuestionAction() {

		QuestionService service = new QuestionService();

		currentQuestion.setTitle(dialog.getTfQuestionName().getText());

		List<Answer> answers = new ArrayList<Answer>(currentQuestion.getAnswers());

		for (int i = 0; i < dialog.getCbAnsList().size(); i++) {
			if (!dialog.getTfAnsList().get(i).getText().isEmpty()) {
				answers.get(i).setQuestion(currentQuestion);
				answers.get(i).setTitle(dialog.getTfAnsList().get(i).getText());
				answers.get(i).setIsCorrect(dialog.getCbAnsList().get(i).isSelected());
			}
		}

		currentQuestion.setAnswers(new HashSet<Answer>(answers));

		Set<QuestionLevel> levels = new HashSet<QuestionLevel>();

		for (int i = 0; i < dialog.getCbLevelsList().size(); i++)
			if (dialog.getCbLevelsList().get(i).isSelected())
				levels.add(QuestionLevel.valueOf(dialog.getCbLevelsList().get(i).getText()));

		currentQuestion.setLevels(levels);

		SpecificationService specService = new SpecificationService();

		List<Specification> spec = specService
				.getByName(dialog.getAccbSpecNames().getSelectedItem().toString());

		Set<Specification> specs = new HashSet<Specification>(spec);

		currentQuestion.setSpecifications(specs);

		currentQuestion.setTitle(dialog.getTaQuestion().getText());

		service.update(currentQuestion.getId(), currentQuestion);

		doFilterAction();
	}

	private void doClearFiltersAction() {
		dialog.getTfId().setText(null);
		dialog.getTfQuestionName().setText(null);
		dialog.getTfSpecs().setText(null);
		dialog.getTfLevels().setText(null);

		doFilterAction();
	}

	private void doEditAction() {
		if (isQuestionEditFieldsBlocked) {
			blockQuestionEditFields(false);
			isQuestionEditFieldsBlocked = false;
		} else {
			blockQuestionEditFields(true);
			isQuestionEditFieldsBlocked = true;
		}

	}

	private void doUnselectTableLines() {
		dialog.getTabQuestList().getSelectionModel().clearSelection();
		clearQuestionEditPanelContents();
	}

	private void doPreviousPageAction() {
		if (currentPage > 0) {
			currentPage--;
			doFilterAction();
		}

	}

	private void doNextPageAction() {
		if ((currentPage + 1) < totalPages) {
			currentPage++;
			doFilterAction();
		}

	}

	private Set<QuestionLevel> setLevels(String levelsText) {
		if (!levelsText.isEmpty()) {
			Set<QuestionLevel> questionLevelList = new HashSet<QuestionLevel>();
			String[] levelsTextList = levelsText.trim().replaceAll(" ", "").split(",");

			for (int i = 0; i < levelsTextList.length; i++) {
				try {
					questionLevelList.add(QuestionLevel.valueOf(levelsTextList[i]));
				} catch (IllegalArgumentException e) {
					MessageBox.showWrongLevelSpecifiedErrorDialog(dialog);
					return null;
				}
			}
			return questionLevelList;
		}
		return null;

	}

	private Set<Specification> setSpecs(String specsText) {
		if (!specsText.isEmpty()) {
			SpecificationService specService = new SpecificationService();
			String[] specsTextList = specsText.trim().replaceAll(" ", "").split(",");

			Set<Specification> specsList = new HashSet<Specification>();
			for (int i = 0; i < specsTextList.length; i++) {
				specsList.addAll(specService.getByName(specsTextList[i]));
			}
			return specsList;
		}
		return null;

	}

	private void showQuestions() {
		dialog.getLblPageInfo()
				.setText("Cтраница " + (currentPage + 1) + " из " + countTotalPages(totalQuestions));
		dialog.getTabQuestList().addQuestions(questionsOnScreenList);
	}

	private void doFilterAction() {
		QuestionService questionService = new QuestionService();
		String idText = dialog.getTfId().getText();
		String specsText = dialog.getTfSpecs().getText();
		String questTitleText = dialog.getTfQuestionName().getText();
		String levelsText = dialog.getTfLevels().getText();
		Long id = 0l;
		try {
			if (!idText.isEmpty())
				id = Long.parseLong(idText);

			int start = currentPage * ENTRIES_FOR_PAGE;
			int max = ENTRIES_FOR_PAGE;
			
			Set<QuestionLevel> levels = setLevels(levelsText);
			Set<Specification> specs = setSpecs(specsText);

			totalQuestions = questionService.countByNameSpecListLvlListAndId(questTitleText, specs,
					levels, id);
			
			if(totalQuestions <= ENTRIES_FOR_PAGE) {
				start = -1;
				max = -1;
			}
			questionsOnScreenList = questionService.getByNameSpecListLvlListAndId(start, max, questTitleText,
					specs, levels, id);
			
			showQuestions();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

}
