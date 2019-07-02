package ru.fssprus.r82.swing.dialogs;

public abstract class CommonController<T extends CommonDialog> {
	protected T dialog;

	public CommonController(T dialog) {
		this.dialog = dialog;
		if ((dialog instanceof DialogWithPassword) && !(dialog.isAccessGained())) {
			return;
		}
		dialog.init();
		dialog.layoutPanelTop();
		setListeners();
	}

	protected abstract void setListeners();

}
