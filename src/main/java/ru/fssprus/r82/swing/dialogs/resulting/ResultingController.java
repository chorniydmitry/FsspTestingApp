package ru.fssprus.r82.swing.dialogs.resulting;

import java.awt.Color;

import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.DialogBuilder;
import ru.fssprus.r82.utils.testingTools.TestingProcessAnaliser;

/**
 * @author Chernyj Dmitry
 *
 */
public class ResultingController extends CommonController<ResultingDialog> {
	private TestingProcessAnaliser analiser;

	public ResultingController(ResultingDialog dialog, TestingProcessAnaliser analiser) {
		super(dialog);
		this.analiser = analiser;
		setDialogCaptions();
	}
	
	@Override
	protected void setListeners() {
		dialog.getBtnShowWrongs().addActionListener(listener -> doShowWrongs());
		dialog.getBottomBtnClose().addActionListener(listener -> doClose());
	}

	private void doShowWrongs() {
		DialogBuilder.showWrongAnswersDialog(analiser.getWrongsAmount(), analiser.printWrongs());

		dialog.dispose();
	}

	private void doClose() {
		dialog.dispose();
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
}
