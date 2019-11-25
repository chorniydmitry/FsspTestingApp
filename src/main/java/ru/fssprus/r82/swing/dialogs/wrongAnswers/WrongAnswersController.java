package ru.fssprus.r82.swing.dialogs.wrongAnswers;

import ru.fssprus.r82.swing.dialogs.ControllerWithTimer;

/**
 * @author Chernyj Dmitry
 *
 */
public class WrongAnswersController extends ControllerWithTimer<WrongAnswersDialog> {
	private static final int TIME_FOR_ANSWER_MULTIPLIER = 2;
	private static final int TIME_OFFSET_SEC = 10;
	
	public WrongAnswersController(WrongAnswersDialog dialog, int wrongQuestionsAmount, String text) {
		super(dialog, countTimeToDisplay(wrongQuestionsAmount), dialog.getLblTimeLeftSec());
		
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
		return TIME_OFFSET_SEC + (wrongQuestionsAmount * TIME_FOR_ANSWER_MULTIPLIER);
	}
	
	@Override
	protected void setListeners() {
		dialog.getBtnBottomClose().addActionListener(listener -> doBtnCloseAction());
	}

}
