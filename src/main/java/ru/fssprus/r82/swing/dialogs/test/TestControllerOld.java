package ru.fssprus.r82.swing.dialogs.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

import ru.fssprus.r82.entity.Answer;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.swing.dialogs.ControllerWithTimer;
import ru.fssprus.r82.swing.dialogs.DialogBuilder;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.ApplicationConfiguration;
import ru.fssprus.r82.utils.TestingProcess;
import ru.fssprus.r82.utils.Utils;

public class TestControllerOld extends ControllerWithTimer<TestDialog> implements KeyListener {
	private static final int ANSWERS_OFFSET = 250;
	private static final String OF_TEXT = " из ";
	private static final String QUESITON_NUM_TEXT = "Вопрос № ";
	private static final int NEXT = 1;
	private static final int PREVIOUS = -1;

	private List<Specification> specs;
	private TestingProcess testingProcess;
	private int questTimeSec;

	public TestControllerOld(TestDialog dialog, List<Specification> specs, TestingProcess testingProcess,
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
		dialog.getBtnNext().addActionListener(listener -> doNextAction());
		dialog.getBtnPrevious().addActionListener(listeber -> doPreviousAction());
		dialog.getBtnFinish().addActionListener(listener -> doFinishAction());
		dialog.getBtnNextUnanswered().addActionListener(listener -> doNextUnansweredAction());
		dialog.addKeyListener(this);

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
		DialogBuilder.showResultingDialog(testingProcess);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
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

	private void doNumAction(int num) {
		dialog.getCbAnswers().get(num - 1).setSelected(true);
	}

	private void doNextAction() {
		setUserChoise();
		scrollQuestionTo(NEXT);
		doUpdate();
	}

	private void doPreviousAction() {
		setUserChoise();
		scrollQuestionTo(PREVIOUS);
		doUpdate();
	}

	private void doFinishAction() {
		setUserChoise();
		dialog.dispose();
		testingProcess.saveResultsToDB(specs.get(0), (questTimeSec - getTimeLeft()));
		doUpdate();
	}

	private void doNextUnansweredAction() {
		setUserChoise();
		goToQuestion(testingProcess.getNextUnansweredIndex());
		doUpdate();
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

	private void scrollQuestionTo(int where) {
		goToQuestion(testingProcess.getCurrentQuestionIndex() + where);
	}

	private void checkButtonsEnabled() {
		checkNextEnabled();
		checkPreviousEnabled();
	}

	private void checkNextEnabled() {
		int goToQuestionIndex = testingProcess.getCurrentQuestionIndex() + NEXT;
		if (goToQuestionIndex >= testingProcess.getQuestions().size())
			dialog.getBtnNext().setEnabled(false);
		else if (!dialog.getBtnNext().isEnabled())
			dialog.getBtnNext().setEnabled(true);

	}

	private void checkPreviousEnabled() {
		int goToQuestionIndex = testingProcess.getCurrentQuestionIndex() + PREVIOUS;
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

	private void setUserChoise() {
		int userSelectedIndex = getSelectedIndex();
		System.out.println(userSelectedIndex);
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
				.setText(QUESITON_NUM_TEXT + (index + 1) + OF_TEXT + testingProcess.getQuestions().size());

		dialog.getLblQuestionText().setText(questionText);
	}

	private void showAnswers(int index) {
		List<Answer> answers = testingProcess.getAnswersMap().get(index);
		for (int i = 0; i < answers.size(); i++) {
			int width = dialog.getWidth() - ANSWERS_OFFSET;
			dialog.getCbAnswers().get(i)
					.setText("<html><p style=\"width:" + width + "px\">" + answers.get(i).getTitle() + "</p></html>");
		}
		for (int i = answers.size(); i < AppConstants.MAX_ANSWERS_AMOUNT; i++)
			dialog.getCbAnswers().get(i).setVisible(false);
	}

	private void showSelectedRb(int index) {
		int selected = testingProcess.getChoises().get(index);
		if (selected != AppConstants.NO_INDEX_SELECTED)
			dialog.getCbAnswers().get(selected).setSelected(true);
	}
	
	
	private void unselectAllCheckBoxes() {
		for(JCheckBox cb: dialog.getCbAnswers())
			cb.setSelected(false);
	
	}

	private void clearRb() {
		unselectAllCheckBoxes();
		
		ArrayList<JCheckBox> cbAnswers = dialog.getCbAnswers();
		for (JCheckBox jrb : cbAnswers) {
			jrb.setText("");
			jrb.setVisible(true);
		}
	}
	
	//TODO to list
	private int getSelectedIndex() {
		ArrayList<JCheckBox> rbs = dialog.getCbAnswers();
		for (int i = 0; i < rbs.size(); i++) {
			if (rbs.get(i).isSelected())
				return i;
		}
		return AppConstants.NO_INDEX_SELECTED;
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
