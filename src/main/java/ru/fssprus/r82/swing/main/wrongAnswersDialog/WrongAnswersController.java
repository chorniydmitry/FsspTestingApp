package ru.fssprus.r82.swing.main.wrongAnswersDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import ru.fssprus.r82.swing.main.ControllerWithTimer;

public class WrongAnswersController extends ControllerWithTimer implements ActionListener {
	private static final int TIME_FOR_ANSWER_MULTIPLIER = 2;
	private WrongAnswersDialog waDialog;
	private final Timer timer = new Timer();
	private int timeSec;
	
	public WrongAnswersController(WrongAnswersDialog waDialog, int time) {
		this.waDialog = waDialog;
		this.timeSec = time;
		waDialog.getBtnClose().addActionListener(this);
		setLblForInfo(waDialog.getLblTimeLeftSec());
		setView(waDialog);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == waDialog.getBtnClose()) {
			doBtnCloseAction();
		}
	}
	
	private void doBtnCloseAction() {
		timer.cancel();
		waDialog.dispose();
	}

	public void setText(String showWrongs) {
		waDialog.getTaWrongs().setText(showWrongs);
	}
	
	public void startCountdown() {
		initTimer(timeSec * TIME_FOR_ANSWER_MULTIPLIER);
	}

}
