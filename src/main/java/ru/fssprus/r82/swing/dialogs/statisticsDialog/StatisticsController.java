package ru.fssprus.r82.swing.dialogs.statisticsDialog;

import java.util.ArrayList;

import ru.fssprus.r82.swing.dialogs.CommonController;

public class StatisticsController extends CommonController<StatisticsDialog> {
	private int currentPage = 0;
	private int totalEntries = 0;
	
	public StatisticsController(StatisticsDialog dialog) {
		super(dialog);
		showStatistics();
	}
	
	
	
	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub
		
	}
	
	private void showStatistics() {
		dialog.getTabPanel().getTfPage().setText(String.valueOf(currentPage + 1));
		dialog.getTabPanel().getLblPagesTotal().setText(" из " + countTotalPages(totalEntries));

		dialog.getTable().getTabModel().clearTable();
		dialog.getTable().getTabModel().addData(convertToDataForTable());
	}



	private ArrayList<Object[]> convertToDataForTable() {
		// TODO Auto-generated method stub
		return null;
	}



	private String countTotalPages(int totalEntries) {
		// TODO Auto-generated method stub
		return null;
	}

}
