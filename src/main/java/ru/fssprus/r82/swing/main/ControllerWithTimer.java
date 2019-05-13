package ru.fssprus.r82.swing.main;

import java.awt.Window;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

public abstract class ControllerWithTimer {

	private Timer timer = new Timer();
	private int timeLeft;
	private boolean isDone = false;
	private JLabel lblForInfo;
	private Window view;

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
		view.dispose();
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

	public Window getView() {
		return view;
	}

	public void setView(Window view) {
		this.view = view;
	}

}
