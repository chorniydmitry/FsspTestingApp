package ru.fssprus.r82.swing.main.testingPanel.ss;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JRadioButton;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.swing.main.ControllerWithTimer;
import ru.fssprus.r82.swing.main.resultingDialog.ResultingController;
import ru.fssprus.r82.swing.main.resultingDialog.ResultingDialog;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.TestingProcess;

public class TestController extends ControllerWithTimer implements ActionListener, KeyListener {
	private static final int NEXT = 1;
	private static final int PREVIOS = -1;

	private List<Specification> specs;
	private TestDialog testDialog;
	private TestingProcess testingProcess;
	private int questTimeSec;

	public TestController(TestDialog testDialog, List<Specification> specs, 
			TestingProcess testingProcess, int selectedLevel) {
		this.testingProcess = testingProcess;
		this.testDialog = testDialog;
		this.specs = specs;

		setListeners();

		showQuestionContents(testingProcess.getCurrentQuestionIndex());
		setLblForInfo(testDialog.getLblTimeLeftSec());
		setTimer(selectedLevel);

		testDialog.setVisible(true);
	}
	
	private void setListeners() {
		setOnCloseListener();
		testDialog.getBtnNext().addActionListener(this);
		testDialog.getBtnPrevious().addActionListener(this);
		testDialog.getBtnFinish().addActionListener(this);
		testDialog.getBtnPause().addActionListener(this);
		testDialog.getBtnNextUnanswered().addActionListener(this);
		testDialog.addKeyListener(this);
	}

