package ru.fssprus.r82.utils.spreadsheet;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Chernyj Dmitry
 *
 */
public class SpreadsheetFileChooser {

	public static File getSelectedFileWithExtension(int option, JFileChooser c) {
		File file = null;
		if (option == JFileChooser.APPROVE_OPTION) {
			file = c.getSelectedFile();
			if (c.getFileFilter() instanceof FileNameExtensionFilter) {
				String[] exts = ((FileNameExtensionFilter) c.getFileFilter()).getExtensions();
				String nameLower = file.getName().toLowerCase();
				for (String ext : exts) { // проверить, имеет ли уже файл нужное расширение
					if (nameLower.endsWith('.' + ext.toLowerCase())) {
						return file; // если да - вернуть "как есть"
					}
				}
				// если нет - вернуть с первым расширением из списка
				file = new File(file.toString() + '.' + exts[0]);
			}
		}
		return file;
	}

	public File selectSpreadSheetFileToOpen() {
		JFileChooser fileChooser = initFileChooser();
		return getSelectedFileWithExtension(fileChooser.showOpenDialog(null), fileChooser);
	}

	public File selectSpreadSheetFileToSave() {
		JFileChooser fileChooser = initFileChooser();
		return getSelectedFileWithExtension(fileChooser.showSaveDialog(null), fileChooser);
	}

	private JFileChooser initFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filterODS = new FileNameExtensionFilter("ODS FILES", "ods", "ods");
		FileNameExtensionFilter filterXSLX = new FileNameExtensionFilter("XSLSX FILES", "xslx", "xlsx");
		fileChooser.addChoosableFileFilter(filterODS);
		fileChooser.addChoosableFileFilter(filterXSLX);
		fileChooser.setAcceptAllFileFilterUsed(false);

		return fileChooser;
	}

}
