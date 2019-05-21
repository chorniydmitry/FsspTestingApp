package ru.fssprus.r82.utils;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ODSFileChooser {
	
	private File selectFileOption(int option, JFileChooser fileChooser, String extension) {
		File file = null;

		if (option == JFileChooser.APPROVE_OPTION) {
			if (fileChooser.getSelectedFile().toString().endsWith(extension)
					|| fileChooser.getSelectedFile().toString().endsWith(extension.toUpperCase()))
				file = fileChooser.getSelectedFile();
			else
				file = new File(fileChooser.getSelectedFile() + extension);
		}
		return file;
	}

	public File selectODSFileToOpen() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filterODS = new FileNameExtensionFilter("ODS FILES", "ods", "ods");
		FileNameExtensionFilter filterXSLX = new FileNameExtensionFilter("XSLSX FILES", "xslx", "xlsx");
		fileChooser.addChoosableFileFilter(filterODS);
		fileChooser.addChoosableFileFilter(filterXSLX);
		return selectFileOption(fileChooser.showOpenDialog(null), fileChooser, "");
	}
	

}
