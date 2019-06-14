package ru.fssprus.r82.swing.ulils;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.DropMode;
import javax.swing.JTable;

import ru.fssprus.r82.swing.dialogs.statisticsDialog.TableCellRenderer;

public class CommonTable extends JTable {
	private static final long serialVersionUID = 1281533315206385819L;
	private CommonTableModel tabModel;
	
	public CommonTable(int[] widths, String[] names) {
		initTableModel(names);
		
		initTable();
		
		updateColumnWidths(widths);
	}
	
	private void initTableModel(String[] names) {
		tabModel = new CommonTableModel(names);
	}
	
	private void initTable() {
		setModel(tabModel);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setAutoscrolls(true);
		setDragEnabled(true);
		setDropMode(DropMode.INSERT_ROWS);
		//table.setTransferHandler(new TableRowTransferHandler(table));
		setDefaultRenderer(Object.class, new TableCellRenderer());
		
	}
	
	public void scrollTableDown() {
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				int lastIndex = getRowCount() - 1;
				changeSelection(lastIndex, 0, false, false);
			}
		});
	}

	public void updateColumnWidths(int[] widths) {
		for(int i = 0; i < widths.length; i++)
		getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
	}

	public CommonTableModel getTabModel() {
		return tabModel;
	}

	public void setTabModel(CommonTableModel tabModel) {
		this.tabModel = tabModel;
	}

}
