package ru.fssprus.r82.swing.ulils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TablePanelController implements ActionListener{
	
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == tablePanel.getBtnAdd()) {
			doAddAction();
		}
		
		if(e.getSource() == tablePanel.getBtnDelete()) {
			doDeleteAction();
		}

		if(e.getSource() == tablePanel.getBtnNext()) {
			doNextAction();
		}
		
		if(e.getSource() == tablePanel.getBtnPrevious()) {
			doPreviousAction();
		}
		
	}
	
	private void setListeners() {
		tablePanel.getBtnAdd().addActionListener(this);
		tablePanel.getBtnDelete().addActionListener(this);
		tablePanel.getBtnNext().addActionListener(this);
		tablePanel.getBtnPrevious().addActionListener(this);
		tablePanel.getTfPage().addActionListener(this);
		setTableOnSelectionListener();
	}
	
	private void setTableOnSelectionListener() {
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel cellSelectionModel = table.getSelectionModel();

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (table.getSelectedRows().length > 0) {
					int[] selectedRow = table.getSelectedRows();
					subscriberController.update(selectedRow[0]);
				}
			}
		});
	}

	private void doPreviousAction() {
		
	}

	private void doNextAction() {
		
	}

	private void doDeleteAction() {
		
	}

	private void doAddAction() { 
		tablePanel.getCommonTable().getTabModel().addRow(new Object[] {"", "", "", ""});
		tablePanel.getCommonTable().scrollTableDown();
	}
	
	public void setSubscriber(UpdatableController subscriberController) {
		this.subscriberController = subscriberController;
	}
	
	public UpdatableController getSubscriber() {
		return subscriberController;
	}

}
