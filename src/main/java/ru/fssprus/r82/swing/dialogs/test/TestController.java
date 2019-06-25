package ru.fssprus.r82.swing.dialogs.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JRadioButton;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.swing.dialogs.ControllerWithTimer;
import ru.fssprus.r82.swing.dialogs.resulting.ResultingController;
import ru.fssprus.r82.swing.dialogs.resulting.ResultingDialog;
import ru.fssprus.r82.utils.ApplicationConfiguration;
import ru.fssprus.r82.utils.TestingProcess;

public class TestController extends ControllerWithTimer<TestDialog> implements ActionListener, KeyListener {
	private static final int NEXT = 1;
	private static final int PREVIOS = -1;

	private List<Specification> specs;
	private TestingProcess testingProcess;
	private int questTimeSec;

	public TestController(TestDialog dialog, List<Specification> specs, TestingProcess testingProcess,
			int selectedLevel) {
		super(dialog, 0);
		this.testingProcess = testingProcess;
		this.specs = specs;
		showQuestionContents(testingProcess.getCurrentQuestionIndex());
		setLblForInfo(dialog.getLblTimeLeftSec());
		setTimer(selectedLevel);

		dialog.setVisible(true);
	}
	
	@Override
	protected void setListeners() {
		setOnCloseListener();
		dialog.getBtnNext().addActionListener(this);
		dialog.getBtnPrevious().addActionListener(this);
		dialog.getBtnFinish().addActionListener(this);
		dialog.getBtnPause().addActionListener(this);
		dialog.getBtnNextUnanswered().addActionListener(this);
		dialog.addKeyListener(this);

	}

	private void setOnCloseListener() {
		dialog.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				ResultingDialog dialog = new ResultingDialog(400, 250);
				dialog.setCaptions(testingProcess.getCorrectAnswersAmount(), testingProcess.getQuestions().size(),
						testingProcess.getMarkPercent(), testingProcess.getMarkOneToFive(),
						testingProcess.getMarkText(), testingProcess.getMarkLetter());

				dialog.setMarkColor(testingProcess.getMarkColor());

				ResultingController resultingController = new ResultingController(dialog);
				resultingController.setTestingProcess(testingProcess);
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
			if (num <= dialog.getRbAnswers().size())
				doNumAction(num);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_ENTER)
			dialog.getBtnNext().doClick();
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			dialog.getBtnPrevious().doClick();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setUserChoise();

		if (e.getSource() == dialog.getBtnNext())
			doNextAction();

		if (e.getSource() == dialog.getBtnPrevious())
			doPreviousAction();

		if (e.getSource() == dialog.getBtnFinish())
			doFinishAction();

		if (e.getSource() == dialog.getBtnPause())
			doPauseAction();

		if (e.getSource() == dialog.getBtnNextUnanswered())
			doNextUnansweredAction();

		doUpdate();
	}

	private void doPauseAction() {
		dialog.hideInterface(!dialog.isPaused());
	}

	private void doNumAction(int num) {
		dialog.getRbAnswers().get(num - 1).setSelected(true);
	}

	private void doNextAction() {
		switchQuestion(NEXT);
	}

	private void doPreviousAction() {
		switchQuestion(PREVIOS);
	}

	private void doFinishAction() {
		dialog.dispose();
		testingProcess.saveResultsToDB(specs.get(0), (questTimeSec - getTimeLeft()));
	}

	private void doNextUnansweredAction() {
		goToQuestion(testingProcess.getNextUnansweredIndex());
	}

	private void doUpdate() {
		// если выбраны ответы на все вопросы кнопка "К следующему" блокируется
		if (!testingProcess.checkUncheckedLeft())
			dialog.getBtnNextUnanswered().setEnabled(false);

		checkButtonsEnabled();
	}

	private void goToQuestion(int number) {
		if (number >= testingProcess.getQuestions().size() || number < 0)
			return;

		clearRb();
		testingProcess.setCurrentQuestionIndex(number);
		showQuestionContents(number);
		dialog.requestFocus();
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
			dialog.getBtnNext().setEnabled(false);
		else if (!dialog.getBtnNext().isEnabled())
			dialog.getBtnNext().setEnabled(true);

	}

	private void checkPreviousEnabled() {
		int goToQuestionIndex = testingProcess.getCurrentQuestionIndex() - 1;
		if (goToQuestionIndex < 0)
			dialog.getBtnPrevious().setEnabled(false);
		else if (!dialog.getBtnPrevious().isEnabled())
			dialog.getBtnPrevious().setEnabled(true);
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
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("base.time"));
			break;
		case 1:
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("standart.time"));
			break;
		case 2:
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("advanced.time"));
			break;
		case 3:
			timeSeconds = Integer.parseInt(ApplicationConfiguration.getItem("reserve.time"));
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
		
		dialog.getLblQuestionInfo()
				.setText("Вопрос № " + (index + 1) + " из " + testingProcess.getQuestions().size());

		dialog.getLblQuestionText().setText(questionText);
	}

	private void showAnswers(int index) {
		List<Answer> answers = testingProcess.getAnswersMap().get(index);
		for (int i = 0; i < answers.size(); i++) {
			int width = dialog.getWidth() - 250;
			dialog.getRbAnswers().get(i)
					.setText("<html><p style=\"width:" + width + "px\">" + answers.get(i).getTitle() + "</p></html>");
		}
		for (int i = answers.size(); i < 5; i++)
			dialog.getRbAnswers().get(i).setVisible(false);
	}

	private void showSelectedRb(int index) {
		int selected = testingProcess.getChoises().get(index);
		if (selected != -1)
			dialog.getRbAnswers().get(selected).setSelected(true);
	}

	private void clearRb() {
		dialog.getBgAnswers().clearSelection();
		ArrayList<JRadioButton> rbAnswers = dialog.getRbAnswers();
		for (JRadioButton jrb : rbAnswers) {
			jrb.setText("");
			jrb.setVisible(true);
		}
	}

	private int getSelectedIndex() {
		ArrayList<JRadioButton> rbs = dialog.getRbAnswers();
		for (int i = 0; i < rbs.size(); i++) {
			if (rbs.get(i).isSelected())
				return i;
		}
		return -1;
	}

	public TestDialog getTestPanel() {
		return dialog;
	}

	public void setTestPanel(TestDialog testPanel) {
		this.dialog = testPanel;
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
