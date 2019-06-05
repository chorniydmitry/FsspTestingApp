package ru.fssprus.r82.swing.dialogs.questionListDialog;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.AnswerService;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.ulils.MessageBox;
import ru.fssprus.r82.utils.AppConstants;

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
		blockQuestionEditFields(true);
		
		doFilterAction();
		showQuestions();
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
		dialog.getBtnAdd().addActionListener(this);
		dialog.getBtnRemove().addActionListener(this);
		setTableOnSelectionListener();

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
		if (e.getSource() == dialog.getBtnAdd())
			doAddAction();
		if (e.getSource() == dialog.getBtnRemove())
			doRemoveAction();

	}
	
	private void doRemoveAction() {
		if(MessageBox.showConfirmQuestionDelete(dialog)) {
			QuestionService service = new QuestionService();
			service.delete(currentQuestion);
		}
		doFilterAction();
	}

	private void doAddAction() {
		dialog.getTabQuestList().getTabModel().addRow(null);

		questionsOnScreenList.add(createEmptyQuestion());
		dialog.getTabQuestList().getTabModel().update();
		dialog.getTabQuestList().scrollTableDown();
		blockQEditElements(true);
		
	}
	
	private Question createEmptyQuestion() {
		Question emptyQuestion = new Question();
		Set<Answer> emtyAnswers = new HashSet<Answer>();
		Specification emptySpec = new Specification();
		Set<QuestionLevel> emptyLevels = new HashSet<QuestionLevel>();
		emptyQuestion.setAnswers(emtyAnswers);
		emptyQuestion.setSpecification(emptySpec);
		emptyQuestion.setLevels(emptyLevels);
		return emptyQuestion;
	}

	private void blockQuestionEditFields(boolean block) {
		dialog.getTaQuestion().setEditable(!block);

		for (int i = 0; i < dialog.getCbLevelsList().size(); i++) {
			dialog.getCbLevelsList().get(i).setEnabled(!block);
		}
		dialog.getAccbSpecNames().setEditable(!block);

		for (int i = 0; i < AppConstants.MAX_ANSWERS_AMOUNT; i++) {
			dialog.getTfAnsList().get(i).setEditable(!block);
			dialog.getCbAnsList().get(i).setEnabled(!block);
		}
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
					String spec = currentQuestion.getSpecification().getName();
					
					dialog.getAccbSpecNames().setSelectedItem(spec);

				}
			}
		});

	}

	private void clearQuestionEditPanelContents() {
		dialog.getTaQuestion().setText(null);
		for (int i = 0; i < AppConstants.MAX_ANSWERS_AMOUNT; i++) {
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
	
	private Question configureQuestionFromUserInput() {
		Question question = new Question();
		
		if(currentQuestion.getId() != null)
			question.setId(currentQuestion.getId());
		
		question.setTitle(dialog.getTaQuestion().getText());
		
		List<Answer> answers = new ArrayList<Answer>();
		for(int i = 0; i < dialog.getTfAnsList().size(); i++) {
			String ansText = dialog.getTfAnsList().get(i).getText();
			if(!ansText.isEmpty()) {
				Answer answer = new Answer();
				answer.setQuestion(question);
				answer.setTitle(ansText);
				answer.setIsCorrect(dialog.getCbAnsList().get(i).isSelected());
				
				answers.add(answer);
			}
		}
		question.setAnswers(new HashSet<Answer>(answers));

		Set<QuestionLevel> levels = new HashSet<QuestionLevel>();

		for (int i = 0; i < dialog.getCbLevelsList().size(); i++)
			if (dialog.getCbLevelsList().get(i).isSelected())
				levels.add(QuestionLevel.valueOf(dialog.getCbLevelsList().get(i).getText()));

		question.setLevels(levels);

		SpecificationService specService = new SpecificationService();

		List<Specification> specs = specService
				.getByName(dialog.getAccbSpecNames().getSelectedItem().toString());
		
		Specification specToAdd = null;
		if(specs.size() == 0) {
			specToAdd = new Specification();
			specToAdd.setName(String.valueOf(dialog.getAccbSpecNames().getSelectedItem()));
		} else {
			specToAdd = specs.get(0);
		}
			
		question.setSpecification(specToAdd);

		
		return question;
	}
	
	private boolean validateQuestionSave() {
		// Валидация текста вопроса
		// Длина текста вопроса слишком маленькая
		if(dialog.getTaQuestion().getText().length() < AppConstants.QUESTION_TEXT_MIN_LENGTH)
			return false;
		
		//----------------
		// Валидация ответов
		boolean isAnyAnswerAsCorrectSelected = false;
		int amountOfAnswers = 0;
		for(int i = 0; i < AppConstants.MAX_ANSWERS_AMOUNT; i++) {
			String currAnswer = dialog.getTfAnsList().get(i).getText();
			// Длина текста ответа слишком маленькая
			if(currAnswer.length() > 0 && currAnswer.length() < AppConstants.ANSWER_TEXT_MIN_LENGTH)
				return false;
			
			if(!currAnswer.isEmpty()) {
				amountOfAnswers ++;
			// Пустой вопрос помечен как верный	
			} else if(dialog.getCbAnsList().get(i).isSelected()){
				return false;
			}
			
			if(dialog.getCbAnsList().get(i).isSelected())
				isAnyAnswerAsCorrectSelected = true;
		}
		// Не заполнено минимальное количество ответов
		if(amountOfAnswers < AppConstants.MIN_ANSWERS_AMOUNT)
			return false;
		
		// Ни один из ответов не помечен как верный
		if(!isAnyAnswerAsCorrectSelected)
			return false;
		
		//----------------
		// Валидация сложности
		boolean isAnyLevelSelected = false;
		for(int i = 0; i < dialog.getCbLevelsList().size(); i++) 
			if(dialog.getCbLevelsList().get(i).isSelected())
				isAnyLevelSelected = true;
		// Не выбрано ни одной сложности для вопроса
		if(!isAnyLevelSelected)
			return false;
		
		//----------------
		// Валидация спецализации
		// Не заполнена специализация
		if(dialog.getAccbSpecNames().getSelectedItem().toString().isEmpty())
			return false;
		
		return true;
		
	}

	private void doSaveQuestionAction() {
		if(!validateQuestionSave()) {
			MessageBox.showWrongQuestionSpecifiedErrorDialog(dialog);
			return;
		}
		
		QuestionService service = new QuestionService();
		Question questionToSave = configureQuestionFromUserInput();
		
		if(questionToSave.getId() == null)
			service.save(questionToSave);
		else
			service.update(questionToSave.getId(), questionToSave);

		doFilterAction();
	}

	private void doClearFiltersAction() {
		dialog.getTfId().setText(null);
		dialog.getTfQuestionName().setText(null);
		dialog.getTfSpecs().setText(null);
		dialog.getTfLevels().setText(null);

		doFilterAction();
	}
	
	private void blockQEditElements(boolean action) {
		blockQuestionEditFields(false);
		isQuestionEditFieldsBlocked = false;
	}

	private void doEditAction() {
		if (isQuestionEditFieldsBlocked) 
			blockQEditElements(false);
		 else 
			blockQEditElements(true);
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
