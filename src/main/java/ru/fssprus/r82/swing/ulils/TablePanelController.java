package ru.fssprus.r82.swing.ulils;

import java.util.List;

import javax.swing.ListSelectionModel;

import ru.fssprus.r82.utils.Utils;

public class TablePanelController {

	private TablePanel tablePanel;
	private CommonTable table;
	private UpdatableController subscriberController;

	public TablePanelController(TablePanel tablePanel) {
		this.tablePanel = tablePanel;
		this.table = tablePanel.getCommonTable();

		setListeners();
	}

	public void addData(List<Object[]> data) {

	}
	
	private void setListeners() {
		tablePanel.getBtnAdd().addActionListener(listener -> doAddAction());
		tablePanel.getBtnDelete().addActionListener(listener -> doDeleteAction());
		tablePanel.getBtnNext().addActionListener(listener -> doNextAction());
		tablePanel.getBtnPrevious().addActionListener(listener -> doPreviousAction());
		tablePanel.getTfPage().addActionListener(listener -> doChangePageAction());

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
			table.getTabModel().setRowSelected(selectedRow[0]);
			
			table.setLastSelectedIndex(selectedRow[0]);
			subscriberController.update(selectedRow[0]);
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
		
		if (lastSelectedIndex > 0 && lastSelectedIndex <= table.getRowCount()) 
			subscriberController.delete(lastSelectedIndex);
			
		table.unselectAll();
		
	}

	private void doAddAction() {
		table.unselectAll();
		table.getTabModel().uncolorAll();
		tablePanel.getCommonTable().getTabModel().addRow(new Object[] { "", "", "", "" });
		tablePanel.getCommonTable().scrollTableDown();
	}

	public void setSubscriber(UpdatableController subscriberController) {
		this.subscriberController = subscriberController;
	}

	public UpdatableController getSubscriber() {
		return subscriberController;
	}

}
