package ru.fssprus.r82.swing.dialogs.questionListDialog;


import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Set;

import javax.swing.DropMode;
import javax.swing.JTable;

import ru.fssprus.r82.entity.Question;
import ru.fssprus.r82.entity.QuestionLevel;
import ru.fssprus.r82.entity.Specification;
import ru.fssprus.r82.swing.dialogs.statisticsDialog.TableCellRenderer;

public class QuestionListTable extends JTable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1281533315206385819L;
	private QuestionListTableModel tabModel = new QuestionListTableModel();
	
	public QuestionListTable() {
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
	
	public void scrollTableDown() {
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				int lastIndex = getRowCount() - 1;
				changeSelection(lastIndex, 0, false, false);
			}
		});
	}

	public void updateColumnWidths() {
		getColumnModel().getColumn(0).setPreferredWidth(35);
		getColumnModel().getColumn(1).setPreferredWidth(500);
		getColumnModel().getColumn(2).setPreferredWidth(225);
		getColumnModel().getColumn(3).setPreferredWidth(225);
	}

	public void addQuestions(List<Question> questions) {
		tabModel.clearTable();
		
		for(int i = 0; i < questions.size(); i++) {
			Question question = questions.get(i);
			
			Long id = question.getId();
			String title = question.getTitle();
			Set<QuestionLevel> levels = question.getLevels();
			Specification specification = question.getSpecification();
			String lvlsString = "";
			for(QuestionLevel ql: levels)
				lvlsString += "[" + ql.name() + "] ";
			
			String specString=specification.getName();
			
			Object[] row = {id, title, lvlsString, specString};
			
			tabModel.setRow(row, i);

			tabModel.update();
		}
		
	}

	public QuestionListTableModel getTabModel() {
		return tabModel;
	}

	public void setTabModel(QuestionListTableModel tabModel) {
		this.tabModel = tabModel;
	}
	
	
	
//	public void addTests(List<Test> tests) {
//		for (int i = 0; i< tests.size(); i++) {
//			Test test = tests.get(i);
//			
//			String userName = test.getUser().getSurname() + " " + test.getUser().getName() + " " + test.getUser().getSecondName();
//			String spec = test.getSpecification().getName();
//			String lvl = test.getLevel();
//			
//			Date testDate = test.getDate();
//			
//			String pattern = "dd MMMMM yyyy HH:mm";
//			SimpleDateFormat simpleDateFormat =
//			        new SimpleDateFormat(pattern, new Locale("ru", "RU"));
//
//			String date = simpleDateFormat.format(testDate);
//			
//			int testSecs = test.getTestingTime();
//			
//			String testTime = String.valueOf(TimeConverter.stringTimes(testSecs));
//			
//			String corrects = String.valueOf(test.getCorrectAnswers());
//			String percent = String.valueOf(test.getScore());
//			String result = test.getResult();
//			
//			Object[] row = {i+1, userName, spec, lvl, date, testTime, corrects, percent, result};
//			
//			tabModel.setRow(row, i);
//			tabModel.setRowColor(i, MarkCounter.countInColors(test.getTotalQuestions(), test.getCorrectAnswers()));
//			
//			tabModel.update();
//		}
//	}
}

