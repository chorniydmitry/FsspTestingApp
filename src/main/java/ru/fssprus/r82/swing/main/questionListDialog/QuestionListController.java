package ru.fssprus.r82.swing.main.questionListDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListSelectionModel;
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

public class QuestionListController implements ActionListener {
	private static final int ENTRIES_FOR_PAGE = 25;

	private int currentPage = 0;
	private int totalPages;
	private List<Question> questionsOnScreenList = new ArrayList<Question>();

	private QuestionListDialog questionListDialog;

	public QuestionListController(QuestionListDialog dialog) {
		this.questionListDialog = dialog;
		initTfSpec();
		questionListDialog.init();
		setListeners();
		loadQestionsFromDB(null);
	}

	private void initTfSpec() {
		ArrayList<String> keywords = getSpecsNames();
		StringSearchable searchable = new StringSearchable(keywords);

		questionListDialog.setAccbSpecNames(new AutocompleteJComboBox(searchable));
		questionListDialog.getAccbSpecNames().addItem(null);
		keywords.forEach((n) -> questionListDialog.getAccbSpecNames().addItem(n));
		questionListDialog.getAccbSpecNames().setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
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
					Question question = questionsOnScreenList.get(selectedRow[0]);
					List<Answer> answers = new ArrayList<Answer>(question.getAnswers());

					questionListDialog.getTaQuestion().setText(question.getTitle());

					for (int i = 0; i < answers.size(); i++) {
						questionListDialog.getTfAnsList().get(i).setText(answers.get(i).getTitle());
						questionListDialog.getCbAnsList().get(i).setSelected(answers.get(i).getIsCorrect());
					}

					List<QuestionLevel> levelsList = new ArrayList<QuestionLevel>(question.getLevels());
					QuestionLevel[] levels = QuestionLevel.values();
					for (int i = 0; i < levelsList.size(); i++) {
						for (int j = 0; j < levels.length; j++) {
							if (levelsList.get(i) == levels[j]) {
								questionListDialog.getCbLevelsList().get(j).setSelected(true);
							}
						}
					}
					String spec = question.getSpecifications().iterator().next().getName();
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

	private void loadQestionsFromDB(List<Question> questionsList) {
		List<Question> questions = null;
		int amountOfQuestions = 0;
		if (questionsList == null) {
			QuestionService questionService = new QuestionService();

			// TODO:
			amountOfQuestions = questionService.countAll();

			questionListDialog.getLblPageInfo()
					.setText("Cтраница " + (currentPage + 1) + " из " + countTotalPages(amountOfQuestions));

			int start = currentPage * ENTRIES_FOR_PAGE;
			int max = ENTRIES_FOR_PAGE;

			questions = questionService.getAll(start, max);
		} else {

			questions = questionsList;
			amountOfQuestions = questionsList.size();
			questionListDialog.getLblPageInfo()
					.setText("Cтраница " + (currentPage + 1) + " из " + countTotalPages(amountOfQuestions));
		}
		questionsOnScreenList = questions;
		questionListDialog.getTabQuestList().addQuestions(questions);

	}

	private int countTotalPages(int amountOfQuestions) {
		this.totalPages = amountOfQuestions / ENTRIES_FOR_PAGE + 1;
		return totalPages;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		doPreUpdate();
		if (e.getSource() == questionListDialog.getBtnFilter())
			doFilterAction();
		if (e.getSource() == questionListDialog.getBtnNextPage())
			doNextPageAction();
		if (e.getSource() == questionListDialog.getBtnPreviousPage())
			doPreviousPageAction();

	}

	private void doPreUpdate() {
		questionListDialog.getTabQuestList().getSelectionModel().clearSelection();
		clearQuestionEditPanelContents();
	}

	private void doPreviousPageAction() {
		if (currentPage > 0) {
			currentPage--;
			loadQestionsFromDB(null);
		}

	}

	private void doNextPageAction() {
		if ((currentPage + 1) < totalPages) {
			currentPage++;
			loadQestionsFromDB(null);
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
					e.printStackTrace();
					// TODO:
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

	private void doFilterAction() {

		QuestionService questionService = new QuestionService();

		String idText = questionListDialog.getTfId().getText();
		String specsText = questionListDialog.getTfSpecs().getText();
		String questTitleText = questionListDialog.getTfQuestionName().getText();
		String levelsText = questionListDialog.getTfLevels().getText();
		Long id = 0l;
		try {
			if(!idText.isEmpty())
			id = Long.parseLong(idText);

			List<Question> questions = questionService.getByNameSpecListLvlListAndId(questTitleText,
					setSpecs(specsText), setLevels(levelsText), id);

			loadQestionsFromDB(questions);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

}
