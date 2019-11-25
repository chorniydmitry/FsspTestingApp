package ru.fssprus.r82.swing.table;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.DropMode;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ru.fssprus.r82.utils.AppConstants;

/**
 * @author Chernyj Dmitry
 *
 */
public class CommonTable extends JTable {
	private static final long serialVersionUID = 1281533315206385819L;
	private CommonTableModel tabModel;
	
	private int lastSelectedIndex = AppConstants.NO_INDEX_SELECTED;
	
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
	
	public void unselectAll() {
		setLastSelectedIndex(AppConstants.NO_INDEX_SELECTED);
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

	public int getLastSelectedIndex() {
		return lastSelectedIndex;
	}

	public void setLastSelectedIndex(int lastSelectedIndex) {
		this.lastSelectedIndex = lastSelectedIndex;
	}
	
	public class TableCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 7406284718249927491L;

		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        CommonTableModel model = (CommonTableModel) table.getModel();
	        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        c.setBackground(model.getRowColor(row));
	        return c;
	    }
	}


}

