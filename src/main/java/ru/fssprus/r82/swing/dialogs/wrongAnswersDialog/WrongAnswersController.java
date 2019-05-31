package ru.fssprus.r82.swing.dialogs.wrongAnswersDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ru.fssprus.r82.swing.dialogs.ControllerWithTimer;

public class WrongAnswersController extends ControllerWithTimer<WrongAnswersDialog> implements ActionListener {
	private static final int TIME_FOR_ANSWER_MULTIPLIER = 2;
	
	public WrongAnswersController(WrongAnswersDialog dialog, int timeLeft) {
		super(dialog, timeLeft);
		
		setLblForInfo(dialog.getLblTimeLeftSec());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == dialog.getBtnClose()) {
			doBtnCloseAction();
		}
	}
	
	private void doBtnCloseAction() {
		done();
	}

	public void setText(String showWrongs) {
		dialog.getTaWrongs().setText(showWrongs);
	}
	
	public void startCountdown() {
		initTimer(getTimeLeft() * TIME_FOR_ANSWER_MULTIPLIER);
	}

	@Override
	protected void setListeners() {
		dialog.getBtnClose().addActionListener(this);
	}

}
