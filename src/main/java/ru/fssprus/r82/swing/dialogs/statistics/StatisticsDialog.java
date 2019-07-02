package ru.fssprus.r82.swing.dialogs.statistics;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import ru.fssprus.r82.swing.dialogs.DialogWithPassword;
import ru.fssprus.r82.swing.table.CommonTable;
import ru.fssprus.r82.swing.table.CommonTableModel;
import ru.fssprus.r82.swing.table.TablePanel;
import ru.fssprus.r82.utils.AppConstants;

public class StatisticsDialog extends DialogWithPassword {
	private static final long serialVersionUID = -1487357130550152798L;
	private static final String SECTION = AppConstants.STATISTICS_DIALOG;
	private static final String TITLE = AppConstants.STATISTICS_TEXT;
	private static final String ICON = AppConstants.STATISTICS_ICON;
	
	private TablePanel tablePanel;
	
	public StatisticsDialog(int width, int heigth) {
		super(width, heigth);
		initTablePanel();
	}
	
	@Override
	public void layoutPanelTop() {
		ImageIcon emblem = new ImageIcon(getClass().getResource(ICON));
		super.layoutPanelTop(TITLE, emblem);
	}
	
	private void initTablePanel() {
		int[] widths = AppConstants.STATDIALOG_TABLE_COL_WIDTHS_ARR;
		String[] names = AppConstants.STATDIALOG_TABLE_COL_CAPTIONS_ARR;
		tablePanel = new TablePanel(widths, names);
		tablePanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
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
		getContentPanel().add(tablePanel);
		
	}

	@Override
	protected String getSection() {
		return SECTION;
	}
	
	@Override
	protected String getTitleText() {
		return TITLE;
	}

}
