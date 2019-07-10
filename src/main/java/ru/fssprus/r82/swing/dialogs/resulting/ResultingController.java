package ru.fssprus.r82.swing.dialogs.resulting;

import java.awt.Color;

import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.DialogBuilder;
import ru.fssprus.r82.utils.testingTools.TestingProcessAnaliser;
import ru.fssprus.r82.utils.testingTools.TestingProcessObjective;

public class ResultingController extends CommonController<ResultingDialog> {
	private TestingProcessObjective testingProcess;
	private TestingProcessAnaliser analiser;

	public ResultingController(ResultingDialog dialog, TestingProcessObjective testingProcess) {
		super(dialog);
		setTestingProcess(testingProcess);
		initAnaliser();
		setDialogCaptions();
	}
	
	private void initAnaliser() {
		analiser = new TestingProcessAnaliser(testingProcess);
		analiser.analize();
	}
	
	@Override
	protected void setListeners() {
		dialog.getBtnShowWrongs().addActionListener(listener -> doShowWrongs());
		dialog.getBtnClose().addActionListener(listener -> doClose());
	}

	private void doShowWrongs() {
		DialogBuilder.showWrongAnswersDialog(analiser.getWrongsAmount(), analiser.printWrongs());

		dialog.dispose();
	}

	private void doClose() {
		dialog.dispose();
	}

	public TestingProcessObjective getTestingProcess() {
		return testingProcess;
	}
	
	private void setDialogCaptions() {
		int corrects = analiser.getCorrectAnswersAmount();
		int total = analiser.getTotalAmount();
		int markPers = analiser.getMarkPercent();
		int markOneToFive = analiser.getMarkOneToFive();
		String markText = analiser.getMarkText();
		String markLetter = analiser.getMarkLetter();
		Color markColor = analiser.getMarkColor();
		
		dialog.setCaptions(corrects, total, markPers, markOneToFive, markText, markLetter);
		dialog.setMarkColor(markColor);
	}

	public void setTestingProcess(TestingProcessObjective testingProcess) {
		this.testingProcess = testingProcess;
	}

}
