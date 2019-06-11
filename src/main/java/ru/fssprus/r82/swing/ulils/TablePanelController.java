package ru.fssprus.r82.swing.ulils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TablePanelController implements ActionListener{
	
	private TablePanel tablePanel;
	
	
	public TablePanelController(TablePanel tablePanel) {
		this.tablePanel = tablePanel;
		
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
	}

	private void doPreviousAction() {
		
	}

	private void doNextAction() {
		
	}

	private void doDeleteAction() {
		
	}

	private void doAddAction() {
		
		tablePanel.getCommonTable().getTabModel().addRow(new Object[] {"1","2", "3", "4"});
	}

}
