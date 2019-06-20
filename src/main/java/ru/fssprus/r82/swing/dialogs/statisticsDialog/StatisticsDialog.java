package ru.fssprus.r82.swing.dialogs.statisticsDialog;

import ru.fssprus.r82.swing.dialogs.DialogWithPassword;
import ru.fssprus.r82.swing.table.CommonTable;
import ru.fssprus.r82.swing.table.CommonTableModel;
import ru.fssprus.r82.swing.table.TablePanel;
import ru.fssprus.r82.utils.AppConstants;

public class StatisticsDialog extends DialogWithPassword {
	private static final long serialVersionUID = -1487357130550152798L;
	private static final String SECTION = AppConstants.STATISTICS_SECTION;
	
	private TablePanel tablePanel;
	
	public StatisticsDialog(int width, int heigth) {
		super(width, heigth);
		initTablePanel();
	}
	
	private void initTablePanel() {
		int[] widths = { 20, 250, 125, 75, 125, 50, 50, 50, 125};
		String[] names = {"#", "ФИО пользователя", "Специализация", 
				"Уровень", "Дата теста", "Время теста", 
				"Верных ответов", "%", "Результат"};
		tablePanel = new TablePanel(widths, names);
		
        
	}
	
	public TablePanel getTabPanel() {
		return tablePanel;
	}
	
	public CommonTable getTable() {
		return tablePanel.getCommonTable();
	}
	
	public  CommonTableModel getTableModel() {
		return getTable().getTabModel();
	}

	@Override
	protected void layoutDialog() {
		add(tablePanel);
		
	}

	@Override
	protected String getSection() {
		return SECTION;
	}

}
