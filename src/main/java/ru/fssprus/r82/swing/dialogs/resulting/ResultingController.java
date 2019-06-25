package ru.fssprus.r82.swing.dialogs.resulting;

import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.wrongAnswers.WrongAnswersController;
import ru.fssprus.r82.swing.dialogs.wrongAnswers.WrongAnswersDialog;
import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.TestingProcess;

public class ResultingController extends CommonController<ResultingDialog> {
	private TestingProcess testingProcess;

	public ResultingController(ResultingDialog dialog) {
		super(dialog);
	}

	@Override
	protected void setListeners() {
		dialog.getBtnShowWrongs().addActionListener(listener -> doShowWrongs());
		dialog.getBtnClose().addActionListener(listener -> doClose());
	}

	private void doShowWrongs() {
		WrongAnswersController waController = new WrongAnswersController(
				new WrongAnswersDialog(AppConstants.DIALOG_WRONG_ANSWERS_WIDTH,
						AppConstants.DIALOG_WRONG_ANSWERS_HEIGHT),
				testingProcess.getWrongAmount());

		waController.setText(testingProcess.showWrongs());
		waController.startCountdown();

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
	}

}
