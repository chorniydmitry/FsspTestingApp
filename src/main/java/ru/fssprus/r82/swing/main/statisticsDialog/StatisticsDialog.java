package ru.fssprus.r82.swing.main.statisticsDialog;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

public class StatisticsDialog extends JDialog {
	private static final long serialVersionUID = -1487357130550152798L;
	private StatisticsTable tabStat = new StatisticsTable();
	
	
	public StatisticsDialog(int width, int heigth) {
		setSize(new Dimension(width, heigth));
		setLocationRelativeTo(null);
		setUndecorated(true);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setAlwaysOnTop(true);
		
		init();
		
		setVisible(true);
	}
	
	private void init() {
		JScrollPane scrollPane = new JScrollPane(tabStat);
		add(scrollPane);
	}

	public StatisticsTable getTabStat() {
		return tabStat;
	}

}
