package ru.fssprus.r82.swing.table;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Chernyj Dmitry
 *
 */
public class CommonTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -9209664050296683407L;

	private static final String WRONG_COLUMN_INDEX_TEXT = "Не верный индекс столбца!";
	private static final String WRONG_TABLE_DATA_TEXT = "Не правильные данные для добавления в таблицу!";

	private String[] columnNames;
	private int columnCount;
	private ArrayList<Object[]> onScreenDataList;
	private List<Color> rowColors = new ArrayList<Color>();

	public CommonTableModel(String[] names) {
		this.columnNames = names; 
		columnCount = names.length;
		
		onScreenDataList = new ArrayList<Object[]>();
		rowColors = new ArrayList<Color>();
		for (int i = 0; i < onScreenDataList.size(); i++) {
			onScreenDataList.add(new Object[getColumnCount()]);
		}
	}

	@Override
	public String getColumnName(int column) {
		if(column < 0 || column >= columnCount) {
			System.err.println(WRONG_COLUMN_INDEX_TEXT);
			return null;
		}
		return columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int column) {
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object[] rows = onScreenDataList.get(rowIndex);

		return rows[columnIndex];
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	@Override
	public int getRowCount() {
		return onScreenDataList.size();
	}

	public void reorder(int fromIndex, int toIndex) {
		if (couldBeReordered(fromIndex, toIndex)) {
			Collections.swap(onScreenDataList, fromIndex, toIndex);
			update();
		}
	}
	
	public void uncolorAll() {
		for(int i = 0; i< rowColors.size(); i++) {
			rowColors.set(i, Color.WHITE);
		}
	}

	public void setRowColor(int row, Color c) {
		rowColors.set(row, c);
		fireTableRowsUpdated(row, row);
	}
	
	public void setRowColor(int row, int color) {
		Color c = new Color(color);
		rowColors.set(row, c);
		fireTableRowsUpdated(row, row);
	}

	public Color getRowColor(int row) {
		return rowColors.get(row);
	}

	public void addRow(Object[] row) {
		if(row == null) {
			row = (Object[]) new Object();
			for(int i = 0; i < columnCount; i++) {
				row[i] = new Object();
			}
		}
		
		setRow(row, getRowCount());
	}

	public void setRow(Object[] row, int rowIndex) {
		if (!(row.length == columnCount)) {
			System.err.println(WRONG_TABLE_DATA_TEXT + row.length + " " + columnCount);
			return;
		}
		onScreenDataList.add(rowIndex, row);
		rowColors.add(Color.WHITE);
		
		update();
	}

	public void updateRow(Object[] row, int rowIndex) {
		onScreenDataList.set(rowIndex, row);
	}

	public Object[] getRowData(int rowIndex) {
		Object data[] = new Object[getColumnCount()];

		for (int i = 0; i < getColumnCount(); i++)
			data[i] = getValueAt(rowIndex, i);

		return data;
	}

	public void update() {
		fireTableDataChanged();
	}

	public void addData(ArrayList<Object[]> data) {
		onScreenDataList.addAll(data);

		for (int i = 0; i < data.size(); i++)
			rowColors.add(Color.WHITE);

		update();
	}

	public void clearTable() {
		onScreenDataList.clear();
		update();
	}

	public void removeRow(int rowIndex) {
		if (couldBeRemoved(rowIndex))
			onScreenDataList.remove(rowIndex);
	}

	public boolean couldBeReordered(int fromIndex, int toIndex) {
		if (fromIndex < 0 || fromIndex >= onScreenDataList.size() || toIndex < 0 || toIndex >= onScreenDataList.size())
			return false;
		return true;
	}

	public boolean couldBeRemoved(int rowIndex) {
		if (rowIndex > getRowCount() || rowIndex < 0)
			return false;
		return true;
	}

	public ArrayList<Object[]> getOnScreenDataList() {
		return onScreenDataList;
	}

}
