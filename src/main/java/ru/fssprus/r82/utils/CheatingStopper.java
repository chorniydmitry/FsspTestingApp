package ru.fssprus.r82.utils;

/**
 * @author Chernyj Dmitry
 *
 */
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class CheatingStopper implements Runnable {
	private static boolean working = true;
	private JFrame frame;

	public CheatingStopper(JFrame frame) {
		this.setFrame(frame);
	}

	public static void stop() {
		working = false;
	}

	public static CheatingStopper create(JFrame frame) {
		working = true;
		CheatingStopper stopper = new CheatingStopper(frame);
		new Thread(stopper, "Cheating Stopper").start();
		return stopper;
	}

	public void run() {
		try {
			Robot robot = new Robot();
			while (working) {
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_TAB);
				clearClipboard();
				frame.requestFocus();
				try {
					Thread.sleep(250);
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private void clearClipboard() {
		try {
			StringSelection stringSelection = new StringSelection("");
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
			            stringSelection, null);
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
			try {
				Thread.sleep(100);
				clearClipboard();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	
}
