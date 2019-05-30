package ru.fssprus.r82.swing.dialogs.statisticsDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.DropMode;
import javax.swing.JTable;

import ru.fssprus.r82.entity.Test;
import ru.fssprus.r82.utils.MarkCounter;
import ru.fssprus.r82.utils.TimeConverter;

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
		setDefaultRenderer(Object.class, new TableCellRenderer());

		updateColumnWidths();
	}

	public void updateColumnWidths() {
		getColumnModel().getColumn(0).setPreferredWidth(20);
		getColumnModel().getColumn(1).setPreferredWidth(250);
		getColumnModel().getColumn(2).setPreferredWidth(125);
		getColumnModel().getColumn(3).setPreferredWidth(75);
		getColumnModel().getColumn(4).setPreferredWidth(125);
		getColumnModel().getColumn(5).setPreferredWidth(50);
		getColumnModel().getColumn(6).setPreferredWidth(50);
		getColumnModel().getColumn(7).setPreferredWidth(50);
		getColumnModel().getColumn(8).setPreferredWidth(125);
	}
	
	public void addTests(List<Test> tests) {
		for (int i = 0; i< tests.size(); i++) {
			Test test = tests.get(i);
			
			String userName = test.getUser().getSurname() + " " + test.getUser().getName() + " " + test.getUser().getSecondName();
			String spec = test.getSpecification().getName();
			String lvl = test.getLevel();
			
			Date testDate = test.getDate();
			
			String pattern = "dd MMMMM yyyy HH:mm";
			SimpleDateFormat simpleDateFormat =
			        new SimpleDateFormat(pattern, new Locale("ru", "RU"));

			String date = simpleDateFormat.format(testDate);
			
			int testSecs = test.getTestingTime();
			
			String testTime = String.valueOf(TimeConverter.stringTimes(testSecs));
			
			String corrects = String.valueOf(test.getCorrectAnswers());
			String percent = String.valueOf(test.getScore());
			String result = test.getResult();
			
			Object[] row = {i+1, userName, spec, lvl, date, testTime, corrects, percent, result};
			
			tabModel.setRow(row, i);
			tabModel.setRowColor(i, MarkCounter.countInColors(test.getTotalQuestions(), test.getCorrectAnswers()));
			
			tabModel.update();
		}
	}
}
