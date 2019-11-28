package ru.fssprus.r82.utils.spreadsheet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * @author Chernyj Dmitry
 *
 */
public class SpreadSheetAdapter {
	private Object sheet;
	private File file;

	public SpreadSheetAdapter(File file) {
		this.file = file;
		assignSheetType();
	}

	private void assignSheetType() {
		// для сохранения в файл, если файл не существует
		if(!file.exists()) {
			return;
		}
		
		if (file.getName().toUpperCase().endsWith("XLSX")) {
			XSSFWorkbook wb = null;
			try {
				OPCPackage pkg = OPCPackage.open(file);
				wb = new XSSFWorkbook(pkg);
			} catch (IOException | InvalidFormatException e) {
				e.printStackTrace();
			}

			sheet = wb.getSheetAt(0);
		}

		if (file.getName().toUpperCase().endsWith("ODS")) {
			try {
				sheet = SpreadSheet.createFromFile(file).getSheet(0);
			} catch (IOException e) {
				sheet = SpreadSheet.createEmpty(null);
				e.printStackTrace();
			}
			
		}
	}

	public int getRowsCount() {
		if (sheet instanceof org.apache.poi.ss.usermodel.Sheet) {
			return ((org.apache.poi.ss.usermodel.Sheet) sheet).getLastRowNum() + 1;
		}
		if (sheet instanceof org.jopendocument.dom.spreadsheet.Sheet) {
			int rowcount = 0;
			String value = null;
			do {
				value =  String.valueOf(((org.jopendocument.dom.spreadsheet.Sheet) sheet).getCellAt(0, rowcount)
				.getValue());
				rowcount ++;
			} while(!value.isEmpty());
			
			return rowcount -1;
		}
		
		return 0;

	}

	public int getColumnsCount() {
		if (sheet instanceof org.apache.poi.ss.usermodel.Sheet) {
			org.apache.poi.ss.usermodel.Row row = ((org.apache.poi.ss.usermodel.Sheet) sheet).getRow(0);
			return row.getLastCellNum();
		}
		if (sheet instanceof org.jopendocument.dom.spreadsheet.Sheet) {
			int colcount = 0;
			String value = null;
			do {
				value =  String.valueOf(((org.jopendocument.dom.spreadsheet.Sheet) sheet).getCellAt(colcount, 0)
				.getValue());
				colcount ++;
			} while(!value.isEmpty());
			
			return colcount -1;
		}
		return 0;
	}

	public String getCellValue(int rowIndex, int columnIndex) {
		if (sheet instanceof org.jopendocument.dom.spreadsheet.Sheet) {
			return String.valueOf( ((org.jopendocument.dom.spreadsheet.Sheet) sheet).getCellAt(columnIndex, rowIndex)
					.getValue());
		}

		if (sheet instanceof org.apache.poi.ss.usermodel.Sheet) {
			org.apache.poi.ss.usermodel.Row row = ((org.apache.poi.ss.usermodel.Sheet) sheet).getRow(rowIndex);
			org.apache.poi.ss.usermodel.Cell cell = row.getCell(columnIndex);

			if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
				return String.valueOf((int)cell.getNumericCellValue());
			}
			if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			}

		}
		return null;

	}
	
    public void exportTable(File file, JTable table) throws IOException {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);
        String groupExport = "";
        for (int i = 0; i < (model.getColumnCount()); i++) {//* disable export from TableHeaders
            groupExport = String.valueOf(model.getColumnName(i));
            out.write(String.valueOf(groupExport) + "\t");
        }
        out.write("\n");
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < (model.getColumnCount()); j++) {
                if (model.getValueAt(i, j) == null) {
                    out.write("null" + "\t");
                } else {
                    groupExport = String.valueOf(model.getValueAt(i, j));
                    out.write(String.valueOf(groupExport) + "\t");
                }
            }
            out.write("\n");
        }
        out.close();
    }
}
