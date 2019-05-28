package ru.fssprus.r82.swing.main.questionListDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import ru.fssprus.r82.swing.main.MessageBox;

public class QuestionListController implements ActionListener {
	private static final int ENTRIES_FOR_PAGE = 25;

	private int currentPage = 0;
	private int totalPages;
	private int totalQuestions;
	private List<Question> questionsOnScreenList = new ArrayList<Question>();
	private boolean isQuestionEditFieldsBlocked = true;
	private Question currentQuestion = null;

	private QuestionListDialog questionListDialog;

	public QuestionListController(QuestionListDialog dialog) {
		this.questionListDialog = dialog;
		initTfSpec();
		questionListDialog.init();
		blockQuestionEditFields(true);
		setListeners();

		doFilterAction();
		showQuestions();
	}

	private void initTfSpec() {
		ArrayList<String> keywords = getSpecsNames();
		StringSearchable searchable = new StringSearchable(keywords);

		questionListDialog.setAccbSpecNames(new AutocompleteJComboBox(searchable));
		questionListDialog.getAccbSpecNames().addItem(null);
		keywords.forEach((n) -> questionListDialog.getAccbSpecNames().addItem(n));
		questionListDialog.getAccbSpecNames().setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
	}

	private void blockQuestionEditFields(boolean block) {
		questionListDialog.getTaQuestion().setEditable(!block);

		for (int i = 0; i < questionListDialog.getCbLevelsList().size(); i++) {
			questionListDialog.getCbLevelsList().get(i).setEnabled(!block);
		}
		questionListDialog.getAccbSpecNames().setEditable(!block);

		for (int i = 0; i < QuestionListDialog.MAX_ANSWERS; i++) {
			questionListDialog.getTfAnsList().get(i).setEditable(!block);

			questionListDialog.getCbAnsList().get(i).setEnabled(!block);
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

	private void setListeners() {
		questionListDialog.getBtnFilter().addActionListener(this);
		questionListDialog.getBtnNextPage().addActionListener(this);
		questionListDialog.getBtnPreviousPage().addActionListener(this);
		questionListDialog.getBtnDiscardQuestionEditChanges().addActionListener(this);
		questionListDialog.getBtnEditQuestion().addActionListener(this);
		questionListDialog.getBtnClearFilters().addActionListener(this);
		questionListDialog.getBtnSaveQuestion().addActionListener(this);
		setTableOnSelectionListener();

	}

	private void setTableOnSelectionListener() {
		ListSelectionModel cellSelectionModel = questionListDialog.getTabQuestList().getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (questionListDialog.getTabQuestList().getSelectedRows().length > 0) {
					clearQuestionEditPanelContents();
					int[] selectedRow = questionListDialog.getTabQuestList().getSelectedRows();
					currentQuestion = questionsOnScreenList.get(selectedRow[0]);
					List<Answer> answers = new ArrayList<Answer>(currentQuestion.getAnswers());

					questionListDialog.getTaQuestion().setText(currentQuestion.getTitle());

					for (int i = 0; i < answers.size(); i++) {
						questionListDialog.getTfAnsList().get(i).setText(answers.get(i).getTitle());
						questionListDialog.getCbAnsList().get(i).setSelected(answers.get(i).getIsCorrect());
					}

					List<QuestionLevel> levelsList = new ArrayList<QuestionLevel>(currentQuestion.getLevels());
					QuestionLevel[] levels = QuestionLevel.values();
					for (int i = 0; i < levelsList.size(); i++) {
						for (int j = 0; j < levels.length; j++) {
							if (levelsList.get(i) == levels[j]) {
								questionListDialog.getCbLevelsList().get(j).setSelected(true);
							}
						}
					}
					String spec = currentQuestion.getSpecifications().iterator().next().getName();
					questionListDialog.getAccbSpecNames().setSelectedItem(spec);

				}
			}
		});

	}

	private void clearQuestionEditPanelContents() {
		questionListDialog.getTaQuestion().setText(null);
		for (int i = 0; i < QuestionListDialog.MAX_ANSWERS; i++) {
			questionListDialog.getTfAnsList().get(i).setText(null);
			questionListDialog.getCbAnsList().get(i).setSelected(false);
		}

		for (int i = 0; i < questionListDialog.getCbLevelsList().size(); i++) {
			questionListDialog.getCbLevelsList().get(i).setSelected(false);
		}

		questionListDialog.getAccbSpecNames().setSelectedIndex(0);

	}

	private int countTotalPages(int amountOfQuestions) {
		this.totalPages = amountOfQuestions / ENTRIES_FOR_PAGE + 1;
		return totalPages;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == questionListDialog.getBtnEditQuestion()) {
			doEditAction();
			return;
		}
		if (e.getSource() == questionListDialog.getBtnSaveQuestion())
			doSaveQuestionAction();

		doUnselectTableLines();
		if (e.getSource() == questionListDialog.getBtnFilter())
			doFilterAction();
		if (e.getSource() == questionListDialog.getBtnNextPage())
			doNextPageAction();
		if (e.getSource() == questionListDialog.getBtnPreviousPage())
			doPreviousPageAction();
		if (e.getSource() == questionListDialog.getBtnClearFilters())
			doClearFiltersAction();

	}

	private void doSaveQuestionAction() {

		QuestionService service = new QuestionService();

		currentQuestion.setTitle(questionListDialog.getTfQuestionName().getText());

		List<Answer> answers = new ArrayList<Answer>(currentQuestion.getAnswers());

		for (int i = 0; i < questionListDialog.getCbAnsList().size(); i++) {
			if (!questionListDialog.getTfAnsList().get(i).getText().isEmpty()) {
				answers.get(i).setQuestion(currentQuestion);
				answers.get(i).setTitle(questionListDialog.getTfAnsList().get(i).getText());
				answers.get(i).setIsCorrect(questionListDialog.getCbAnsList().get(i).isSelected());
			}
		}

		currentQuestion.setAnswers(new HashSet<Answer>(answers));

		Set<QuestionLevel> levels = new HashSet<QuestionLevel>();

		for (int i = 0; i < questionListDialog.getCbLevelsList().size(); i++)
			if (questionListDialog.getCbLevelsList().get(i).isSelected())
				levels.add(QuestionLevel.valueOf(questionListDialog.getCbLevelsList().get(i).getText()));

		currentQuestion.setLevels(levels);

		SpecificationService specService = new SpecificationService();

		List<Specification> spec = specService
				.getByName(questionListDialog.getAccbSpecNames().getSelectedItem().toString());

		Set<Specification> specs = new HashSet<Specification>(spec);

		currentQuestion.setSpecifications(specs);

		currentQuestion.setTitle(questionListDialog.getTaQuestion().getText());

		service.update(currentQuestion.getId(), currentQuestion);

		doFilterAction();
	}

	private void doClearFiltersAction() {
		questionListDialog.getTfId().setText(null);
		questionListDialog.getTfQuestionName().setText(null);
		questionListDialog.getTfSpecs().setText(null);
		questionListDialog.getTfLevels().setText(null);

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
		questionListDialog.getTabQuestList().getSelectionModel().clearSelection();
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
					MessageBox.showWrongLevelSpecifiedErrorDialog(questionListDialog);
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
		questionListDialog.getLblPageInfo()
				.setText("Cтраница " + (currentPage + 1) + " из " + countTotalPages(totalQuestions));
		questionListDialog.getTabQuestList().addQuestions(questionsOnScreenList);
	}

	private void doFilterAction() {
		QuestionService questionService = new QuestionService();
		String idText = questionListDialog.getTfId().getText();
		String specsText = questionListDialog.getTfSpecs().getText();
		String questTitleText = questionListDialog.getTfQuestionName().getText();
		String levelsText = questionListDialog.getTfLevels().getText();
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
