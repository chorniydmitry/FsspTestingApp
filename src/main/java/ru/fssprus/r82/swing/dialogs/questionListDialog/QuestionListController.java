package ru.fssprus.r82.swing.dialogs.questionListDialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.service.QuestionService;
import ru.fssprus.r82.service.SpecificationService;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.table.TablePanelController;
import ru.fssprus.r82.swing.table.UpdatableController;
import ru.fssprus.r82.swing.ulils.MessageBox;
import ru.fssprus.r82.utils.AppConstants;

public class QuestionListController extends CommonController<QuestionListDialog> implements UpdatableController {
	private static final int ENTRIES_FOR_PAGE = AppConstants.TABLE_ROWS_LIMIT;
	private int currentPage;
	private int totalPages;
	
	private List<Question> questionsOnScreenList;

	private Question currentQuestion = null;
	private boolean questionEditing = false;
	
	private int totalQuestions;

	public QuestionListController(QuestionListDialog dialog) {
		super(dialog);

		TablePanelController tablePanelController = new TablePanelController(dialog.getTabPanel());
		tablePanelController.setSubscriber(this);

		blockQuestionEditPanel(true);

		updateTable();
	}

	@Override
	protected void setListeners() {
		dialog.getBtnFilter().addActionListener(listener -> updateTable());
		dialog.getBtnDiscardQuestionEditChanges().addActionListener(listener -> doDiscardChangesAction());
		dialog.getBtnEditQuestion().addActionListener(listener -> doEditAction());
		dialog.getBtnClearFilters().addActionListener(listener -> doClearFiltersAction());
		dialog.getBtnSaveQuestion().addActionListener(listener -> doSaveQuestionAction());
	}

	private void doEditAction() {
		if (dialog.getTable().getLastSelectedIndex()== -1)
			return;
		if (questionEditing) {
			blockQuestionEditPanel(false);
		} else {
			blockQuestionEditPanel(true);
		}
	}

	private void doSaveQuestionAction() {
		if (!validateQuestionSave()) {
			MessageBox.showWrongQuestionSpecifiedErrorDialog(dialog);
			return;
		}

		QuestionService service = new QuestionService();
		Question questionToSave = configureQuestionFromUserInput();

		if (questionToSave.getId() == null)
			service.save(questionToSave);
		else
			service.update(questionToSave.getId(), questionToSave);

		updateTable();
		blockQuestionEditPanel(true);
	}

	private void doClearFiltersAction() {
		clearQuestionEditPanelContents();
		dialog.getTable().unselectAll();

		dialog.getTfId().setText(null);
		dialog.getTfQuestionName().setText(null);
		dialog.getTfSpecs().setText(null);
		dialog.getTfLevels().setText(null);

		updateTable();
	}

	private void doDiscardChangesAction() {
		clearQuestionEditPanelContents();
		showQuestion(currentQuestion);
	}

