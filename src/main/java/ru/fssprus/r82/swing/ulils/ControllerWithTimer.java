package ru.fssprus.r82.swing.ulils;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JLabel;

import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.CommonDialog;

public abstract class ControllerWithTimer<T extends CommonDialog> extends CommonController<T>{
	private Timer timer = new Timer();
	private int timeLeft;
	private boolean isDone = false;
	private JLabel lblForInfo;
	
	public ControllerWithTimer(T dialog, int time) {
		super(dialog);
		this.timeLeft = time;
	}

	protected void initTimer(int sec) {
		setTimeLeft(sec);
		if (sec < 1) {
			System.err.println("Не правильное время таймера!");
		}

		timer.scheduleAtFixedRate(new TimerTask() {
			int i = sec;

			public void run() {
				setTimeLeft(--i);
				lblForInfo.setText("Времени осталось: " + getTimeLeft());
				if (i < 0) {
					setDone(true);
					done();
					timer.cancel();
				}
			}
		}, 0, 1000);
	}

	protected void done() {
		timer.cancel();
		dialog.dispose();
	}

	protected int getTimeLeft() {
		return this.timeLeft;
	}

	private void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public JLabel getLblForInfo() {
		return lblForInfo;
	}

	public void setLblForInfo(JLabel lblForInfo) {
		this.lblForInfo = lblForInfo;
	}

	public JDialog getDialog() {
		return dialog;
	}

}
