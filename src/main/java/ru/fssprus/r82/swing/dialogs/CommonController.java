package ru.fssprus.r82.swing.dialogs;

import java.awt.event.ActionListener;

public abstract class CommonController<T extends CommonDialog> implements ActionListener {
	protected T dialog;
	public CommonController(T dialog) {
		this.dialog = dialog;
		dialog.init();
		setListeners();
	}
	
	protected abstract void setListeners();
	

}