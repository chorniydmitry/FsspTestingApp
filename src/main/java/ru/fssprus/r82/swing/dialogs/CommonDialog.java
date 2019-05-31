package ru.fssprus.r82.swing.dialogs;

import java.awt.Dimension;

import javax.swing.JDialog;

public abstract class CommonDialog extends JDialog {
	private static final long serialVersionUID = -933522897356606777L;
	protected boolean accesGained = false;

	public CommonDialog(int width, int height) {
		setSize(new Dimension(width, height));
	}

	protected void loadDialog() {
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);

		setVisible(true);
	}

	public void init() {
		loadDialog();
		layoutDialog();
	}

	protected abstract void layoutDialog();

	protected abstract String getSection();

	protected boolean isAccessGained() {
		return accesGained;
	}

}
