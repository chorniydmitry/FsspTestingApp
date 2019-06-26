package ru.fssprus.r82.swing.dialogs.wrongAnswers;

import ru.fssprus.r82.swing.dialogs.ControllerWithTimer;

public class WrongAnswersController extends ControllerWithTimer<WrongAnswersDialog> {
	private static final int TIME_FOR_ANSWER_MULTIPLIER = 2;
	private static final int TIME_OFFSET_SEC = 20;
	
	public WrongAnswersController(WrongAnswersDialog dialog, int timeLeft) {
		super(dialog, timeLeft);
		
		setLblForInfo(dialog.getLblTimeLeftSec());
	}
	
	private void doBtnCloseAction() {
		done();
	}

	public void setText(String showWrongs) {
		dialog.getTaWrongs().setText(showWrongs);
	}
	
	public void startCountdown() {
		initTimer(TIME_OFFSET_SEC + (getTimeLeft() * TIME_FOR_ANSWER_MULTIPLIER));
	}

	@Override
	protected void setListeners() {
		dialog.getBtnClose().addActionListener(listener -> doBtnCloseAction());
	}

}
