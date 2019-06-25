package ru.fssprus.r82.swing.dialogs.statistics;

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
		int[] widths = AppConstants.STATDIALOG_TABLE_COL_WIDTHS_ARR;
		String[] names = AppConstants.STATDIALOG_TABLE_COL_CAPTIONS_ARR;
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
