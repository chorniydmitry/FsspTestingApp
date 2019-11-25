package ru.fssprus.r82.swing.dialogs;

/**
 * @author Chernyj Dmitry
 *
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class CommonController<T extends CommonDialog> {
	protected T dialog;

	public CommonController(T dialog) {
		this.dialog = dialog;
		if ((dialog instanceof DialogWithPassword) && !(dialog.isAccessGained())) {
			return;
		}
		dialog.init();
		dialog.layoutPanelTop();
		setDefaultListeners();
		setListeners();
	}

	protected abstract void setListeners();
	
	private void setDefaultListeners() {
		setDialogMotionListener();
		dialog.getBtnClose().addActionListener(listener -> dialog.dispose());
	}
	
	private void setDialogMotionListener() {
		dialog.getPnlTop().addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				dialog.setLocation(dialog.getLocationOnScreen().x + e.getX(),
						dialog.getLocationOnScreen().y + e.getY());
			}
		});
	}

}