	private void updateTable() {
		clearQuestionEditPanelContents();
		dialog.getTable().unselectAll();

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

			totalQuestions = questionService.countByNameSpecListLvlListAndId(questTitleText, specs, levels, id);

			if (totalQuestions <= ENTRIES_FOR_PAGE) {
				start = -1;
				max = -1;
			}
			questionsOnScreenList = questionService.getByNameSpecListLvlListAndId(start, max, questTitleText, specs,
					levels, id);
			
			dialog.getTabPanel().getTfPage().setText(String.valueOf(currentPage + 1));
			dialog.getTabPanel().getLblPagesTotal().setText(" из " + countTotalPages(totalQuestions));

			dialog.getTable().getTabModel().clearTable();
			convertAndAddToTable(questionsOnScreenList);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	private void blockQuestionEditPanel(boolean block) {
		questionEditing = block;
		dialog.getTaQuestion().setEditable(!block);

		for (int i = 0; i < dialog.getCbLevelsList().size(); i++) {
			dialog.getCbLevelsList().get(i).setEnabled(!block);
		}
		dialog.getAccbSpecNames().setEditable(!block);

		for (int i = 0; i < AppConstants.MAX_ANSWERS_AMOUNT; i++) {
			dialog.getTfAnsList().get(i).setEditable(!block);
			dialog.getCbAnsList().get(i).setEnabled(!block);
		}
		dialog.getBtnSaveQuestion().setEnabled(!block);
		dialog.getBtnDiscardQuestionEditChanges().setEnabled(!block);
	}

	private void showQuestion(Question currentQuestion) {
		List<Answer> answers = new ArrayList<Answer>(currentQuestion.getAnswers());

		dialog.getTaQuestion().setText(currentQuestion.getTitle());

		for (int i = 0; i < answers.size(); i++) {
			dialog.getTfAnsList().get(i).setText(answers.get(i).getTitle());
			dialog.getTfAnsList().get(i).setCaretPosition(0);
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

		if (currentQuestion.getId() != null)
			question.setId(currentQuestion.getId());

		question.setTitle(dialog.getTaQuestion().getText());

		List<Answer> answers = new ArrayList<Answer>();
		for (int i = 0; i < dialog.getTfAnsList().size(); i++) {
			String ansText = dialog.getTfAnsList().get(i).getText();
			if (!ansText.isEmpty()) {
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

		List<Specification> specs = specService.getByName(dialog.getAccbSpecNames().getSelectedItem().toString());

		Specification specToAdd = null;
		if (specs.size() == 0) {
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
		if (dialog.getTaQuestion().getText().length() < AppConstants.QUESTION_TEXT_MIN_LENGTH)
			return false;

		// ----------------
		// Валидация ответов
		boolean isAnyAnswerAsCorrectSelected = false;
		int amountOfAnswers = 0;
		for (int i = 0; i < AppConstants.MAX_ANSWERS_AMOUNT; i++) {
			String currAnswer = dialog.getTfAnsList().get(i).getText();

			if (!currAnswer.isEmpty()) {
				amountOfAnswers++;
				// Пустой вопрос помечен как верный
			} else if (dialog.getCbAnsList().get(i).isSelected()) {
				return false;
			}

			if (dialog.getCbAnsList().get(i).isSelected())
				isAnyAnswerAsCorrectSelected = true;
		}
		// Не заполнено минимальное количество ответов
		if (amountOfAnswers < AppConstants.MIN_ANSWERS_AMOUNT)
			return false;

		// Ни один из ответов не помечен как верный
		if (!isAnyAnswerAsCorrectSelected)
			return false;

		// ----------------
		// Валидация сложности
		boolean isAnyLevelSelected = false;
		for (int i = 0; i < dialog.getCbLevelsList().size(); i++)
			if (dialog.getCbLevelsList().get(i).isSelected())
				isAnyLevelSelected = true;
		// Не выбрано ни одной сложности для вопроса
		if (!isAnyLevelSelected)
			return false;

		// ----------------
		// Валидация спецализации
		// Не заполнена специализация
		if (dialog.getAccbSpecNames().getSelectedItem().toString().isEmpty())
			return false;

		return true;

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
	
	private void convertAndAddToTable(List<Question> questions) {

		for (int i = 0; i < questions.size(); i++) {
			Question question = questions.get(i);

			Long id = question.getId();
			String title = question.getTitle();
			Set<QuestionLevel> levels = question.getLevels();
			Specification specification = question.getSpecification();
			String lvlsString = "";
			for (QuestionLevel ql : levels)
				lvlsString += "[" + ql.name() + "] ";

			String specString = specification.getName();

			Object[] row = { id, title, lvlsString, specString };
			
			dialog.getTableModel().setRow(row, i);
			
			dialog.getTableModel().update();
		}
	}

	private void addBlankQuestion() {
		Question q = new Question();
		q.setAnswers(new HashSet<Answer>(AppConstants.MAX_ANSWERS_AMOUNT));
		q.setLevels(new HashSet<QuestionLevel>(QuestionLevel.values().length));
		q.setSpecification(new Specification());

		questionsOnScreenList.add(q);
		
		dialog.getBtnEditQuestion().doClick();
	}

	@Override
	public void edit(int index) {
		if (index >= questionsOnScreenList.size())
			addBlankQuestion();
		clearQuestionEditPanelContents();
		currentQuestion = questionsOnScreenList.get(index);
		doDiscardChangesAction();
		
	}

	@Override
	public void nextPage() {
		if(currentPage+1 < totalPages)
			currentPage++;
		updateTable();
		blockQuestionEditPanel(true);
	}

	@Override
	public void previousPage() {
		if(currentPage > 0)
			currentPage--;
		updateTable();
		blockQuestionEditPanel(true);
	}

	@Override
	public void delete(int index) {
		if (MessageBox.showConfirmQuestionDelete(dialog)) {
			QuestionService service = new QuestionService();
			service.delete(currentQuestion);
		}
		updateTable();
		
	}

	@Override
	public void goToPage(int page) {
		if(page <= totalPages && page > 0)
			currentPage = page -1;
		
		updateTable();
	}

}
