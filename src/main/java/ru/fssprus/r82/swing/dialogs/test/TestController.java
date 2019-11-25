package ru.fssprus.r82.swing.dialogs.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JCheckBox;

import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.P;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.swing.dialogs.ControllerWithTimer;
import ru.fssprus.r82.swing.dialogs.DialogBuilder;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.TimeUtils;
import ru.fssprus.r82.utils.Utils;
import ru.fssprus.r82.utils.testingTools.TestingProcessAnaliser;
import ru.fssprus.r82.utils.testingTools.TestingProcess;
import ru.fssprus.r82.utils.testingTools.TestingResultsSaver;

/**
 * @author Chernyj Dmitry
 *
 */
public class TestController extends ControllerWithTimer<TestDialog> implements KeyListener {

	private static final String QUESTION_NUM_TEXT = "ВОПРОС #";
	private static final String OF_TEXT = " из ";
	private static final int ANSWER_OFFSET = 300;
	private static final int NEXT = 1;
	private static final int PREVIOUS = -1;
	private static final String ANS_P_STYLE_WIDTH = "width: ";
	private static final String ANS_P_STYLE_PX = " px";
	private static final String HTML_ARGUMENT_TO_REMOVE = "xmlns";
	
	private TestingProcess testingProcess;
	private List<Question> questionList;

	private int currentIndex;

	public TestController(TestDialog dialog, TestingProcess testingProcess) {
		super(dialog, getTime(testingProcess.getTestLevel()), dialog.getLblTimeLeftSec());
		this.testingProcess = testingProcess;

		initVariables();
		showCurrentQuestionAndAnswers();
		
		dialog.setFocusable(true);
		dialog.requestFocusInWindow();
		dialog.addKeyListener(this);
	}
	
	private void initVariables() {
		questionList = new ArrayList<>(testingProcess.getQuestionsAndAnswersGiven().keySet());
		currentIndex = 0;
	}

	private void showCurrentQuestionAndAnswers() {
		resetCheckBoxes();
		showQuestion(questionList.get(currentIndex));
		showAnswers();
		
		dialog.requestFocus();
		update();
	}

	private void showQuestion(Question questionToShow) {
		dialog.getLblQuestionInfo().setText(QUESTION_NUM_TEXT + (currentIndex + 1) + OF_TEXT + questionList.size());

		dialog.getTaQuestionText().setText(questionToShow.getTitle());
	}
	
	private void showAnswers() {
		List<Answer> ansList = new ArrayList<>(questionList.get(currentIndex).getAnswers());
		for (Answer ans : ansList) {
		

			dialog.getCbAnswers().get(ansList.indexOf(ans))
					.setText(generateHTMLText(ans));

			checkIfAlreadySelected(ans, ansList);
		}

		hideNotShowingAnswers(ansList.size());
	}
		
	private String generateHTMLText(Answer answer) {
		final int ansWidth = dialog.getWidth() - ANSWER_OFFSET;
		
		Html html = new Html();
		P p = new P();
		p.setStyle(ANS_P_STYLE_WIDTH + ansWidth + ANS_P_STYLE_PX);
		p.appendText(answer.getTitle());
		html.appendChild(p);
		html.removeAttribute(HTML_ARGUMENT_TO_REMOVE);
		
		return html.write();
	}

	private void checkIfAlreadySelected(Answer ans, List<Answer> ansList) {
		List<Answer> selectedQuestions = testingProcess.getQuestionsAndAnswersGiven()
				.get(questionList.get(currentIndex));
		for (Answer ansSelected : selectedQuestions) {
			if (ans.equals(ansSelected))
				dialog.getCbAnswers().get(ansList.indexOf(ans)).setSelected(true);
		}
	}

	private void hideNotShowingAnswers(int fromIndex) {
		for (int i = fromIndex; i < AppConstants.MAX_ANSWERS_AMOUNT; i++)
			dialog.getCbAnswers().get(i).setVisible(false);
	}

	@Override
	protected void setListeners() {
		setOnCloseListener();
		dialog.getBtnNext().addActionListener(listener -> doNextAction());
		dialog.getBtnPrevious().addActionListener(listeber -> doPreviousAction());
		dialog.getBtnFinish().addActionListener(listener -> doFinishAction());
		dialog.getBtnNextUnanswered().addActionListener(listener -> doNextUnansweredAction());
	}

