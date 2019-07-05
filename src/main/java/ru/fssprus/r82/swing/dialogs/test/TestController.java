package ru.fssprus.r82.swing.dialogs.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.swing.dialogs.ControllerWithTimer;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.TimeUtils;
import ru.fssprus.r82.utils.Utils;
import ru.fssprus.r82.utils.testingTools.TestingProcessObjective;

public class TestController extends ControllerWithTimer<TestDialog> implements KeyListener {
	
	private static final String QUESTION_NUM_TEXT = "ТЕКУЩИЙ ВОПРОС: Вопрос # ";
	private static final String OF_TEXT = " из ";
	private static final int ANSWER_OFFSET = 250;
	private static final int NEXT = 1;
	private static final int PREVIOUS = -1;
	private static String ANS_HTML_OPEN = "<html><p style=\"width:";
	private static String ANS_HTML_STYLE_CLOSE = "px\">";
	private static String ANS_HTML_CLOSE = "</p></html>";
	
	private TestingProcessObjective testingProcess;
	private List<Question> questionList;	
	
	private int currentIndex;
	
	public TestController(TestDialog dialog, 
			TestingProcessObjective testingProcess) {
		
		super(dialog, getTime(testingProcess.getTestLevel().ordinal()));
		initTimer(testingProcess.getTestLevel().ordinal());
		
		this.testingProcess = testingProcess;
		
		//TODO: инициализировать время теста
		
		initVariables();
		showCurrentQuestionAndAnswers();
		
		dialog.setVisible(true);
	}
	
	private void initVariables() {
		questionList = new ArrayList<>(testingProcess.getQuestionsAndAnswersGiven().keySet());
		currentIndex = 0;
	}
	
	private void showCurrentQuestionAndAnswers() {
		showQuestion(questionList.get(currentIndex));
		showAnswers();
	}
	
	private void showQuestion(Question questionToShow) {
		dialog.getLblQuestionInfo().setText(
				QUESTION_NUM_TEXT + 
				(currentIndex + 1) + 
				OF_TEXT + 
				questionList.size());
		
		dialog.getLblQuestionText().setText(questionToShow.getTitle());
	}
	
	private void showAnswers() {
		List<Answer> ansList = new ArrayList<>(questionList.get(currentIndex).getAnswers());
		for(Answer ans : ansList) {
			final int ansWidth = dialog.getWidth() - ANSWER_OFFSET;
			
			dialog.getCbAnswers().get(ansList.indexOf(ans)).setText(
					ANS_HTML_OPEN + ansWidth + ANS_HTML_STYLE_CLOSE + ans.getTitle() + ANS_HTML_CLOSE);

		}
		
		hideNotShowingAnswers(ansList.size());
	}
	
	private void hideNotShowingAnswers(int fromIndex) {
		for(int i = fromIndex; i < AppConstants.MAX_ANSWERS_AMOUNT; i++)
			dialog.getCbAnswers().get(i).setVisible(false);
	}
	
	@Override
	protected void setListeners() {
		setOnCloseListener();
		dialog.getBtnNext().addActionListener(listener -> doNextAction());
		dialog.getBtnPrevious().addActionListener(listeber -> doPreviousAction());
		dialog.getBtnFinish().addActionListener(listener -> doFinishAction());
		dialog.getBtnNextUnanswered().addActionListener(listener -> doNextUnansweredAction());
		dialog.addKeyListener(this);

	}
	
	private void checkBtnsEnabled() {
		checkNextBtnAllowed();
		checkPreviousBtnAllowed();
	}
	
	private void checkNextBtnAllowed() {
		if(currentIndex + NEXT >= questionList.size())
			dialog.getBtnNext().setEnabled(false);
		dialog.getBtnNext().setEnabled(true);
	}
	
	private void checkPreviousBtnAllowed() {
		if(currentIndex + PREVIOUS < 0)
			dialog.getBtnPrevious().setEnabled(false);
		dialog.getBtnPrevious().setEnabled(true);
	}
	
	private Object doNextUnansweredAction() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object doFinishAction() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object doPreviousAction() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object doNextAction() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void doNumAction(int num) {
		// TODO Auto-generated method stub
		
	}

	private void setOnCloseListener() {
		dialog.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				initResultingDialog();
			}
		});
	}
	
	private void initResultingDialog() {
		//DialogBuilder.showResultingDialog(testingProcess);
	}

	private static int getTime(int i) {
		return TimeUtils.getQuizzTimeSecByLevel(i);
	}

	public TestController(TestDialog dialog, int time) {
		super(dialog, time);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
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
	
}