	private void setOnCloseListener() {
		testDialog.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				ResultingDialog dialog = new ResultingDialog(400, 250);
				dialog.setCaptions(testingProcess.getCorrectAnswersAmount(), testingProcess.getQuestions().size(),
						testingProcess.getMarkPercent(), testingProcess.getMarkOneToFive(),
						testingProcess.getMarkText(), testingProcess.getMarkLetter());

				dialog.setMarkColor(testingProcess.getMarkColor());

				new ResultingController(dialog, testingProcess);
			}
		});
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (isNumeric(String.valueOf(e.getKeyChar()))) {
			int num = Integer.parseInt(String.valueOf(e.getKeyChar()));
			if (num <= testDialog.getRbAnswers().size())
				doNumAction(num);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_ENTER)
			testDialog.getBtnNext().doClick();
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			testDialog.getBtnPrevious().doClick();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setUserChoise();
		
		if (e.getSource() == testDialog.getBtnNext())
			doNextAction();

		if (e.getSource() == testDialog.getBtnPrevious())
			doPreviousAction();

		if (e.getSource() == testDialog.getBtnFinish())
			doFinishAction();

		if (e.getSource() == testDialog.getBtnPause())
			doPauseAction();

		if (e.getSource() == testDialog.getBtnNextUnanswered())
			doNextUnansweredAction();
		
		doUpdate();
	}
	
	private void doPauseAction() {
		testDialog.hideInterface(!testDialog.isPaused());
	}

	private void doNumAction(int num) {
		testDialog.getRbAnswers().get(num - 1).setSelected(true);
	}

	private void doNextAction() {
		switchQuestion(NEXT);
	}

	private void doPreviousAction() {
		switchQuestion(PREVIOS);
	}
	
	private void doFinishAction() {
		testDialog.dispose();
		testingProcess.saveResultsToDB(specs.get(0), (questTimeSec - getTimeLeft()));
	}

	private void doNextUnansweredAction() {
		goToQuestion(testingProcess.getNextUnansweredIndex());
	}

	private void doUpdate() {
		// если выбраны ответы на все вопросы кнопка "К следующему" блокируется
		if (!testingProcess.checkUncheckedLeft())
			testDialog.getBtnNextUnanswered().setEnabled(false);
		
		checkButtonsEnabled();
	}
	
	private void goToQuestion(int number) {
		if (number >= testingProcess.getQuestions().size() || number < 0)
			return;
		
		clearRb();
		testingProcess.setCurrentQuestionIndex(number);
		showQuestionContents(number);
		testDialog.requestFocus();
	}
	
	private void switchQuestion(int where) {
		goToQuestion(testingProcess.getCurrentQuestionIndex() + where);
	}

	private void checkButtonsEnabled() {
		checkNextEnabled();
		checkPreviousEnabled();
	}

	private void checkNextEnabled() {
		int goToQuestionIndex = testingProcess.getCurrentQuestionIndex() + 1;
		if (goToQuestionIndex >= testingProcess.getQuestions().size())
			testDialog.getBtnNext().setEnabled(false);
		else if (!testDialog.getBtnNext().isEnabled())
			testDialog.getBtnNext().setEnabled(true);

	}

	private void checkPreviousEnabled() {
		int goToQuestionIndex = testingProcess.getCurrentQuestionIndex() - 1;
		if (goToQuestionIndex < 0)
			testDialog.getBtnPrevious().setEnabled(false);
		else if (!testDialog.getBtnPrevious().isEnabled())
			testDialog.getBtnPrevious().setEnabled(true);
	}

	private void setTimer(int level) {
		int timeSec = getQuestionTimeSec(level);
		initTimer(timeSec);
		setQuestTimeSec(timeSec);
	}
	
	private int getQuestionTimeSec(int level) {
		int timeSeconds = 0;
		switch (level) {
		case 0:
			timeSeconds = AppConstants.BASE_TIME;
			break;
		case 1:
			timeSeconds = AppConstants.STANDART_TIME;
			break;
		case 2:
			timeSeconds = AppConstants.ADVANCED_TIME;
			break;
		case 3:
			timeSeconds = AppConstants.RESERVE_TIME;
			break;
		}
		return timeSeconds;
	}

	private static boolean isNumeric(String strNum) {
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}

	private void setUserChoise() {
		int userSelectedIndex = getSelectedIndex();
		testingProcess.setChoise(userSelectedIndex);
	}

	private void showQuestionContents(int index) {
		showQuestion(index);
		showAnswers(index);
		showSelectedRb(index);
	}

	private void showQuestion(int index) {
		String questionText = testingProcess.getQuestions().get(index).getTitle();

		testDialog.getLblQuestionInfo()
				.setText("Вопрос № " + (index + 1) + " из " + testingProcess.getQuestions().size());

		testDialog.getLblQuestionText().setText(questionText);
	}

	private void showAnswers(int index) {
		List<Answer> answers = testingProcess.getAnswersMap().get(index);
		for (int i = 0; i < answers.size(); i++) {
			int width = testDialog.getWidth() - 250;
			testDialog.getRbAnswers().get(i)
					.setText("<html><p style=\"width:" + width + "px\">" + answers.get(i).getTitle() + "</p></html>");
		}
		for (int i = answers.size(); i < 5; i++)
			testDialog.getRbAnswers().get(i).setVisible(false);
	}

	private void showSelectedRb(int index) {
		int selected = testingProcess.getChoises().get(index);
		if (selected != -1)
			testDialog.getRbAnswers().get(selected).setSelected(true);
	}

	private void clearRb() {
		testDialog.getBgAnswers().clearSelection();
		ArrayList<JRadioButton> rbAnswers = testDialog.getRbAnswers();
		for (JRadioButton jrb : rbAnswers) {
			jrb.setText("");
			jrb.setVisible(true);
		}
	}

	private int getSelectedIndex() {
		ArrayList<JRadioButton> rbs = testDialog.getRbAnswers();
		for (int i = 0; i < rbs.size(); i++) {
			if (rbs.get(i).isSelected())
				return i;
		}
		return -1;
	}

	public TestDialog getTestPanel() {
		return testDialog;
	}

	public void setTestPanel(TestDialog testPanel) {
		this.testDialog = testPanel;
	}

	public List<Specification> getSpecs() {
		return specs;
	}

	public void setSpecs(List<Specification> specs) {
		this.specs = specs;
	}

	public int getQuestTimeSec() {
		return questTimeSec;
	}

	public void setQuestTimeSec(int questTimeSec) {
		this.questTimeSec = questTimeSec;
	}
}