	private void checkButtonsEnabled() {
		checkNextBtnAllowed();
		checkPreviousBtnAllowed();
	}

	private void checkNextBtnAllowed() {
		boolean isEnabled = ((currentIndex + NEXT) >= questionList.size()) ? false : true;

		dialog.getBtnNext().setEnabled(isEnabled);
	}

	private void checkPreviousBtnAllowed() {
		boolean isEnabled = (currentIndex + PREVIOUS < 0) ? false : true;

		dialog.getBtnPrevious().setEnabled(isEnabled);
	}

	private void doNextUnansweredAction() {
		setUserChoise();
		goToQuestion(findNextUnansweredIndex());
	}

	private void doFinishAction() {
		dialog.dispose();
	}

	private int findNextUnansweredIndex() {
		for(Entry<Question, List<Answer>> entry : testingProcess.getQuestionsAndAnswersGiven().entrySet()) {
			if(entry.getValue().size() == 0)
				return questionList.indexOf(entry.getKey());
		}
		return AppConstants.NO_INDEX_SELECTED;
	}
	
	private boolean isUnckeckedLeft() {
		return !(findNextUnansweredIndex() == AppConstants.NO_INDEX_SELECTED);
	}

	private void doPreviousAction() {
		setUserChoise();
		goToQuestion(currentIndex + PREVIOUS);
	}

	private void doNextAction() {
		setUserChoise();
		goToQuestion(currentIndex + NEXT);
	}

	private void doNumAction(int num) {
		dialog.getCbAnswers().get(num - 1).setSelected(
				!dialog.getCbAnswers().get(num - 1).isSelected());
	}

	private void goToQuestion(int questionIndex) {
		if (questionIndex >= questionList.size() || questionIndex < 0) {
			update();
			return;
		}

		currentIndex = questionIndex;
		showCurrentQuestionAndAnswers();
	}

	private void update() {
		// если выбраны ответы на все вопросы кнопка "К следующему" блокируется
		dialog.getBtnNextUnanswered().setEnabled(isUnckeckedLeft());
		checkButtonsEnabled();
	}

	private void setUserChoise() {
		List<Integer> indexes = getSelectedIndexes();
		List<Answer> selected = getAnswersByIndexes(indexes);
		testingProcess.makeAnswer(questionList.get(currentIndex), selected);
	}

	private void resetCheckBoxes() {
		dialog.getCbAnswers().forEach(n -> {
			n.setSelected(false);
			n.setText(null);
			n.setVisible(true);
		});
	}

	private List<Integer> getSelectedIndexes() {
		List<Integer> selectedIndexes = new ArrayList<>();
		for (JCheckBox cb : dialog.getCbAnswers())
			if (cb.isSelected())
				selectedIndexes.add(dialog.getCbAnswers().indexOf(cb));

		return selectedIndexes;
	}

	private List<Answer> getAnswersByIndexes(List<Integer> indexes) {
		if (indexes == null || indexes.size() == 0)
			return null;
		
		List<Answer> answers = new ArrayList<Answer>();

		for (int index : indexes) {
			if (index >= questionList.get(currentIndex).getAnswers().size())
				break;
			answers.add(new ArrayList<Answer>(questionList.get(currentIndex).getAnswers()).get(index));
		}
		
		return answers;

	}

	private void setOnCloseListener() {
		dialog.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				setUserChoise();
				
				TestingProcessAnaliser tpAnaliser = new TestingProcessAnaliser(testingProcess);
				tpAnaliser.analize();
				
				new TestingResultsSaver().saveResultsToDB(getTimeLeft(), tpAnaliser);
				
				DialogBuilder.showResultingDialog(tpAnaliser);
			}
		});
	}


	private static int getTime(QuestionLevel lvl) {
		return TimeUtils.getQuizzTimeSecByLevel(lvl);
	}

	public TestController(TestDialog dialog, int time) {
		super(dialog, time, dialog.getLblTimeLeftSec());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (Utils.isNumeric(String.valueOf(e.getKeyChar()))) {
			int num = Integer.parseInt(String.valueOf(e.getKeyChar()));
			if (num <= dialog.getCbAnswers().size())
				doNumAction(num);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			dialog.getBtnNext().doClick();
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			dialog.getBtnPrevious().doClick();
	}

	@Override
	public void keyReleased(KeyEvent e) {


	}

}
