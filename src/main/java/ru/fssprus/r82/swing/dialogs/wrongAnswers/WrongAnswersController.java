package ru.fssprus.r82.swing.dialogs.wrongAnswers;

import ru.fssprus.r82.swing.dialogs.ControllerWithTimer;
import ru.fssprus.r82.utils.CheatingStopper;

/**
 * @author Chernyj Dmitry
 *
 */
public class WrongAnswersController extends ControllerWithTimer<WrongAnswersDialog> {
	private static final int TIME_FOR_ANSWER_MULTIPLIER = 15;
	private static final int TIME_MAX = 120;

	public WrongAnswersController(WrongAnswersDialog dialog, int wrongQuestionsAmount, String text) {
		super(dialog, countTimeToDisplay(wrongQuestionsAmount), dialog.getLblTimeLeftSec());

		CheatingStopper.create(dialog.getMainFrame());
		setText(text);
		dialog.getTaWrongs().setCaretPosition(0);
	}

	private void doBtnCloseAction() {
		done();
	}

	public void setText(String showWrongs) {
		dialog.getTaWrongs().setText(showWrongs);
	}

	private static int countTimeToDisplay(int wrongQuestionsAmount) {
		int retVal = wrongQuestionsAmount * TIME_FOR_ANSWER_MULTIPLIER;
		return retVal < TIME_MAX ? retVal : TIME_MAX;
	}

	@Override
	protected void setListeners() {
		setOnCloseListener();
		dialog.getBtnBottomClose().addActionListener(listener -> doBtnCloseAction());
	}

	private void setOnCloseListener() {
		dialog.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				CheatingStopper.stop();
			}
		});
	}

}
