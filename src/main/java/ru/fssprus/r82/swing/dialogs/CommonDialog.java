package ru.fssprus.r82.swing.dialogs;

import java.awt.Dimension;

import javax.swing.JDialog;

public abstract class CommonDialog extends JDialog{
	private static final long serialVersionUID = -933522897356606777L;
	
	public CommonDialog(int width, int height) {
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		
		setVisible(true);
	}
	
	public void init() {
		layoutDialog();
	}
	
	protected abstract void layoutDialog();

}
