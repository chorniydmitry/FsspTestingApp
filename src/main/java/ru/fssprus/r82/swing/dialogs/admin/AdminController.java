package ru.fssprus.r82.swing.dialogs.admin;

import ru.fssprus.r82.swing.dialogs.CommonController;
import ru.fssprus.r82.swing.dialogs.DialogBuilder;

public class AdminController extends CommonController<AdminDialog> {

	public AdminController(AdminDialog dialog) {
		super(dialog);
	}

	@Override
	protected void setListeners() {
		dialog.getBtnPasswords().addActionListener(listener -> doOpenManagePasswordsDialog());
		dialog.getBtnQuestionEdit().addActionListener(listener -> doOpenQuestionEditDialog());
		dialog.getBtnQuestionLoad().addActionListener(listener -> doOpenQuestionLoagingDialog());
		dialog.getBtnSettings().addActionListener(listener -> doOpenSettingsDialog());
	}

	private void doOpenSettingsDialog() {
		DialogBuilder.showConfigDialog();
		dialog.dispose();
	}

	private void doOpenQuestionLoagingDialog() {
		DialogBuilder.showQuestionLoadingSetDialog();
		dialog.dispose();
	}

	private void doOpenQuestionEditDialog() {
		DialogBuilder.showQuestionListDialog();
		dialog.dispose();
	}

	private void doOpenManagePasswordsDialog() {
		DialogBuilder.showPasswordManageDialog();
		dialog.dispose();
	}
}
