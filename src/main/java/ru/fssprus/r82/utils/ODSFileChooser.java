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
		FileNameExtensionFilter filter = new FileNameExtensionFilter("ODS FILES", "ods", "ods");
		fileChooser.setFileFilter(filter);
		return selectFileOption(fileChooser.showOpenDialog(null), fileChooser, ".ods");
	}

}
