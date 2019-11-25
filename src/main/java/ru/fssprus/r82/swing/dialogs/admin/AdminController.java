package ru.fssprus.r82.swing.dialogs.admin;

/**
 * @author Chernyj Dmitry
 *
 */
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
		dialog.dispose();
		DialogBuilder.showConfigDialog();
		
	}

	private void doOpenQuestionLoagingDialog() {
		dialog.dispose();
		DialogBuilder.showQuestionLoadingSetDialog();
		
	}

	private void doOpenQuestionEditDialog() {
		dialog.dispose();
		DialogBuilder.showQuestionListDialog();
		
	}

	private void doOpenManagePasswordsDialog() {
		dialog.dispose();
		DialogBuilder.showPasswordManageDialog();
		
	}
}
