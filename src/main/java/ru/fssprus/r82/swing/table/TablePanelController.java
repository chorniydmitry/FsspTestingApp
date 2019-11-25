package ru.fssprus.r82.swing.table;

import java.awt.Color;

import javax.swing.ListSelectionModel;

import ru.fssprus.r82.utils.AppConstants;
import ru.fssprus.r82.utils.Utils;

/**
 * @author Chernyj Dmitry
 *
 */
public class TablePanelController {

	private TablePanel tablePanel;
	private CommonTable table;
	private UpdatableController subscriberController;

	public TablePanelController(TablePanel tablePanel) {
		this.tablePanel = tablePanel;
		this.table = tablePanel.getCommonTable();

		setListeners();
	}

	private void setListeners() {
		tablePanel.getBtnAdd().addActionListener(listener -> doAddAction());
		tablePanel.getBtnDelete().addActionListener(listener -> doDeleteAction());
		tablePanel.getBtnNext().addActionListener(listener -> doNextAction());
		tablePanel.getBtnPrevious().addActionListener(listener -> doPreviousAction());
		tablePanel.getTfPage().addActionListener(listener -> doChangePageAction());

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionForeground(AppConstants.TABLE_SELECTION_COLOR);
		table.setSelectionBackground(Color.BLACK);	
		ListSelectionModel cellSelectionModel = table.getSelectionModel();

		cellSelectionModel.addListSelectionListener(listener -> doTableRowChangedAction());
	}

	private void doChangePageAction() {
		table.unselectAll();
		if(Utils.isNumeric(tablePanel.getTfPage().getText()))
			subscriberController.goToPage(Integer.parseInt(tablePanel.getTfPage().getText()));
		else {
			tablePanel.getTfPage().setText("");
		}
		
	}

	private void doTableRowChangedAction() {
		if (table.getSelectedRows().length > 0) {
			int[] selectedRow = table.getSelectedRows();
			
			table.setLastSelectedIndex(selectedRow[0]);
			subscriberController.edit(selectedRow[0]);
		}

	}

	private void doPreviousAction() {
		table.unselectAll();
		subscriberController.previousPage();
	}

	private void doNextAction() {
		table.unselectAll();
		subscriberController.nextPage();
	}

	private void doDeleteAction() {
		int lastSelectedIndex = table.getLastSelectedIndex();
		
		if (lastSelectedIndex >= 0 && lastSelectedIndex <= table.getRowCount()) 
			subscriberController.delete(lastSelectedIndex);
			
		table.unselectAll();
		
	}

	private void doAddAction() {
		table.unselectAll();
		table.getTabModel().uncolorAll();
		
		tablePanel.getCommonTable().getTabModel().addRow(new Object[table.getTabModel().getColumnCount()]);
		tablePanel.getCommonTable().scrollTableDown();
	}

	public void setSubscriber(UpdatableController updatableController) {
		this.subscriberController = updatableController;
	}

	public UpdatableController getSubscriber() {
		return subscriberController;
	}

}
