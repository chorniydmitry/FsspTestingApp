package ru.fssprus.r82.swing.dialogs.resulting;

import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.DialogBuilder;
import ru.fssprus.r82.utils.TestingProcess;

public class ResultingController extends CommonController<ResultingDialog> {
	private TestingProcess testingProcess;

	public ResultingController(ResultingDialog dialog, TestingProcess testingProcess) {
		super(dialog);
		setTestingProcess(testingProcess);
	}

	@Override
	protected void setListeners() {
		dialog.getBtnShowWrongs().addActionListener(listener -> doShowWrongs());
		dialog.getBtnClose().addActionListener(listener -> doClose());
	}

	private void doShowWrongs() {
		DialogBuilder.showWrongAnswersDialog(testingProcess.getWrongAmount(), testingProcess.showWrongs());

		dialog.dispose();
	}

	private void doClose() {
		dialog.dispose();
	}

	public TestingProcess getTestingProcess() {
		return testingProcess;
	}

	public void setTestingProcess(TestingProcess testingProcess) {
		this.testingProcess = testingProcess;
		
		dialog.setCaptions(testingProcess.getCorrectAnswersAmount(), testingProcess.getQuestions().size(),
				testingProcess.getMarkPercent(), testingProcess.getMarkOneToFive(),
				testingProcess.getMarkText(), testingProcess.getMarkLetter());

		dialog.setMarkColor(testingProcess.getMarkColor());
	}

}
