package ru.fssprus.r82.swing.dialogs.statisticsDialog;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRenderer extends DefaultTableCellRenderer{
	private static final long serialVersionUID = -3068241817824609841L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
       // TableModel model = table.getModel();
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
       // c.setBackground(model.getRowColor(row));
        return c;
    }
}

