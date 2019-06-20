package ru.fssprus.r82.swing.table;

import java.util.List;

import ru.fssprus.r82.entity.Model;
import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.CommonDialog;
import ru.fssprus.r82.utils.AppConstants;

public abstract class CommonTableAbstractController extends CommonController<CommonDialog>{
	private static final int ENTRIES_FOR_PAGE = AppConstants.TABLE_ROWS_LIMIT;
	private int currentPage;
	private int totalPages;
	
	private List<Model> modelOnScreenList;
	
	
	public CommonTableAbstractController(CommonDialog dialog, TablePanel tabPanel) {
		super(dialog);
		TablePanelController tablePanelController = new TablePanelController(tabPanel);
		tablePanelController.setSubscriber(this);
	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub
		
	}
	
	abstract void edit(int index);
	abstract void nextPage();
	abstract void previousPage();
	abstract void delete(int index);
	abstract void goToPage(int page);
	
	
	

}
