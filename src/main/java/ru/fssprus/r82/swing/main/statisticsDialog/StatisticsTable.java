package ru.fssprus.r82.swing.main.statisticsDialog;

import java.util.List;

import javax.swing.DropMode;
import javax.swing.JTable;

import ru.fssprus.r82.entity.Test;

public class StatisticsTable extends JTable{
	private static final long serialVersionUID = -5803184243278916570L;
	private StatisticsTableModel tabModel = new StatisticsTableModel();
	
	public StatisticsTable() {
		initTable();
	}
	
	private void initTable() {
		setModel(tabModel);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setAutoscrolls(true);
		setDragEnabled(true);
		setDropMode(DropMode.INSERT_ROWS);
		//table.setTransferHandler(new TableRowTransferHandler(table));
		//table.setDefaultRenderer(Object.class, new PaymentCellRenderer());

		updateColumnWidths();
	}

	public void updateColumnWidths() {
		getColumnModel().getColumn(0).setPreferredWidth(25);
		getColumnModel().getColumn(1).setPreferredWidth(255);
		getColumnModel().getColumn(2).setPreferredWidth(100);
		getColumnModel().getColumn(3).setPreferredWidth(55);
		getColumnModel().getColumn(4).setPreferredWidth(100);
		getColumnModel().getColumn(5).setPreferredWidth(100);
		getColumnModel().getColumn(6).setPreferredWidth(100);
	}
	
	public void addTests(List<Test> tests) {
		for (int i = 0; i< tests.size(); i++) {
			Test test = tests.get(i);
			
			String userName = test.getUser().getSurname() + " " + test.getUser().getName() + " " + test.getUser().getSecondName();
			String spec = test.getSpecification().getName();
			String lvl = test.getLevel();
			String date = test.getDate().toString();
			String corrects = String.valueOf(test.getCorrectAnswers());
			String result = String.valueOf(test.getScore());
			
			Object[] row = {i, userName, spec, lvl, date, corrects, result};
			
			tabModel.setRow(row, i);
			
			tabModel.update();
			
		}
		
	}
	
	
}
