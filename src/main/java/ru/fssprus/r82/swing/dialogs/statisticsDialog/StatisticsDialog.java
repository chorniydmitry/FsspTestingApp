package ru.fssprus.r82.swing.dialogs.statisticsDialog;

import javax.swing.JScrollPane;

import ru.fssprus.r82.swing.dialogs.CommonDialog;

public class StatisticsDialog extends CommonDialog {
	private static final long serialVersionUID = -1487357130550152798L;
	private StatisticsTable tabStat = new StatisticsTable();
	private JScrollPane scrollPane = new JScrollPane(tabStat);
	
	public StatisticsDialog(int width, int heigth) {
		super(width, heigth);
	}
	
	public StatisticsTable getTabStat() {
		return tabStat;
	}

	@Override
	protected void layoutDialog() {
		add(scrollPane);
		
	}

}
