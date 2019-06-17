package ru.fssprus.r82.swing.dialogs.resultingDialog;

import java.awt.event.ActionEvent;

import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.wrongAnswersDialog.WrongAnswersController;
import ru.fssprus.r82.swing.dialogs.wrongAnswersDialog.WrongAnswersDialog;
import ru.fssprus.r82.utils.TestingProcess;

public class ResultingController extends CommonController<ResultingDialog>{
	private TestingProcess testingProcess;
	
	public ResultingController(ResultingDialog dialog) {
		super(dialog);
	}
	
	@Override
	protected void setListeners() {
		dialog.getBtnShowWrongs().addActionListener(listener->doShowWrongs());
		dialog.getBtnClose().addActionListener(listener->doClose());
	}

	private void doShowWrongs() {
		WrongAnswersController waController = 
				new WrongAnswersController(new WrongAnswersDialog(800, 600), testingProcess.getWrongAmount());
		
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
