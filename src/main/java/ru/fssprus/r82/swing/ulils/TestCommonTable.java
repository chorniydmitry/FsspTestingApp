package ru.fssprus.r82.swing.ulils;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TestCommonTable {

	public static void main(String[] args) {
		int[] widths = { 30, 15, 60, 45 };
		String[] names = {"A", "B", "C", "D"};
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame testFrame = new JFrame();
				testFrame.setSize(new Dimension(800, 600));
				testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				
				testFrame.setVisible(true);
				
			}
		});

	}

}
